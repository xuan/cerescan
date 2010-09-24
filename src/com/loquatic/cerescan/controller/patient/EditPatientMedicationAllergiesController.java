package com.loquatic.cerescan.controller.patient;

import java.util.ArrayList;
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

import com.loquatic.cerescan.api.entities.Allergy;
import com.loquatic.cerescan.api.entities.Attorney;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.entities.TraumaticBrainInjury;
import com.loquatic.cerescan.api.persistence.managers.CerescanPersistenceManager;
import com.loquatic.cerescan.api.persistence.managers.LookupEntityManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManagerFactory;
import com.loquatic.cerescan.common.Param;
import com.loquatic.cerescan.view.ErrorLabel;

/**
 * $Id:$
 */
public class EditPatientMedicationAllergiesController extends
		GenericForwardComposer {
	private static Log log = LogFactory
			.getLog(EditPatientMedicationAllergiesController.class);
	private SessionInfoManager sessionManager;
	private SessionInfo sessionInfo;
	
	private List<Allergy> allMedicationAllergies = new ArrayList<Allergy>();
	private List<Allergy> allOtherAllergies = new ArrayList<Allergy>();
	private List<Allergy> medicationAllergies = new ArrayList<Allergy>();
	private List<Allergy> otherAllergies = new ArrayList<Allergy>();

	private Label successLbl;
	private ErrorLabel errLbl;
	Textbox medicationAdverseReactionTb, otherAllergyAdverseReactionTb;
	private Listbox medicationAllergiesLb, otherAllergiesLb;
	private Textbox medicationAllergyTb, otherAllergyTb;
	private Button medicationAllergiesDeleteBtn, otherAllergyDeleteBtn;

	public SessionInfoManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionInfoManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public List<Allergy> getAllMedicationAllergies() {
		return allMedicationAllergies;
	}

	public void setAllMedicationAllergies(List<Allergy> allMedicationAllergies) {
		this.allMedicationAllergies = allMedicationAllergies;
	}

	public List<Allergy> getAllOtherAllergies() {
		return allOtherAllergies;
	}

	public void setAllOtherAllergies(List<Allergy> allOtherAllergies) {
		this.allOtherAllergies = allOtherAllergies;
	}

	public List<Allergy> getMedicationAllergies() {
		return this.medicationAllergies;
	}

	public void setMedicationAllergies(List<Allergy> medicationAllergies) {
		this.medicationAllergies = medicationAllergies;
	}

	public List<Allergy> getOtherAllergies() {
		return otherAllergies;
	}

	public void setOtherAllergies(List<Allergy> otherAllergies) {
		this.otherAllergies = otherAllergies;
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
		allMedicationAllergies = LookupEntityManager.getAllMedicinalAllergies();
		allOtherAllergies = LookupEntityManager.getAllOtherAllergies();
		this.medicationAllergies = sessionManager.getMedicineAllergies();
		this.otherAllergies = sessionManager.getOtherAllergies();
	}

	public void onSelect$medicationAllergiesLb(Event event) {
		if (medicationAllergiesLb.getSelectedItems().size() > 0) {
			medicationAllergiesDeleteBtn.setVisible(true);
		} else {
			medicationAllergiesDeleteBtn.setVisible(false);
		}
	}

	public void onSelect$otherAllergiesLb(Event event) {
		if (otherAllergiesLb.getSelectedItems().size() > 0) {
			otherAllergyDeleteBtn.setVisible(true);
		} else {
			otherAllergyDeleteBtn.setVisible(false);
		}
	}

	public void onClick$medicationAllergiesDeleteBtn(MouseEvent event) {
		try {
			String msg = "Are you sure you want to delete the patient with this medication allergy?";
			Messagebox.show(msg, "Prompt", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION, new EventListener() {
						public void onEvent(Event evt) {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								Allergy _all = (Allergy) medicationAllergiesLb
										.getSelectedItem().getValue();
								((List<Allergy>) medicationAllergiesLb
										.getModel()).remove(_all);
								sessionManager.deleteAllergy(_all);
								medicationAllergiesDeleteBtn.setVisible(false);
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

	public void onClick$otherAllergyDeleteBtn(MouseEvent event) {
		try {
			String msg = "Are you sure you want to delete the patient with this other allergy?";
			Messagebox.show(msg, "Prompt", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION, new EventListener() {
						public void onEvent(Event evt) {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								Allergy _all = (Allergy) otherAllergiesLb
										.getSelectedItem().getValue();
								otherAllergies = (List<Allergy>) otherAllergiesLb
										.getModel();
								otherAllergies.remove(_all);
								sessionManager.deleteAllergy(_all);
								//CerescanPersistenceManager.delete(_all);
								otherAllergyDeleteBtn.setVisible(false);
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

	public void onClick$medicationAllergyAddBtn() {
		Allergy _al = new Allergy();
		_al.setDescription(medicationAllergyTb.getValue());
		_al.setAdverseReaction(medicationAdverseReactionTb.getValue());
		medicationAllergyTb.setValue("");
		medicationAdverseReactionTb.setValue("");
		sessionManager.addMedicinalAllergy( _al ) ;
		medicationAllergies = sessionManager.getMedicineAllergies() ;
		((List<Allergy>)medicationAllergiesLb.getModel()).clear() ;
		((List<Allergy>)medicationAllergiesLb.getModel()).addAll( medicationAllergies ) ;
		successLbl.setValue("Medication Allergies have been added.");
	}

	public void onClick$otherAllergyAddBtn() {
		Allergy _al = new Allergy();
		_al.setDescription(otherAllergyTb.getValue());
		_al.setAdverseReaction(otherAllergyAdverseReactionTb.getValue());
		otherAllergyTb.setValue("");
		otherAllergyAdverseReactionTb.setValue("");
		sessionManager.addOtherAllergy( _al ) ;
		otherAllergies = sessionManager.getOtherAllergies() ;
		((List<Allergy>)otherAllergiesLb.getModel()).clear() ;
		((List<Allergy>)otherAllergiesLb.getModel()).addAll( otherAllergies ) ;
		successLbl.setValue("Allergies have been added.");
		
	}
}
