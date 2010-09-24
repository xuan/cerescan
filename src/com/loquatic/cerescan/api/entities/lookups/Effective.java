package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="effective_lookup")
@NamedQueries({
	@NamedQuery(name="Effective.findAll", 
			    query="select object(e) from Effective e order by e.orderNumber")
})
public class Effective extends LookUp {
}
