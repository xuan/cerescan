package com.loquatic.cerescan.api.persistence.managers;

import java.util.List ;

import com.loquatic.cerescan.api.entities.Allergy ;
import com.loquatic.cerescan.api.entities.lookups.BirthTrauma ;
import com.loquatic.cerescan.api.entities.lookups.BrainArea ;
import com.loquatic.cerescan.api.entities.lookups.BrainHemisphere ;
import com.loquatic.cerescan.api.entities.lookups.BsInjectionSite ;
import com.loquatic.cerescan.api.entities.lookups.ClinicalSite ;
import com.loquatic.cerescan.api.entities.lookups.ConcentrationTaskType ;
import com.loquatic.cerescan.api.entities.lookups.CsInjectionSite ;
import com.loquatic.cerescan.api.entities.lookups.Degree ;
import com.loquatic.cerescan.api.entities.lookups.DevelopmentMilestone ;
import com.loquatic.cerescan.api.entities.lookups.DevelopmentTrauma ;
import com.loquatic.cerescan.api.entities.lookups.Dosage ;
import com.loquatic.cerescan.api.entities.lookups.DrugAbuse ;
import com.loquatic.cerescan.api.entities.lookups.EducationLevel ;
import com.loquatic.cerescan.api.entities.lookups.Effective ;
import com.loquatic.cerescan.api.entities.lookups.EmploymentStatus ;
import com.loquatic.cerescan.api.entities.lookups.Ethnicity ;
import com.loquatic.cerescan.api.entities.lookups.FamilyMemberType ;
import com.loquatic.cerescan.api.entities.lookups.IncomingDiagnoses ;
import com.loquatic.cerescan.api.entities.lookups.InjuryType ;
import com.loquatic.cerescan.api.entities.lookups.LossConsciousness ;
import com.loquatic.cerescan.api.entities.lookups.Medicine ;
import com.loquatic.cerescan.api.entities.lookups.Mini ;
import com.loquatic.cerescan.api.entities.lookups.OtherImagingType ;
import com.loquatic.cerescan.api.entities.lookups.OutgoingDiagnoses ;
import com.loquatic.cerescan.api.entities.lookups.PhysicalInjury ;
import com.loquatic.cerescan.api.entities.lookups.PsychometricAssessmentType ;
import com.loquatic.cerescan.api.entities.lookups.ReadingPhysician ;
import com.loquatic.cerescan.api.entities.lookups.RecreationalDrug ;
import com.loquatic.cerescan.api.entities.lookups.RelationshipStatus ;
import com.loquatic.cerescan.api.entities.lookups.ScannedFormType ;
import com.loquatic.cerescan.api.entities.lookups.Schedule ;
import com.loquatic.cerescan.api.entities.lookups.Symptom ;
import com.loquatic.cerescan.api.entities.lookups.TraumaticAmnesia ;
import com.loquatic.cerescan.api.entities.lookups.TraumaticBrainInjuryType ;
import com.loquatic.cerescan.api.entities.lookups.VeteranStatus;

/**
 * This is a utility class that provides access to the various lookup tables and
 * abstracts the calls to the JPA EntityManager.
 * <p>
 * This class also provides methods for creating new lookup values for certain
 * tables.
 * 
 * @author jsvede, Loquatic Software, LLC.
 * 
 */
public class LookupEntityManager extends CerescanPersistenceManager {

	private LookupEntityManager() {
	}

	
	public static List<BrainHemisphere> getAllBrainHemispheres() {
	  List<BrainHemisphere> hemis = genericNoParamFinder( "BrainHemisphere.findAll" ) ;
	  return hemis ;
	}
	
	public static List<Ethnicity> getAllEthnicities() {
		List<Ethnicity> ethnicities = genericNoParamFinder("Ethnicity.findAll");
		return ethnicities;
	}

	public static List<RelationshipStatus> getAllRelationshipStatuses() {
		List<RelationshipStatus> relStatuses = genericNoParamFinder("RelationshipStatus.findAll");
		return relStatuses;
	}

	public static List<EducationLevel> getAllEducationLevels() {
		List<EducationLevel> edLevels = genericNoParamFinder("EducationLevel.findAll");
		return edLevels;
	}

	public static List<EmploymentStatus> getAllEmploymentStatus() {
		List<EmploymentStatus> status = genericNoParamFinder("EmploymentStatus.findAll");
		return status;
	}

	public static List<ClinicalSite> getAllClinicalSites() {
		List<ClinicalSite> sites = genericNoParamFinder("ClinicalSite.findAll");
		return sites;
	}

	public static List<InjuryType> getAllInjuryTypes() {
		List<InjuryType> types = genericNoParamFinder("InjuryType.findAll");
		return types;
	}

	public static List<TraumaticBrainInjuryType> getAllTraumaticBrainInjuries() {
		List<TraumaticBrainInjuryType> injuries = genericNoParamFinder("TraumaticBrainInjuryType.findAll");
		return injuries;
	}

	public static List<Symptom> getAllSymptoms() {
		List<Symptom> symptoms = genericNoParamFinder("Symptom.findAll");
		return symptoms;
	}

	public static List<IncomingDiagnoses> getAllIncomingDiagnoses() {
		List<IncomingDiagnoses> diagnoses = genericNoParamFinder("IncomingDiagnoses.findAll");
		return diagnoses;
	}

	public static List<BirthTrauma> getAllBirthTrauma() {
		List<BirthTrauma> bTrauma = genericNoParamFinder("BirthTrauma.findAll");
		return bTrauma;
	}

	public static List<DevelopmentMilestone> getAllDevelopmentMilestones() {
		List<DevelopmentMilestone> milestones = genericNoParamFinder("DevelopmentMilestone.findAll");
		return milestones;
	}

	public static List<DevelopmentTrauma> getAllDevelopmentTrauma() {
		List<DevelopmentTrauma> dTrauma = genericNoParamFinder("DevelopmentTrauma.findAll");
		return dTrauma;
	}

	public static List<RecreationalDrug> getAllRecreationalDrug() {
		List<RecreationalDrug> drugs = genericNoParamFinder("RecreationalDrug.findAll");
		return drugs;
	}

	public static List<Medicine> getAllMedicines() {
		List<Medicine> meds = genericNoParamFinder("Medicine.findAll");
		return meds;
	}

	public static List<Dosage> getAllDosages() {
		List<Dosage> dosages = genericNoParamFinder("Dosage.findAll");
		return dosages;
	}

	public static List<Schedule> getAllSchedules() {
		List<Schedule> schedules = genericNoParamFinder("Schedule.findAll");
		return schedules;
	}

	public static List<Effective> getAllEffectives() {
		List<Effective> effective = genericNoParamFinder("Effective.findAll");
		return effective;
	}

	public static List<FamilyMemberType> getAllFamilyMemberTypes() {
		List<FamilyMemberType> familyMemberTypes = genericNoParamFinder("FamilyMemberType.findAll");
		return familyMemberTypes;
	}

	public static List<Allergy> getAllMedicinalAllergies() {
		List<Allergy> medAllergies = CerescanPersistenceManager
				.genericNoParamFinder("Allergy.findAllMedicinalAllergies");
		return medAllergies;
	}

	public static List<Allergy> getAllOtherAllergies() {
		List<Allergy> medAllergies = CerescanPersistenceManager
				.genericNoParamFinder("Allergy.findAllOtherAllergies");
		return medAllergies;
	}

	public static List<ScannedFormType> getAllScannedFormTypes() {
		List<ScannedFormType> allScannedFormTypes = CerescanPersistenceManager
				.genericNoParamFinder("ScannedFormType.findAll");
		return allScannedFormTypes;
	}

	public static List<PsychometricAssessmentType> getAllPsychometricAssessmentTypes() {
		List<PsychometricAssessmentType> allPsychometricAssessmentType = CerescanPersistenceManager
				.genericNoParamFinder("PsychometricAssementType.findAll");
		return allPsychometricAssessmentType;
	}

	public static List<ConcentrationTaskType> getAllConcentrationTaskTypes() {
		List<ConcentrationTaskType> allConcentrationTaskType = CerescanPersistenceManager
				.genericNoParamFinder("ConcentrationTaskType.findAll");
		return allConcentrationTaskType;
	}

	public static List<CsInjectionSite> getCsAllInjectionSites() {
		List<CsInjectionSite> csInjectionSite = CerescanPersistenceManager
				.genericNoParamFinder("CsInjectionSite.findAll");
		return csInjectionSite;
	}

	public static List<BsInjectionSite> getBsAllInjectionSites() {
		List<BsInjectionSite> bsInjectionSite = CerescanPersistenceManager
				.genericNoParamFinder("BsInjectionSite.findAll");
		return bsInjectionSite;
	}

	public static List<OtherImagingType> getAllOtherImagingTypes() {
		List<OtherImagingType> otherImagingType = CerescanPersistenceManager
				.genericNoParamFinder("OtherImagingType.findAll");
		return otherImagingType;
	}

	public static List<ReadingPhysician> getAllReadingPhysicians() {
		List<ReadingPhysician> readingPhsyician = CerescanPersistenceManager
				.genericNoParamFinder("ReadingPhysician.findAll");
		return readingPhsyician;
	}

	public static List<LossConsciousness> getAllLossConsciousness() {
		List<LossConsciousness> lossConsciousness = CerescanPersistenceManager
				.genericNoParamFinder("LossConsciousness.findAll");
		return lossConsciousness;
	}

	public static List<TraumaticAmnesia> getAllTraumaticAmnesias() {
		List<TraumaticAmnesia> traumaticAmnesia = CerescanPersistenceManager
				.genericNoParamFinder("TraumaticAmnesia.findAll");
		return traumaticAmnesia;
	}

	public static List<PhysicalInjury> getAllPhysicalInjuries() {
		List<PhysicalInjury> physicalInjuries = CerescanPersistenceManager
				.genericNoParamFinder("PhysicalInjury.findAll");
		return physicalInjuries;
	}

	public static List<Degree> getAllDegrees() {
		List<Degree> degrees = CerescanPersistenceManager
				.genericNoParamFinder("Degree.findAll");
		return degrees;
	}

	public static List<Mini> getAllMinis() {
		List<Mini> minis = CerescanPersistenceManager
				.genericNoParamFinder("Mini.findAll");
		return minis;
	}

	public static List<DrugAbuse> getAllDrugAbuse() {
		List<DrugAbuse> da = CerescanPersistenceManager
				.genericNoParamFinder("DrugAbuse.findAll");
		return da;
	}

	public static List<OutgoingDiagnoses> getAllOutgoingDiagnoses() {
		return CerescanPersistenceManager
				.genericNoParamFinder("OutgoingDiagnoses.findAll");
	}

	public static List<VeteranStatus> getAllVeteranStatuses() {
		return CerescanPersistenceManager
				.genericNoParamFinder("VeteranStatus.findAll");
	}

	public static List<BrainArea> getAllBrainAreas() {
		return CerescanPersistenceManager
				.genericNoParamFinder("BrainArea.findAll");
	}

	public static Ethnicity newEthnicity(String ethnicityName) {
		if (addToDefaultList(ethnicityName)) {
			return newEthnicity(ethnicityName, true);
		} else {
			return newEthnicity(ethnicityName, false);
		}
	}

	public static Ethnicity newEthnicity(String ethnicityName,
			boolean isInDefaultList) {
		Ethnicity e = new Ethnicity();
		e.setName(ethnicityName);
		persist(e);
		return e;
	}

	public static EducationLevel newEducationLevel(String level) {
		if (addToDefaultList(level)) {
			return newEducationLevel(level, true);
		} else {
			return newEducationLevel(level, false);
		}
	}

	public static EducationLevel newEducationLevel(String level,
			boolean isInDefaultList) {
		EducationLevel edLevel = new EducationLevel();
		edLevel.setName(level);
		persist(edLevel);
		return edLevel;
	}

	public static RelationshipStatus newRelationshipStatus(String status) {
		RelationshipStatus rs = null;
		if (addToDefaultList(status)) {
			rs = newRelationshipStatus(status, true);
		} else {
			rs = newRelationshipStatus(status, false);
		}
		persist(rs);
		return rs;
	}

	public static RelationshipStatus newRelationshipStatus(String status,
			boolean isInDefaultList) {
		RelationshipStatus rs = new RelationshipStatus();
		rs.setName(status);
		persist(rs);
		return rs;
	}

	private static boolean addToDefaultList(String value) {
		if (value != null && value.endsWith("++")) {
			return true;
		} else {
			return false;
		}
	}

}