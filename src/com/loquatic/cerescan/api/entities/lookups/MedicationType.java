package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "medication_type_lookup", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
@NamedQueries( { @NamedQuery(name = "MedicationType.findById", query = "select object(m) from MedicationType m where m.id=:id") })
public class MedicationType extends LookUp {

}
