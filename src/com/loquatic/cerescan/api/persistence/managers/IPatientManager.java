package com.loquatic.cerescan.api.persistence.managers;

import com.loquatic.cerescan.api.entities.Address;
import com.loquatic.cerescan.api.entities.Attorney;
import com.loquatic.cerescan.api.entities.Patient;
import com.loquatic.cerescan.api.entities.Physician;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.entities.lookups.EducationLevel;
import com.loquatic.cerescan.api.entities.lookups.EmploymentStatus;
import com.loquatic.cerescan.api.entities.lookups.Ethnicity;
import com.loquatic.cerescan.api.entities.lookups.RelationshipStatus;

public interface IPatientManager {

	public abstract Patient getPatient();

	public abstract void setCurrentAddress(Address addr);

	public abstract Address getCurrentAddress();

	public abstract SessionInfo createSession();

	public abstract void addSession(SessionInfo ses);

	public abstract void addReferringAttorney(long sessionId, Attorney atty);

	public abstract void addReferringAttorney(SessionInfo ses, Attorney atty);

	public abstract void addReferringPhysician(long sessionId, Physician phys);

	public abstract void addReferringPhysician(SessionInfo ses, Physician phys);

	public abstract void addEthnicity(long sessionId, Ethnicity eth);

	public abstract void addEthnicity(SessionInfo ses, Ethnicity eth);

	public abstract void addRelationshipStatus(long sessionId,
			RelationshipStatus rs);

	public abstract void addRelationshipStatus(SessionInfo ses,
			RelationshipStatus rs);

	public abstract void addEducationLevel(long sessionId, EducationLevel edLvl);

	public abstract void addEducationLevel(SessionInfo ses, EducationLevel edLvl);

	public abstract void addEmploymentStatus(long sessionId,
			EmploymentStatus empStatus);

	public abstract void addEmploymentStatus(SessionInfo ses,
			EmploymentStatus empStatus);
	
	public void save()  ;
	
	public SessionInfoManager getSessionInfoManager( long id ) ;

}