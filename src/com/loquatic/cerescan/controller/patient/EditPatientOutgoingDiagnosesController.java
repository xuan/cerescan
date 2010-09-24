package com.loquatic.cerescan.controller.patient;

import java.util.ArrayList;
import java.util.HashSet;
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

import com.loquatic.cerescan.api.entities.BrainHemisphereArea;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.entities.lookups.BrainArea;
import com.loquatic.cerescan.api.entities.lookups.BrainHemisphere;
import com.loquatic.cerescan.api.entities.lookups.OutgoingDiagnoses;
import com.loquatic.cerescan.api.persistence.managers.LookupEntityManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManagerFactory;
import com.loquatic.cerescan.common.Param;

public class EditPatientOutgoingDiagnosesController extends
		GenericForwardComposer {
	private static Log log = LogFactory
			.getLog(EditPatientOutgoingDiagnosesController.class);
	private Label errLbl;
	private Label successLbl;
	private SessionInfo sessionInfo;
	private SessionInfoManager sessionManager;

	private List<OutgoingDiagnoses> allOutgoingDiagnoses;
	private HashSet<OutgoingDiagnoses> selectedOutgoingDiagnoses;

	private List<BrainArea> allBrainArea;
	private HashSet<BrainArea> selectedBrainArea;

	private List<BrainHemisphere> allBrainHemisphere;

	private List<BrainHemisphereArea> brainHemisphereAreas;

	private Listbox brainHemisphereAreasLb, brainHemisphereLb, brainAreaLb;
	private Button deleteBtn;

	public SessionInfo getSessionInfo() {
		return sessionInfo;
	}

	public List<OutgoingDiagnoses> getAllOutgoingDiagnoses() {
		return allOutgoingDiagnoses;
	}

	public void setAllOutgoingDiagnoses(
			List<OutgoingDiagnoses> allOutgoingDiagnoses) {
		this.allOutgoingDiagnoses = allOutgoingDiagnoses;
	}

	public HashSet<OutgoingDiagnoses> getSelectedOutgoingDiagnoses() {
		return selectedOutgoingDiagnoses;
	}

	public void setSelectedOutgoingDiagnoses(
			HashSet<OutgoingDiagnoses> selectedOutgoingDiagnoses) {
		this.selectedOutgoingDiagnoses = selectedOutgoingDiagnoses;
	}

	public List<BrainArea> getAllBrainArea() {
		return allBrainArea;
	}

	public void setAllBrainArea(List<BrainArea> allBrainArea) {
		this.allBrainArea = allBrainArea;
	}

	public List<BrainHemisphere> getAllBrainHemisphere() {
		return allBrainHemisphere;
	}

	public List<BrainHemisphereArea> getBrainHemisphereAreas() {
		return brainHemisphereAreas;
	}

	public void setBrainHemisphereAreas(
			List<BrainHemisphereArea> brainHemisphereAreas) {
		this.brainHemisphereAreas = brainHemisphereAreas;
	}

	public void setAllBrainHemisphere(List<BrainHemisphere> allBrainHemisphere) {
		this.allBrainHemisphere = allBrainHemisphere;
	}

	public HashSet<BrainArea> getSelectedBrainArea() {
		return selectedBrainArea;
	}

	public void setSelectedBrainArea(HashSet<BrainArea> selectedBrainArea) {
		this.selectedBrainArea = selectedBrainArea;
	}

	public void onSelect$brainHemisphereAreasLb(Event event) {
		if (brainHemisphereAreasLb.getSelectedItems().size() > 0) {
			deleteBtn.setVisible(true);
		} else {
			deleteBtn.setVisible(false);
		}
	}

	public void onClick$deleteBtn(MouseEvent event) {
		try {
			String msg = "Are you sure you want to disassociate the patient from this specified region?";
			Messagebox.show(msg, "Prompt", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION, new EventListener() {
						public void onEvent(Event evt) {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								BrainHemisphereArea bha = (BrainHemisphereArea) brainHemisphereAreasLb
										.getSelectedItem().getValue();
								((List<BrainHemisphereArea>) brainHemisphereAreasLb
										.getModel()).remove(bha);
								SessionInfo ses = sessionManager
										.getSessionInfo();
								ses.getBrainHemisphereAreas().remove(bha);
								sessionManager.save(ses);
								deleteBtn.setVisible(false);
								brainHemisphereAreasLb.clearSelection();
								errLbl.setValue("");
								successLbl
										.setValue("Specified region has been deleted");
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

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute("controller", this);
		Map<String, SessionInfo> args = Executions.getCurrent().getArg();
		SessionInfo _ses = args.get(Param.SESSION);
		sessionManager = SessionInfoManagerFactory.getInstance().getSession(
				_ses.getId());
		this.sessionInfo = sessionManager.getSessionInfo();

		allOutgoingDiagnoses = LookupEntityManager.getAllOutgoingDiagnoses();
		allBrainArea = LookupEntityManager.getAllBrainAreas();
		allBrainHemisphere = LookupEntityManager.getAllBrainHemispheres();

		if (sessionInfo.getOutgoingDiagnoses() != null) {
			selectedOutgoingDiagnoses = new HashSet<OutgoingDiagnoses>(
					sessionInfo.getOutgoingDiagnoses());
		}

		brainHemisphereAreas = sessionInfo.getBrainHemisphereAreas();
		brainHemisphereLb.setSelectedIndex(0);
	}

	public void onClick$updateBtn(MouseEvent event) {
		if (selectedOutgoingDiagnoses != null) {
			sessionInfo.setOutgoingDiagnoses(new ArrayList<OutgoingDiagnoses>(
					selectedOutgoingDiagnoses));
		} else {
			sessionInfo
					.setOutgoingDiagnoses(new ArrayList<OutgoingDiagnoses>());
		}
		sessionManager.save(sessionInfo);
		successLbl.setValue("Outgoing Diagnoses has been saved");
	}

	public void onClick$addBtn(MouseEvent event) {
		if (selectedBrainArea != null) {
			brainHemisphereAreas = (List<BrainHemisphereArea>) brainHemisphereAreasLb
					.getModel();

			BrainHemisphereArea bha = new BrainHemisphereArea();
			bha.setBrainHemisphere((BrainHemisphere) brainHemisphereLb
					.getSelectedItem().getValue());
			bha.setBrainArea(new ArrayList(selectedBrainArea));
			brainHemisphereAreas.add(bha);

			brainHemisphereAreas.clear();
			brainHemisphereAreas.addAll(sessionManager
					.addBrainHemisphereAreas(bha));
			brainHemisphereLb.setSelectedIndex(0);
			brainAreaLb.clearSelection();
			errLbl.setValue("");
			successLbl.setValue("Specified regions has been saved");
		}
	}
}
