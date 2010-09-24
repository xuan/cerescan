package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="allergy_type_lookup")
@NamedQueries({
	@NamedQuery(name="AllergyType.findAll", 
			    query="select object(a) from AllergyType a order by a.orderNumber"),
	@NamedQuery(name="AllergyType.findById",
				query="select object(a) from AllergyType a where id=:id" )
})
public class AllergyType extends LookUp {

}
