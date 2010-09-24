package managers;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.junit.Test;

import com.loquatic.cerescan.api.entities.Address;
import com.loquatic.cerescan.api.entities.Allergy;
import com.loquatic.cerescan.api.entities.Attorney;
import com.loquatic.cerescan.api.entities.Medication;
import com.loquatic.cerescan.api.entities.Physician;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.entities.lookups.Dosage;
import com.loquatic.cerescan.api.entities.lookups.EmploymentStatus;
import com.loquatic.cerescan.api.entities.lookups.Ethnicity;
import com.loquatic.cerescan.api.entities.lookups.Medicine;
import com.loquatic.cerescan.api.entities.lookups.Schedule;
import com.loquatic.cerescan.api.persistence.managers.CerescanPersistenceManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManagerFactory;

import entities.EntityGenerator;

public class SessionInfoManagerTests {
	
	@Test
	public void testSessionInfoManager() {
		SessionInfo ses = EntityGenerator.createRandomSessionInfo() ;
		CerescanPersistenceManager.persist( ses ) ;
		
		SessionInfoManager sim = SessionInfoManagerFactory.getInstance().getSession( ses.getId() ) ; 

	    Attorney att = EntityGenerator.createRandomAttorney() ;
	    
	    Address addr  = EntityGenerator.createRandomAddress() ;
	    
	    ArrayList<Address> addresses = new ArrayList<Address>() ;
	    addresses.add( addr ) ;
	    
	    att.setAddresses( addresses  ) ;
		
//		sim.addReferringAttorney( att ) ;
		
	}
	
	@Test
	public void testAddRemovePhysician() {
		Physician physician = EntityGenerator.createRandomPhysician() ;
		SessionInfo sesInfo = EntityGenerator.createRandomSessionInfo() ;
		CerescanPersistenceManager.persist( physician ) ;
		CerescanPersistenceManager.persist( sesInfo ) ;
		
		SessionInfoManager sim = SessionInfoManagerFactory.getInstance().getSession( sesInfo.getId() ) ;
		
//		sim.addReferringPhysician( physician ) ;
		
//		if( ! (physician == sim.getSessionInfo().getReferringPhysician() ) ) {
//			fail() ;
//		}
//		
//		sim.removePhysician() ;
//		
//		if( sim.getSessionInfo().getReferringPhysician() != null ) {
//			fail() ;
//		}
	}
	
	@Test
	public void testAddEthnicity() {
		Ethnicity eth = EntityGenerator.createRandomEthnicity() ;
		SessionInfo sesInfo = EntityGenerator.createRandomSessionInfo() ;
		CerescanPersistenceManager.persist( sesInfo ) ;
		
		SessionInfoManager sim = SessionInfoManagerFactory.getInstance().getSession( sesInfo.getId() ) ;
		sim.addEthnicity( eth ) ;
		
		if( sim.getSessionInfo().getEthnicity() != null ) {
			if( eth != sim.getSessionInfo().getEthnicity() ) {
				fail() ;
			}
		} else {
			fail() ;
		}
	}
	
	@Test
	public void testAddEthnicityByString() {
		SessionInfo sesInfo = EntityGenerator.createRandomSessionInfo() ;
		CerescanPersistenceManager.persist( sesInfo ) ;
		
		SessionInfoManager sim = SessionInfoManagerFactory.getInstance().getSession( sesInfo.getId() ) ;

		String ethnicityName = "Some New Ethnicity" ;
		sim.addEthnicity(ethnicityName ) ;
		
		Query q = CerescanPersistenceManager.getEntityManager().createNamedQuery( "Ethnicity.findByName" ) ;
		q.setParameter( "name", ethnicityName ) ;
		
		Ethnicity eth = (Ethnicity)q.getSingleResult() ;
		
		if( eth != sesInfo.getEthnicity() ) {
			fail() ;
		}	
	}
	
	@Test
	public void testAddingMedicationsByCollections() {
		SessionInfo sesInfo = EntityGenerator.createRandomSessionInfo() ;
		CerescanPersistenceManager.persist( sesInfo ) ;
		
		SessionInfoManager sim = SessionInfoManagerFactory.getInstance().getSession( sesInfo.getId() ) ;
		
		List<Medication> currentMeds = new ArrayList() ;
		List<Medication> pastMeds = new ArrayList() ;
		
		sim.addCurrentMedication( EntityGenerator.createRandomMedication()  ) ;
		sim.addPastMedication( EntityGenerator.createRandomMedication() ) ;
		
		if( currentMeds.size() != sim.getCurrentMedication().size() ) {
			fail() ;
		}
		
		if( pastMeds.size() != sim.getPastMedication().size() ) {
			fail() ;
		}
	}
	
	@Test
	public void testAddingEmploymentStatus() {
		SessionInfo sesInfo = EntityGenerator.createRandomSessionInfo() ;
		CerescanPersistenceManager.persist( sesInfo ) ;
		
		SessionInfoManager sim = SessionInfoManagerFactory.getInstance().getSession( sesInfo.getId() ) ;

		EmploymentStatus es = EntityGenerator.createRandomEmploymentStatus() ;
		
		sim.addEmploymentStatus( es ) ;
		
		if( es != sesInfo.getEmploymentStatus() ) {
			fail() ;
		}
	}
	
	@Test
	public void testAddingMedicinalAllergy() {
		SessionInfo sesInfo = EntityGenerator.createRandomSessionInfo() ;
		CerescanPersistenceManager.persist( sesInfo ) ;
		
		SessionInfoManager sim = SessionInfoManagerFactory.getInstance().getSession( sesInfo.getId() ) ;

		Allergy allergy = EntityGenerator.createRandomAllergy() ;
		sim.addMedicinalAllergy( allergy ) ;
		
		SessionInfo ses = sim.getSessionInfo() ;
		List<Allergy> medAllergies = sim.getMedicineAllergies() ;
		
		if( !medAllergies.contains( allergy ) ) {
			fail() ;
		}
	}

	@Test
	public void testAddingOtherAllergy() {
		SessionInfo sesInfo = EntityGenerator.createRandomSessionInfo() ;
		CerescanPersistenceManager.persist( sesInfo ) ;
		
		SessionInfoManager sim = SessionInfoManagerFactory.getInstance().getSession( sesInfo.getId() ) ;

		Allergy allergy = EntityGenerator.createRandomAllergy() ;
		sim.addOtherAllergy( allergy ) ;
		
		SessionInfo ses = sim.getSessionInfo() ;
		List<Allergy> medAllergies = sim.getOtherAllergies() ;
		
		if( !medAllergies.contains( allergy ) ) {
			fail() ;
		}
	}

}
