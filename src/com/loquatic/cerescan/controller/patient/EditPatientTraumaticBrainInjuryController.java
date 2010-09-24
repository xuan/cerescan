package com.loquatic.cerescan.controller.patient;

import java.util.ArrayList;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import com.loquatic.cerescan.api.entities.Attorney;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.entities.TraumaticBrainInjury;
import com.loquatic.cerescan.api.entities.lookups.LossConsciousness;
import com.loquatic.cerescan.api.entities.lookups.PhysicalInjury;
import com.loquatic.cerescan.api.entities.lookups.TraumaticAmnesia;
import com.loquatic.cerescan.api.entities.lookups.TraumaticBrainInjuryType;
import com.loquatic.cerescan.api.persistence.managers.LookupEntityManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManagerFactory;
import com.loquatic.cerescan.common.Param;

public class EditPatientTraumaticBrainInjuryController extends
		GenericForwardComposer {
	private static Log log = LogFactory
			.getLog(EditPatientTraumaticBrainInjuryController.class);
	private Label successLbl;
	private SessionInfoManager sessionManager;
	private List<TraumaticBrainInjury> traumaticBrainInjuries = new ArrayList<TraumaticBrainInjury>();
	private List<TraumaticBrainInjuryType> allTraumaticBrainInjuryTypes;
	private List<LossConsciousness> allLossConsciousness;
	private List<TraumaticAmnesia> allTraumaticAmnesias;
	private List<PhysicalInjury> allPhysicalInjuries;
	private Listbox traumaticBrainInjuryTypeLb, lossConsciousnessLb,
			traumaticAmnesiaLb, physicalInjuryLb;
	private Listbox traumaticBrainInjuriesLb;
	private Textbox additionalDescriptionTb;
	private Button deleteBtn;

	public List<TraumaticBrainInjury> getTraumaticBrainInjuries() {
		return traumaticBrainInjuries;
	}

	public void setTraumaticBrainInjuries(
			List<TraumaticBrainInjury> traumaticBrainInjuries) {
		this.traumaticBrainInjuries = traumaticBrainInjuries;
	}

	public List<TraumaticBrainInjuryType> getAllTraumaticBrainInjuryTypes() {
		return allTraumaticBrainInjuryTypes;
	}

	public void setAllTraumaticBrainInjuryTypes(
			List<TraumaticBrainInjuryType> allTraumaticBrainInjuryTypes) {
		this.allTraumaticBrainInjuryTypes = allTraumaticBrainInjuryTypes;
	}

	public List<LossConsciousness> getAllLossConsciousness() {
		return allLossConsciousness;
	}

	public void setAllLossConsciousness(
			List<LossConsciousness> allLossConsciousness) {
		this.allLossConsciousness = allLossConsciousness;
	}

	public List<TraumaticAmnesia> getAllTraumaticAmnesias() {
		return allTraumaticAmnesias;
	}

	public void setAllTraumaticAmnesias(
			List<TraumaticAmnesia> allTraumaticAmnesias) {
		this.allTraumaticAmnesias = allTraumaticAmnesias;
	}

	public List<PhysicalInjury> getAllPhysicalInjuries() {
		return allPhysicalInjuries;
	}

	public void setAllPhysicalInjuries(List<PhysicalInjury> allPhysicalInjuries) {
		this.allPhysicalInjuries = allPhysicalInjuries;
	}

	public SessionInfoManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionInfoManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute("controller", this);

		Map<String, SessionInfo> args = Executions.getCurrent().getArg();
		SessionInfo _ses = args.get(Param.SESSION);
		sessionManager = SessionInfoManagerFactory.getInstance().getSession(
				_ses.getId());
		traumaticBrainInjuries = sessionManager.getSessionInfo()
				.getTraumaticBrainInjuries();
		doLookup();
		// select the first textbox because of zk NPE bug
		deselect();
	}

	public void onSelect$traumaticBrainInjuriesLb(Event event) {
		if (traumaticBrainInjuriesLb.getSelectedItems().size() > 0) {
			deleteBtn.setVisible(true);
		} else {
			deleteBtn.setVisible(false);
		}
	}

	public void onClick$deleteBtn(MouseEvent event) {
		try {
			String msg = "Are you sure you want to disassociate the patient with this tbi?";
			Messagebox.show(msg, "Prompt", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION, new EventListener() {
						public void onEvent(Event evt) {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								TraumaticBrainInjury tbi = (TraumaticBrainInjury) traumaticBrainInjuriesLb
										.getSelectedItem().getValue();
								((List<Attorney>) traumaticBrainInjuriesLb
										.getModel()).remove(tbi);
								deleteBtn.setVisible(false);
								traumaticBrainInjuriesLb.clearSelection();
								SessionInfo ses = sessionManager.getSessionInfo();
								ses.setTraumaticBrainInjuries(traumaticBrainInjuries);
								sessionManager.save(ses);
								successLbl.setValue("Traumatic brain injury has been saved");
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

	private void doLookup() {
		this.allTraumaticBrainInjuryTypes = LookupEntityManager
				.getAllTraumaticBrainInjuries();
		this.allLossConsciousness = LookupEntityManager
				.getAllLossConsciousness();
		this.allTraumaticAmnesias = LookupEntityManager
				.getAllTraumaticAmnesias();
		this.allPhysicalInjuries = LookupEntityManager.getAllPhysicalInjuries();
	}

	private void deselect() {
		traumaticBrainInjuryTypeLb.setSelectedIndex(0);
		lossConsciousnessLb.setSelectedIndex(0);
		traumaticAmnesiaLb.setSelectedIndex(0);
		physicalInjuryLb.setSelectedIndex(0);
	}

	public void onClick$addBtn(MouseEvent event) {
		traumaticBrainInjuries = (List<TraumaticBrainInjury>) traumaticBrainInjuriesLb
				.getModel();
		TraumaticBrainInjury tbi = new TraumaticBrainInjury();
		tbi
				.setTraumaticBrainInjuryType((TraumaticBrainInjuryType) traumaticBrainInjuryTypeLb
						.getSelectedItem().getValue());
		tbi.setLossOfConsciousness((LossConsciousness) lossConsciousnessLb
				.getSelectedItem().getValue());
		tbi.setPostTraumaticAmnesia((TraumaticAmnesia) traumaticAmnesiaLb
				.getSelectedItem().getValue());
		tbi.setPhysicalInjury((PhysicalInjury) physicalInjuryLb
				.getSelectedItem().getValue());
		tbi.setDescription(additionalDescriptionTb.getValue());
		traumaticBrainInjuries.add(tbi);
		sessionManager.addTraumaticBrainInjury(tbi);
		successLbl.setValue("Traumatic brain injury has been added");
		deselect();
	}
}
