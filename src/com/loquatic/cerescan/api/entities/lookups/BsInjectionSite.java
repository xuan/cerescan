package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "bs_injection_site_lookup")
@NamedQueries( { @NamedQuery(name = "BsInjectionSite.findAll", query = "select object(b) from BsInjectionSite b order by b.orderNumber") })
public class BsInjectionSite extends LookUp {

}
