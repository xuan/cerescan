package com.loquatic.cerescan.controller.patient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import com.loquatic.cerescan.api.entities.PsychometricAssessment;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.entities.lookups.Mini;
import com.loquatic.cerescan.api.entities.lookups.PsychometricAssessmentType;
import com.loquatic.cerescan.api.persistence.managers.CerescanPersistenceManager;
import com.loquatic.cerescan.api.persistence.managers.LookupEntityManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManagerFactory;
import com.loquatic.cerescan.common.FileUtil;
import com.loquatic.cerescan.common.Param;
import com.loquatic.cerescan.view.ErrorLabel;

public class EditPatientPsychAssessController extends GenericForwardComposer {
	private static Log log = LogFactory
			.getLog(EditPatientPsychAssessController.class);
	private SessionInfoManager sessionManager;
	private SessionInfo sessionInfo;

	private PsychometricAssessment selectedPsychometricAssessment;
	private HashSet<Mini> selectedMinis;
	private List<PsychometricAssessment> psychometricAssessments = new ArrayList<PsychometricAssessment>();
	private List<PsychometricAssessmentType> allPsychometricAssessmentTypes = new ArrayList<PsychometricAssessmentType>();
	private Label successLbl;
	private ErrorLabel errLbl;
	private Fileupload fileupload;
	private Media media;
	private Label fileNameLbl;
	private Image thumbnailImg;
	private Textbox descriptionTb;
	private Listbox psychometricAssessmentLb, psychometricAssessmentTypeLb,
			psychometricAssessmentMiniLb;
	private List<Mini> allMinis;
	private Button deleteBtn, downloadBtn;
	private Groupbox miniGb;

	public List<Mini> getAllMinis() {
		return allMinis;
	}

	public void setAllMinis(List<Mini> allMinis) {
		this.allMinis = allMinis;
	}

	public HashSet<Mini> getSelectedMinis() {
		return selectedMinis;
	}

	public void setSelectedMinis(HashSet<Mini> selectedMinis) {
		this.selectedMinis = selectedMinis;
	}

	public PsychometricAssessment getSelectedPsychometricAssessment() {
		return selectedPsychometricAssessment;
	}

	public void setSelectedPsychometricAssessment(
			PsychometricAssessment selectedPsychometricAssessment) {
		this.selectedPsychometricAssessment = selectedPsychometricAssessment;
	}

	public List<PsychometricAssessment> getPsychometricAssessments() {
		return psychometricAssessments;
	}

	public void setPsychometricAssessments(
			List<PsychometricAssessment> psychometricAssessments) {
		this.psychometricAssessments = psychometricAssessments;
	}

	public List<PsychometricAssessmentType> getAllPsychometricAssessmentTypes() {
		return allPsychometricAssessmentTypes;
	}

	public void setAllPsychometricAssessmentTypes(
			List<PsychometricAssessmentType> allPsychometricAssessmentTypes) {
		this.allPsychometricAssessmentTypes = allPsychometricAssessmentTypes;
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
		if (sessionInfo.getAssessments() != null) {
			this.psychometricAssessments = sessionInfo.getAssessments();
		}
		allPsychometricAssessmentTypes = LookupEntityManager
				.getAllPsychometricAssessmentTypes();
		allMinis = LookupEntityManager.getAllMinis();
		selectedMinis = new HashSet(sessionInfo.getMinis());
		psychometricAssessmentTypeLb.setSelectedIndex(0);
		hideshowMini();
	}

	private void hideshowMini() {
		if (psychometricAssessments == null
				|| psychometricAssessments.isEmpty()) {
			miniGb.setVisible(false);
		} else {
			for (PsychometricAssessment _phsyc : psychometricAssessments) {
				if (_phsyc.getPsychometricAssessmentType() != null
						&& _phsyc.getPsychometricAssessmentType().getName()
								.equals("MINI")) {
					miniGb.setVisible(true);
					break;
				} else {
					miniGb.setVisible(false);
				}
			}
		}
	}

	public void onClick$updateBtn(MouseEvent event) {
		sessionInfo.setMinis(new ArrayList(selectedMinis));
		CerescanPersistenceManager.merge(sessionInfo);
		successLbl.setValue("MINI has been saved");
	}

	public void onClick$deleteBtn(MouseEvent event) {
		try {
			String msg = "Are you sure you want to delete the Psychometric Assessment?";
			Messagebox.show(msg, "Prompt", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION, new EventListener() {
						public void onEvent(Event evt) {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								PsychometricAssessment _asses = (PsychometricAssessment) psychometricAssessmentLb
										.getSelectedItem().getValue();
								((List<PsychometricAssessment>) psychometricAssessmentLb
										.getModel()).remove(_asses);
								sessionInfo
										.setAssessments(psychometricAssessments);
								deleteMini(psychometricAssessments);
								CerescanPersistenceManager.merge(sessionInfo);
								deleteBtn.setVisible(false);
								downloadBtn.setVisible(false);
								psychometricAssessmentLb.clearSelection();
								hideshowMini();
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

	private void deleteMini(List<PsychometricAssessment> assessments) {
		log.debug(assessments.size());
		if (assessments.isEmpty()) {
			log.debug("delete");
			sessionInfo.setMinis(null);
		} else {
			for (PsychometricAssessment _phsyc : assessments) {
				if (_phsyc.getPsychometricAssessmentType() != null
						&& _phsyc.getPsychometricAssessmentType().getName()
								.equals("MINI")) {
					log.debug("delete");
					sessionInfo.setMinis(null);
				}
			}
		}
		CerescanPersistenceManager.merge(sessionInfo);
	}

	public void onSelect$psychometricAssessmentLb(Event event) {

		if (psychometricAssessmentLb.getSelectedItems().size() > 0) {
			PsychometricAssessment _pa = (PsychometricAssessment) psychometricAssessmentLb
					.getSelectedItem().getValue();

			if (_pa.getFilePath() != null && !_pa.getFilePath().isEmpty()) {
				downloadBtn.setVisible(true);
			} else {
				downloadBtn.setVisible(false);
			}
			deleteBtn.setVisible(true);
		} else {
			deleteBtn.setVisible(false);
			downloadBtn.setVisible(false);
		}
	}

	public void onClick$downloadBtn(MouseEvent event) throws Exception {
		PsychometricAssessment pa = (PsychometricAssessment) psychometricAssessmentLb
				.getSelectedItem().getValue();
		Filedownload.save(FileUtil.getAbsolutePath(pa.getFilePath(), pa
				.getStoredFileName()), pa.getContentType());
	}

	public void onUpload$fileupload(UploadEvent event)
			throws InterruptedException {
		this.media = event.getMedia();
		if (media != null) {
			fileNameLbl.setValue(media.getName());
			if (media.isBinary() && (media instanceof org.zkoss.image.Image)) {
				thumbnailImg.setContent((org.zkoss.image.Image) media);
				thumbnailImg.setVisible(true);
			}
		}
	}

	public void onClick$addBtn(MouseEvent event) throws Exception {

		PsychometricAssessmentType type = (PsychometricAssessmentType) psychometricAssessmentTypeLb
				.getSelectedItem().getValue();

		psychometricAssessments = (List<PsychometricAssessment>) psychometricAssessmentLb
				.getModel();
		PsychometricAssessment pa = new PsychometricAssessment();
		pa.setPsychometricAssessmentType(type);

		if (media != null) {
			String storedFileName = +System.currentTimeMillis() + "_"
					+ media.getName();
			pa.setContentType(media.getContentType());
			pa.setFileName(media.getName());
			pa.setFilePath(String.valueOf(sessionInfo.getId()));
			pa.setStoredFileName(storedFileName);

			FileUtil.saveFile(sessionInfo, media, storedFileName);
			media = null;
		}

		pa.setDescription(descriptionTb.getValue());
		psychometricAssessments.clear();
		psychometricAssessments.addAll(sessionManager.addPsychometricAssessment(pa));

		thumbnailImg.setVisible(false);
		descriptionTb.setValue("");
		fileNameLbl.setValue("");
		psychometricAssessmentTypeLb.setSelectedIndex(0);
		hideshowMini();
		successLbl.setValue("Psychometric Assessments has been saved");
	}
}