package com.loquatic.cerescan.api.persistence.managers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.loquatic.cerescan.api.entities.Patient;
import com.loquatic.cerescan.controller.patient.CreateNewPatientController;

/**
 * This is a utility class that encapsulates querying for Patient objects.
 * Patient is essentially the root of all the data.
 * 
 * @author jonsvede
 * 
 */
public class PatientQueryManager extends CerescanPersistenceManager {
	private static Log log = LogFactory.getLog(PatientQueryManager.class);

	public static List<Patient> findByLastName(String lastName) {

		List<Patient> patients = new ArrayList<Patient>();
		try {
			patients = genericOneParamFinder("Patient.findByLastName",
					"lastName", lastName + "%");
		} catch (javax.persistence.NoResultException e) {
			// TODO Auto-generated catch block
			log.debug("No patient found for lastName " + lastName);
		}

		return patients;
	}

	public static List<Patient> findByFirstName(String firstName) {
		List<Patient> patients = new ArrayList<Patient>();
		try {
			patients = genericOneParamFinder("Patient.findByFirstName",
					"firstName", firstName);
		} catch (javax.persistence.NoResultException e) {
			// TODO Auto-generated catch block
			log.debug("No patient found for firstName " + firstName);
		}

		return patients;
	}

	public static List<Patient> findByMrNumber(String mrNumber) {
		List<Patient> patients = new ArrayList<Patient>();
		try {
			patients = genericOneParamFinder("Patient.findByMrNumber",
					"mrNumber", mrNumber + "%");
		} catch (javax.persistence.NoResultException e) {
			// TODO Auto-generated catch block
			log.debug("No patient found for MR# " + mrNumber);
		}

		return patients;
	}

	public static Patient findByUniqueMrNumber(String mrNumber) {
		Patient p = null;
		try {
			p = (Patient) genericOneParamSingleResultFinder(
					"Patient.findByMrNumber", "mrNumber", mrNumber);
		} catch (javax.persistence.NoResultException e) {
			log.debug("No patient found for MR# " + mrNumber);
		}
		return p;
	}

	public static Patient findById(long id) {
		Patient p = null;
		try {
			p = (Patient) findById("Patient.findById", id);
		} catch (javax.persistence.NoResultException e) {
			log.debug("No patient found for id " + id);
		}
		return p;
	}

	public static List<Patient> findAll() {
		List<Patient> patients = new ArrayList<Patient>();
		try {
			patients = genericFindAll("Patient.findAll");
		} catch (javax.persistence.NoResultException e) {
			log.error(e);
		}
		return patients;
	}

}
