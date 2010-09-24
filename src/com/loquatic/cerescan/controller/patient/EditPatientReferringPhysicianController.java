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
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import com.loquatic.cerescan.api.entities.Address;
import com.loquatic.cerescan.api.entities.Physician;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.persistence.managers.PhysicanManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManagerFactory;
import com.loquatic.cerescan.common.Param;
import com.loquatic.cerescan.view.ErrorLabel;

public class EditPatientReferringPhysicianController extends
		GenericForwardComposer {
	private static Log log = LogFactory
			.getLog(EditPatientReferringPhysicianController.class);
	private SessionInfo sessionInfo;
	private SessionInfoManager sessionManager;
	private Physician physician = new Physician();
	private List<Physician> selectedPhysicians;
	private Address address = new Address();
	private List<Physician> physicians;
	private Textbox searchLastNameTb;
	private Div searchResult;
	private Listbox searchResultLb, selectedPhysiciansLb;
	private ErrorLabel errLbl;
	private Label lastNameLb, firstNameLb, firmNameLb, phoneLb, faxLb, emailLb,
			street1Lb, street2Lb, cityLb, zipCodeLb, cityCodeLb, stateLb,
			successLbl;
	private Button deleteBtn;

	public Physician getPhysician() {
		return physician;
	}

	public void setPhysician(Physician physician) {
		this.physician = physician;
	}

	public List<Physician> getSelectedPhysicians() {
		return selectedPhysicians;
	}

	public void setSelectedPhysicians(List<Physician> selectedPhysicians) {
		this.selectedPhysicians = selectedPhysicians;
	}

	public List<Physician> getAttornies() {
		return physicians;
	}

	public Address getAddress() {
		return address;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute("controller", this);
		Map<String, SessionInfo> args = Executions.getCurrent().getArg();
		SessionInfo _ses = args.get(Param.SESSION);
		sessionManager = SessionInfoManagerFactory.getInstance().getSession(
				_ses.getId());
		this.sessionInfo = sessionManager.getSessionInfo();
		selectedPhysicians = sessionInfo.getReferringPhysicians();
	}

	public void lastNameSearch() {
		if (searchLastNameTb.getValue().length() < 3) {
			errLbl
					.setValue("Please enter at least 3 characters when searching");
			searchResult.setVisible(false);
		} else {
			errLbl.setValue("");
			this.physicians = PhysicanManager.searchByLastName(searchLastNameTb
					.getValue());
			if (physicians != null && physicians.size() > 0) {
				List<Physician> physicianList = (List<Physician>) searchResultLb
						.getModel();
				physicianList.clear();
				physicianList.addAll(physicians);
				searchResult.setVisible(true);
			} else {
				errLbl.setValue("No Physician Found for: "
						+ searchLastNameTb.getValue());
				searchResult.setVisible(false);
			}
		}
	}

	public void save() {
		sessionInfo
				.setReferringPhysicians((List<Physician>) selectedPhysiciansLb
						.getModel());
		sessionManager.save(sessionInfo);
		successLbl.setValue("Physician has been added to patient's file");
	}

	public void onSelect$searchResultLb(Event event) {
		Physician _att = (Physician) searchResultLb.getSelectedItem()
				.getValue();
		List<Physician> physicianList = (List<Physician>) selectedPhysiciansLb
				.getModel();
		if (physicianList.contains(_att) == false) {
			physicianList.add(_att);
		}
		save();
	}

	public void onSelect$selectedPhysiciansLb(Event event) {
		if (selectedPhysiciansLb.getSelectedItems().size() > 0) {
			deleteBtn.setVisible(true);
		} else {
			deleteBtn.setVisible(false);
		}
	}

	public void onClick$deleteBtn(MouseEvent event) {
		try {
			String msg = "Do you want to delete this physican from the patient's file?";
			Messagebox.show(msg, "Prompt", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION, new EventListener() {
						public void onEvent(Event evt) {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								physician = (Physician) selectedPhysiciansLb
										.getSelectedItem().getValue();
								((List<Physician>) selectedPhysiciansLb
										.getModel()).remove(physician);
								save();
								deleteBtn.setVisible(false);
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

	public void onClick$searchLastNameBtn(MouseEvent event) {
		lastNameSearch();
	}

}
