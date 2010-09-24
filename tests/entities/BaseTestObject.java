package entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import com.loquatic.cerescan.api.entities.Address;
import com.loquatic.cerescan.api.entities.Patient;
import com.loquatic.cerescan.api.entities.lookups.ClinicalSite;
import com.loquatic.cerescan.api.persistence.managers.CerescanPersistenceManager;
import com.loquatic.cerescan.api.persistence.managers.PatientQueryManager;

public class BaseTestObject {
	
	private List<Patient> patients ;
	
	protected void loadPatient() {
		patients = new ArrayList<Patient>() ;
		EntityManager em = CerescanPersistenceManager.getEntityManager();
		em.getTransaction().begin();
		em.persist(createPatient("Road", "Runner", "M", "123" + (System.currentTimeMillis())));
		em.persist(createPatient("Wily", "Coyote", "M", "234" + (System.currentTimeMillis())));
		em.persist(createPatient("Yosemite", "Sam", "M", "345" + (System.currentTimeMillis())));
		em.persist(createPatient("Bugs", "Bunny", "M", "456" + (System.currentTimeMillis())));
		em.persist(createPatient("Foghorn", "Leghorn", "M", "567" + (System.currentTimeMillis())));
		em.persist(createPatient("Daffy", "Duck", "M", "678" + (System.currentTimeMillis())));
		em.getTransaction().commit();
	}
	
	private Patient createPatient( String firstName, String lastName,
			                       String gender, String mrNumber ) {
		Patient aPatient = new Patient() ;
		aPatient.setFirstName( firstName ) ;
		aPatient.setLastName( lastName ) ;
		aPatient.setGender( gender ) ;
		aPatient.setMrNumber( mrNumber ) ; 
		
		return aPatient ;
	}
	
	protected void deleteAllPatients() {
		List<Patient> patients = PatientQueryManager.findAll() ;
		Iterator<Patient> patientsIt = patients.iterator() ;
		while( patientsIt.hasNext() ) {
			CerescanPersistenceManager.delete( patientsIt.next() ) ;
		}
		//deleteAllRecords( "Patient" ) ;
	}

//	protected void deleteAllRecords( String tableName ) {
//		EntityManager em = CerescanPersistenceManager.getEntityManager();
//		em.getTransaction().begin();
//		int returnValue = em.createQuery( "delete from " + tableName+ " o" ).executeUpdate() ;
//		em.getTransaction().commit();
//	}
	
	protected void loadAddresses() {
		EntityManager em = CerescanPersistenceManager.getEntityManager();
		em.getTransaction().begin();
		em.persist( createAddress( "123 Some Pl", "Suite 5", "Anytown", "NY", "99999" ) ) ;
		em.persist( createAddress( "321 Any W.", "Suite 55", "Somewhere", "CA", "12345" ) ) ;
		em.persist( createAddress( "44 Tennis Ct", "P.O. Box 5", "Right Here", "MT", "55443" ) ) ;
		em.persist( createAddress( "5 Where Ev", "455", "Whatever", "NV", "65743" ) ) ;
		em.persist( createAddress( "3554 Rightuv Way", "#5", "Anyplace", "CA", "90210" ) ) ;
		em.getTransaction().commit();
		
	}
	
	private Address createAddress( String street1, String street2, String city, String state, String zipCode ) {
		Address address = new Address() ;
		address.setStreet1( street1 ) ;
		address.setStreet2( street2 ) ;
		address.setCity( city ) ;
		address.setState( state ) ;
		address.setZipCode( zipCode ) ;
		return address ;
	}
	
	public void loadClinicalSites() {
		EntityManager em = CerescanPersistenceManager.getEntityManager();
		em.getTransaction().begin();
		em.persist( createClinicalSite( "Site 1") ) ;
		em.persist( createClinicalSite( "Site 2") ) ;
		em.persist( createClinicalSite( "Site 3") ) ;
		em.persist( createClinicalSite( "Site 4") ) ;
		em.persist( createClinicalSite( "Site 5") ) ;
		em.getTransaction().commit();
	}
	
	private ClinicalSite createClinicalSite( String siteName ) {
		ClinicalSite clinicalSite = new ClinicalSite() ;
		clinicalSite.setName( siteName ) ;
		return clinicalSite ;
	}	
}
