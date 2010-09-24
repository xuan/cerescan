package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="schedule_lookup")
@NamedQueries({
	@NamedQuery(name="Schedule.findAll", 
			    query="select object(s) from Schedule s order by s.orderNumber")
})
public class Schedule extends LookUp {

}
