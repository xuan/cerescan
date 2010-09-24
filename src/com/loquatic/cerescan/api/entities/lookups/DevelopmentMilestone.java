package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="development_milestone_lookup")
@NamedQueries({
	@NamedQuery(name="DevelopmentMilestone.findAll", 
			    query="select object(d) from DevelopmentMilestone d order by d.orderNumber")
})
public class DevelopmentMilestone extends LookUp {

}
