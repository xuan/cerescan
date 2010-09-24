package com.loquatic.cerescan.api.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.loquatic.cerescan.api.entities.lookups.ScannedFormType;


@Entity
@Table(name="scanned_form")
@NamedQueries({
	@NamedQuery(name="ScannedForm.findAll",
			    query="select object(s) from ScannedForm s"),
	@NamedQuery(name="ScannedForm.findById",
			    query="select object(s) from ScannedForm s where s.id=:id"),
    @NamedQuery(name="ScannedForm.findBySessionId",
    		    query="select object(s) from ScannedForm s join s.sessionInfo as i where i.id=s.id and i.id=:sid")
})
public class ScannedForm extends UploadEntity implements IAuditable {

	@ManyToOne( cascade=CascadeType.ALL)
	@JoinColumn(name="scanned_form_type_id", referencedColumnName="id") 
    private ScannedFormType scannedFormType ;

	@ManyToOne( cascade=CascadeType.ALL)
	@JoinColumn(name="session_info_id", referencedColumnName="id") 
    private SessionInfo sessionInfo ;


	
	public ScannedFormType getScannedFormType() {
		return scannedFormType;
	}

	public void setScannedFormType(ScannedFormType scannedFormType) {
		this.scannedFormType = scannedFormType;
	}

	public SessionInfo getSessionInfo() {
		return sessionInfo;
	}

	public void setSessionInfo(SessionInfo sessionInfo) {
		this.sessionInfo = sessionInfo;
	}

	@Override
	public String toString() {
		return log() ;
	}
	
	public String log() {
				return "ScannedForm [scannedFormType=" + scannedFormType
				+ ", createdDate="
				+ createdDate + ", id=" + id + ", lastModified=" + lastModified + "]";
	}


	
}
