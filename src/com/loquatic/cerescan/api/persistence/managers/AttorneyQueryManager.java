package com.loquatic.cerescan.api.persistence.managers;

import java.util.List;

import com.loquatic.cerescan.api.entities.Attorney;

@Deprecated
public class AttorneyQueryManager extends CerescanPersistenceManager {
	
	public static Attorney findById( long id ) {
		Attorney attny = (Attorney) findById( "Attorney.findById", id ) ;
		return attny ;
	}
	
	public static List<Attorney> findByLastName( String lastName ) {
		List<Attorney> attorneys = 
			      genericOneParamFinder("Attorney.findByLastName", "lastName", lastName ) ;
		return attorneys ;
	}
	
	public static List<Attorney> searchByLastName( String lastName ) {
		List<Attorney> attorneys = 
		      genericOneParamFinder("Attorney.searchByLastName", "lastName", lastName + "%" ) ;
	return attorneys ;
	}
	
	public static List<Attorney> findAll() {
		List<Attorney> attorneys = genericFindAll( "Attorney.findAll" ) ;
		return attorneys ;
	}

}