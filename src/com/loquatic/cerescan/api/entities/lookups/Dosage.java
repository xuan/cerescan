package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "dosage_lookup", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
@NamedQueries({
	@NamedQuery(name="Dosage.findAll", 
			    query="select object(d) from Dosage d order by d.orderNumber")
})
public class Dosage extends LookUp {

}
