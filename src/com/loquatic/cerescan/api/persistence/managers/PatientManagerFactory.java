package com.loquatic.cerescan.api.persistence.managers;
import javax.persistence.EntityManager;

import com.loquatic.cerescan.api.entities.Patient;


public class PatientManagerFactory {
	
	private EntityManager entityMgr ;
	
	private static PatientManagerFactory pmf ;
	
	private PatientManagerFactory() {
		entityMgr = CerescanPersistenceManager.getEntityManager() ;
	}
	
	public static PatientManagerFactory getInstance() {
		if( pmf == null ) {
			pmf = new PatientManagerFactory() ;
		}
		return pmf ;
	}
	
	public PatientManager createPatient( String firstName, String lastName, String mrNumber ) {
		
		Patient newPatient = new Patient() ;
		newPatient.setFirstName( firstName ) ;
		newPatient.setLastName( lastName ) ;
		newPatient.setMrNumber( mrNumber ) ;
		
		PatientManager patMgr = new PatientManager( newPatient, entityMgr ) ;
		
		return patMgr ;
			
 	}
	
	public PatientManager getPatient( long id ) {
		Patient p = (Patient)CerescanPersistenceManager.findById("Patient.findById", id ) ;
		PatientManager pMgr = new PatientManager( p, entityMgr ) ;
		return pMgr ;
	}
	
	public PatientManager getPatient( String mrNumber ) {
		Patient p  = (Patient)CerescanPersistenceManager.genericOneParamSingleResultFinder("Patient.findByMrNumber", "mrNumber", mrNumber ) ;
		PatientManager pMgr = new PatientManager( p, entityMgr ) ;
		return pMgr ;
	}
	
	

}
