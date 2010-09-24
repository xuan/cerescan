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

import com.loquatic.cerescan.api.entities.OtherImaging;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.entities.lookups.OtherImagingType;
import com.loquatic.cerescan.api.persistence.managers.CerescanPersistenceManager;
import com.loquatic.cerescan.api.persistence.managers.LookupEntityManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManagerFactory;
import com.loquatic.cerescan.common.FileUtil;
import com.loquatic.cerescan.common.Param;
import com.loquatic.cerescan.view.ErrorLabel;

public class EditPatientOtherImageController extends GenericForwardComposer {

	private static Log log = LogFactory
			.getLog(EditPatientOtherImageController.class);
	private SessionInfoManager sessionManager;
	private SessionInfo sessionInfo;

	private OtherImaging selectedOtherImaging;
	private List<OtherImaging> otherImagings = new ArrayList<OtherImaging>();
	private List<OtherImagingType> allOtherImagingTypes = new ArrayList<OtherImagingType>();
	private Label successLbl;
	private ErrorLabel errLbl;
	private Fileupload fileupload;
	private Media media;
	private Label fileNameLbl;
	private Image thumbnailImg;
	private Textbox descriptionTb;
	private Listbox otherImagingLb, otherImagingTypeLb;
	private Button deleteBtn, downloadBtn;

	public SessionInfo getSessionInfo() {
		return sessionInfo;
	}

	public void setSessionInfo(SessionInfo sessionInfo) {
		this.sessionInfo = sessionInfo;
	}

	public OtherImaging getSelectedOtherImaging() {
		return selectedOtherImaging;
	}

	public void setSelectedOtherImaging(OtherImaging selectedOtherImaging) {
		this.selectedOtherImaging = selectedOtherImaging;
	}

	public List<OtherImaging> getOtherImagings() {
		return otherImagings;
	}

	public void setOtherImagings(List<OtherImaging> otherImagings) {
		this.otherImagings = otherImagings;
	}

	public List<OtherImagingType> getAllOtherImagingTypes() {
		return allOtherImagingTypes;
	}

	public void setAllOtherImagingTypes(
			List<OtherImagingType> allOtherImagingTypes) {
		this.allOtherImagingTypes = allOtherImagingTypes;
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
			this.otherImagings = sessionInfo.getOtherImagings();
		}
		allOtherImagingTypes = LookupEntityManager.getAllOtherImagingTypes();
		otherImagingTypeLb.setSelectedIndex(0);
	}

	public void onClick$deleteBtn(MouseEvent event) {
		try {
			String msg = "Are you sure you want to delete the Other Imaging selection?";
			Messagebox.show(msg, "Prompt", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION, new EventListener() {
						public void onEvent(Event evt) {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								OtherImaging _imaging = (OtherImaging) otherImagingLb
										.getSelectedItem().getValue();
								((List<OtherImaging>) otherImagingLb.getModel())
										.remove(_imaging);

								sessionInfo.setOtherImagings(otherImagings);
								CerescanPersistenceManager.merge(sessionInfo);
								deleteBtn.setVisible(false);
								downloadBtn.setVisible(false);
								otherImagingLb.clearSelection();
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
	public void onSelect$otherImagingLb(Event event) {
		
		if (otherImagingLb.getSelectedItems().size() > 0) {
			OtherImaging oi = (OtherImaging) otherImagingLb.getSelectedItem()
			.getValue();
			
			if(oi.getFilePath() != null && !oi.getFilePath().isEmpty()){
				downloadBtn.setVisible(true);
			}
			else{
				downloadBtn.setVisible(false);
			}
			deleteBtn.setVisible(true);
		} else {
			deleteBtn.setVisible(false);
			downloadBtn.setVisible(false);
		}
	}

	public void onClick$downloadBtn(MouseEvent event) throws Exception {
		OtherImaging oi = (OtherImaging) otherImagingLb.getSelectedItem()
				.getValue();
		if (oi.getFilePath() != null) {
			Filedownload.save(FileUtil.getAbsolutePath(oi.getFilePath(), oi
					.getStoredFileName()), oi.getContentType());
		}
	}

	public void onClick$addBtn(MouseEvent event) throws Exception {

		OtherImagingType type = (OtherImagingType) otherImagingTypeLb
				.getSelectedItem().getValue();

		otherImagings = (List<OtherImaging>) otherImagingLb.getModel();
		OtherImaging oi = new OtherImaging();
		oi.setOtherImagingType(type);

		if (media != null) {
			String storedFileName = +System.currentTimeMillis() + "_"
					+ media.getName();
			oi.setContentType(media.getContentType());
			oi.setFileName(media.getName());
			oi.setFilePath(String.valueOf(sessionInfo.getId()));
			oi.setStoredFileName(storedFileName);

			FileUtil.saveFile(sessionInfo, media, storedFileName);
			media = null;
		}

		oi.setDescription(descriptionTb.getValue());
		otherImagings.clear();
		otherImagings.addAll(sessionManager.addOtherImaging(oi));

		thumbnailImg.setVisible(false);
		descriptionTb.setValue("");
		fileNameLbl.setValue("");
		otherImagingTypeLb.setSelectedIndex(0);
		successLbl.setValue("Other Imaging has been saved");
	}
}
