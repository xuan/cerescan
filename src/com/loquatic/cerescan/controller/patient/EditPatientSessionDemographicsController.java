package com.loquatic.cerescan.controller.patient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.West;

import com.loquatic.cerescan.api.entities.Patient;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.entities.lookups.ClinicalSite;
import com.loquatic.cerescan.api.entities.lookups.EducationLevel;
import com.loquatic.cerescan.api.entities.lookups.EmploymentStatus;
import com.loquatic.cerescan.api.entities.lookups.Ethnicity;
import com.loquatic.cerescan.api.entities.lookups.InjuryType;
import com.loquatic.cerescan.api.entities.lookups.RelationshipStatus;
import com.loquatic.cerescan.api.entities.lookups.VeteranStatus;
import com.loquatic.cerescan.api.persistence.managers.LookupEntityManager;
import com.loquatic.cerescan.api.persistence.managers.PatientQueryManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManagerFactory;
import com.loquatic.cerescan.common.Param;

public class EditPatientSessionDemographicsController extends
		GenericForwardComposer {
	private static Log log = LogFactory
			.getLog(EditPatientSessionDemographicsController.class);
	private Label successLbl;
	private Button btn;
	private SessionInfo sessionInfo = new SessionInfo();
	private List<ClinicalSite> allClinicalSites;
	private List<EmploymentStatus> allEmploymentStatus;
	private List<EducationLevel> allEducationLevels;
	private List<Ethnicity> allEthnicities;
	private List<RelationshipStatus> allRelationshipStatuses;
	private List<InjuryType> allInjuryTypes;
	private HashSet<VeteranStatus> allVeteranStatus;
	private HashSet<VeteranStatus> selectedVeteranStatus;
	private HashSet<ClinicalSite> selectedClinicalSites = new HashSet<ClinicalSite>();

	private Row combatHistoryRw;
	private Row forensicRw;
	private Textbox combatDateTb, combatLocationTb, injuryDescriptionTb,
			caseNumberTb;
	private Listbox clinicalSiteLb, veteranStatusLb, injuryTypeLb;
	private Radiogroup forensicRg;

	private SessionInfoManager sessionManager;

	public HashSet<ClinicalSite> getSelectedClinicalSites() {
		return selectedClinicalSites;
	}

	public void setSelectedClinicalSites(
			HashSet<ClinicalSite> selectedClinicalSites) {
		this.selectedClinicalSites = selectedClinicalSites;
	}

	public List<ClinicalSite> getAllClinicalSites() {
		return allClinicalSites;
	}

	public List<EmploymentStatus> getAllEmploymentStatus() {
		return allEmploymentStatus;
	}

	public List<EducationLevel> getAllEducationLevels() {
		return allEducationLevels;
	}

	public List<Ethnicity> getAllEthnicities() {
		return allEthnicities;
	}

	public List<RelationshipStatus> getAllRelationshipStatuses() {
		return allRelationshipStatuses;
	}

	public List<InjuryType> getAllInjuryTypes() {
		return allInjuryTypes;
	}

	public SessionInfo getSessionInfo() {
		return sessionInfo;
	}

	public void setSessionInfo(SessionInfo sessionInfo) {
		this.sessionInfo = sessionInfo;
	}

	public HashSet<VeteranStatus> getAllVeteranStatus() {
		return allVeteranStatus;
	}

	public void setAllVeteranStatus(HashSet<VeteranStatus> allVeteranStatus) {
		this.allVeteranStatus = allVeteranStatus;
	}

	public HashSet<VeteranStatus> getSelectedVeteranStatus() {
		return selectedVeteranStatus;
	}

	public void setSelectedVeteranStatus(
			HashSet<VeteranStatus> selectedVeteranStatus) {
		this.selectedVeteranStatus = selectedVeteranStatus;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute("controller", this);

		// Get the session passed in from the navigation
		Map<String, SessionInfo> args = Executions.getCurrent().getArg();
		this.sessionInfo = args.get(Param.SESSION);
		LookupEntityManager.getAllVeteranStatuses();
		if (sessionInfo != null) { // Update View
			sessionManager = SessionInfoManagerFactory.getInstance()
					.getSession(sessionInfo.getId());
			sessionInfo = sessionManager.getSessionInfo();
			selectedClinicalSites = new HashSet(sessionInfo.getClinicalSites());
			toggleUpdateView();
		} else { // New Session
			sessionInfo = new SessionInfo();
			toggleAddView();
		}
		populateSelections();

	}

	private void populateSelections() {
		this.allClinicalSites = LookupEntityManager.getAllClinicalSites();
		allInjuryTypes = LookupEntityManager.getAllInjuryTypes();

		this.allRelationshipStatuses = LookupEntityManager
				.getAllRelationshipStatuses();
		this.allEthnicities = LookupEntityManager.getAllEthnicities();
		this.allEducationLevels = LookupEntityManager.getAllEducationLevels();
		this.allEmploymentStatus = LookupEntityManager.getAllEmploymentStatus();
		this.allVeteranStatus = new HashSet(LookupEntityManager
				.getAllVeteranStatuses());
	}

	private void toggleAddView() {
		combatHistoryRw.setVisible(false);
		forensicRw.setVisible(false);
		btn.setLabel("Add");
	}

	public void toggleUpdateView() {
		if (sessionInfo.getClinicalSites() != null) {
			selectedClinicalSites = new HashSet<ClinicalSite>(sessionInfo
					.getClinicalSites());
		}

		if ("F".equals(sessionInfo.getForensicPatient())) {
			forensicRw.setVisible(true);
		} else {
			forensicRw.setVisible(false);
			injuryTypeLb.setSelectedIndex(0);
			injuryDescriptionTb.setValue("");
			caseNumberTb.setValue("");
			sessionInfo.setInjuryDescription("");
			sessionInfo.setCaseNumber("");
			sessionInfo.setInjuryType(null);
			
		}
		toggleVeteranStatus();
		btn.setLabel("Update");
	}

	public void onSelect$clinicalSiteLb() {
		selectedClinicalSites = new HashSet<ClinicalSite>();
		Set<Listitem> items = clinicalSiteLb.getSelectedItems();
		for (Listitem item : items) {
			ClinicalSite site = (ClinicalSite) item.getValue();
			selectedClinicalSites.add(site);
		}
	}

	public void onSelect$veteranStatusLb() {
		toggleVeteranStatus();
	}

	private void toggleVeteranStatus() {
		VeteranStatus vs = sessionInfo.getVeteranStatus();
		if (vs != null && (vs.getName().equals("Active")||vs.getName().equals("Reserves")||vs.getName().equals("Retired"))) {
			combatHistoryRw.setVisible(true);
		} else {
			combatHistoryRw.setVisible(false);
			combatDateTb.setValue("");
			combatLocationTb.setValue("");
			sessionInfo.setCombatDate(null);
			sessionInfo.setCombatLocation(null);
		}
	}

	public void onClick$btn() {
		toggleVeteranStatus();
		if (sessionInfo.getId() != 0) {// update existing session
			sessionInfo.setClinicalSites(new ArrayList<ClinicalSite>(
					selectedClinicalSites));
			sessionManager.save(sessionInfo);
		} else {// create new session

			Long patientId = (Long) Executions.getCurrent().getSession()
					.getAttribute(Param.PATIENT_ID);
			Patient patient = PatientQueryManager.findById(patientId);

			sessionManager = SessionInfoManagerFactory.getInstance()
					.saveNewSession(patient, sessionInfo);

			sessionInfo = sessionManager.getSessionInfo();
			sessionInfo.setClinicalSites(new ArrayList<ClinicalSite>(
					selectedClinicalSites));
			sessionManager.save(sessionInfo);
			sessionInfo = sessionManager.getSessionInfo();
			log.debug(sessionInfo);
		}

		refreshNav();
		successLbl.setValue("Session has been saved");
		toggleUpdateView();
	}

	public void refreshNav() {
		Borderlayout bl = (Borderlayout) Path.getComponent("/patientBl");
		/* get an instance of the searched CENTER layout area */
		West west = bl.getWest();
		/* clear the center child comps */
		west.getChildren().clear();
		Executions
				.createComponents("/secure/common/navigation.zul", west, null);
	}
}
