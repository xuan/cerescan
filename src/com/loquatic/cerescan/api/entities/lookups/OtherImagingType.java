package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "other_imaging_type_lookup")
@NamedQueries( { @NamedQuery(name = "OtherImagingType.findAll", query = "select object(o) from OtherImagingType o order by o.orderNumber")
})
public class OtherImagingType extends LookUp {

}
