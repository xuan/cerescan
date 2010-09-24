package com.loquatic.cerescan.api.persistence.managers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.loquatic.cerescan.api.entities.Patient;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.controller.patient.EditPatientSessionDemographicsController;

/**
 * This is a manager class that provides instances of the SessionInfoManager
 * class.  This is a singleton, so you can call it statically:
 * <p>
 * <code>
 * SessionInfoManager sim = SessionInfoManagerFactory.getInstance().getSession( sessionId);<br>
 * </code>
 * <p>
 * 
 * @author jonsvede, Loquatic Software, LLC.
 *
 */
public class SessionInfoManagerFactory {
	private static Log log = LogFactory
	.getLog(SessionInfoManagerFactory.class);

	
	private static SessionInfoManagerFactory smf ;
	
	private static EntityManagerFactory factory ;
	
	private SessionInfoManagerFactory() {}
	
	public static SessionInfoManagerFactory getInstance() {
		if( smf == null ) {
			smf = new SessionInfoManagerFactory() ;
			factory = Persistence.createEntityManagerFactory("cerescan-mysql");
		}
		return smf ;
	}
	
	/**
	 * Returns an instance of the SessionInfoManager initialized with a 
	 * reference to the SessionInfo object specified by the long you pass in.
	 * 
	 * @param id
	 * @return
	 */
	public SessionInfoManager getSession( long id ) {
		SessionInfo ses = (SessionInfo)CerescanPersistenceManager.findById("SessionInfo.findById", id ) ;
		SessionInfoManager sim = new SessionInfoManager( ses.getId(), factory ) ;
		return sim ;
	}
	
	/**
	 * Using the Patient instance passed in, create a new SessionInfo instance
	 * and bind it to the Patient object. Saves the new instance in the DB.
	 * 
	 * @param patient
	 * @return
	 */
	public SessionInfoManager createSession( Patient patient ) {
		SessionInfo ses = new SessionInfo() ;
		CerescanPersistenceManager.persist( ses ) ;
		patient.addSession(ses ) ;
		CerescanPersistenceManager.merge( patient ) ;
		return new SessionInfoManager( ses.getId(), factory ) ; 
	}
	
	/**
	 * Using the long you pass in, this method finds the Patient instance associated
	 * with the id and creates a new SessionInfo object and binds it to this Patient
	 * instance.
	 * 
	 * @param patientId
	 * @return
	 */
	public SessionInfoManager createSession( long patientId ) {
		Patient patient = (Patient)CerescanPersistenceManager.findById("Patient.findById", patientId ) ; 
		SessionInfo ses = new SessionInfo() ;
		CerescanPersistenceManager.persist( ses ) ;
		patient.addSession(ses ) ;
		CerescanPersistenceManager.merge( patient ) ;
		return new SessionInfoManager( ses.getId(), factory ) ; 
	}
	
	public SessionInfoManager saveNewSession( Patient patient, SessionInfo session ) {
		EntityManager em = factory.createEntityManager() ;
		em.getTransaction().begin();
		if( session != null && session.getId() == 0 ) {
			em.persist( session ) ;
		} else {
		  em.merge(  session ) ;
		}
		patient.addSession( session ) ;
		em.merge( patient ) ;
		em.getTransaction().commit() ;
		em.close();
		em = null ;
		return new SessionInfoManager( session.getId(), factory ) ;		
	}
}
