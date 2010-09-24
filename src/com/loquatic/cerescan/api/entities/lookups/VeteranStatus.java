package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "veteran_status_lookup")
@NamedQueries( { @NamedQuery(name = "VeteranStatus.findAll", query = "select object(v) from VeteranStatus v order by v.orderNumber") })
public class VeteranStatus extends LookUp {

}
