package entities;

import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.loquatic.cerescan.api.entities.Address;
import com.loquatic.cerescan.api.entities.Patient;
import com.loquatic.cerescan.api.persistence.managers.CerescanPersistenceManager;
import com.loquatic.cerescan.api.persistence.managers.PatientQueryManager;

public class PatientTests extends BaseTestObject {
	
	@Before
	public void init() {
		loadPatient() ;
	}
	
	@After
	public void delete() {
		deleteAllPatients() ;
	}
			
	@Test
	public void testFindByMrLastName() {
		
		String lastName = "Coyote" ;
		List<Patient> patients = PatientQueryManager.findByLastName( lastName ) ;
		
		if( patients != null ) {
			Iterator<Patient> patientsIt = patients.iterator() ;
 			if( patients.size() != 1 ) {
 				fail() ; 
 			}
 			while( patientsIt.hasNext() ) {
				Patient p = patientsIt.next() ;
				if( !p.getLastName().equals( lastName ) ) {
					fail() ;
				} 
			} 
		}	
	}
	
	@Test
	public void testAddPatientAddress() {
		loadAddresses() ;
		
		EntityManager em = CerescanPersistenceManager.getEntityManager() ;
		
		List<Address> addresses = em.createNamedQuery("Address.findAll").getResultList() ;
		
		Patient aPatient = PatientQueryManager.findByLastName( "Sam" ).get(0) ;
		
		long patientId = aPatient.getId() ;
		
		Address anAddress = addresses.get(0) ;
		
		aPatient.addAddress( anAddress ) ;
		
		em.getTransaction().begin();
		em.merge( aPatient ) ;
		em.getTransaction().commit();
		
		Patient verifyPatient = PatientQueryManager.findById( patientId ) ;
		
		List<Address> patientAddresses = verifyPatient.getAddresses() ;
		
		if( patientAddresses == null || 
		  ( patientAddresses != null && patientAddresses.size() !=1  ) ) {
			fail() ;
		}
	}
	
	@Test
	public void testGetCurrentAddress() {
		EntityManager em = CerescanPersistenceManager.getEntityManager() ;
	
		List<Address> addresses ;
	}
	


}

