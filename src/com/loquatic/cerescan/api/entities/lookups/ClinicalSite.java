package com.loquatic.cerescan.api.entities.lookups;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.loquatic.cerescan.api.entities.SessionInfo;

@Entity
@Table(name="clinical_site_lookup")
@NamedQueries({
	@NamedQuery(name="ClinicalSite.findAll", 
			    query="select object(c) from ClinicalSite c order by c.orderNumber")
})
public class ClinicalSite extends LookUp {
	
}
