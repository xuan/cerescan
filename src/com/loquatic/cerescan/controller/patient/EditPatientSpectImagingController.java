package com.loquatic.cerescan.controller.patient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.api.Textbox;

import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.entities.User;
import com.loquatic.cerescan.api.entities.lookups.BsInjectionSite;
import com.loquatic.cerescan.api.entities.lookups.ConcentrationTaskType;
import com.loquatic.cerescan.api.entities.lookups.CsInjectionSite;
import com.loquatic.cerescan.api.entities.lookups.ReadingPhysician;
import com.loquatic.cerescan.api.persistence.managers.LookupEntityManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManagerFactory;
import com.loquatic.cerescan.common.Param;
import com.loquatic.cerescan.view.ErrorLabel;

public class EditPatientSpectImagingController extends GenericForwardComposer {
	private static Log log = LogFactory
			.getLog(EditPatientMedicationController.class);
	private Label successLbl;
	private ErrorLabel errLbl;

	private SessionInfoManager sessionManager;
	private SessionInfo sessionInfo;
	private List<ConcentrationTaskType> allScannedFormTypes;
	private List<BsInjectionSite> allBsInjectionSites;
	private List<CsInjectionSite> allCsInjectionSites;
	private List<ReadingPhysician> allReadingPhysicians;
	private HashSet<ReadingPhysician> selectedReadingPhysicians;
	private Textbox csTechnologistsInitialsTb, bsTechnologistsInitialsTb;

	public SessionInfo getSessionInfo() {
		return sessionInfo;
	}

	public void setSessionInfo(SessionInfo sessionInfo) {
		this.sessionInfo = sessionInfo;
	}

	public List<ConcentrationTaskType> getAllScannedFormTypes() {
		return allScannedFormTypes;
	}

	public void setAllScannedFormTypes(
			List<ConcentrationTaskType> allScannedFormTypes) {
		this.allScannedFormTypes = allScannedFormTypes;
	}

	public List<BsInjectionSite> getAllBsInjectionSites() {
		return allBsInjectionSites;
	}

	public void setAllBsInjectionSites(List<BsInjectionSite> allBsInjectionSites) {
		this.allBsInjectionSites = allBsInjectionSites;
	}

	public List<CsInjectionSite> getAllCsInjectionSites() {
		return allCsInjectionSites;
	}

	public void setAllCsInjectionSites(List<CsInjectionSite> allCsInjectionSites) {
		this.allCsInjectionSites = allCsInjectionSites;
	}

	public List<ReadingPhysician> getAllReadingPhysicians() {
		return allReadingPhysicians;
	}

	public void setAllReadingPhysicians(
			List<ReadingPhysician> allReadingPhysicians) {
		this.allReadingPhysicians = allReadingPhysicians;
	}

	public HashSet<ReadingPhysician> getSelectedReadingPhysicians() {
		return selectedReadingPhysicians;
	}

	public void setSelectedReadingPhysicians(
			HashSet<ReadingPhysician> selectedReadingPhysicians) {
		this.selectedReadingPhysicians = selectedReadingPhysicians;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute("controller", this);
		Map<String, SessionInfo> args = Executions.getCurrent().getArg();
		SessionInfo _ses = args.get(Param.SESSION);
		sessionManager = SessionInfoManagerFactory.getInstance().getSession(
				_ses.getId());

		allScannedFormTypes = LookupEntityManager
				.getAllConcentrationTaskTypes();
		allCsInjectionSites = LookupEntityManager.getCsAllInjectionSites();
		allBsInjectionSites = LookupEntityManager.getBsAllInjectionSites();
		allReadingPhysicians = LookupEntityManager.getAllReadingPhysicians();
		sessionInfo = sessionManager.getSessionInfo();

		if (sessionInfo.getReadingPhysicians() != null) {
			selectedReadingPhysicians = new HashSet(sessionInfo
					.getReadingPhysicians());
		}
	}

	public void onClick$csSigBtn(MouseEvent event) {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		csTechnologistsInitialsTb.setValue(user.getUsername());
		sessionInfo.setCsTechnologistsInitials(user.getUsername());
	}

	public void onClick$bsSigBtn(MouseEvent event) {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		bsTechnologistsInitialsTb.setValue(user.getUsername());
		sessionInfo.setBsTechnologistInitials(user.getUsername());
	}

	public void onClick$updateBtn(MouseEvent event) {
		if (selectedReadingPhysicians != null) {
			sessionInfo.setReadingPhysicians(new ArrayList<ReadingPhysician>(
					selectedReadingPhysicians));
		} else {
			sessionInfo.setReadingPhysicians(new ArrayList<ReadingPhysician>());
		}
		sessionManager.save(sessionInfo);
		successLbl.setValue("Spect Imaging has been saved");
	}
}
