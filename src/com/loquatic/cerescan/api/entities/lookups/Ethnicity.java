package com.loquatic.cerescan.api.entities.lookups;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.loquatic.cerescan.api.entities.SessionInfo;


@Entity
@Table(name="ethnicity_lookup",
		   uniqueConstraints=
		       @UniqueConstraint(columnNames={"name"}))
@NamedQueries({
	@NamedQuery(name="Ethnicity.findAll", 
			    query="select object(e) from Ethnicity e  order by e.orderNumber"),
	@NamedQuery(name="Ethnicity.findByName", 
			    query="select object(e) from Ethnicity e  where e.name=:name order by e.name")
})
public class Ethnicity extends LookUp {

}
