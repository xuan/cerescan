package com.loquatic.cerescan.controller.patient;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import com.loquatic.cerescan.api.entities.Medication;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.entities.lookups.Dosage;
import com.loquatic.cerescan.api.entities.lookups.Effective;
import com.loquatic.cerescan.api.entities.lookups.Medicine;
import com.loquatic.cerescan.api.entities.lookups.Schedule;
import com.loquatic.cerescan.api.persistence.managers.LookupEntityManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManagerFactory;
import com.loquatic.cerescan.common.Param;
import com.loquatic.cerescan.view.ErrorLabel;

public class EditPatientMedicationController extends GenericForwardComposer {
	private static Log log = LogFactory
			.getLog(EditPatientMedicationController.class);
	private Label successLbl;
	private ErrorLabel errLbl;
	private SessionInfoManager sessionManager;
	private SessionInfo sessionInfo;

	private List<Medicine> allMedicines;
	private List<Dosage> allDosage;
	private List<Schedule> allSchedule;
	private List<Effective> allEffective;

	private List<Medication> currentMedications;
	private Medication selectedCurrentMedication;

	private List<Medication> pastMedications;
	private Medication selectedPastMedication;
	private Combobox currentMedicineCb, pastMedicineCb;
	private Listbox currentMedicationsLb, pastMedicationsLb, currentDosageLb,
			currentScheduleLb, pastDosageLb, pastScheduleLb;
	private Listbox pastEffectiveLb;
	private Textbox adverseReactionTb, currentStrengthTb, pastStrengthTb;
	private Button currentMedicationsDeleteBtn, pastMedicationsDeleteBtn;

	public List<Medicine> getAllMedicines() {
		return allMedicines;
	}

	public List<Dosage> getAllDosage() {
		return allDosage;
	}

	public List<Schedule> getAllSchedule() {
		return allSchedule;
	}

	public List<Effective> getAllEffective() {
		return allEffective;
	}

	public List<Medication> getCurrentMedications() {
		return currentMedications;
	}

	public Medication getSelectedCurrentMedication() {
		return selectedCurrentMedication;
	}

	public List<Medication> getPastMedications() {
		return pastMedications;
	}

	public Medication getSelectedPastMedication() {
		return selectedPastMedication;
	}

	private void updateLookups() {
		this.allMedicines = LookupEntityManager.getAllMedicines();
		this.allDosage = LookupEntityManager.getAllDosages();
		this.allSchedule = LookupEntityManager.getAllSchedules();
		this.allEffective = LookupEntityManager.getAllEffectives();
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute("controller", this);

		Map<String, SessionInfo> args = Executions.getCurrent().getArg();
		SessionInfo _ses = args.get(Param.SESSION);
		sessionManager = SessionInfoManagerFactory.getInstance().getSession(
				_ses.getId());
		sessionInfo = sessionManager.getSessionInfo();
		this.currentMedications = sessionManager.getCurrentMedication();
		this.pastMedications = sessionManager.getPastMedication();
		clearCurrent();
		clearPast();
		updateLookups();
	}

	public void onSelect$currentMedicationsLb(Event event) {
		if (currentMedicationsLb.getSelectedItems().size() > 0) {
			currentMedicationsDeleteBtn.setVisible(true);
		} else {
			currentMedicationsDeleteBtn.setVisible(false);
		}
	}

	public void onSelect$pastMedicationsLb(Event event) {
		if (pastMedicationsLb.getSelectedItems().size() > 0) {
			pastMedicationsDeleteBtn.setVisible(true);
		} else {
			pastMedicationsDeleteBtn.setVisible(false);
		}
	}

	public void onClick$currentMedicationsDeleteBtn(MouseEvent event) {
		try {
			String msg = "Are you sure you want to disassociate the patient with this Current Medication?";
			Messagebox.show(msg, "Prompt", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION, new EventListener() {
						public void onEvent(Event evt) {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								Medication _med = (Medication) currentMedicationsLb
										.getSelectedItem().getValue();
								((List<Medication>) currentMedicationsLb
										.getModel()).remove(_med);
								sessionManager.deleteMedication(_med);

								currentMedicationsDeleteBtn.setVisible(false);

								errLbl.reset();
								successLbl
										.setValue("Medications has been saved.");
								break;
							case Messagebox.NO:
								break;
							}
						}
					});

		} catch (InterruptedException e) {
			log.error(e);
		}
	}

	public void onClick$pastMedicationsDeleteBtn(MouseEvent event) {
		try {
			String msg = "Are you sure you want to disassociate the patient with this Past Medication?";
			Messagebox.show(msg, "Prompt", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION, new EventListener() {
						public void onEvent(Event evt) {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								Medication _med = (Medication) pastMedicationsLb
										.getSelectedItem().getValue();
								((List<Medication>) pastMedicationsLb
										.getModel()).remove(_med);
								sessionManager.deleteMedication(_med);

								pastMedicationsDeleteBtn.setVisible(false);
								errLbl.reset();
								successLbl
										.setValue("Medications has been saved.");
								break;
							case Messagebox.NO:
								break;
							}
						}
					});

		} catch (InterruptedException e) {
			log.error(e);
		}
	}

	public void onClick$currentAddBtn(MouseEvent event) {
		if (isCurrentValid()) {
			Dosage dosage = (Dosage) currentDosageLb.getSelectedItem()
					.getValue();
			Schedule schedule = (Schedule) currentScheduleLb.getSelectedItem()
					.getValue();
			String strength = currentStrengthTb.getValue();

			Medicine medicine = sessionManager.getMedicine(currentMedicineCb
					.getValue());
			if (medicine != null) {
				Medication _med = new Medication();
				_med.setDosage(dosage);
				_med.setSchedule(schedule);
				_med.setStrength(strength);
				_med.setMedicine(medicine);
				List<Medication> currentMed = (List<Medication>) currentMedicationsLb
						.getModel();
				currentMed.clear();
				currentMed.addAll(sessionManager.addCurrentMedication(_med));
			} else {
				errLbl.setValue("The value entered for Current Medication does not exist. Please select a valid medication.");
			}

			clearCurrent();
		}
	}

	public void onClick$pastAddBtn(MouseEvent event) {
		if (isPastValid()) {
			Dosage dosage = (Dosage) pastDosageLb.getSelectedItem()
					.getValue();
			Schedule schedule = (Schedule) pastScheduleLb.getSelectedItem()
					.getValue();
			String strength = pastStrengthTb.getValue();

			Medicine medicine = sessionManager.getMedicine(pastMedicineCb
					.getValue());
			Effective effective = (Effective) pastEffectiveLb.getSelectedItem().getValue();
			if (medicine != null) {
				Medication _med = new Medication();
				_med.setDosage(dosage);
				_med.setSchedule(schedule);
				_med.setStrength(strength);
				_med.setMedicine(medicine);
				_med.setEffective(effective);
				_med.setAdverseReaction(adverseReactionTb.getValue());
				List<Medication> pastMed = (List<Medication>) pastMedicationsLb
						.getModel();
				pastMed.clear();
				pastMed.addAll(sessionManager.addPastMedication(_med));
			} else {
				errLbl.setValue("The value entered for Past Medication does not exist. Please select a valid medication.");
			}

			clearPast();
		}
	}

	private void clearCurrent() {
		pastEffectiveLb.setSelectedIndex(0);
		currentDosageLb.setSelectedIndex(0);
		currentScheduleLb.setSelectedIndex(0);
		currentMedicineCb.setValue("");
		currentStrengthTb.setValue("");
	}

	private void clearPast() {
		pastMedicineCb.setValue("");
		pastDosageLb.setSelectedIndex(0);
		pastScheduleLb.setSelectedIndex(0);
		adverseReactionTb.setValue("");
		pastStrengthTb.setValue("");
	}

	private boolean isCurrentValid() {
		successLbl.setValue("");
		errLbl.reset();

		if (currentMedicineCb.getValue().isEmpty()) {
			errLbl.addErr("Current Medicine cannot be empty.");
		}
		errLbl.refresh();

		if (errLbl.getErrs().size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	private boolean isPastValid() {
		successLbl.setValue("");
		errLbl.reset();

		if (pastMedicineCb.getValue().isEmpty()) {
			errLbl.addErr("Past Medicine cannot be empty.");
		}
		errLbl.refresh();

		if (errLbl.getErrs().size() > 0) {
			return false;
		} else {
			return true;
		}
	}
}
