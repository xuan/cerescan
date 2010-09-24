package managers;

import java.util.List ;

import javax.persistence.EntityManager ;
import javax.persistence.EntityManagerFactory ;
import javax.persistence.Persistence ;
import javax.persistence.PersistenceException ;

import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;
import org.junit.Test ;

import com.loquatic.cerescan.api.entities.Patient ;
import com.loquatic.cerescan.api.entities.SessionInfo ;
import com.loquatic.cerescan.api.entities.lookups.ClinicalSite ;

import entities.EntityGenerator ;

public class SessionInfoManagerFactoryTests {
  
  private final Log log = LogFactory.getLog( SessionInfoManagerFactoryTests.class ) ;
  @Test
  public void simpleTest() {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("cerescan-mysql");
    SessionInfo session = EntityGenerator.createRandomSessionInfo() ;
    Patient patient = EntityGenerator.createRandomPatient() ;

    EntityManager em2 = factory.createEntityManager() ;
    em2.getTransaction().begin();
    List<ClinicalSite> sites = (List<ClinicalSite>) em2.createNamedQuery( "ClinicalSite.findAll" ).getResultList() ;
    em2.getTransaction().commit() ;
    em2.close();
    em2 = null ;
    
    
    
    EntityManager em = factory.createEntityManager() ;
    em.getTransaction().begin();

    session.setClinicalSites(  sites  ) ;
    
    try {
      em.persist( session ) ;
    } catch ( Throwable e ) {
      em.getTransaction().rollback() ;
      em.getTransaction().begin();
      List<ClinicalSite> detachedSites = session.getClinicalSites() ;
      session.setClinicalSites( null ) ;
      em.merge( session ) ;
      for( ClinicalSite s : detachedSites ) {
        em.merge(  s ) ;
        //session.addClinicalSite( s ) ;
        
      }
      em.merge( session ) ;
    }
    
//    patient.addSession( session ) ;
//    em.merge( patient ) ;
//    log.debug( patient ) ;
    em.getTransaction().commit() ;
    em.close();
    em = null ;
    System.out.println( "session id = " + session.getId() ) ;

  }

}
