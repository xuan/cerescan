package com.loquatic.cerescan.controller.patient;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.api.Textbox;

import com.loquatic.cerescan.api.persistence.managers.PatientManagerFactory;
import com.loquatic.cerescan.api.persistence.managers.PatientQueryManager;
import com.loquatic.cerescan.common.Param;
import com.loquatic.cerescan.view.ErrorLabel;

public class CreateNewPatientController extends GenericForwardComposer {
	private static Log log = LogFactory
			.getLog(CreateNewPatientController.class);
	private Textbox lastNameTb;
	private Textbox firstNameTb;
	private Textbox mrTb;
	private ErrorLabel errLbl;

	public boolean isValid() {
		String lastName = lastNameTb.getValue();
		String firstName = firstNameTb.getValue();
		String mrNumber = mrTb.getValue();
		List<String> errs = new ArrayList<String>();
		if (lastName.trim().equals("")) {
			errs.add("Please Enter the Patient's Last name");
		}
		if (firstName.trim().equals("")) {
			errs.add("Please Enter the Patient's First name");
		}
		if (mrNumber.trim().equals("")) {
			errs.add("Please Enter the Patient's Medical Record Number (MR#)");
		}
		// check to see if MR already exist
		if (PatientQueryManager.findByUniqueMrNumber(mrNumber) != null) {
			errs.add("Patient MR# " + mrNumber + " already exist");
		}

		if (errs.isEmpty() == false) {
			errLbl.setErrs(errs);
		}
		return errs.isEmpty();
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute("controller", this);
	}

	public void createPatient() {
		String lastName = lastNameTb.getValue();
		String firstName = firstNameTb.getValue();
		String mrNumber = mrTb.getValue();

		if (isValid()) {
			Long id = PatientManagerFactory.getInstance().createPatient(
					firstName, lastName, mrNumber).getPatient().getId();
			Sessions.getCurrent().setAttribute(Param.PATIENT_ID, id);
			Executions.getCurrent().sendRedirect("/secure/patient/index.zul");
		}
	}

	public void onOK() {
		createPatient();
	}

	public void onClick$createBtn(MouseEvent event) {
		createPatient();
	}
}
