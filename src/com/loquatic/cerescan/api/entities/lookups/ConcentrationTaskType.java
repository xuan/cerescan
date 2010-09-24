package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="concentration_task_lookup")
@NamedQueries({
	@NamedQuery(name="ConcentrationTaskType.findAll", 
			    query="select object(c) from ConcentrationTaskType c order by c.orderNumber")
})
public class ConcentrationTaskType extends LookUp {

}
