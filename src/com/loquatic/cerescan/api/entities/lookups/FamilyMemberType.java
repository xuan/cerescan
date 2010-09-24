package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="family_member_type_lookup",
		   uniqueConstraints=
		       @UniqueConstraint(columnNames={"name"}))
@NamedQueries({
	@NamedQuery(name="FamilyMemberType.findAll", 
			    query="select object(f) from FamilyMemberType f order by f.orderNumber"),
	@NamedQuery(name="FamilyMemberType.findByName",
			    query="select object(f) from FamilyMemberType f where f.name=:name")
})
public class FamilyMemberType extends LookUp {

}
