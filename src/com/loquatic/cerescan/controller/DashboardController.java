package com.loquatic.cerescan.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.api.Listbox;
import org.zkoss.zul.api.Textbox;

import com.loquatic.cerescan.api.entities.Patient;
import com.loquatic.cerescan.api.persistence.managers.PatientQueryManager;
import com.loquatic.cerescan.common.Param;
import com.loquatic.cerescan.view.ErrorLabel;

public class DashboardController extends GenericForwardComposer {
	private static Log log = LogFactory.getLog(DashboardController.class);
	private Textbox searchPatientTb;
	private Button searchPatientBtn;
	private Button mrBtn;
	private Listbox list;
	private ErrorLabel errLbl;
	private Div lastNameResult;
	private List<Patient> patients = new ArrayList<Patient>();
	private Textbox mrTb;

	public List<Patient> getPatients() {
		return patients;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute("controller", this);
	}
	
	public void searchPatientLastName() {
		String lastName = searchPatientTb.getValue();
		List<String> errs = new ArrayList<String>();
		errLbl.setErrs(errs);
		if (lastName.trim().equals("") || lastName.trim().length() < 3) {
			errs.add("Patient's last name to search must be more than 3 characters long.");
			lastNameResult.setVisible(true);
		}

		List<Patient> patientList = (List<Patient>) list.getModel();
		patientList.clear();
		if (errs.isEmpty() == false) {
			errLbl.setErrs(errs);
		} else {
			patients = PatientQueryManager.findByLastName(lastName);
			if (patients.isEmpty()) {
				errs.add("No Patient records found for: " + lastName);
				errLbl.setErrs(errs);
				lastNameResult.setVisible(false);
			} else {
				patientList.addAll(patients);
				lastNameResult.setVisible(true);
			}
		}
	}

	public void searchMrNumber() {
		
		String mrNumber = mrTb.getValue();
		List<String> errs = new ArrayList<String>();
		errLbl.setErrs(errs);
		if (mrNumber.trim().equals("") || mrNumber.trim().length() < 3) {
			errs.add("Patient's MR to search must be more than 3 characters long.");
			lastNameResult.setVisible(true);
		}

		List<Patient> patientList = (List<Patient>) list.getModel();
		patientList.clear();
		if (errs.isEmpty() == false) {
			errLbl.setErrs(errs);
		} else {
			patients = PatientQueryManager.findByMrNumber(mrNumber);
			if (patients.isEmpty()) {
				errs.add("No Patient records found for: " + mrNumber);
				errLbl.setErrs(errs);
				lastNameResult.setVisible(false);
			} else {
				patientList.addAll(patients);
				lastNameResult.setVisible(true);
			}
		}
	}

	public void onOK$mrTb(Event event) {
		searchMrNumber();
	}

	public void onClick$mrBtn(MouseEvent event) {
		searchMrNumber();
	}

	public void onOK$searchPatientTb(Event event) {
		searchPatientLastName();
	}

	public void onClick$searchPatientBtn(MouseEvent event) {
		searchPatientLastName();
	}

	public void onSelect$list(Event event) throws Exception {
		for (Listitem listitem : (Set<Listitem>) list.getSelectedItems()) {
			Patient patient = (Patient) listitem.getValue();
			// storing patient into the session and redirecting
			Sessions.getCurrent().setAttribute("patientId", patient.getId());
			Executions.getCurrent().sendRedirect("/secure/patient/index.zul");
		}
	}
}
