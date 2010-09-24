package com.loquatic.cerescan.controller.patient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;

import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.entities.lookups.BirthTrauma;
import com.loquatic.cerescan.api.entities.lookups.DevelopmentMilestone;
import com.loquatic.cerescan.api.entities.lookups.DevelopmentTrauma;
import com.loquatic.cerescan.api.persistence.managers.LookupEntityManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManagerFactory;
import com.loquatic.cerescan.common.Param;

public class EditPatientDevelopmentHistoryController extends
		GenericForwardComposer {

	private static Log log = LogFactory
			.getLog(EditPatientDevelopmentHistoryController.class);
	private Label successLbl;
	private SessionInfo sessionInfo;
	private SessionInfoManager sessionManager;

	private List<BirthTrauma> allBirthTraumas = new ArrayList<BirthTrauma>();
	private HashSet<BirthTrauma> selectedBirthTraumas;

	private List<DevelopmentMilestone> allDevelopmentMilestones;
	private HashSet<DevelopmentMilestone> selectedDevelopmentMilestones;

	private List<DevelopmentTrauma> allDevelopmentTraumas;
	private HashSet<DevelopmentTrauma> selectedDevelopmentTraumas;

	public SessionInfo getSessionInfo() {
		return sessionInfo;
	}

	public void setSessionInfo(SessionInfo sessionInfo) {
		this.sessionInfo = sessionInfo;
	}

	public List<BirthTrauma> getAllBirthTraumas() {
		return allBirthTraumas;
	}

	public void setAllBirthTraumas(List<BirthTrauma> allBirthTraumas) {
		this.allBirthTraumas = allBirthTraumas;
	}

	public HashSet<BirthTrauma> getSelectedBirthTraumas() {
		return selectedBirthTraumas;
	}

	public void setSelectedBirthTraumas(
			HashSet<BirthTrauma> selectedBirthTraumas) {
		this.selectedBirthTraumas = selectedBirthTraumas;
	}

	public List<DevelopmentMilestone> getAllDevelopmentMilestones() {
		return allDevelopmentMilestones;
	}

	public void setAllDevelopmentMilestones(
			List<DevelopmentMilestone> allDevelopmentMilestones) {
		this.allDevelopmentMilestones = allDevelopmentMilestones;
	}

	public HashSet<DevelopmentMilestone> getSelectedDevelopmentMilestones() {
		return selectedDevelopmentMilestones;
	}

	public void setSelectedDevelopmentMilestones(
			HashSet<DevelopmentMilestone> selectedDevelopmentMilestones) {
		this.selectedDevelopmentMilestones = selectedDevelopmentMilestones;
	}

	public List<DevelopmentTrauma> getAllDevelopmentTraumas() {
		return allDevelopmentTraumas;
	}

	public void setAllDevelopmentTraumas(
			List<DevelopmentTrauma> allDevelopmentTraumas) {
		this.allDevelopmentTraumas = allDevelopmentTraumas;
	}

	public HashSet<DevelopmentTrauma> getSelectedDevelopmentTraumas() {
		return selectedDevelopmentTraumas;
	}

	public void setSelectedDevelopmentTraumas(
			HashSet<DevelopmentTrauma> selectedDevelopmentTraumas) {
		this.selectedDevelopmentTraumas = selectedDevelopmentTraumas;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute("controller", this);
		allBirthTraumas = LookupEntityManager.getAllBirthTrauma();
		allDevelopmentMilestones = LookupEntityManager
				.getAllDevelopmentMilestones();
		allDevelopmentTraumas = LookupEntityManager.getAllDevelopmentTrauma();
		Map<String, SessionInfo> args = Executions.getCurrent().getArg();
		SessionInfo _ses = args.get(Param.SESSION);
		sessionManager = SessionInfoManagerFactory.getInstance().getSession(
				_ses.getId());
		this.sessionInfo = sessionManager.getSessionInfo();
		if (sessionInfo.getBirthTraumas() != null) {
			selectedBirthTraumas = new HashSet(sessionInfo.getBirthTraumas());
		}

		if (sessionInfo.getDevelopmentMilestones() != null) {
			selectedDevelopmentMilestones = new HashSet(sessionInfo
					.getDevelopmentMilestones());
		}

		if (sessionInfo.getDevelopmentTraumas() != null) {
			selectedDevelopmentTraumas = new HashSet(sessionInfo
					.getDevelopmentTraumas());
		}
	}

	public void onClick$updateBtn(MouseEvent event) {
		if (selectedBirthTraumas != null) {
			sessionInfo.setBirthTraumas(new ArrayList<BirthTrauma>(
					selectedBirthTraumas));
		} else {
			sessionInfo.setBirthTraumas(new ArrayList<BirthTrauma>());
		}

		if (selectedDevelopmentMilestones != null) {
			sessionInfo
					.setDevelopmentMilestones(new ArrayList<DevelopmentMilestone>(
							selectedDevelopmentMilestones));
		} else {
			sessionInfo
					.setDevelopmentMilestones(new ArrayList<DevelopmentMilestone>());
		}

		if (selectedDevelopmentTraumas != null) {
			sessionInfo.setDevelopmentTraumas(new ArrayList<DevelopmentTrauma>(
					selectedDevelopmentTraumas));
		} else {
			sessionInfo
					.setDevelopmentTraumas(new ArrayList<DevelopmentTrauma>());
		}

		sessionManager.save(sessionInfo);
		successLbl.setValue("Development Milestones has been saved");
	}
}
