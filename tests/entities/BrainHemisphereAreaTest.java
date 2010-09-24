package entities;

import java.util.ArrayList ;
import java.util.List ;
import java.util.Random ;

import org.junit.Test ;

import com.loquatic.cerescan.api.entities.BrainHemisphereArea ;
import com.loquatic.cerescan.api.entities.SessionInfo ;
import com.loquatic.cerescan.api.entities.lookups.BrainArea ;
import com.loquatic.cerescan.api.entities.lookups.BrainHemisphere ;
import com.loquatic.cerescan.api.persistence.managers.CerescanPersistenceManager ;
import com.loquatic.cerescan.api.persistence.managers.LookupEntityManager ;

public class BrainHemisphereAreaTest extends BaseTestObject {
  
  @Test
  public void testBasics() {
    
    SessionInfo ses = EntityGenerator.createRandomSessionInfo() ;
    
    CerescanPersistenceManager.persist( ses ) ;
    
    List<BrainArea> areas = LookupEntityManager.getAllBrainAreas() ;
    
    List<BrainHemisphere> hemis = LookupEntityManager.getAllBrainHemispheres() ;
    
    Random r1 = new Random() ;
    Random r2 = new Random() ;
    
    int idx1 = r1.nextInt(  areas.size() ) ;
    int idx2 = r2.nextInt( hemis.size() ) ;
    
    List<BrainArea> selected = new ArrayList<BrainArea>() ;
    for( int x=0; x<idx1; x++ ) {
      selected.add( areas.get( x ) ) ;
    }
    
    BrainArea ba = areas.get( idx1 ) ;
    BrainHemisphere bh = hemis.get(  idx2 ) ;
    
    BrainHemisphereArea bha = new BrainHemisphereArea() ;
    
    CerescanPersistenceManager.persist( bha ) ;
    
    bha.setBrainHemisphere( bh ) ;
    bha.setBrainArea( selected ) ;
    
    CerescanPersistenceManager.merge( bha ) ;
    
    List<BrainHemisphereArea> affectedAreas = new ArrayList<BrainHemisphereArea>() ;
    
    affectedAreas.add( bha ) ;
    
    ses.setBrainHemisphereAreas( affectedAreas ) ;
    
    CerescanPersistenceManager.merge( ses ) ;
  }

}
