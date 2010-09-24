package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "cs_injection_site_lookup")
@NamedQueries( { @NamedQuery(name = "CsInjectionSite.findAll", query = "select object(c) from CsInjectionSite c order by c.orderNumber") })
public class CsInjectionSite extends LookUp {

}
