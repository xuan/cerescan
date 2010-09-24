package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="birth_trauma_lookup")
@NamedQueries({
	@NamedQuery(name="BirthTrauma.findAll", 
			    query="select object(b) from BirthTrauma b order by b.orderNumber")
})
public class BirthTrauma extends LookUp {

}
