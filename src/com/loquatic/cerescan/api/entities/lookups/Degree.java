package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "degree_lookup", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
@NamedQueries( { @NamedQuery(name = "Degree.findAll", query = "select object(d) from Degree d order by d.orderNumber") })
public class Degree extends LookUp {
}
