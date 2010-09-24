package com.loquatic.cerescan.api.entities.lookups;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.loquatic.cerescan.api.entities.SessionInfo;


@Entity
@Table(name="incoming_diagnoses_lookup")
@NamedQueries({
	@NamedQuery(name="IncomingDiagnoses.findAll", 
			    query="select object(d) from IncomingDiagnoses d order by d.orderNumber")
})
public class IncomingDiagnoses extends LookUp {

}
