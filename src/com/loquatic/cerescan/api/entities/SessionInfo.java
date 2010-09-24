package com.loquatic.cerescan.api.entities;

import java.util.ArrayList ;
import java.util.Date ;
import java.util.List ;

import javax.persistence.Basic ;
import javax.persistence.CascadeType ;
import javax.persistence.Column ;
import javax.persistence.Entity ;
import javax.persistence.FetchType ;
import javax.persistence.JoinColumn ;
import javax.persistence.JoinTable ;
import javax.persistence.ManyToMany ;
import javax.persistence.ManyToOne ;
import javax.persistence.NamedQueries ;
import javax.persistence.NamedQuery ;
import javax.persistence.OneToMany ;
import javax.persistence.Table ;

import com.loquatic.cerescan.api.entities.lookups.BirthTrauma ;
import com.loquatic.cerescan.api.entities.lookups.BrainHemisphere ;
import com.loquatic.cerescan.api.entities.lookups.BsInjectionSite ;
import com.loquatic.cerescan.api.entities.lookups.ClinicalSite ;
import com.loquatic.cerescan.api.entities.lookups.ConcentrationTaskType ;
import com.loquatic.cerescan.api.entities.lookups.CsInjectionSite ;
import com.loquatic.cerescan.api.entities.lookups.DevelopmentMilestone ;
import com.loquatic.cerescan.api.entities.lookups.DevelopmentTrauma ;
import com.loquatic.cerescan.api.entities.lookups.DrugAbuse ;
import com.loquatic.cerescan.api.entities.lookups.EducationLevel ;
import com.loquatic.cerescan.api.entities.lookups.EmploymentStatus ;
import com.loquatic.cerescan.api.entities.lookups.Ethnicity ;
import com.loquatic.cerescan.api.entities.lookups.IncomingDiagnoses ;
import com.loquatic.cerescan.api.entities.lookups.InjuryType ;
import com.loquatic.cerescan.api.entities.lookups.Mini ;
import com.loquatic.cerescan.api.entities.lookups.OutgoingDiagnoses ;
import com.loquatic.cerescan.api.entities.lookups.ReadingPhysician ;
import com.loquatic.cerescan.api.entities.lookups.RecreationalDrug ;
import com.loquatic.cerescan.api.entities.lookups.RelationshipStatus ;
import com.loquatic.cerescan.api.entities.lookups.Symptom ;
import com.loquatic.cerescan.api.entities.lookups.VeteranStatus;

/**
 * SessionInfo objects represent a scan session for a patient. A patient may
 * have one or more SessionInfo objects.
 * 
 * @author jonsvede
 * 
 */
@Entity
@Table(name = "session_info")
@NamedQueries( {
		@NamedQuery(name = "SessionInfo.findAll", query = "select object(s) from SessionInfo s"),
		@NamedQuery(name = "SessionInfo.findById", query = "select object(s) from SessionInfo s where s.id=:id") })
public class SessionInfo extends CerescanBaseEntity implements IAuditable {

	@Column(name = "height")
	private String height;

	@Column(name = "weight")
	private Double weight;

	@Column(name = "hand_dominance", length = 1)
	private String handDominance;

	@Column(name = "eye_dominance", length = 1)
	private String eyeDominance;

	@Column(name = "combat_history")
	private String combatHistory;

	@Column(name = "combat_location")
	private String combatLocation;

	@Column(name = "combat_date")
	private String combatDate;

	@Column(name = "forensic_patient", length = 1)
	private String forensicPatient;

	@Column(name = "case_number")
	private String caseNumber;

	@Column(name = "symptom_note")
	private String symptomNotes;

	@Column(name = "alcohol_consumption")
	private Integer alcoholConsumption;

	@Column(name = "alcohol_abuse")
	private Boolean historyOfAlcoholAbuse;
	
	@Column(name = "alcohol_frequency_use")
	private String alcoholFrequencyOfUse;
	

	@Column(name = "alcohol_abuse_desc", columnDefinition = "LONGTEXT")
	private String alcoholAbuseDescription;

	@Column(name = "drug_abuse")
	private Boolean historyOfRecreationDrugUse;

	@Column(name = "drug_abuse_desc", columnDefinition = "LONGTEXT")
	private String drugAbuseDescription;
	
	@Column(name="drug_abuse_desc_qualifier")
	private String drugAbuseDescriptionQualifier ;
	
	private String recreationalDrugAbuseNotes ;

	@Column(name = "type_nicotine_use")
	private String typeOfNicotineUsed;

	@Column(name = "quantity_nicotine_used")
	private String quantityNicotineUsed;

	@Column(name = "frequency_nicotine_used")
	private String frequencyOfNicotineUsed;

	@Column(name = "years_nicotine_used")
	private String yearsNicotineUsed;

	@Column(name = "current_caffiene_use")
	private String currentCaffieneUse;
	
	@Column(name = "clinical_indication")
	private String clinicalIndication;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "sessions_drug_abuse_jt", 
	    joinColumns = @JoinColumn(name = "sessioninfo_id", referencedColumnName = "id"), 
			inverseJoinColumns = @JoinColumn(name = "drug_abuse_id", referencedColumnName = "id"))
	private List<DrugAbuse> drugAbuse;
	
	@ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "session_info_recreation_drug_jt", 
      joinColumns = @JoinColumn(name = "session_info_id", referencedColumnName = "id"), 
      inverseJoinColumns = @JoinColumn(name = "recreational_drug_id", referencedColumnName = "id"))
  private List<RecreationalDrug> recreationDrugs;
	
	@ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "session_info_past_recreation_drug_jt", 
      joinColumns = @JoinColumn(name = "session_info_id", referencedColumnName = "id"), 
      inverseJoinColumns = @JoinColumn(name = "recreational_drug_id", referencedColumnName = "id"))
  private List<RecreationalDrug> pastRecreationalDrugs ;
	
  
  @Column(name = "prescription_drug_abuse", columnDefinition = "LONGTEXT")
	private String prescriptionDrugAbuse;

	@ManyToOne
	@JoinColumn(name = "injury_type_id", referencedColumnName = "id")
	private InjuryType injuryType;

	@Column(name = "injury_description")
	private String injuryDescription;

	@Column(name = "adoptee")
	private Boolean isAdoptee;

	@Column(name = "disorders_in_childhood", columnDefinition = "LONGTEXT")
	private String disordersInChildhood;

	@Column(name = "medical_disorders_childhood", columnDefinition = "LONGTEXT")
	private String medicalDisordersInChildhood;

	@Column(name = "addtional_development_notes", columnDefinition = "LONGTEXT")
	private String additionalDevelopmentNotes;

	@Column(name = "biomarker_data", columnDefinition = "LONGTEXT")
	private String biomarkerData;

	@Column(name = "protocol_exceptions", columnDefinition = "LONGTEXT")
	private String protocolExceptions;

	@Column(name = "notes", columnDefinition = "LONGTEXT")
	private String notes;

	@Column(name = "protocol", columnDefinition = "LONGTEXT")
	private String protocol;

	/*
	 * Concentration Scan elements (cs)
	 */
	@Column(name = "concentration_scan_date")
	private Date csDate;

	@Column(name = "concentration_scan_dose")
	private String csDose;

	@Column(name = "concentration_scan_counts")
	private Double csCounts;

	@Column(name = "concentration_scan_injection_site_side")
	private String csInjectionSiteSide;

	@ManyToOne
	@JoinColumn(name = "cs_injection_site_id", referencedColumnName = "id")
	private CsInjectionSite csInjectionSite;

	@Column(name = "concentration_time_scan")
	private Date csTimeOfInjection;

	@Column(name = "concentration_scan_scan_time")
	private Date csTimeOfScan;

	@Column(name = "concentration_scan_tech_initials")
	private String csTechnologistsInitials;

	@ManyToOne
	@JoinColumn(name = "concentration_task_type_id", referencedColumnName = "id")
	private ConcentrationTaskType concentrationTask;

	/*
	 * Baseline Scan elements (bs)
	 */
	@Column(name = "baseline_scan_date")
	private Date bsDate;

	@Column(name = "baseline_scan_dose")
	private String bsDose;

	@Column(name = "baseline_scan_counts")
	private Double bsCounts;

	@Column(name = "baseline_scan_injection_site_side")
	private String bsInjectionSiteSide;

	@ManyToOne
	@JoinColumn(name = "bs_injection_site_id", referencedColumnName = "id")
	private BsInjectionSite bsInjectionSite;

	@Column(name = "baseline_scan_time_scan")
	private Date bsTimeOfScan;

	@Column(name = "baseline_scan_injection_site_location")
	private String bsInjectionSiteLocation;

	@Column(name = "baseline_scan_injection_time")
	private Date bsTimeOfInjection;

	@Column(name = "baseline_scan_tech_initials")
	private String bsTechnologistInitials;

	@Column(name = "family_members_notes")
	private String familyMembersNotes;

	/*
	 * Relationships
	 */
	@Basic(fetch = FetchType.EAGER)
	@OneToMany(cascade = { CascadeType.ALL })
	private List<FamilyMember> familyMembers;

	@OneToMany(cascade = { CascadeType.ALL })
	private List<Allergy> allergies;

	@OneToMany(cascade = { CascadeType.ALL })
	private List<PsychometricAssessment> assessments;

	@OneToMany(cascade = { CascadeType.ALL })
	private List<OtherImaging> otherImagings;

	@OneToMany(cascade = { CascadeType.ALL })
	private List<ScannedForm> scannedForms;

	@OneToMany(cascade = { CascadeType.ALL })
	private List<HospitalizationsSurgeries> hospitalizations;

	@OneToMany(cascade = { CascadeType.ALL })
	private List<TraumaticBrainInjury> traumaticBrainInjuries;
	
	@OneToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "session_info_brain_hemisphere_area_jt", 
	           joinColumns = { @JoinColumn(name = "session_info_id") }, 
	           inverseJoinColumns = { @JoinColumn(name = "brain_hemisphere_area_id") })
	private List<BrainHemisphereArea> brainHemisphereAreas ;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "session_info_medication_jt", joinColumns = { @JoinColumn(name = "session_info_id") }, inverseJoinColumns = { @JoinColumn(name = "medication_id") })
	private List<Medication> medications;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "session_info_attorney_jt", joinColumns = { @JoinColumn(name = "session_info_id") }, inverseJoinColumns = { @JoinColumn(name = "attorney_id") })
	private List<Attorney> referringAttorneys;

	@ManyToOne
	@JoinColumn(name = "ethnicity_id", referencedColumnName = "id")
	private Ethnicity ethnicity;

	@ManyToOne
	@JoinColumn(name = "relationship_status_id", referencedColumnName = "id")
	private RelationshipStatus relationshipStatus;

	@ManyToOne
	@JoinColumn(name = "education_level_id", referencedColumnName = "id")
	private EducationLevel educationLevel;

	@ManyToOne
	@JoinColumn(name = "employment_status_id", referencedColumnName = "id")
	private EmploymentStatus employmentStatus;
	
	@ManyToOne
	@JoinColumn(name = "veteran_status_id", referencedColumnName = "id")
	private VeteranStatus veteranStatus;


	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "session_info_physician_jt", joinColumns = { @JoinColumn(name = "session_info_id") }, inverseJoinColumns = { @JoinColumn(name = "physician_id") })
	private List<Physician> referringPhysicians;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "session_info_treatment_provider_jt", joinColumns = { @JoinColumn(name = "session_info_id") }, inverseJoinColumns = { @JoinColumn(name = "physician_id") })
	private List<Physician> alternateTreatmentProviders;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "sessions_symptoms_jt", joinColumns = @JoinColumn(name = "sessioninfo_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "symptom_id", referencedColumnName = "id"))
	private List<Symptom> symptoms;

	@Basic(fetch = FetchType.EAGER)
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "sessions_clinical_sites_jt", joinColumns = @JoinColumn(name = "sessioninfo_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "clinical_site_id", referencedColumnName = "id"))
	private List<ClinicalSite> clinicalSites;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "sessions_incoming_diagnoses_jt", joinColumns = @JoinColumn(name = "sessioninfo_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "incoming_diagnoses_id", referencedColumnName = "id"))
	private List<IncomingDiagnoses> incomingDiagnoses;

	@Column(name = "incoming_diagnoses_comment", columnDefinition = "LONGTEXT")
	private String incomingDiagnosisComment;
	
	@Column(name = "substance_use_note", columnDefinition = "LONGTEXT")
	private String substanceUseNote;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "sessions_birth_trauma_jt", joinColumns = @JoinColumn(name = "sessioninfo_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "birth_trauma_id", referencedColumnName = "id"))
	private List<BirthTrauma> birthTraumas;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "sessions_development_milestone_jt", joinColumns = @JoinColumn(name = "sessioninfo_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "development_milestome_id", referencedColumnName = "id"))
	private List<DevelopmentMilestone> developmentMilestones;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "sessions_development_trauma_jt", joinColumns = @JoinColumn(name = "sessioninfo_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "development_trauma_id", referencedColumnName = "id"))
	private List<DevelopmentTrauma> developmentTraumas;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "sessions_reading_physician_jt", joinColumns = @JoinColumn(name = "sessioninfo_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "reading_physician_id", referencedColumnName = "id"))
	private List<ReadingPhysician> readingPhysicians;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "session_info_mini_jt", joinColumns = { @JoinColumn(name = "session_info_id") }, inverseJoinColumns = { @JoinColumn(name = "mini_id") })
	private List<Mini> minis;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "session_info_outgoing_diagnoses_jt", joinColumns = { @JoinColumn(name = "session_info_id") }, inverseJoinColumns = { @JoinColumn(name = "outgoing_diagnoses_id") })
	private List<OutgoingDiagnoses> outgoingDiagnoses;
	
	
	
	public String getRecreationalDrugAbuseNotes() {
    return recreationalDrugAbuseNotes ;
  }

  public void setRecreationalDrugAbuseNotes( String recreationalDrugAbuseNotes ) {
    this.recreationalDrugAbuseNotes = recreationalDrugAbuseNotes ;
  }

  public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double wt) {
		this.weight = wt;
	}

	public String getHandDominance() {
		return handDominance;
	}

	public void setHandDominance(String handDominance) {
		this.handDominance = handDominance;
	}

	public String getEyeDominance() {
		return eyeDominance;
	}

	public void setEyeDominance(String eyeDominance) {
		this.eyeDominance = eyeDominance;
	}

	public VeteranStatus getVeteranStatus() {
		return veteranStatus;
	}

	public void setVeteranStatus(VeteranStatus veteranStatus) {
		this.veteranStatus = veteranStatus;
	}

	public String getCombatHistory() {
		return combatHistory;
	}

	public void setCombatHistory(String combatHistory) {
		this.combatHistory = combatHistory;
	}

	public String getCombatLocation() {
		return combatLocation;
	}

	public void setCombatLocation(String combatLocation) {
		this.combatLocation = combatLocation;
	}

	public String getCombatDate() {
		return combatDate;
	}

	public void setCombatDate(String combatDate) {
		this.combatDate = combatDate;
	}

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public Ethnicity getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(Ethnicity ethnicity) {
		this.ethnicity = ethnicity;
	}

	public RelationshipStatus getRelationshipStatus() {
		return relationshipStatus;
	}

	public void setRelationshipStatus(RelationshipStatus relationshipStatus) {
		this.relationshipStatus = relationshipStatus;
	}

	public EducationLevel getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(EducationLevel educationLevel) {
		this.educationLevel = educationLevel;
	}

	public String getForensicPatient() {
		return forensicPatient;
	}

	public void setForensicPatient(String forensicPatient) {
		this.forensicPatient = forensicPatient;
	}

	public EmploymentStatus getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(EmploymentStatus employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public List<ClinicalSite> getClinicalSites() {
		return clinicalSites;
	}

	public void setClinicalSites(List<ClinicalSite> clinicalSites) {
		this.clinicalSites = clinicalSites;
	}

	public List<Attorney> getReferringAttorney() {
		return referringAttorneys;
	}

	public void setReferringAttorney(List<Attorney> referringAttorneys) {
		this.referringAttorneys = referringAttorneys;
	}

	public InjuryType getInjuryType() {
		return injuryType;
	}

	public void setInjuryType(InjuryType injuryType) {
		this.injuryType = injuryType;
	}

	public String getInjuryDescription() {
		return injuryDescription;
	}

	public void setInjuryDescription(String injuryDescription) {
		this.injuryDescription = injuryDescription;
	}

	public String getSymptomNotes() {
		return symptomNotes;
	}

	public void setSymptomNotes(String symptomNotes) {
		this.symptomNotes = symptomNotes;
	}

	public List<Symptom> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(List<Symptom> symptoms) {
		this.symptoms = symptoms;
	}

	public List<IncomingDiagnoses> getIncomingDiagnoses() {
		return incomingDiagnoses;
	}

	public void setIncomingDiagnoses(List<IncomingDiagnoses> incomingDiagnoses) {
		this.incomingDiagnoses = incomingDiagnoses;
	}

	public List<Medication> getMedications() {
		return medications;
	}

	public void setMedications(List<Medication> medications) {
		this.medications = medications;
	}

	public void addMedication(Medication med) {
		if (medications == null) {
			medications = new ArrayList<Medication>();
		}
		medications.add(med);
	}

	public Integer getAlcoholConsumption() {
		return alcoholConsumption;
	}

	public void setAlcoholConsumption(Integer alcoholConsumption) {
		this.alcoholConsumption = alcoholConsumption;
	}
	
	

	public Boolean isHistoryOfAlcoholAbuse() {
		return historyOfAlcoholAbuse;
	}

	public void setHistoryOfAlcoholAbuse(Boolean historyOfAlcoholAbuse) {
		this.historyOfAlcoholAbuse = historyOfAlcoholAbuse;
	}

	public String getAlcoholFrequencyOfUse() {
		return alcoholFrequencyOfUse;
	}

	public void setAlcoholFrequencyOfUse(String alcoholFrequencyOfUse) {
		this.alcoholFrequencyOfUse = alcoholFrequencyOfUse;
	}

	public String getAlcoholAbuseDescription() {
		return alcoholAbuseDescription;
	}

	public void setAlcoholAbuseDescription(String alcoholAbuseDescription) {
		this.alcoholAbuseDescription = alcoholAbuseDescription;
	}

	public Boolean isHistoryOfRecreationDrugUse() {
		return historyOfRecreationDrugUse;
	}

	public void setHistoryOfRecreationDrugUse(Boolean historyOfRecreationDrugUse) {
		this.historyOfRecreationDrugUse = historyOfRecreationDrugUse;
	}

	public String getDrugAbuseDescription() {
		return drugAbuseDescription;
	}

	public void setDrugAbuseDescription(String drugAbuseDescription) {
		this.drugAbuseDescription = drugAbuseDescription;
	}

	public String getTypeOfNicotineUsed() {
		return typeOfNicotineUsed;
	}

	public void setTypeOfNicotineUsed(String typeOfNicotineUsed) {
		this.typeOfNicotineUsed = typeOfNicotineUsed;
	}

	public String getQuantityNicotineUsed() {
		return quantityNicotineUsed;
	}

	public void setQuantityNicotineUsed(String quantityNicotineUsed) {
		this.quantityNicotineUsed = quantityNicotineUsed;
	}

	public String getFrequencyOfNicotineUsed() {
		return frequencyOfNicotineUsed;
	}

	public void setFrequencyOfNicotineUsed(String frequencyOfNicotineUsed) {
		this.frequencyOfNicotineUsed = frequencyOfNicotineUsed;
	}

	public String getYearsNicotineUsed() {
		return yearsNicotineUsed;
	}

	public void setYearsNicotineUsed(String yearsNicotineUsed) {
		this.yearsNicotineUsed = yearsNicotineUsed;
	}

	public String getCurrentCaffieneUse() {
		return currentCaffieneUse;
	}

	public void setCurrentCaffieneUse(String currentCaffieneUse) {
		this.currentCaffieneUse = currentCaffieneUse;
	}

	public List<DrugAbuse> getDrugAbuse() {
		return drugAbuse;
	}

	public void setDrugAbuse(List<DrugAbuse> drugAbuse) {
		this.drugAbuse = drugAbuse;
	}

	public String getPrescriptionDrugAbuse() {
		return prescriptionDrugAbuse;
	}

	public String getSubstanceUseNote() {
		return substanceUseNote;
	}

	public void setSubstanceUseNote(String substanceUseNote) {
		this.substanceUseNote = substanceUseNote;
	}

	public void setPrescriptionDrugAbuse(String prescriptionDrugAbuse) {
		this.prescriptionDrugAbuse = prescriptionDrugAbuse;
	}

	public Boolean isAdoptee() {
		return isAdoptee;
	}

	public void setAdoptee(Boolean isAdoptee) {
		this.isAdoptee = isAdoptee;
	}

	public String getDisordersInChildhood() {
		return disordersInChildhood;
	}

	public void setDisordersInChildhood(String disordersInChildhood) {
		this.disordersInChildhood = disordersInChildhood;
	}

	public String getMedicalDisordersInChildhood() {
		return medicalDisordersInChildhood;
	}

	public void setMedicalDisordersInChildhood(
			String medicalDisordersInChildhood) {
		this.medicalDisordersInChildhood = medicalDisordersInChildhood;
	}

	public String getAdditionalDevelopmentNotes() {
		return additionalDevelopmentNotes;
	}

	public void setAdditionalDevelopmentNotes(String additionalDevelopmentNotes) {
		this.additionalDevelopmentNotes = additionalDevelopmentNotes;
	}

	public List<BirthTrauma> getBirthTraumas() {
		return birthTraumas;
	}

	public void setBirthTraumas(List<BirthTrauma> birthTraumas) {
		this.birthTraumas = birthTraumas;
	}

	public List<DevelopmentMilestone> getDevelopmentMilestones() {
		return developmentMilestones;
	}

	public void setDevelopmentMilestones(
			List<DevelopmentMilestone> developmentMilestones) {
		this.developmentMilestones = developmentMilestones;
	}

	public List<DevelopmentTrauma> getDevelopmentTraumas() {
		return developmentTraumas;
	}

	public void setDevelopmentTraumas(List<DevelopmentTrauma> developmentTraumas) {
		this.developmentTraumas = developmentTraumas;
	}

	public String getIncomingDiagnosisComment() {
		return incomingDiagnosisComment;
	}

	public void setIncomingDiagnosisComment(String incomingDiagnosisComment) {
		this.incomingDiagnosisComment = incomingDiagnosisComment;
	}

	public String getFamilyMembersNotes() {
		return familyMembersNotes;
	}

	public void setFamilyMembersNotes(String familyMembersNotes) {
		this.familyMembersNotes = familyMembersNotes;
	}

	public List<FamilyMember> getFamilyMembers() {
		return familyMembers;
	}

	public void setFamilyMembers(List<FamilyMember> familyMembers) {
		this.familyMembers = familyMembers;
	}

	public void addFamilyMember(FamilyMember fMember) {
		if (familyMembers == null) {
			familyMembers = new ArrayList<FamilyMember>();
		}

		if (!(familyMembers.contains(fMember))) {
			familyMembers.add(fMember);
		}
	}

	public List<Allergy> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<Allergy> allergies) {
		this.allergies = allergies;
	}

	public void addAllergy(Allergy allergy) {
		if (allergies == null) {
			allergies = new ArrayList<Allergy>();
		}
		if (!allergies.contains(allergy)) {
			allergies.add(allergy);
		}
	}

	public Date getCsDate() {
		return csDate;
	}

	public void setCsDate(Date csDate) {
		this.csDate = csDate;
	}

	public String getCsDose() {
		return csDose;
	}

	public void setCsDose(String csDose) {
		this.csDose = csDose;
	}

	public Double getCsCounts() {
		return csCounts;
	}

	public void setCsCounts(Double csCounts) {
		this.csCounts = csCounts;
	}

	public String getCsInjectionSiteSide() {
		return csInjectionSiteSide;
	}

	public void setCsInjectionSiteSide(String csSide) {
		this.csInjectionSiteSide = csSide;
	}

	public Date getCsTimeOfInjection() {
		return csTimeOfInjection;
	}

	public void setCsTimeOfInjection(Date csTimeOfInjection) {
		this.csTimeOfInjection = csTimeOfInjection;
	}

	public Date getCsTimeOfScan() {
		return csTimeOfScan;
	}

	public void setCsTimeOfScan(Date csTimeOfScan) {
		this.csTimeOfScan = csTimeOfScan;
	}

	public CsInjectionSite getCsInjectionSite() {
		return csInjectionSite;
	}

	public void setCsInjectionSite(CsInjectionSite csInjectionSite) {
		this.csInjectionSite = csInjectionSite;
	}

	public String getCsTechnologistsInitials() {
		return csTechnologistsInitials;
	}

	public void setCsTechnologistsInitials(String csTechnologistsInitials) {
		this.csTechnologistsInitials = csTechnologistsInitials;
	}

	public ConcentrationTaskType getConcentrationTask() {
		return concentrationTask;
	}

	public void setConcentrationTask(ConcentrationTaskType concentrationTask) {
		this.concentrationTask = concentrationTask;
	}

	public CsInjectionSite getCsInjectionSiteLocation() {
		return csInjectionSite;
	}

	public void setCsInjectionSiteLocation(CsInjectionSite csInjectionSite) {
		this.csInjectionSite = csInjectionSite;
	}

	public Date getBsDate() {
		return bsDate;
	}

	public void setBsDate(Date bsDate) {
		this.bsDate = bsDate;
	}

	public String getBsDose() {
		return bsDose;
	}

	public void setBsDose(String bsDose) {
		this.bsDose = bsDose;
	}

	public BsInjectionSite getBsInjectionSite() {
		return bsInjectionSite;
	}

	public void setBsInjectionSite(BsInjectionSite bsInjectionSite) {
		this.bsInjectionSite = bsInjectionSite;
	}

	public Double getBsCounts() {
		return bsCounts;
	}

	public void setBsCounts(Double bsCounts) {
		this.bsCounts = bsCounts;
	}

	public String getBsInjectionSiteSide() {
		return bsInjectionSiteSide;
	}

	public Date getBsTimeOfScan() {
		return bsTimeOfScan;
	}

	public void setBsTimeOfScan(Date bsTimeOfScan) {
		this.bsTimeOfScan = bsTimeOfScan;
	}

	public void setBsInjectionSiteSide(String bsInjectionSiteSide) {
		this.bsInjectionSiteSide = bsInjectionSiteSide;
	}

	public String getBsInjectionSiteLocation() {
		return bsInjectionSiteLocation;
	}

	public void setBsInjectionSiteLocation(String bsInjectionSiteLocation) {
		this.bsInjectionSiteLocation = bsInjectionSiteLocation;
	}

	public Date getBsTimeOfInjection() {
		return bsTimeOfInjection;
	}

	public void setBsTimeOfInjection(Date bsTimeOfInjection) {
		this.bsTimeOfInjection = bsTimeOfInjection;
	}

	public String getBsTechnologistInitials() {
		return bsTechnologistInitials;
	}

	public void setBsTechnologistInitials(String bsTechnologistInitials) {
		this.bsTechnologistInitials = bsTechnologistInitials;
	}

	public String getBiomarkerData() {
		return biomarkerData;
	}

	public void setBiomarkerData(String biomarkerData) {
		this.biomarkerData = biomarkerData;
	}

	public String getProtocolExceptions() {
		return protocolExceptions;
	}

	public void setProtocolExceptions(String protocolExceptions) {
		this.protocolExceptions = protocolExceptions;
	}

	public List<Attorney> getReferringAttorneys() {
		return referringAttorneys;
	}

	public void setReferringAttorneys(List<Attorney> referringAttorneys) {
		this.referringAttorneys = referringAttorneys;
	}

	public List<Physician> getReferringPhysicians() {
		return referringPhysicians;
	}

	public void setReferringPhysicians(List<Physician> referringPhysicians) {
		this.referringPhysicians = referringPhysicians;
	}

	public List<Physician> getAlternateTreatmentProviders() {
		return alternateTreatmentProviders;
	}

	public void setAlternateTreatmentProviders(
			List<Physician> alternateTreatmentProviders) {
		this.alternateTreatmentProviders = alternateTreatmentProviders;
	}

	public List<PsychometricAssessment> getAssessments() {
		return assessments;
	}

	public void setAssessments(List<PsychometricAssessment> assessments) {
		this.assessments = assessments;
	}

	public void addAssessment(PsychometricAssessment assessment) {
		if (assessments == null) {
			assessments = new ArrayList<PsychometricAssessment>();
		}
		assessments.add(assessment);
	}

	public List<ScannedForm> getScannedForms() {
		return scannedForms;
	}

	public void setScannedForms(List<ScannedForm> scannedForms) {
		this.scannedForms = scannedForms;
	}

	public void addScannedForm(ScannedForm form) {
		if (scannedForms == null) {
			scannedForms = new ArrayList<ScannedForm>();
		}
		scannedForms.add(form);
	}

	public List<OtherImaging> getOtherImagings() {
		return otherImagings;
	}

	public void setOtherImagings(List<OtherImaging> otherImagings) {
		this.otherImagings = otherImagings;
	}

	public List<HospitalizationsSurgeries> getHospitalizations() {
		return hospitalizations;
	}

	public void setHospitalizations(
			List<HospitalizationsSurgeries> hospitalizations) {
		this.hospitalizations = hospitalizations;
	}

	public void addHospitalizationOrSurgery(String desc) {
		if (hospitalizations == null) {
			hospitalizations = new ArrayList<HospitalizationsSurgeries>();
		}
		HospitalizationsSurgeries hs = new HospitalizationsSurgeries();
		hs.setDescription(desc);
		hospitalizations.add(hs);
	}

	public void addHospitalizationOrSurgery(HospitalizationsSurgeries hosp) {
		if (hospitalizations == null) {
			hospitalizations = new ArrayList<HospitalizationsSurgeries>();
		}
		hospitalizations.add(hosp);
	}

	public List<TraumaticBrainInjury> getTraumaticBrainInjuries() {
		return traumaticBrainInjuries;
	}

	public void setTraumaticBrainInjuries(
			List<TraumaticBrainInjury> traumaticBrainInjuries) {
		this.traumaticBrainInjuries = traumaticBrainInjuries;
	}

	public List<ReadingPhysician> getReadingPhysicians() {
		return readingPhysicians;
	}

	public void setReadingPhysicians(List<ReadingPhysician> readingPhysicians) {
		this.readingPhysicians = readingPhysicians;
	}

	public List<Mini> getMinis() {
		return minis;
	}

	public void setMinis(List<Mini> minis) {
		this.minis = minis;
	}

	public List<OutgoingDiagnoses> getOutgoingDiagnoses() {
		return outgoingDiagnoses;
	}

	public void setOutgoingDiagnoses(List<OutgoingDiagnoses> outgoingDiagnoses) {
		this.outgoingDiagnoses = outgoingDiagnoses;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public List<RecreationalDrug> getRecreationDrugs() {
    return recreationDrugs ;
  }

  public void setRecreationDrugs( List<RecreationalDrug> recreationDrugs ) {
    this.recreationDrugs = recreationDrugs ;
  }

  
  public List<RecreationalDrug> getPastRecreationalDrugs() {
    return pastRecreationalDrugs ;
  }

  public void setPastRecreationalDrugs(
      List<RecreationalDrug> pastRecreationalDrugs ) {
    this.pastRecreationalDrugs = pastRecreationalDrugs ;
  }

  public String getDrugAbuseDescriptionQualifier() {
    return drugAbuseDescriptionQualifier ;
  }

  public void setDrugAbuseDescriptionQualifier(
      String drugAbuseDescriptionQuailifier ) {
    this.drugAbuseDescriptionQualifier = drugAbuseDescriptionQuailifier ;
  }

  public List<BrainHemisphereArea> getBrainHemisphereAreas() {
    return brainHemisphereAreas ;
  }

  public void setBrainHemisphereAreas(
      List<BrainHemisphereArea> brainHemisphereAreas ) {
    this.brainHemisphereAreas = brainHemisphereAreas ;
  }
  
  

  public String getClinicalIndication() {
	return clinicalIndication;
  }

  public void setClinicalIndication(String clinicalIndication) {
	this.clinicalIndication = clinicalIndication;
  }

@Override
	public String toString() {
		return log();
	}

	public String log() {
		return "SessionInfo [additionalDevelopmentNotes="
				+ additionalDevelopmentNotes + ", alcoholAbuseDescription="
				+ alcoholAbuseDescription + ", alcoholConsumption="
				+ alcoholConsumption + ", allergies=" + allergies
				+ ", alternateTreatmentProviders="
				+ alternateTreatmentProviders + ", assessments=" + assessments
				+ ", biomarkerData=" + biomarkerData + ", birthTraumas="
				+ birthTraumas + ", bsCounts=" + bsCounts + ", bsDate="
				+ bsDate + ", bsDose=" + bsDose + ", bsInjectionSiteLocation="
				+ bsInjectionSiteLocation + ", bsInjectionSiteSide="
				+ bsInjectionSiteSide + ", bsTechnologistInitials="
				+ bsTechnologistInitials + ", bsTimeOfInjection="
				+ bsTimeOfInjection + ", clinicalSites=" + clinicalSites
				+ ", combatDate=" + combatDate + ", combatHistory="
				+ combatHistory + ", combatLocation=" + combatLocation
				+ ", csConcentrationTask=" + concentrationTask + ", csCounts="
				+ csCounts + ", csDate=" + csDate + ", csDose=" + csDose
				+ ", csInjectionSite=" + csInjectionSite + ", bsInjectionSite="
				+ bsInjectionSite + ", csInjectionSiteSide="
				+ csInjectionSiteSide + ", csTechnologistsInitials="
				+ csTechnologistsInitials + ", csTimeOfInjection="
				+ csTimeOfInjection + ", csTimeOfScan=" + csTimeOfScan
				+ ", currentCaffieneUse=" + currentCaffieneUse
				+ ", developmentMilestones=" + developmentMilestones
				+ ", developmentTraumas=" + developmentTraumas
				+ ", disordersInChildhood=" + disordersInChildhood
				+ ", drugAbuseDescription=" + drugAbuseDescription
				+ ", educationLevel=" + educationLevel + ", employmentStatus="
				+ employmentStatus + ", ethnicity=" + ethnicity
				+ ", eyeDominance=" + eyeDominance + ", familyMembers="
				+ familyMembers + ", forensicPatient=" + forensicPatient
				+ ", handDominance=" + handDominance + ", height=" + height
				+ ", historyOfAlcoholAbuse=" + historyOfAlcoholAbuse
				+ ", historyOfRecreationDrugUse=" + historyOfRecreationDrugUse
				+ ", hospitalizations=" + hospitalizations
				+ ", incomingDiagnoses=" + incomingDiagnoses
				+ ", additionalDevelopmentNotes=" + additionalDevelopmentNotes
				+ ", incomingDiagnosisComment=" + incomingDiagnosisComment
				+ ", injuryDescription=" + injuryDescription + ", injuryType="
				+ medications + ", neuropsychiatricPatient="
				+ protocolExceptions + ", otherImagings=" + otherImagings
				+ ", readingPhysicians=" + readingPhysicians
				+ ", referringPhysicians=" + referringPhysicians
				+ ", relationshipStatus=" + relationshipStatus
				+ ", scannedForms=" + scannedForms + ", symptomNotes="
				+ symptomNotes + ", symptoms=" + symptoms
				+ ", traumaticBrainInjuries=" + traumaticBrainInjuries
				+ ", veteranStatus=" + veteranStatus + ", weight=" + weight
				+ ", clinicalIndication=" + clinicalIndication
				+ ", createdDate=" + createdDate + ", id=" + id
				+ ", lastModified=" + lastModified + "]";
	}

}
