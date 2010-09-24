package com.loquatic.cerescan.controller.patient;

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
import com.loquatic.cerescan.api.entities.HospitalizationsSurgeries;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.entities.TraumaticBrainInjury;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManagerFactory;
import com.loquatic.cerescan.common.Param;
import com.loquatic.cerescan.view.ErrorLabel;

/**
 * $Id$
 */
public class EditPatientHospitalizationController extends
		GenericForwardComposer {
	private static Log log = LogFactory
			.getLog(EditPatientHospitalizationController.class);
	private SessionInfoManager sessionManager;
	List<HospitalizationsSurgeries> hospitalizations;

	private ErrorLabel errLbl;
	private Listbox hospitalizationLb;
	private Label successLbl;
	private Textbox hospitalizationTb;
	private Button deleteBtn;

	public List<HospitalizationsSurgeries> getHospitalizations() {
		return hospitalizations;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute("controller", this);

		Map<String, SessionInfo> args = Executions.getCurrent().getArg();
		SessionInfo _ses = args.get(Param.SESSION);
		sessionManager = SessionInfoManagerFactory.getInstance().getSession(
				_ses.getId());

		hospitalizations = sessionManager.getSessionInfo()
				.getHospitalizations();
	}

	public void onClick$addBtn() {
		if (isValid()) {
			HospitalizationsSurgeries _hosp = new HospitalizationsSurgeries();
			_hosp.setDescription(hospitalizationTb.getValue());
			sessionManager.addHospitalization( _hosp ) ;
			hospitalizations = sessionManager.getSessionInfo().getHospitalizations() ;
			((List<HospitalizationsSurgeries>) hospitalizationLb.getModel())
      .clear();
			((List<HospitalizationsSurgeries>) hospitalizationLb.getModel())
					.addAll( hospitalizations );
			hospitalizationTb.setValue("");
		}
	}

	public void onSelect$hospitalizationLb(Event event) {
		if (hospitalizationLb.getSelectedItems().size() > 0) {
			deleteBtn.setVisible(true);
		} else {
			deleteBtn.setVisible(false);
		}
	}

	public void onClick$deleteBtn(MouseEvent event) {
		try {
			String msg = "Are you sure you want to delete?";
			Messagebox.show(msg, "Prompt", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION, new EventListener() {
						public void onEvent(Event evt) {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								HospitalizationsSurgeries _hosp = (HospitalizationsSurgeries) hospitalizationLb
										.getSelectedItem().getValue();
								sessionManager.deleteHospitalization( _hosp ) ;
								((List<HospitalizationsSurgeries>) hospitalizationLb.getModel()).clear();
								((List<HospitalizationsSurgeries>) hospitalizationLb.getModel()).addAll(  sessionManager.getSessionInfo().getHospitalizations() ) ;
								deleteBtn.setVisible(false);
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

	private boolean isValid() {
		errLbl.reset();
		if (hospitalizationTb.getValue().isEmpty()) {
			errLbl.addErr("Hospitalization Name cannot be empty.");
			errLbl.refresh();
			return false;
		} else {
			errLbl.setValue("");
			errLbl.refresh();
			return true;
		}
	}
}
