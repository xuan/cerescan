package managers;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.loquatic.cerescan.api.entities.Attorney;
import com.loquatic.cerescan.api.persistence.managers.AttorneyManager;
import com.loquatic.cerescan.api.persistence.managers.CerescanPersistenceManager;

import entities.EntityGenerator;

public class AttorneyManagerTests {
	
	@Test
	public void testAddingAttorney() {
		
		Attorney atty = EntityGenerator.createRandomAttorney() ;
		AttorneyManager.persist( atty ) ;
		if( atty.getId() == 0 ) {
			fail() ;
		}
	}
	
	@Test
	public void testRemovingAttorney() {
		for( int x=0; x<5; x++ ) {
			Attorney atty = EntityGenerator.createRandomAttorney() ;
			CerescanPersistenceManager.persist( atty ) ;
		}
		
		List<Attorney> attorneys = AttorneyManager.findAll() ;

		Random random = new Random() ;
		int attyIdx = random.nextInt( attorneys.size()-1 ) ;
		
		Attorney atty = attorneys.get( attyIdx ) ;
		System.out.println( atty ) ;
		if ( atty != null ) {
			AttorneyManager.deleteAttorney( atty ) ;
		} else {
			fail() ;
		}
	}
	
	public void testFindByLastName() {
		
	}

}
