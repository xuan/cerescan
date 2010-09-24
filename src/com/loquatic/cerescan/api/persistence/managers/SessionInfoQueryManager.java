package com.loquatic.cerescan.api.persistence.managers;

import com.loquatic.cerescan.api.entities.SessionInfo;

public class SessionInfoQueryManager extends CerescanPersistenceManager {
	
	public static SessionInfo findById( long id ) {
		
		return (SessionInfo)findById("SessionInfo.findById", id ) ;
	}

}
