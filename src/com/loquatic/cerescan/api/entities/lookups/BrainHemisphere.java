package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity ;
import javax.persistence.NamedQueries ;
import javax.persistence.NamedQuery ;
import javax.persistence.Table ;

@Entity
@Table(name = "brain_hemisphere_lookup")
@NamedQueries( { @NamedQuery(name = "BrainHemisphere.findAll", 
                 query = "select object(b) from BrainHemisphere b order by b.orderNumber") })
public class BrainHemisphere extends LookUp {

}
