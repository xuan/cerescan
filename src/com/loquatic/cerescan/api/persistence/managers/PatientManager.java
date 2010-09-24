package com.loquatic.cerescan.api.persistence.managers;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import com.loquatic.cerescan.api.entities.Address;
import com.loquatic.cerescan.api.entities.Attorney;
import com.loquatic.cerescan.api.entities.Patient;
import com.loquatic.cerescan.api.entities.Physician;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.entities.lookups.EducationLevel;
import com.loquatic.cerescan.api.entities.lookups.EmploymentStatus;
import com.loquatic.cerescan.api.entities.lookups.Ethnicity;
import com.loquatic.cerescan.api.entities.lookups.RelationshipStatus;

public class PatientManager  {
	
	private EntityManager entityMgr ;
	
	private Patient patient ;
	
	
	protected PatientManager( Patient p, EntityManager em ) {
		entityMgr = em ;
		patient = p ;
		if( patient.getId() == 0 ) {
			entityMgr.getTransaction().begin();
			entityMgr.persist( patient ) ;
			entityMgr.getTransaction().commit() ;
		} 
		
	}

	public Patient getPatient() {
		return patient ;
	}
	
	public void setCurrentAddress( Address addr ) {
		addr.setAsCurrentAddress( true ) ;
		Address oldCurrAddr = getCurrentAddress() ;
		oldCurrAddr.setAsCurrentAddress( false ) ;
		entityMgr.getTransaction().begin() ;
		entityMgr.merge( addr ) ;
		entityMgr.remove( oldCurrAddr ) ;
		entityMgr.getTransaction().commit() ;

	}
	
	public Address getCurrentAddress() {
		List<Address> addresses = patient.getAddresses() ;
		Iterator<Address> addrIt = addresses.iterator() ;
		while( addrIt.hasNext() ) {
			Address addr = addrIt.next() ;
			if( addr.isCurrentAddress() ) {
				return addr ;
			}
		}
		return null ;
	}
	
	public SessionInfo createSession() {
		SessionInfo ses = new SessionInfo() ;
		CerescanPersistenceManager.persist( ses ) ;
		patient.addSession(ses ) ;
		CerescanPersistenceManager.merge( patient ) ;
		return ses ; 
	}
	
	public void addSession( SessionInfo ses ) {
		if( ses.getId() <= 0 ) {
			CerescanPersistenceManager.persist( ses ) ;			
		}

		patient.addSession( ses ) ; 
		CerescanPersistenceManager.merge( patient ) ;
	}


	
	public SessionInfoManager getSessionInfoManager( long id ) {
		return SessionInfoManagerFactory.getInstance().getSession( id ) ;
	}
	
	public void save() {
		CerescanPersistenceManager.merge( patient ) ;
	}
	
}