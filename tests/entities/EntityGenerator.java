package entities;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.persistence.Query;

import com.loquatic.cerescan.api.entities.Address;
import com.loquatic.cerescan.api.entities.Allergy;
import com.loquatic.cerescan.api.entities.Attorney;
import com.loquatic.cerescan.api.entities.Medication;
import com.loquatic.cerescan.api.entities.Patient;
import com.loquatic.cerescan.api.entities.Physician;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.entities.lookups.BirthTrauma;
import com.loquatic.cerescan.api.entities.lookups.ClinicalSite;
import com.loquatic.cerescan.api.entities.lookups.Dosage;
import com.loquatic.cerescan.api.entities.lookups.EducationLevel;
import com.loquatic.cerescan.api.entities.lookups.EmploymentStatus;
import com.loquatic.cerescan.api.entities.lookups.Ethnicity;
import com.loquatic.cerescan.api.entities.lookups.InjuryType;
import com.loquatic.cerescan.api.entities.lookups.Medicine;
import com.loquatic.cerescan.api.entities.lookups.RelationshipStatus;
import com.loquatic.cerescan.api.entities.lookups.Schedule;
import com.loquatic.cerescan.api.persistence.managers.CerescanPersistenceManager;

/**
 * A test class that is used to generate object instances but not persist them.
 * The purpose is to support the various persistence tests without actually
 * exercising the persistence here - so differing that to the test.
 * 
 * @author jonsvede
 * 
 */
public class EntityGenerator {

	private static Random random ;
	
	private static EntityGenerator self ;
	
	private EntityGenerator() {
		if( random == null ) {
			random = new Random( System.currentTimeMillis()) ;
		}
		
	}
	
	public static EntityGenerator getInstance() {
		if( self == null ) {
			self = new EntityGenerator() ;
		}
		return self ;
	}
	
	public static Patient createRandomPatient() {
		
		return createPatient(generateRandomString(), 
				             generateRandomString(), 
				             "M", 
				             getSmallId( "123" ) ) ;
	}
	
	public static SessionInfo createRandomSessionInfo() {
		
		SessionInfo session = new SessionInfo() ;
		session.setCombatDate( generateRandomString() ) ;
		session.setCombatHistory(generateRandomString() ) ;
		session.setCombatLocation(generateRandomString() ) ;
		session.setEyeDominance( "R" )  ;
		session.setHandDominance( "L" ) ;
 		return session ;
	}
	
	public static ClinicalSite createRandomClinicalSite() {
		ClinicalSite cs = new ClinicalSite() ;
		cs.setName( generateRandomString() ) ;
		return cs ;
	}
	
	public static InjuryType createRandomInjuryType() {
		InjuryType it = new InjuryType() ;
		it.setName( generateRandomString() ) ;

		return it ;
	}
	
	public static Ethnicity createRandomEthnicity() {
		Ethnicity ethnicity = new Ethnicity() ;
		ethnicity.setName( generateRandomString() ) ;
		return ethnicity ;
	}
	
	public static EducationLevel createRandomEducationLevel() {
		EducationLevel edLevel = new EducationLevel() ;
		edLevel.setName( generateRandomString() ) ; 
		return edLevel ;
	}
	
	public static RelationshipStatus createRandomRelationshipStatus() {
		RelationshipStatus relationshipStatus = new RelationshipStatus() ;
		relationshipStatus.setName( generateRandomString() ) ; 
		return relationshipStatus ;	
	}
	
	public static BirthTrauma createRandomBirthTrauma() {
		BirthTrauma bt = new BirthTrauma() ;
		bt.setName( generateRandomString() ) ;
		return bt ;
	}
	
	public static Attorney createRandomAttorney() {
		Attorney att = new Attorney() ;
		att.setFirstName(generateRandomString() ) ;
		att.setLastName( generateRandomString() ) ;
		att.setFirmName(generateRandomString() ) ;
		
		return att ;
	}
	
	public static Attorney getRandomAttorney() {
		Query getAttorneyQuery = CerescanPersistenceManager.getEntityManager().createNamedQuery( "Attorney.findAll" ) ;
		List<Attorney> attorneys = getAttorneyQuery.getResultList() ;
		int idx = getRandomIndex( attorneys.size()) ;
		return attorneys.get( idx ) ;
		
	}
	
	private static int getRandomIndex( int listSize ) {
		Random random = new Random() ;
		return random.nextInt( listSize - 1 ) ;
	}

	public static Address createRandomAddress() {
		Address addr = new Address() ;
		addr.setStreet1( generateRandomString() ) ;
		addr.setStreet2( generateRandomString() ) ;
		addr.setCity( generateRandomString() ) ;
		addr.setState( "NY" ) ;
		addr.setZipCode("12345" ) ;
		
		return addr ;
	}
	
	private static Patient createPatient(String firstName, String lastName,
			String gender, String mrNumber) {
		Patient aPatient = new Patient();
		aPatient.setFirstName(firstName);
		aPatient.setLastName(lastName);
		aPatient.setGender(gender);
		aPatient.setMrNumber(mrNumber);

		return aPatient;
	}
	
	public static Physician createRandomPhysician() {
		Physician phys = new Physician() ;
		phys.setFirstName( generateRandomString() ) ;
		phys.setLastName( generateRandomString() ) ;
		phys.setEmail1( generateRandomString() ) ;
		phys.setHomePhone(generateRandomString() ) ;
		
		return phys ;
	}
	
    public static Patient getRandomPatient() {
    	Query q = CerescanPersistenceManager.getEntityManager().createNamedQuery( "Patient.findAll" ) ;
    	List<Patient> patients = q.getResultList() ;
    	return patients.get( getRandomIndex( patients.size() ) ) ;
    }
    
    public static Medication createRandomMedication() {
    	Dosage d = new Dosage() ;
    	d.setName( generateRandomString() ) ;
    	Schedule s = new Schedule() ;
    	s.setName( generateRandomString() ) ;
    	Medicine m = new Medicine() ;
    	m.setName( generateRandomString()  ) ;
    	
    	Medication med = new Medication( d, s, m ) ;
    	
    	return med ;
    }
    
    public static EmploymentStatus createRandomEmploymentStatus() {
    	EmploymentStatus empStatus = new EmploymentStatus() ;
    	empStatus.setName( generateRandomString() ) ;
    	return empStatus ;
    }
    
    public static Allergy createRandomAllergy() {
    	Allergy allergy = new Allergy() ;
    	allergy.setDescription( generateRandomString() ) ;
    	return allergy ;
    }
	
	public static String getBigId(String ident) {
		String rs = "" + Math.random();
		rs = rs.substring(rs.indexOf(".") + 1);
		int end = rs.indexOf("E");
		if (end == -1) {
			end = rs.length();
		}
		rs = rs.substring(0, end);
		long last2 = (long) System.currentTimeMillis();
		String s = ident + "-"
				+ java.math.BigInteger.valueOf(last2).toString(36)
				+ new java.math.BigInteger(rs).toString(36);

		return s.toUpperCase();
	}

	public static String getSmallId(String ident) {
		String rs = "" + Math.random();
		rs = rs.substring(rs.indexOf(".") + 1);
		int end = rs.indexOf("E");

		if (end == -1) {
			end = rs.length();
		}
		rs = rs.substring(0, end);
		long last2 = (long) System.currentTimeMillis();
		java.math.BigInteger bi1 = java.math.BigInteger.valueOf(last2);
		java.math.BigInteger bi2 = new java.math.BigInteger(rs);

		java.math.BigInteger bi3 = bi1.divide(java.math.BigInteger
				.valueOf(100000l));
		java.math.BigInteger bi4 = bi2.divide(java.math.BigInteger
				.valueOf(100000000l));

		bi3.abs();
		bi4.abs();

		String s = ident + bi3.toString(36) + bi4.toString(36);

		return s.toUpperCase();
	}
	
	public static String generateRandomString() {
		return UUID.randomUUID().toString();
	}

}
