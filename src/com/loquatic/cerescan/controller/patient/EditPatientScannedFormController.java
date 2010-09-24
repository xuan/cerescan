package com.loquatic.cerescan.controller.patient;

import java.util.ArrayList;
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
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import com.loquatic.cerescan.api.entities.ScannedForm;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.entities.lookups.ScannedFormType;
import com.loquatic.cerescan.api.persistence.managers.CerescanPersistenceManager;
import com.loquatic.cerescan.api.persistence.managers.LookupEntityManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManagerFactory;
import com.loquatic.cerescan.common.FileUtil;
import com.loquatic.cerescan.common.Param;
import com.loquatic.cerescan.view.ErrorLabel;

public class EditPatientScannedFormController extends GenericForwardComposer {

	private static Log log = LogFactory
			.getLog(EditPatientScannedFormController.class);
	private SessionInfoManager sessionManager;
	SessionInfo sessionInfo;

	private ScannedForm selectedScannedForm;
	private List<ScannedForm> scannedForms = new ArrayList<ScannedForm>();
	private List<ScannedFormType> allScannedFormTypes = new ArrayList<ScannedFormType>();
	private Label successLbl;
	private ErrorLabel errLbl;
	private Fileupload fileupload;
	private Media media;
	private Label fileNameLbl;
	private Image thumbnailImg;
	private Textbox descriptionTb;
	private Listbox scannedFormLb;
	private Listbox scanFormTypeLb;
	private Button deleteBtn, downloadBtn;

	public ScannedForm getSelectedScannedForm() {
		return selectedScannedForm;
	}

	public void setSelectedScannedForm(ScannedForm selectedScannedForm) {
		this.selectedScannedForm = selectedScannedForm;
	}

	public List<ScannedForm> getScannedForms() {
		return scannedForms;
	}

	public void setScannedForms(List<ScannedForm> scannedForms) {
		this.scannedForms = scannedForms;
	}

	public List<ScannedFormType> getAllScannedFormTypes() {
		return allScannedFormTypes;
	}

	public void setAllScannedFormTypes(List<ScannedFormType> allScannedFormTypes) {
		this.allScannedFormTypes = allScannedFormTypes;
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
		if (sessionInfo.getScannedForms() != null) {
			this.scannedForms = sessionInfo.getScannedForms();
		}
		allScannedFormTypes = LookupEntityManager.getAllScannedFormTypes();
		scanFormTypeLb.setSelectedIndex(0);
	}

	public void onClick$deleteBtn(MouseEvent event) {
		try {
			String msg = "Are you sure you want to delete the Scanned Form selection?";
			Messagebox.show(msg, "Prompt", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION, new EventListener() {
						public void onEvent(Event evt) {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								ScannedForm _sf = (ScannedForm) scannedFormLb
										.getSelectedItem().getValue();
								((List<ScannedForm>) scannedFormLb.getModel())
										.remove(_sf);
								sessionInfo.setScannedForms(scannedForms);
								CerescanPersistenceManager.merge(sessionInfo);
								deleteBtn.setVisible(false);
								downloadBtn.setVisible(false);
								scannedFormLb.clearSelection();
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

	/**
	 * select from the list to view/download the content
	 */
	public void onSelect$scannedFormLb(Event event) throws Exception {
		if (scannedFormLb.getSelectedItems().size() > 0) {
			ScannedForm _sf = (ScannedForm) scannedFormLb.getSelectedItem()
					.getValue();

			if (_sf.getFilePath() != null && !_sf.getFilePath().isEmpty()) {
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
		ScannedForm _sf = (ScannedForm) scannedFormLb.getSelectedItem()
				.getValue();
		if (_sf.getFilePath() != null) {
			Filedownload.save(FileUtil.getAbsolutePath(_sf.getFilePath(), _sf
					.getStoredFileName()), _sf.getContentType());
		}
	}

	public void onClick$addBtn(MouseEvent event) throws Exception {
		ScannedFormType type = (ScannedFormType) scanFormTypeLb
				.getSelectedItem().getValue();
		
		
		scannedForms = (List<ScannedForm>) scannedFormLb.getModel();
		ScannedForm _sf = new ScannedForm();
		_sf.setScannedFormType(type);

		if (media != null) {
			String storedFileName = +System.currentTimeMillis() + "_"
					+ media.getName();
			_sf.setContentType(media.getContentType());
			_sf.setFileName(media.getName());
			_sf.setFilePath(String.valueOf(sessionInfo.getId()));
			_sf.setStoredFileName(storedFileName);

			FileUtil.saveFile(sessionInfo, media, storedFileName);
			media = null;
		}

		_sf.setDescription(descriptionTb.getValue());
		scannedForms.clear();
		scannedForms.addAll(sessionManager.addScannedForm(_sf));

		thumbnailImg.setVisible(false);
		descriptionTb.setValue("");
		fileNameLbl.setValue("");
		scanFormTypeLb.setSelectedIndex(0);
		successLbl.setValue("Scanned Form has been saved");
	}

}
