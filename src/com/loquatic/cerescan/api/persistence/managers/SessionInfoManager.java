package com.loquatic.cerescan.api.persistence.managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.loquatic.cerescan.api.entities.Address;
import com.loquatic.cerescan.api.entities.Allergy;
import com.loquatic.cerescan.api.entities.BrainHemisphereArea;
import com.loquatic.cerescan.api.entities.FamilyMember;
import com.loquatic.cerescan.api.entities.HospitalizationsSurgeries;
import com.loquatic.cerescan.api.entities.Medication;
import com.loquatic.cerescan.api.entities.OtherImaging;
import com.loquatic.cerescan.api.entities.PsychometricAssessment;
import com.loquatic.cerescan.api.entities.ScannedForm;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.entities.TraumaticBrainInjury;
import com.loquatic.cerescan.api.entities.lookups.AllergyType;
import com.loquatic.cerescan.api.entities.lookups.BrainArea;
import com.loquatic.cerescan.api.entities.lookups.BrainHemisphere;
import com.loquatic.cerescan.api.entities.lookups.Dosage;
import com.loquatic.cerescan.api.entities.lookups.EducationLevel;
import com.loquatic.cerescan.api.entities.lookups.Effective;
import com.loquatic.cerescan.api.entities.lookups.EmploymentStatus;
import com.loquatic.cerescan.api.entities.lookups.Ethnicity;
import com.loquatic.cerescan.api.entities.lookups.FamilyMemberType;
import com.loquatic.cerescan.api.entities.lookups.IncomingDiagnoses;
import com.loquatic.cerescan.api.entities.lookups.LossConsciousness;
import com.loquatic.cerescan.api.entities.lookups.MedicationType;
import com.loquatic.cerescan.api.entities.lookups.Medicine;
import com.loquatic.cerescan.api.entities.lookups.OtherImagingType;
import com.loquatic.cerescan.api.entities.lookups.PhysicalInjury;
import com.loquatic.cerescan.api.entities.lookups.PsychometricAssessmentType;
import com.loquatic.cerescan.api.entities.lookups.RelationshipStatus;
import com.loquatic.cerescan.api.entities.lookups.ScannedFormType;
import com.loquatic.cerescan.api.entities.lookups.Schedule;
import com.loquatic.cerescan.api.entities.lookups.TraumaticAmnesia;
import com.loquatic.cerescan.api.entities.lookups.TraumaticBrainInjuryType;

/**
 * This class helps manage SessionInfo objects and their relationships so that
 * callers don't need to call the EntityManager directly.
 * <p>
 * One instance of this class is a proxy for one instance of a session.
 * <p>
 * To get and instance of this class, do one of the following:
 * <p>
 * <code>
 * SessionInfoManagerFactory sessionMgrFactory = SessionInfoManagerFactory().getInstance() ;<br>
 * SessionInfoManager sessionMgr = sessionMgrFactory.getSession( id ) ;
 * </code>
 * <p>
 * Where the id that is passed is a long. You can also create a new session
 * using this same manager instance. In that case the SessionInfo that is
 * created is bound to the Patient id you pass in:
 * <p>
 * <code>
 * SessionInfoManagerFactory sessionMgrFactory = SessionInfoManagerFactory().getInstance() ;<br>
 * SessionInfoManager sessionMgr = sessionMgrFactory.createSession(patientId) ;
 * </code>
 * <p>
 * In this case you pass in a long that is the Patient id and the
 * SessionInfoManager will take care of the rest for you.
 * <p>
 * Modifications to SessionInfo can be stored via a call to
 * <code>getManager().merge( ses )</code> on this object.
 * 
 * @author jonsvede, Loquatic Software, LLC.
 * 
 * 
 */
public class SessionInfoManager {

	private static Log log = LogFactory.getLog(SessionInfoManager.class);

	private long sessionId;

	private FileManager fileManager;

	private EntityManagerFactory factory;

	protected SessionInfoManager(long sessionInfoId,
			EntityManagerFactory aFactory) {
		factory = aFactory;
		sessionId = sessionInfoId;
	}

	private EntityManager getManager() {
		return factory.createEntityManager();
	}

	public SessionInfo getSessionInfo() {
		EntityManager mgr = getManager();
		Query query = mgr.createNamedQuery("SessionInfo.findById");
		query.setParameter("id", sessionId);
		SessionInfo sess = (SessionInfo) query.getSingleResult();
		// mgr.detach( sess ) ;

		return sess;
	}

	public void addEthnicity(String eth) {
		log.debug("does " + eth + " exist in DB?" + doesEthnicityExist(eth));
		SessionInfo ses = getSessionInfo();
		if (doesEthnicityExist(eth)) {
			ses.setEthnicity(getEthnicity(eth));
		} else {
			ses.setEthnicity(createEthnicity(eth));
		}
		save(ses);
	}

	public void save(SessionInfo ses) {
		if (ses.getId() == sessionId) {
			updateExisting(ses);
		}
		log.debug("after merge: " + getSessionInfo().toString());
	}

	private void saveNew(Object obj) {
		EntityManager em = getManager();
		em.getTransaction().begin();
		em.persist(obj);
		em.getTransaction().commit();
		em.close();
		em = null;
	}

	private void updateExisting(Object obj) {
		EntityManager em = getManager();
		em.getTransaction().begin();
		em.merge(obj);
		em.getTransaction().commit();
		em.close();
		em = null;
	}

	private void delete(Object obj) {
		EntityManager em = getManager();
		em.getTransaction().begin();
		em.remove(obj);
		em.getTransaction().commit();
		em.close();
		em = null;
	}

	private Ethnicity getEthnicity(String eth) {
		List<Ethnicity> ethns = CerescanPersistenceManager
				.genericOneParamFinder("Ethnicity.findByName", "name", eth);
		if (ethns != null && ethns.size() == 1) {
			return ethns.get(0);
		}
		return null;
	}

	private boolean doesEthnicityExist(String eth) {
		List<Ethnicity> ethns = CerescanPersistenceManager
				.genericOneParamFinder("Ethnicity.findByName", "name", eth);
		if (ethns.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	private Ethnicity createEthnicity(String ethnicityName) {
		Ethnicity e = new Ethnicity();
		e.setName(ethnicityName);
		log.debug("before persistence: " + e);
		saveNew(e);
		log.debug("after persistence: " + e);
		return e;
	}

	// public void addHospitalizations( List<HospitalizationsSurgeries>
	// hospitalizations ) {
	// SessionInfo sess = getSessionInfo() ;
	// Iterator<HospitalizationsSurgeries> hospitalizationsIt =
	// hospitalizations.iterator() ;
	// log.debug( "# of hospitalizations = " + hospitalizations.size() ) ;
	// while( hospitalizationsIt.hasNext() ) {
	// HospitalizationsSurgeries hosp = hospitalizationsIt.next() ;
	// if( hosp != null && hosp.getId() == 0 ) {
	// saveNew( hosp ) ;
	// }
	// log.debug( hosp ) ;
	// sess.addHospitalizationOrSurgery( hosp ) ;
	// }
	// save( sess ) ;
	// }
	//
	// public void addHospitalization( HospitalizationsSurgeries hospitalization
	// ) {
	//
	// }

	// public void addReferringAttorney( Attorney atty ) {
	// atty.setAddresses( checkAddresses( atty.getAddresses() ) ) ;
	// SessionInfo ses = getSessionInfo() ;
	// if ( atty.getId() == 0 ) {
	// saveNew( atty ) ;
	// } else {
	// updateExisting( atty ) ;
	// }
	// getSessionInfo().setReferringAttorney( atty ) ;
	//
	// save( ses ) ;
	// }

	private List<Address> checkAddresses(List<Address> addresses) {
		ArrayList<Address> saved = new ArrayList<Address>();
		if (addresses != null) {
			Iterator<Address> addressesIt = addresses.iterator();
			while (addressesIt.hasNext()) {
				Address addr = addressesIt.next();
				if (addr.getId() == 0) {
					saveNew(addr);
				} else {
					updateExisting(addr);
				}
				saved.add(addr);
			}
		}
		return saved;
	}

	public void addEthnicity(Ethnicity eth) {
		SessionInfo ses = getSessionInfo();
		if (eth.getId() == 0) {
			saveNew(eth);
		}
		ses.setEthnicity(eth);

		updateExisting(ses);
	}

	public void addRelationshipStatus(RelationshipStatus rs) {
		SessionInfo ses = getSessionInfo();
		if (rs.getId() == 0) {
			saveNew(rs);
		}

		getSessionInfo().setRelationshipStatus(rs);

		save(ses);
	}

	public void addEducationLevel(EducationLevel edLvl) {
		SessionInfo ses = getSessionInfo();
		if (edLvl.getId() == 0) {
			saveNew(edLvl);
		}

		ses.setEducationLevel(edLvl);

		save(ses);
	}

	public void addEmploymentStatus(EmploymentStatus empStatus) {
		SessionInfo ses = getSessionInfo();
		if (empStatus.getId() == 0) {
			saveNew(empStatus);
		}

		ses.setEmploymentStatus(empStatus);

		save(ses);
	}

	public List<Medication> getCurrentMedication() {
		SessionInfo ses = getSessionInfo();
		return getMedicationsByType(ses.getId(), 1l);
	}

	public List<Medication> getPastMedication() {
		SessionInfo ses = getSessionInfo();
		return getMedicationsByType(ses.getId(), 2l);
	}

	private List<Medication> getMedicationsByType(Long sessionId, Long typeId) {
		String sql = "select object(m) from SessionInfo s join "
				+ "s.medications as m join m.type as t "
				+ "where t.id=:tid and s.id=:sid";
		EntityManager em = getManager();
		Query q = em.createQuery(sql);
		q.setParameter("sid", sessionId);
		q.setParameter("tid", typeId);
		List<Medication> meds = q.getResultList();

		return meds;
	}

	public List<Allergy> getMedicineAllergies() {
		SessionInfo ses = getSessionInfo();
		return getAllergiesByType(ses.getId(), 1l);
	}

	public List<Allergy> getOtherAllergies() {
		SessionInfo ses = getSessionInfo();
		return getAllergiesByType(ses.getId(), 2l);
	}

	private List<Allergy> getAllergiesByType(Long sessionId, Long typeId) {
		String sql = "select object(l) from SessionInfo s join "
			+ "s.allergies as l join l.allergyType as t "
			+ "where t.id=:tid and s.id=:sid";
		EntityManager em = getManager();
		Query q = em.createQuery(sql);
		q.setParameter("sid", sessionId);
		q.setParameter("tid", typeId);
		
		List<Allergy> allergies = q.getResultList();
		return allergies;
	}

	private List<Object> getCollectionByType(String query, Long sessionId,
			Long typeId) {
		EntityManager em = getManager();
		Query q = em.createQuery(query);
		q.setParameter("sid", sessionId);
		q.setParameter("tid", typeId);
		List<Object> objs = q.getResultList();

		return objs;
	}

	public void deleteAllergy(Allergy allergy) {
		SessionInfo ses = getSessionInfo();
		ses.getAllergies().remove(allergy);
		save(ses);
	}

	public void addMedicinalAllergy(List<Allergy> allergies) {
		log.debug("going to try to add " + allergies.size() + " to session");
		if (allergies != null && allergies.size() > 0) {
			Iterator<Allergy> allg = allergies.iterator();
			while (allg.hasNext()) {
				Allergy allergy = allg.next();
				log.debug("going to check out allergy: " + allergy.toString());
				if (allergy != null && allergy.getId() == 0) {
					addMedicinalAllergy(allergy);
				}
			}
		}
	}

	public void addOtherAllergy(List<Allergy> allergies) {
		if (allergies != null && allergies.size() > 0) {
			Iterator<Allergy> allg = allergies.iterator();
			while (allg.hasNext()) {
				Allergy allergy = allg.next();
				if (allergy != null && allergy.getId() == 0) {
					addOtherAllergy(allergy);
				}
			}
		}
	}

	public void addMedicinalAllergy(Allergy allg) {
		addAllergy(allg, 1l);
	}

	public void addOtherAllergy(Allergy allg) {
		addAllergy(allg, 2l);
	}

	private void addAllergy(Allergy allg, Long allergyTypeId) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		allg.setAllergyType(em.find(AllergyType.class, allergyTypeId));
		em.persist(allg);
		SessionInfo ses = getSessionInfo();
		ses.addAllergy(allg);
		em.merge(ses);
		em.getTransaction().commit();
	}

	private Allergy findAllergyByDescription(String desc) {
		EntityManager em = getManager();
		Query q = em.createNamedQuery("Allergy.findByDescription");
		q.setParameter("description", desc);
		Allergy allg = null;
		try {
			allg = (Allergy) q.getSingleResult();
		} catch (NoResultException e) {
			// ignore b/c this is ok if it's not found
		}
		// Allergy allg = (Allergy)CerescanPersistenceManager
		// .genericOneParamFinder("Allergy.findByDescription", "description",
		// desc ) ;
		return allg;
	}

	public List<Medication> deleteMedication(Medication medication) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		SessionInfo ses = getSessionInfo();
		Medication deleteMed = em.find(Medication.class, medication.getId());
		ses.getMedications().remove(deleteMed);
		em.remove(deleteMed);
		em.merge(ses);
		em.getTransaction().commit();
		em.close();
		return ses.getMedications();
	}

	public Medicine getMedicine(String name) {
		List<Medicine> meds = CerescanPersistenceManager.genericOneParamFinder(
				"Medicine.findByName", "name", name);
		if (meds != null && meds.size() == 1) {
			return meds.get(0);
		} else {
			return null;
		}
	}

	public List<Medication> addCurrentMedication(Medication medication) {
		 addMedication(medication.getDosage(), medication.getSchedule(),
				medication.getStrength(), medication.getMedicine(), 1L,
				medication.getEffective(), medication.getAdverseReaction());
		 return getCurrentMedication();
	}

	public List<Medication> addPastMedication(Medication medication) {
		 addMedication(medication.getDosage(), medication.getSchedule(),
				medication.getStrength(), medication.getMedicine(), 2L,
				medication.getEffective(), medication.getAdverseReaction());
		 return getPastMedication();
	}

	private void addMedication(Dosage d, Schedule s,
			String strength, Medicine m, Long type, Effective e,
			String adverseReaction) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Medication medication = new Medication();
		medication.setType(em.find(MedicationType.class, type));
		medication.setDosage(em.find(Dosage.class, d.getId()));
		medication.setSchedule(em.find(Schedule.class, s.getId()));
		medication.setStrength(strength);
		medication.setMedicine(em.find(Medicine.class, m.getId()));
		if (e != null) {
			medication.setEffective(em.find(Effective.class, e.getId()));
		}
		medication.setAdverseReaction(adverseReaction);
		
		em.persist(medication);
		SessionInfo ses = getSessionInfo();
		ses.getMedications().add(medication);
		em.merge(ses);
		em.getTransaction().commit();
		em.close();
	}

	public void addFamilyMembers(FamilyMember familyMember) {
		if (familyMember != null) {
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			FamilyMemberType type = em.find(FamilyMemberType.class,
					familyMember.getFamilyMemberType().getId());
			familyMember.setFamilyMemberType(type);

			List<IncomingDiagnoses> tmp = new ArrayList<IncomingDiagnoses>();
			for (IncomingDiagnoses _id : familyMember.getIncomingDiagnoses()) {
				tmp.add(em.find(IncomingDiagnoses.class, _id.getId()));
			}
			familyMember.setIncomingDiagnoses(tmp);

			em.persist(familyMember);
			SessionInfo ses = getSessionInfo();
			ses.getFamilyMembers().add(familyMember);
			em.merge(ses);
			em.getTransaction().commit();
			em.close();
			em = null;
		}
	}

	public void addHospitalization(HospitalizationsSurgeries hosp) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(hosp);
		SessionInfo ses = getSessionInfo();
		ses.addHospitalizationOrSurgery(hosp);
		em.merge(ses);
		em.getTransaction().commit();
	}

	public void deleteHospitalization(HospitalizationsSurgeries hosp) {
		SessionInfo ses = getSessionInfo();
		ses.getHospitalizations().remove(hosp);
		save(ses);
		// EntityManager em = factory.createEntityManager();
		// em.getTransaction().begin() ;
		// em.remove( hosp ) ;
		// em.getTransaction().commit();
	}

	public void addFamilyMember(FamilyMember member, EntityManager em) {
		SessionInfo ses = getSessionInfo();
		FamilyMemberType fType = member.getFamilyMemberType();
		log.debug(member);
		log.debug(fType);
		if (member != null && member.getId() == 0) {
			if (fType != null && fType.getId() == 0) {
				// new, save it - but just in case, see if one exists
				log.debug("go find the family member by name");
				Query q = getManager().createNamedQuery(
						"FamilyMemberType.findByName");
				q.setParameter("name", fType.getName());
				FamilyMemberType foundType = null;
				try {
					foundType = (FamilyMemberType) q.getSingleResult();
				} catch (NoResultException nre) {
					// ignore - this means that the entity is actually new
				}
				log.debug(foundType);
				if (foundType != null) {
					member.setFamilyMemberType(foundType);
				} else {
					em.persist(fType);
				}
			}
			em.persist(member);

			ses.addFamilyMember(member);
			em.merge(ses);
		}
	}

	public void addTraumaticBrainInjury(TraumaticBrainInjury tbi) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		LossConsciousness loc = em.find(LossConsciousness.class, tbi
				.getLossOfConsciousness().getId());
		tbi.setLossOfConsciousness(loc);

		PhysicalInjury pi = em.find(PhysicalInjury.class, tbi
				.getPhysicalInjury().getId());
		tbi.setPhysicalInjury(pi);

		TraumaticAmnesia ti = em.find(TraumaticAmnesia.class, tbi
				.getPostTraumaticAmnesia().getId());
		tbi.setPostTraumaticAmnesia(ti);

		TraumaticBrainInjuryType tbt = em.find(TraumaticBrainInjuryType.class,
				tbi.getTraumaticBrainInjuryType().getId());
		tbi.setTraumaticBrainInjuryType(tbt);

		em.merge(tbi);
		em.persist(tbi);
		SessionInfo ses = getSessionInfo();
		ses.getTraumaticBrainInjuries().add(tbi);
		em.merge(ses);
		em.getTransaction().commit();
		em.close();
	}

	public List<ScannedForm> addScannedForm(ScannedForm scannedForm) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		SessionInfo ses = getSessionInfo();

		ScannedFormType sft = em.find(ScannedFormType.class, scannedForm
				.getScannedFormType().getId());
		scannedForm.setScannedFormType(sft);

		em.merge(scannedForm);
		em.persist(scannedForm);
		ses.getScannedForms().add(scannedForm);

		em.merge(ses);
		em.getTransaction().commit();
		em.close();

		return ses.getScannedForms();
	}

	public List<OtherImaging> addOtherImaging(OtherImaging otherImaging) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		SessionInfo ses = getSessionInfo();

		OtherImagingType sft = em.find(OtherImagingType.class, otherImaging
				.getOtherImagingType().getId());
		otherImaging.setOtherImagingType(sft);

		em.merge(otherImaging);
		em.persist(otherImaging);
		ses.getOtherImagings().add(otherImaging);

		em.merge(ses);
		em.getTransaction().commit();
		em.close();

		return ses.getOtherImagings();
	}

	public List<PsychometricAssessment> addPsychometricAssessment(
			PsychometricAssessment pa) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		SessionInfo ses = getSessionInfo();

		PsychometricAssessmentType pat = em.find(
				PsychometricAssessmentType.class, pa
						.getPsychometricAssessmentType().getId());
		pa.setPsychometricAssessmentType(pat);

		em.merge(pat);
		em.persist(pa);
		ses.getAssessments().add(pa);

		em.merge(ses);
		em.getTransaction().commit();
		em.close();

		return ses.getAssessments();
	}

	public List<BrainHemisphereArea> addBrainHemisphereAreas(
			BrainHemisphereArea bha) {

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		SessionInfo ses = getSessionInfo();

		BrainHemisphere bh = em.find(BrainHemisphere.class, bha
				.getBrainHemisphere().getId());
		bha.setBrainHemisphere(bh);

		List<BrainArea> ba = new ArrayList<BrainArea>();
		for (BrainArea b : bha.getBrainArea()) {
			ba.add(em.find(BrainArea.class, b.getId()));
		}
		bha.setBrainArea(ba);

		em.persist(bha);
		ses.getBrainHemisphereAreas().add(bha);
		em.merge(ses);
		em.getTransaction().commit();
		em.close();
		return ses.getBrainHemisphereAreas();
	}
}
