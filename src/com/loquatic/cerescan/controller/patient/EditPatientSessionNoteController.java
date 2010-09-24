package com.loquatic.cerescan.controller.patient;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManagerFactory;
import com.loquatic.cerescan.common.Param;

public class EditPatientSessionNoteController extends GenericForwardComposer {
	private static Log log = LogFactory
	.getLog(EditPatientSessionNoteController.class);

	private Label successLbl;
	private SessionInfoManager sessionManager;
	private SessionInfo sessionInfo;

	public SessionInfo getSessionInfo() {
		return sessionInfo;
	}

	public void setSessionInfo(SessionInfo sessionInfo) {
		this.sessionInfo = sessionInfo;
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute("controller", this);

		Map<String, SessionInfo> args = Executions.getCurrent().getArg();
		SessionInfo _ses = args.get(Param.SESSION);
		sessionManager = SessionInfoManagerFactory.getInstance().getSession(
				_ses.getId());
		sessionInfo = sessionManager.getSessionInfo();
	}
	public void onClick$updateBtn(MouseEvent event) throws Exception {
		sessionManager.save( sessionInfo ) ;
		successLbl.setValue("Note has been saved");
	}
}
