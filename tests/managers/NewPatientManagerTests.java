package managers;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import com.loquatic.cerescan.api.entities.Patient;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.entities.lookups.EducationLevel;
import com.loquatic.cerescan.api.entities.lookups.RelationshipStatus;
import com.loquatic.cerescan.api.persistence.managers.IPatientManager;
import com.loquatic.cerescan.api.persistence.managers.PatientManager;
import com.loquatic.cerescan.api.persistence.managers.PatientManagerFactory;

import entities.EntityGenerator;

public class NewPatientManagerTests {
	
	@Test
	public void testCreatingInstance() {
		
		PatientManagerFactory pmf = PatientManagerFactory.getInstance() ;

		if( pmf == null ) {
			fail() ;
		}

		PatientManager pm = pmf.createPatient("Bob", "Brass", "1s4n4ssh0l3" ) ;

		if( pm == null ) {
			fail() ;
		}
		
		Patient p = pm.getPatient() ;
		
		if( p == null ) {
			fail() ;
		}
		
		if( p.getId() <= 0 ) {
			fail() ;
		}

	}
	
	@Test
	public void testCreatingASession() {
		PatientManagerFactory pmf = PatientManagerFactory.getInstance() ;
		PatientManager pm = pmf.createPatient("Bob", "Brass", "5uck5d1ck5" ) ;
		
	    SessionInfo ses  = pm.createSession() ;
	    
	    if( ses == null ) {
	    	fail() ;
	    }
	    
	    if( ses.getId() <= 0 ) {
	    	fail() ;
	    }

	    Patient p = pm.getPatient() ;
	    
	    List<SessionInfo> sessions = p.getSessions() ;
	    
	    if( sessions == null ) {
	    	fail() ;
	    }
	    
	    if( sessions.size() <=0 ) {
	    	fail() ;
	    }
	    
	}

	
	@Test
	public void testAddingRelationshipStatus() {
		PatientManagerFactory pmf = PatientManagerFactory.getInstance() ;
		
		Patient p = EntityGenerator.getRandomPatient() ;
		
		PatientManager pm = pmf.getPatient( p.getId() ) ;

		SessionInfo ses = pm.createSession() ;
		
		RelationshipStatus rs = EntityGenerator.createRandomRelationshipStatus() ;
		
		pm.getSessionInfoManager(ses.getId()).addRelationshipStatus( rs ) ;
		
		if( rs.getId() <= 0 ) {
			fail() ;
		}
		
		if( rs != ses.getRelationshipStatus() ) {
			fail() ;
		}
	}
	
	@Test
	public void testAddingEducationLevel() {
		PatientManagerFactory pmf = PatientManagerFactory.getInstance() ;
		
		Patient p = EntityGenerator.getRandomPatient() ;
		
		PatientManager pm = pmf.getPatient( p.getId() ) ;

		SessionInfo ses = pm.createSession() ;

		EducationLevel edLvl = EntityGenerator.createRandomEducationLevel() ;
		
		pm.getSessionInfoManager(ses.getId()).addEducationLevel( edLvl ) ;
		
		if( edLvl.getId() <= 0 ) {
			fail() ;
		}
		
		if( edLvl != ses.getEducationLevel() ) {
			fail() ;
		}
	}

}
