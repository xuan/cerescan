package entities;

import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.loquatic.cerescan.api.entities.Allergy;
import com.loquatic.cerescan.api.entities.lookups.BirthTrauma;
import com.loquatic.cerescan.api.entities.lookups.ClinicalSite;
import com.loquatic.cerescan.api.entities.lookups.DevelopmentMilestone;
import com.loquatic.cerescan.api.entities.lookups.DevelopmentTrauma;
import com.loquatic.cerescan.api.entities.lookups.Dosage;
import com.loquatic.cerescan.api.entities.lookups.EducationLevel;
import com.loquatic.cerescan.api.entities.lookups.Ethnicity;
import com.loquatic.cerescan.api.entities.lookups.IncomingDiagnoses;
import com.loquatic.cerescan.api.entities.lookups.InjuryType;
import com.loquatic.cerescan.api.entities.lookups.Medicine;
import com.loquatic.cerescan.api.entities.lookups.RelationshipStatus;
import com.loquatic.cerescan.api.entities.lookups.Schedule;
import com.loquatic.cerescan.api.entities.lookups.Symptom;
import com.loquatic.cerescan.api.entities.lookups.TraumaticBrainInjuryType;
import com.loquatic.cerescan.api.persistence.managers.LookupEntityManager;

public class LookupTests extends BaseTestObject {
	
	@Test
	public void testBirthTrauma() {
		List<BirthTrauma> traumas = LookupEntityManager.getAllBirthTrauma() ;
		if( !( verifyCollectionSize( traumas, "birth_trauma_lookup" ) ) ) {
			fail() ;
		}
	}
	
	@Test
	public void testClinicalSites() {
		List<ClinicalSite>sites = LookupEntityManager.getAllClinicalSites() ;
		if( !( verifyCollectionSize( sites, "clinical_site" ) ) ) {
			fail() ;
		}
	}
	
	@Test
	public void testDevelopmentMilestones() {
		List<DevelopmentMilestone> milestones = LookupEntityManager.getAllDevelopmentMilestones() ;
		if( !( verifyCollectionSize( milestones, "development_milestone_lookup" ) ) ) {
			fail() ;
		}
	}
	
	@Test
	public void testDevelopmentTrauma() {
		List<DevelopmentTrauma> traumas = LookupEntityManager.getAllDevelopmentTrauma() ;
		if( !( verifyCollectionSize( traumas, "development_trauma_lookup" ) ) ) {
			fail() ;
		}
	}
	
	@Test
	public void testDosages() {
		List<Dosage> dosages = LookupEntityManager.getAllDosages() ;
		if( !( verifyCollectionSize( dosages, "dosage_lookup" ) ) ) {
			fail() ;
		}
	}
	
	@Test
	public void testEducationlevels() {
		List<EducationLevel> levels = LookupEntityManager.getAllEducationLevels() ;
		if( !( verifyCollectionSize( levels, "education_level_lookup" ) ) ) {
			fail() ;
		}
	}
	
	@Test
	public void testEthnicities() {
		List<Ethnicity> ethnicities = LookupEntityManager.getAllEthnicities() ;
		if( !( verifyCollectionSize( ethnicities, "ethnicity_lookup" ) ) ) {
			fail() ;
		}
	}
	
	@Test
	public void testIncomingDiagnosis() {
		List<IncomingDiagnoses> diagnoses = LookupEntityManager.getAllIncomingDiagnoses() ;
		if( !( verifyCollectionSize( diagnoses, "incoming_diagnoses_lookup" ) ) ) {
			fail() ;
		}		
	}
	
	@Test
	public void testInjuryTypes() {
		List<InjuryType> injuryTypes = LookupEntityManager.getAllInjuryTypes() ;
		if( !( verifyCollectionSize( injuryTypes, "injury_type" ) ) ) {
			fail() ;
		}		
	}
	
	@Test
	public void testMedicines() {
		List<Medicine> meds = LookupEntityManager.getAllMedicines() ;
		if( !( verifyCollectionSize( meds, "medicine_lookup" ) ) ) {
			fail() ;
		}		
	}
	
	@Test
	public void testRelationshipStatuses() {
		List<RelationshipStatus> relationshipStatuses = LookupEntityManager.getAllRelationshipStatuses() ;
		if( !( verifyCollectionSize( relationshipStatuses, "relationships_status_lookup" ) ) ) {
			fail() ;
		}		
	}
	
	@Test
	public void testSchedules() {
		List<Schedule> schedules = LookupEntityManager.getAllSchedules() ;
		if( !( verifyCollectionSize( schedules, "schedule_lookup" ) ) ) {
			fail() ;
		}		
	}
	
	@Test
	public void testSymptoms() {
		List<Symptom> symptoms = LookupEntityManager.getAllSymptoms() ;
		if( !( verifyCollectionSize( symptoms, "symptom_lookup" ) ) ) {
			fail() ;
		}		
	}
	
	@Test
	public void testTraumaticBrainInjury() {
		List<TraumaticBrainInjuryType> traumaticBrainInjury = LookupEntityManager.getAllTraumaticBrainInjuries() ;
		if( !( verifyCollectionSize( traumaticBrainInjury, "traumatic_brain_injury_lookup" ) ) ) {
			fail() ;
		}		
	}
	
	@Test
	public void testOtherAllergies() {
		List<Allergy> allergies = LookupEntityManager.getAllOtherAllergies() ;
		if( !verifyCollectionSize(allergies, "allergy_lookup where allergy_type_id=1" ) ) {
			fail() ;
		}
	}

	@Test
	public void testMedicinalAllergies() {
		List<Allergy> allergies = LookupEntityManager.getAllMedicinalAllergies() ;
		if( !verifyCollectionSize(allergies, "allergy_lookup where allergy_type_id=2" ) ) {
			fail() ;
		}
	}

	private boolean verifyCollectionSize( List rows, String tableName ) {
		try {
			int rowCount = TableRowCountVerifier.getRowCount( tableName ) ;
			if( rows == null ) {
				return false ;
			} else {
				if( rows.size() != rowCount ) {
					return false ;
				} else {
					System.out.println( tableName + " objects " + rows.size() + " and " + rowCount + " rows" ) ;
					return true ;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false ;
		}
		
	}

}
