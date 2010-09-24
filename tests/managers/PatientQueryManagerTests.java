package managers;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.loquatic.cerescan.api.entities.Patient;
import com.loquatic.cerescan.api.persistence.managers.PatientManager;
import com.loquatic.cerescan.api.persistence.managers.PatientManagerFactory;
import com.loquatic.cerescan.api.persistence.managers.PatientQueryManager;

public class PatientQueryManagerTests {

	@Test
	public void testFindByLastName() {
		
		PatientManagerFactory pmf = PatientManagerFactory.getInstance() ;
		PatientManager pm = pmf.createPatient("Bob", "Brass", ("SS" + System.currentTimeMillis()) ) ;
		
		List<Patient> patients = PatientQueryManager.findByLastName( "Br" ) ;
		
		Iterator<Patient> pat = patients.iterator() ;
		
		while( pat.hasNext() ) {
			Patient p = pat.next();
			System.out.println( p ) ;
		}
	}
}
