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

import com.loquatic.cerescan.api.entities.FamilyMember;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.entities.lookups.FamilyMemberType;
import com.loquatic.cerescan.api.entities.lookups.IncomingDiagnoses;
import com.loquatic.cerescan.api.persistence.managers.LookupEntityManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManagerFactory;
import com.loquatic.cerescan.common.Param;

public class EditPatientFamilyHistoryController extends GenericForwardComposer {
	private static Log log = LogFactory
			.getLog(EditPatientFamilyHistoryController.class);

	private Label errLbl;
	private Label successLbl;
	private Listbox familyMembersLb, familyMemberTypeLb, incomingDiagnosesLb;
	private Button deleteBtn;
	private SessionInfoManager sessionManager;

	private List<FamilyMember> familyMembers = new ArrayList<FamilyMember>();
	private List<FamilyMemberType> allFamilyMemberTypes = new ArrayList<FamilyMemberType>();
	private List<IncomingDiagnoses> allIncomingDiagnoses = new ArrayList<IncomingDiagnoses>();
	private HashSet<IncomingDiagnoses> selectedIncomingDiagnoses = new HashSet<IncomingDiagnoses>();

	public List<FamilyMember> getFamilyMembers() {
		return familyMembers;
	}

	public void setFamilyMembers(List<FamilyMember> familyMembers) {
		this.familyMembers = familyMembers;
	}

	public List<FamilyMemberType> getAllFamilyMemberTypes() {
		return allFamilyMemberTypes;
	}

	public void setAllFamilyMemberTypes(
			List<FamilyMemberType> allFamilyMemberTypes) {
		this.allFamilyMemberTypes = allFamilyMemberTypes;
	}

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

	public void onSelect$familyMembersLb(Event event) {
		if (familyMembersLb.getSelectedItems().size() > 0) {
			deleteBtn.setVisible(true);
		} else {
			deleteBtn.setVisible(false);
		}
	}

	public void onClick$deleteBtn(MouseEvent event) {
		try {
			String msg = "Are you sure you want to disassociate the patient from this family member?";
			Messagebox.show(msg, "Prompt", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION, new EventListener() {
						public void onEvent(Event evt) {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								FamilyMember fm = (FamilyMember) familyMembersLb
										.getSelectedItem().getValue();
								((List<FamilyMember>) familyMembersLb
										.getModel()).remove(fm);
								SessionInfo ses=sessionManager.getSessionInfo();
								ses.getFamilyMembers().remove(fm);
								sessionManager.save(ses);
								deleteBtn.setVisible(false);
								familyMembersLb.clearSelection();
								errLbl.setValue("");
								successLbl
										.setValue("Family history has been deleted");
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

		this.allIncomingDiagnoses = LookupEntityManager
				.getAllIncomingDiagnoses();

		this.allFamilyMemberTypes = LookupEntityManager
				.getAllFamilyMemberTypes();

		Map<String, SessionInfo> args = Executions.getCurrent().getArg();
		sessionManager = SessionInfoManagerFactory.getInstance().getSession(
				args.get(Param.SESSION).getId());
		this.familyMembers = sessionManager.getSessionInfo().getFamilyMembers();
		familyMemberTypeLb.setSelectedIndex(0);
	}

	public void onClick$addBtn(MouseEvent event) {
		allFamilyMemberTypes = (List<FamilyMemberType>) familyMemberTypeLb
				.getModel();
		if (selectedIncomingDiagnoses.size() == 0) {
			errLbl
					.setValue("Please select both Family Members and Diagnoses before adding.");
		} else {
			errLbl.setValue("");
			this.familyMembers = (List<FamilyMember>) familyMembersLb
					.getModel();
			FamilyMember _fam = new FamilyMember();
			_fam.setFamilyMemberType((FamilyMemberType) familyMemberTypeLb
					.getSelectedItem().getValue());
			_fam.setIncomingDiagnoses(new ArrayList(selectedIncomingDiagnoses));
			this.familyMembers.add(_fam);
			familyMemberTypeLb.setSelectedIndex(0);
			incomingDiagnosesLb.clearSelection();
			sessionManager.addFamilyMembers(_fam);
			errLbl.setValue("");
			successLbl.setValue("Family history has been saved");
		}
	}
}
