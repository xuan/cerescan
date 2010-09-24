package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "brain_area_lookup")
@NamedQueries( { @NamedQuery(name = "BrainArea.findAll", 
                 query = "select object(b) from BrainArea b order by b.orderNumber") })
public class BrainArea extends LookUp {

}
