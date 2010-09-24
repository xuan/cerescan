package com.loquatic.cerescan.controller.patient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;

import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.entities.lookups.DrugAbuse;
import com.loquatic.cerescan.api.entities.lookups.RecreationalDrug ;
import com.loquatic.cerescan.api.entities.lookups.Symptom ;
import com.loquatic.cerescan.api.persistence.managers.LookupEntityManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManagerFactory;
import com.loquatic.cerescan.common.Param;

public class EditPatientSubstanceUseController extends GenericForwardComposer {

	private SessionInfoManager sessionManager;
	private SessionInfo sessionInfo;
	private List<DrugAbuse> allDrugAbuse;
	private HashSet<DrugAbuse> selectedDrugAbuse;
	private List<RecreationalDrug> allRecreationalDrugs ;
	private HashSet<RecreationalDrug> selectedRecreationalDrugs = new HashSet<RecreationalDrug>();
	private HashSet<RecreationalDrug> selectedPastRecreationDrugs = new HashSet<RecreationalDrug>();

	private Label successLbl;
	private Listbox drugAbuseLb;

	public SessionInfo getSessionInfo() {
		return sessionInfo;
	}

	
	
	public HashSet<RecreationalDrug> getSelectedPastRecreationalDrugs() {
    return selectedPastRecreationDrugs ;
  }



  public void setSelectedPastRecreationalDrugs(
      HashSet<RecreationalDrug> selectedPastRecreationDrugs ) {
    this.selectedPastRecreationDrugs = selectedPastRecreationDrugs ;
  }



  public List<RecreationalDrug> getAllRecreationalDrugs() {
    return allRecreationalDrugs ;
  }


  public void setAllRecreationalDrugs( List<RecreationalDrug> allRecreationalDrugs ) {
    this.allRecreationalDrugs = allRecreationalDrugs ;
  }


  public HashSet<RecreationalDrug> getSelectedRecreationalDrugs() {
    return selectedRecreationalDrugs ;
  }


  public void setSelectedRecreationalDrugs( HashSet<RecreationalDrug> selectedRecreational ) {
    this.selectedRecreationalDrugs = selectedRecreational ;
  }


  public void setSessionInfo(SessionInfo sessionInfo) {
		this.sessionInfo = sessionInfo;
	}

	public List<DrugAbuse> getAllDrugAbuse() {
		return allDrugAbuse;
	}

	public void setAllDrugAbuse(List<DrugAbuse> allDrugAbuse) {
		this.allDrugAbuse = allDrugAbuse;
	}

	public HashSet<DrugAbuse> getSelectedDrugAbuse() {
		return selectedDrugAbuse;
	}

	public void setSelectedDrugAbuse(HashSet<DrugAbuse> selectedDrugAbuse) {
		this.selectedDrugAbuse = selectedDrugAbuse;
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
		allDrugAbuse = LookupEntityManager.getAllDrugAbuse();
		allRecreationalDrugs = LookupEntityManager.getAllRecreationalDrug() ;
		
		if (sessionInfo.getDrugAbuse() != null) {
			selectedDrugAbuse = new HashSet<DrugAbuse>(sessionInfo
					.getDrugAbuse());
		}
		if( sessionInfo.getRecreationDrugs() != null  ) {
		  selectedRecreationalDrugs = new HashSet( sessionInfo.getRecreationDrugs() ) ;
		}
		if( sessionInfo.getPastRecreationalDrugs() != null ) {
		  selectedPastRecreationDrugs = new HashSet( sessionInfo.getPastRecreationalDrugs() ) ;
		}

	}

	public void onClick$updateBtn(MouseEvent event) {
		if (selectedDrugAbuse != null) {
			sessionInfo
					.setDrugAbuse(new ArrayList<DrugAbuse>(selectedDrugAbuse));
		} else{
			sessionInfo
			.setDrugAbuse(new ArrayList<DrugAbuse>());
		}
		
		if( selectedRecreationalDrugs!= null ) {
		  sessionInfo.setRecreationDrugs( new ArrayList<RecreationalDrug>(selectedRecreationalDrugs) ) ;
		} else {
		  sessionInfo.setRecreationDrugs( new ArrayList<RecreationalDrug>()) ;
		}
		
		if( selectedPastRecreationDrugs != null ) {
		  sessionInfo.setPastRecreationalDrugs( new ArrayList<RecreationalDrug>( selectedPastRecreationDrugs ) ) ;
		} else {
		  sessionInfo.setPastRecreationalDrugs( new ArrayList<RecreationalDrug>() ) ;
		}
		
		sessionManager.save(sessionInfo);
		successLbl.setValue("Substance Use has been saved");
	}
}
