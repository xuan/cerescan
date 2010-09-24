package com.loquatic.cerescan.controller;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import com.loquatic.cerescan.api.entities.Patient;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.persistence.managers.PatientQueryManager;
import com.loquatic.cerescan.common.Param;

/**
 * $Id$
 */
public class NavigationController extends GenericForwardComposer {
	private static Log log = LogFactory.getLog(NavigationController.class);
	private Tree tree;
	private static final String PATIENT_SESSDEMO_URL = "/secure/patient/editPatientSessionDemographics.zul";
	private static final String PATIENT_REF_ATTORNEY_URL = "/secure/patient/editPatientReferringAttorney.zul";
	private static final String PATIENT_REF_PHYSICIAN_URL = "/secure/patient/editPatientReferringPhysician.zul";
	private static final String OTHER_TREATMENT_PROV_URL = "/secure/patient/editPatientOtherTreatmentProvider.zul";
	private static final String PATIENT_DEMO_URL = "/secure/patient/editPatientDemographics.zul";
	private static final String TRAUMATIC_BRAIN_INJURY_URL = "/secure/patient/editPatientTraumaticBrainInjury.zul";
	private static final String SYMPTOM_URL = "/secure/patient/editPatientSymptom.zul";
	private static final String INCOMING_DIAGNOSES_URL = "/secure/patient/editPatientIncomingDiagnoses.zul";
	private static final String MED_ALLERGIES_URL = "/secure/patient/editPatientMedicationAllergies.zul";
	private static final String MEDICATION_URL = "/secure/patient/editPatientMedication.zul";
	private static final String HOSP_URL = "/secure/patient/editPatientHospitalization.zul";
	private static final String OTHER_IMAGING_URL = "/secure/patient/editPatientOtherImaging.zul";
	private static final String SUPSTANCE_USE_URL = "/secure/patient/editPatientSubstanceUse.zul";
	private static final String DEV_HIST_URL = "/secure/patient/editPatientDevelopmentHistory.zul";
	private static final String PSYCH_ASSESS_URL = "/secure/patient/editPatientPsychAssess.zul";
	private static final String FAM_HIST_URL = "/secure/patient/editPatientFamilyHistory.zul";
	private static final String SPECT_IMG_URL = "/secure/patient/editPatientSpectImaging.zul";
	private static final String OUTGOING_DIAGNSES_URL = "/secure/patient/editPatientOutgoingDiagnoses.zul";
	private static final String SES_NOTE_URL = "/secure/patient/editPatientSessionNote.zul";
	private static final String SCANNED_FORM_URL = "/secure/patient/editPatientScannedForm.zul";

	public void onSelect$tree() {
		Treeitem selectedItem = tree.getSelectedItem();
		Nav nav = (Nav) selectedItem.getAttribute("nav");
		if (nav != null) {
			/* get an instance of the borderlayout defined in the index.zul-file */
			Borderlayout bl = (Borderlayout) Path.getComponent("/patientBl");
			/* get an instance of the searched CENTER layout area */

			Center center = bl.getCenter();
			/* clear the center child comps */
			center.getChildren().clear();
			center.setAutoscroll(true);
			/*
			 * create the page and put it in the center layout area
			 */
			Map<String, SessionInfo> sessionParam = null;
			if (nav.getSession() != null) {
				sessionParam = new HashMap<String, SessionInfo>();
				sessionParam.put(Param.SESSION, nav.getSession());
			}
			if (nav.isFullRefresh()) {
				Executions.getCurrent().sendRedirect(nav.getUrl());
			} else {
				Executions.createComponents(nav.getUrl(), center, sessionParam);

			}
		}
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute("controller", this);

		Treechildren root = new Treechildren();
		Treechildren sessionChildren = new Treechildren();
		Patient p = PatientQueryManager.findById((Long) Sessions.getCurrent()
				.getAttribute(Param.PATIENT_ID));
		populateSessions(sessionChildren, p);
		populateTreeItem(p.getLastName() + ", " + p.getFirstName() + " "
				+ p.getMrNumber(), root, new Nav(PATIENT_DEMO_URL, false));
		populateTreeItem("Create New Session", sessionChildren, new Nav(
				PATIENT_SESSDEMO_URL, false));
		Treeitem patientSessionItem = populateTreeItem("Patient Sessions", root);
		sessionChildren.setParent(patientSessionItem);

		root.setParent(tree);
	}

	private void populateSessions(Treechildren sessionChildren, Patient p) {

		List<SessionInfo> sessions = p.getSessions();
		if (sessions != null) {
			int count = 0;
			for (SessionInfo _sessionInfo : sessions) {
				count = count + 1;
				// String sessionLabel = DateFormat.getDateInstance(
				// DateFormat.MEDIUM)
				// .format(_sessionInfo.getCreatedDate());
				Treechildren createdDateChildren = new Treechildren();
				Treeitem sessionItem = populateTreeItem("SPECT Eval " + count,
						sessionChildren);
				populateTreeItem("Demographics", createdDateChildren, new Nav(
						PATIENT_SESSDEMO_URL, _sessionInfo, false));
				populateTreeItem("Referring Attorney", createdDateChildren,
						new Nav(PATIENT_REF_ATTORNEY_URL, _sessionInfo, false));
				populateTreeItem("Referring Physician", createdDateChildren,
						new Nav(PATIENT_REF_PHYSICIAN_URL, _sessionInfo, false));
				populateTreeItem("Treatment Providers", createdDateChildren,
						new Nav(OTHER_TREATMENT_PROV_URL, _sessionInfo, false));
				populateTreeItem(
						"Traumatic Brain Injury",
						createdDateChildren,
						new Nav(TRAUMATIC_BRAIN_INJURY_URL, _sessionInfo, false));
				populateTreeItem("Symptoms", createdDateChildren, new Nav(
						SYMPTOM_URL, _sessionInfo, false));
				populateTreeItem("Incoming Diagnoses", createdDateChildren,
						new Nav(INCOMING_DIAGNOSES_URL, _sessionInfo, false));
				populateTreeItem("Allergies", createdDateChildren, new Nav(
						MED_ALLERGIES_URL, _sessionInfo, false));
				populateTreeItem("Medications", createdDateChildren, new Nav(
						MEDICATION_URL, _sessionInfo, false));
				populateTreeItem("Hospitalizations", createdDateChildren,
						new Nav(HOSP_URL, _sessionInfo, false));
				populateTreeItem("Other Imaging", createdDateChildren, new Nav(
						OTHER_IMAGING_URL, _sessionInfo, false));
				populateTreeItem("Substance Use", createdDateChildren, new Nav(
						SUPSTANCE_USE_URL, _sessionInfo, false));
				populateTreeItem("Developmental History", createdDateChildren,
						new Nav(DEV_HIST_URL, _sessionInfo, false));
				populateTreeItem("Family History", createdDateChildren,
						new Nav(FAM_HIST_URL, _sessionInfo, false));
				populateTreeItem("Psychometrics", createdDateChildren, new Nav(
						PSYCH_ASSESS_URL, _sessionInfo, false));
				populateTreeItem("SPECT Imaging", createdDateChildren, new Nav(
						SPECT_IMG_URL, _sessionInfo, false));
				populateTreeItem("SPECT Findings", createdDateChildren, new Nav(
						OUTGOING_DIAGNSES_URL, _sessionInfo, false));
				populateTreeItem("Scanned Forms", createdDateChildren, new Nav(
						SCANNED_FORM_URL, _sessionInfo, false));
				populateTreeItem("Note", createdDateChildren, new Nav(
						SES_NOTE_URL, _sessionInfo, false));
				createdDateChildren.setParent(sessionItem);
				
				
			}
		}
	}

	private Treeitem populateTreeItem(String label, Component parent) {
		return populateTreeItem(label, parent, null);
	}

	private Treeitem populateTreeItem(String label, Component parent, Nav nav) {
		Treeitem item = new Treeitem();
		// item.setOpen(false);
		item.setAttribute("nav", nav);
		Treerow row = new Treerow();
		Treecell cell = new Treecell();
		if (nav == null) {
			cell.setStyle("color:gray;font-style: italic;");
		}
		cell.setLabel(label);
		cell.setParent(row);
		row.setParent(item);
		item.setParent(parent);
		return item;
	}

	class Nav {
		String url;
		SessionInfo session;
		boolean fullRefresh = false;

		public Nav(String url, boolean fullRefresh) {
			super();
			this.url = url;
			this.fullRefresh = fullRefresh;
		}

		public Nav(String url, SessionInfo session, boolean fullRefresh) {
			super();
			this.url = url;
			this.session = session;
			this.fullRefresh = fullRefresh;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public SessionInfo getSession() {
			return session;
		}

		public void setSession(SessionInfo session) {
			this.session = session;
		}

		public boolean isFullRefresh() {
			return fullRefresh;
		}

		public void setFullRefresh(boolean fullRefresh) {
			this.fullRefresh = fullRefresh;
		}
	}
}
