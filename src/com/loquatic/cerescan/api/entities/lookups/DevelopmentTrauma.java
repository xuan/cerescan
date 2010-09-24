package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="development_trauma_lookup")
@NamedQueries({
	@NamedQuery(name="DevelopmentTrauma.findAll", 
			    query="select object(d) from DevelopmentTrauma d order by d.orderNumber")
})
public class DevelopmentTrauma extends LookUp {

}
