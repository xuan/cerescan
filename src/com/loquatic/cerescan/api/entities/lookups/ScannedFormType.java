package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="scanned_form_type_lookup")
@NamedQueries({
	@NamedQuery(name="ScannedFormType.findAll",
			    query="select object(s) from ScannedFormType s order by s.orderNumber")
})
public class ScannedFormType extends LookUp {


	
}
