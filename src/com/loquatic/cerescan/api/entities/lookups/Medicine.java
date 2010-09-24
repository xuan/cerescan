package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="medicine_lookup",
 uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
@NamedQueries({
	@NamedQuery(name="Medicine.findAll", 
			    query="select object(m) from Medicine m order by m.name"),
	@NamedQuery(name="Medicine.findByName", 
			    query="select object(m) from Medicine m where m.name=:name")
})
public class Medicine extends LookUp {
	

}
