package entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Test;

import com.loquatic.cerescan.api.entities.Medication;
import com.loquatic.cerescan.api.entities.SessionInfo;
import com.loquatic.cerescan.api.entities.lookups.Dosage;
import com.loquatic.cerescan.api.entities.lookups.MedicationType;
import com.loquatic.cerescan.api.entities.lookups.Medicine;
import com.loquatic.cerescan.api.entities.lookups.Schedule;
import com.loquatic.cerescan.api.persistence.managers.CerescanPersistenceManager;

public class MedicationTests {

	@Test
	public void testMedications() {
		Dosage dosage = new Dosage() ;
		dosage.setName( "5mg" ) ;
		CerescanPersistenceManager.persist( dosage ) ;
		
		Schedule schedule = new Schedule() ;
		schedule.setName( "once a day" ) ;
		CerescanPersistenceManager.persist( schedule ) ;
		
		Medicine medicine = new Medicine() ;
		medicine.setName( EntityGenerator.generateRandomString() ) ;
		CerescanPersistenceManager.persist( medicine ) ;
		
		MedicationType mt = new MedicationType() ;
		mt.setName( "current" ) ;
		CerescanPersistenceManager.persist( mt ) ;
		
		Medication medication = new Medication() ;
		medication.setDosage( dosage ) ;
		medication.setSchedule( schedule ) ;
		medication.setMedicine( medicine ) ;
		medication.setType( mt ) ;
		
		CerescanPersistenceManager.persist( medication ) ;
		
		SessionInfo ses = EntityGenerator.createRandomSessionInfo() ;
		CerescanPersistenceManager.persist( ses ) ;
		
		ArrayList<Medication> meds = new ArrayList<Medication>() ;
		meds.add( medication ) ;
		
		ses.setMedications( meds ) ;
		CerescanPersistenceManager.merge( ses ) ;
		
		
	}
	
	/*
	 * http://technology.amis.nl/blog/1065/getting-started-with-the-ejb-30-query-language-ejbql-30-using-glassfish-reference-implementation
	 * 
	 * public List<Author> findManAuthors() {
	 * 		String sql = "select object(o) from Author AS o " + 
	 *                   "JOIN o.books As b join b.publisher p " + 
	 *                   "where lower(p.website) like '%man%' "
	 *         return getEntityManager().createQuery( sql ).getResultList();    }
	 */
	@Test
	public void testQueryingMedication() {
		String sql = "select object(m) from SessionInfo s join s.medications as m  join m.type as t " +
	                "where t.id=:tid and s.id=:sid";
		EntityManager em = CerescanPersistenceManager.getEntityManager() ;
		Query q = em.createQuery( sql ) ;
		q.setParameter( "sid", 1l ) ;
		q.setParameter( "tid", 1l ) ;
		List<Medication> meds = q.getResultList(); 
		System.out.println( meds.size() ) ;
		Iterator<Medication> medsIt = meds.iterator() ;
		while( medsIt.hasNext() ) {
			System.out.println( medsIt.next() ) ;
		}
		
	}
}
