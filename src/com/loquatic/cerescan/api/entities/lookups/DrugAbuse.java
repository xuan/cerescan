package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "drug_abuse_lookup", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
@NamedQueries( { @NamedQuery(name = "DrugAbuse.findAll", query = "select d from DrugAbuse d order by d.orderNumber") })
public class DrugAbuse extends LookUp {

}
