package entities;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.loquatic.cerescan.api.entities.Allergy;
import com.loquatic.cerescan.api.entities.Attorney;
import com.loquatic.cerescan.api.entities.Patient;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.entities.lookups.BirthTrauma;
import com.loquatic.cerescan.api.entities.lookups.ClinicalSite;
import com.loquatic.cerescan.api.persistence.managers.CerescanPersistenceManager;
import com.loquatic.cerescan.api.persistence.managers.PatientManager;
import com.loquatic.cerescan.api.persistence.managers.PatientManagerFactory;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManagerFactory;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoQueryManager;

public class SessionInfoTests {
	
	@Test
	public void testNewSession() {
		
		EntityGenerator.getInstance();
		Patient aPatient = EntityGenerator.createRandomPatient() ;
		CerescanPersistenceManager.persist( aPatient ) ;
		
		SessionInfo session = EntityGenerator.createRandomSessionInfo() ;

		CerescanPersistenceManager.persist( session ) ;
		
		aPatient.addSession( session );
		
		CerescanPersistenceManager.merge( aPatient ) ;
		
		long id = aPatient.getId() ;
		
		if( id == 0 ) {
			fail() ;
		}
		
		PatientManager pm = PatientManagerFactory.getInstance().getPatient(id) ;
		Patient p = pm.getPatient() ;
		if( p != aPatient ) {
			fail() ;
		}
		
		SessionInfo ses = p.getSessions().get( 0 ) ;
		
		if( ses != session ) {
			fail() ;
		}	
		
	}
	
	@Test
	public void testAddingClinicalSiteToSession() {
		
		EntityGenerator.getInstance() ;
		SessionInfo sessionInfo = EntityGenerator.createRandomSessionInfo() ;
		List<ClinicalSite> sites = new ArrayList<ClinicalSite>() ;
		sites.add( EntityGenerator.createRandomClinicalSite() ) ;
		sites.add( EntityGenerator.createRandomClinicalSite() ) ;
		sites.add( EntityGenerator.createRandomClinicalSite() ) ;
		sites.add( EntityGenerator.createRandomClinicalSite() ) ;
		sessionInfo.setClinicalSites( sites ) ;
		
		CerescanPersistenceManager.persist( sessionInfo ) ;

		long sessionId = sessionInfo.getId() ;

		SessionInfo newSes = SessionInfoQueryManager.findById( sessionId ) ;
		
		if( newSes != sessionInfo ) {
			fail() ;
		}
		
		if( newSes.getClinicalSites() == null ) {
			fail() ;
		} else {
			if( newSes.getClinicalSites().size() !=4 ) {
				fail() ;
			}
		}
	}
	
	@Test
	public void testBirthTrauma() {
		SessionInfo ses = EntityGenerator.getInstance().createRandomSessionInfo() ;
		BirthTrauma bt = EntityGenerator.createRandomBirthTrauma() ;
		List<BirthTrauma> bts = new ArrayList<BirthTrauma>() ;
		CerescanPersistenceManager.persist( bt ) ;
		bts.add( bt ) ;
		ses.setBirthTraumas(bts ) ;
		CerescanPersistenceManager.persist( ses ) ;
	}
	
//	@Test
//	public void testAddingAttorney() {
//		SessionInfo sessionInfo = EntityGenerator.getInstance().createRandomSessionInfo() ;
//		Attorney att = EntityGenerator.createRandomAttorney() ;
//		sessionInfo.setReferringAttorney( att ) ;
//		CerescanPersistenceManager.persist( sessionInfo ) ;
//	}
//	
//	@Test 
//	public void testRemovingAttorney() {
//		SessionInfo sesInfo = EntityGenerator.createRandomSessionInfo() ;
//		CerescanPersistenceManager.persist( sesInfo ) ;
//		SessionInfoManager sim = SessionInfoManagerFactory.getInstance().getSession(sesInfo.getId()) ;
//		Attorney atty = EntityGenerator.getRandomAttorney() ;
//		sim.addReferringAttorney( atty ) ;
//		//sim.save() ;
//		sim.removeAttorney() ;
//		
//		Attorney att = sim.getSessionInfo().getReferringAttorney() ;
//		
//		if( att != null ) {
//			fail() ;
//		}
//	}
	
	@Test
	public void testAddingAllergies() {
		SessionInfo sessionInfo = EntityGenerator.getInstance().createRandomSessionInfo() ;
		CerescanPersistenceManager.persist( sessionInfo ) ;
		SessionInfoManager sim = SessionInfoManagerFactory.getInstance().getSession( sessionInfo.getId() ) ;
		
		Allergy allg = new Allergy() ;
		allg.setDescription( "cats" ) ;
		sim.addOtherAllergy( allg ) ;
		
		if( allg.getId() == 0 ) {
			fail() ;
		}
		
		allg = new Allergy() ;
		allg.setDescription( "Valium" ) ;
		sim.addMedicinalAllergy( allg ) ;
		
		if( allg.getId() == 0 ) {
			fail() ;
		}

		List<Allergy> medAllg = sim.getMedicineAllergies() ;
		if( medAllg == null ) {
			fail() ;
		} else if( medAllg.size() != 1 ) {
			fail() ;
		}
		
	}
}
