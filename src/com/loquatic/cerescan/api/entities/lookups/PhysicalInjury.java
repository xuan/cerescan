package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "physical_injury_lookup")
@NamedQueries( { @NamedQuery(name = "PhysicalInjury.findAll", query = "select object(p) from PhysicalInjury p  order by p.orderNumber") })
public class PhysicalInjury extends LookUp{

}
