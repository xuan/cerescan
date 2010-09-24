package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "mini_lookup", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
@NamedQueries( { @NamedQuery(name = "Mini.findAll", query = "select m from Mini m order by m.orderNumber") })
public class Mini extends LookUp {

}
