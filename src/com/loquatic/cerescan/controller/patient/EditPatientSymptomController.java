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
import com.loquatic.cerescan.api.entities.lookups.Symptom;
import com.loquatic.cerescan.api.entities.lookups.TraumaticBrainInjuryType;
import com.loquatic.cerescan.api.persistence.managers.LookupEntityManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManagerFactory;
import com.loquatic.cerescan.common.Param;

public class EditPatientSymptomController extends GenericForwardComposer {
	private static Log log = LogFactory
			.getLog(EditPatientTraumaticBrainInjuryController.class);
	private Label successLbl;
	private SessionInfo sessionInfo;
	private SessionInfoManager sessionManager;

	private List<Symptom> allSymptoms = new ArrayList<Symptom>();
	private HashSet<Symptom> selectedSymptoms = new HashSet<Symptom>();

	public List<Symptom> getAllSymptoms() {
		return allSymptoms;
	}

	public void setAllSymptoms(List<Symptom> allSymptoms) {
		this.allSymptoms = allSymptoms;
	}

	public HashSet<Symptom> getSelectedSymptoms() {
		return selectedSymptoms;
	}

	public void setSelectedSymptoms(HashSet<Symptom> selectedSymptoms) {
		this.selectedSymptoms = selectedSymptoms;
	}

	public SessionInfo getSessionInfo() {
		return sessionInfo;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute("controller", this);

		this.allSymptoms = LookupEntityManager.getAllSymptoms();

		Map<String, SessionInfo> args = Executions.getCurrent().getArg();
		SessionInfo _ses = args.get(Param.SESSION);
		sessionManager = SessionInfoManagerFactory.getInstance().getSession(
				_ses.getId());
		this.sessionInfo = sessionManager.getSessionInfo();
		if (sessionInfo.getSymptoms() != null) {
			selectedSymptoms = new HashSet<Symptom>(sessionInfo.getSymptoms());
		}

	}

	public void onClick$btn(MouseEvent event) {
		if (selectedSymptoms != null) {
			sessionInfo.setSymptoms(new ArrayList<Symptom>(selectedSymptoms));
		} else {
			sessionInfo.setSymptoms(new ArrayList<Symptom>());
		}
		sessionManager.save(sessionInfo);
		successLbl.setValue("Symptom has been saved");
	}
}
