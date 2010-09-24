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
import com.loquatic.cerescan.api.entities.Attorney;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.persistence.managers.AttorneyManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManagerFactory;
import com.loquatic.cerescan.common.Param;
import com.loquatic.cerescan.view.ErrorLabel;

public class EditPatientReferringAttorneyController extends
		GenericForwardComposer {
	private static Log log = LogFactory
			.getLog(EditPatientReferringAttorneyController.class);
	private SessionInfo sessionInfo;
	private SessionInfoManager sessionManager;
	private Attorney attorney = new Attorney();
	private List<Attorney> selectedAttorneys;
	private Address address = new Address();
	private List<Attorney> attornies;
	private Textbox searchLastNameTb;
	private Div searchResult;
	private Listbox searchResultLb, selectedAttorneysLb;
	private ErrorLabel errLbl;
	private Label lastNameLb, firstNameLb, firmNameLb, phoneLb, faxLb, emailLb,
			street1Lb, street2Lb, cityLb, zipCodeLb, cityCodeLb, stateLb,
			successLbl;
	private Button deleteBtn;

	public Attorney getAttorney() {
		return attorney;
	}

	public void setAttorney(Attorney attorney) {
		this.attorney = attorney;
	}

	public List<Attorney> getSelectedAttorneys() {
		return selectedAttorneys;
	}

	public void setSelectedAttorneys(List<Attorney> selectedAttorneys) {
		this.selectedAttorneys = selectedAttorneys;
	}

	public List<Attorney> getAttornies() {
		return attornies;
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
		selectedAttorneys = sessionInfo.getReferringAttorney();
	}

	public void lastNameSearch() {
		successLbl.setValue("");
		errLbl.setValue("");
		if (searchLastNameTb.getValue().length() < 3) {
			errLbl
					.setValue("Please enter at least 3 characters when searching");
			searchResult.setVisible(false);
		} else {
			errLbl.setValue("");
			this.attornies = AttorneyManager.searchByLastName(searchLastNameTb
					.getValue());
			if (attornies != null && attornies.size() > 0) {
				List<Attorney> attorneyList = (List<Attorney>) searchResultLb
						.getModel();
				attorneyList.clear();
				attorneyList.addAll(attornies);
				searchResult.setVisible(true);
			} else {
				errLbl.setValue("No Attorney Found for: "
						+ searchLastNameTb.getValue());
				searchResult.setVisible(false);
			}
		}
	}

	public void save() {
		sessionInfo.setReferringAttorney((List<Attorney>) selectedAttorneysLb
				.getModel());
		sessionManager.save(sessionInfo);
		successLbl.setValue("Attorney has been saved.");
	}

	public void onSelect$searchResultLb(Event event) {
		Attorney _att = (Attorney) searchResultLb.getSelectedItem().getValue();
		List<Attorney> attorneyList = (List<Attorney>) selectedAttorneysLb
				.getModel();
		if (attorneyList.contains(_att) == false) {
			attorneyList.add(_att);
		}
		save();
	}

	public void onSelect$selectedAttorneysLb(Event event) {
		if (selectedAttorneysLb.getSelectedItems().size() > 0) {
			deleteBtn.setVisible(true);
		} else {
			deleteBtn.setVisible(false);
		}
	}

	public void onClick$deleteBtn(MouseEvent event) {
		try {
			String msg = "Do you want to delete this attorney from the patient's file?";
			Messagebox.show(msg, "Prompt", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION, new EventListener() {
						public void onEvent(Event evt) {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								attorney = (Attorney) selectedAttorneysLb
										.getSelectedItem().getValue();
								((List<Attorney>) selectedAttorneysLb
										.getModel()).remove(attorney);
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
		successLbl.setValue("");
		errLbl.setValue("");
		lastNameSearch();
	}

}
