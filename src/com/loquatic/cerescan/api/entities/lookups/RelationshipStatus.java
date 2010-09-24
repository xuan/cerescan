package com.loquatic.cerescan.api.entities.lookups;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.loquatic.cerescan.api.entities.SessionInfo;


@Entity
@Table(name="relationships_status_lookup")
@NamedQueries({
	@NamedQuery(name="RelationshipStatus.findAll", 
			    query="select object(r) from RelationshipStatus r order by r.orderNumber")
})
public class RelationshipStatus extends LookUp {
	
}
