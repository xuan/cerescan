package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "traumatic_amnesia_lookup")
@NamedQueries( { @NamedQuery(name = "TraumaticAmnesia.findAll", query = "select object(t) from TraumaticAmnesia t order by t.orderNumber") })
public class TraumaticAmnesia extends LookUp {

}
