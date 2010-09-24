package com.loquatic.cerescan.api.entities.lookups;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.loquatic.cerescan.api.entities.SessionInfo;

@Entity
@Table(name="symptom_lookup")
@NamedQueries({
	@NamedQuery(name="Symptom.findAll", 
			    query="select object(s) from Symptom s order by s.orderNumber")
})
public class Symptom extends LookUp {
	
}
