package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "outgoing_diagnoses_lookup")
@NamedQueries( { @NamedQuery(name = "OutgoingDiagnoses.findAll", query = "select object(o) from OutgoingDiagnoses o order by o.orderNumber") })
public class OutgoingDiagnoses extends LookUp {

}
