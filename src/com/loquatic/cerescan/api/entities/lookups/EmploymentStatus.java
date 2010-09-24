package com.loquatic.cerescan.api.entities.lookups;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.loquatic.cerescan.api.entities.SessionInfo;

@Entity
@Table(name="employment_status_lookup")
@NamedQueries({
	@NamedQuery(name="EmploymentStatus.findAll", 
			    query="select object(e) from EmploymentStatus e order by e.orderNumber")
})
public class EmploymentStatus extends LookUp {

}
