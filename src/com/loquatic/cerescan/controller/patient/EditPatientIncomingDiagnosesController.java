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
import com.loquatic.cerescan.api.entities.lookups.IncomingDiagnoses;
import com.loquatic.cerescan.api.persistence.managers.LookupEntityManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManagerFactory;
import com.loquatic.cerescan.common.Param;

public class EditPatientIncomingDiagnosesController extends
		GenericForwardComposer {
	private static Log log = LogFactory
			.getLog(EditPatientIncomingDiagnosesController.class);
	private Label successLbl;
	private SessionInfo sessionInfo;
	private SessionInfoManager sessionManager;

	private List<IncomingDiagnoses> allIncomingDiagnoses = new ArrayList<IncomingDiagnoses>();
	private HashSet<IncomingDiagnoses> selectedIncomingDiagnoses = new HashSet<IncomingDiagnoses>();

	public List<IncomingDiagnoses> getAllIncomingDiagnoses() {
		return allIncomingDiagnoses;
	}

	public void setAllIncomingDiagnoses(
			List<IncomingDiagnoses> allIncomingDiagnoses) {
		this.allIncomingDiagnoses = allIncomingDiagnoses;
	}

	public HashSet<IncomingDiagnoses> getSelectedIncomingDiagnoses() {
		return selectedIncomingDiagnoses;
	}

	public void setSelectedIncomingDiagnoses(
			HashSet<IncomingDiagnoses> selectedIncomingDiagnoses) {
		this.selectedIncomingDiagnoses = selectedIncomingDiagnoses;
	}

	public SessionInfo getSessionInfo() {
		return sessionInfo;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute("controller", this);

		this.allIncomingDiagnoses = LookupEntityManager
				.getAllIncomingDiagnoses();

		Map<String, SessionInfo> args = Executions.getCurrent().getArg();
		SessionInfo _ses = args.get(Param.SESSION);
		sessionManager = SessionInfoManagerFactory.getInstance().getSession(
				_ses.getId());
		this.sessionInfo = sessionManager.getSessionInfo();
		if (sessionInfo.getIncomingDiagnoses() != null) {
			selectedIncomingDiagnoses = new HashSet(sessionInfo
					.getIncomingDiagnoses());
		}

	}

	public void onClick$btn(MouseEvent event) {
		if (selectedIncomingDiagnoses != null) {
			sessionInfo.setIncomingDiagnoses(new ArrayList<IncomingDiagnoses>(
					selectedIncomingDiagnoses));
		} else {
			sessionInfo
					.setIncomingDiagnoses(new ArrayList<IncomingDiagnoses>());
		}
		sessionManager.save(sessionInfo);
		successLbl.setValue("Incoming Diagnoses has been saved");
	}
}
