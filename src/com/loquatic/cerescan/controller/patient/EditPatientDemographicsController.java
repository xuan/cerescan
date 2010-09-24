package com.loquatic.cerescan.controller.patient;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;

import com.loquatic.cerescan.api.entities.Address;
import com.loquatic.cerescan.api.entities.Patient;
import com.loquatic.cerescan.api.persistence.managers.CerescanPersistenceManager;
import com.loquatic.cerescan.api.persistence.managers.PatientQueryManager;
import com.loquatic.cerescan.common.Param;
import com.loquatic.cerescan.view.ErrorLabel;

public class EditPatientDemographicsController extends GenericForwardComposer {
	private static Log log = LogFactory
			.getLog(EditPatientDemographicsController.class);
	private Patient patient;
	private ErrorLabel errLbl;
	private Label successLbl;
	// address
	private Address address = new Address();

	public Address getAddress() {
		return address;
	}

	public Patient getPatient() {
		return patient;
	}

	public boolean isValid() {
		List<String> errs = new ArrayList<String>();
		if (patient.getLastName() == null
				|| patient.getLastName().trim().equals("")) {
			errs.add("Please Enter the Patient's Last name");
		}
		if (patient.getFirstName() == null
				|| patient.getFirstName().trim().equals("")) {
			errs.add("Please Enter the Patient's First name");
		}
		if (patient.getMrNumber() == null
				|| patient.getMrNumber().trim().equals("")) {
			errs.add("Please Enter the Patient's Medical Record Number (MR#)");
		}
		if (patient.getGender() == null
				|| patient.getGender().trim().equals("")) {
			errs.add("Please Enter the Patient's Gender");
		}
		if (patient.getDateOfBirth() == null) {
			errs.add("Please Enter the Patient's Date of Birth (DOB)");
		}
		// check to see if MR already exist for another patient
		Patient _patient = PatientQueryManager.findByUniqueMrNumber(patient
				.getMrNumber());
		if (_patient != null && (_patient.getId() != patient.getId())) {
			errs.add("Patient MR# " + patient.getMrNumber() + " already exist");
		}

		if (errs.isEmpty() == false) {
			errLbl.setVisible(true);
			errLbl.setErrs(errs);
		} else {
			errLbl.setVisible(false);
		}
		return errs.isEmpty();
	}

	public void persistPatient() {
		if (isValid()) {
			List<Address> _addresses = patient.getAddresses();
			if (_addresses != null && _addresses.isEmpty() == false) {
				for (Address _adr : _addresses) {
					if (_adr.isCurrentAddress()) {
						_adr = address;
					}
				}
			} else {
				address.setAsCurrentAddress(true);
				patient.addAddress(address);
			}
			CerescanPersistenceManager.merge(patient);
			successLbl.setValue("Successfully Updated Patient Information: " + patient.getLastName());
			successLbl.setVisible(true);
		}
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute("controller", this); // make models visible for
		// the databinder
		long patientId = (Long) Sessions.getCurrent().getAttribute(
				Param.PATIENT_ID);
		patient = PatientQueryManager.findById(patientId);
		List<Address> _addresses = patient.getAddresses();
		if (_addresses != null) {
			for (Address _adr : _addresses) {
				if (_adr.isCurrentAddress()) {
					address = _adr;
				}
			}
		}
	}

	public void onClick$updateBtn(MouseEvent event) {
		persistPatient();
	}

	public void onOK() {
		persistPatient();
	}
}
