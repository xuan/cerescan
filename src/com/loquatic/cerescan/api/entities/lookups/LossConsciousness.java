package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "loss_consciousness_lookup")
@NamedQueries( { @NamedQuery(name = "LossConsciousness.findAll", query = "select object(l) from LossConsciousness l order by l.orderNumber") })
public class LossConsciousness extends LookUp {

}
