package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity ;
import javax.persistence.NamedQueries ;
import javax.persistence.NamedQuery ;
import javax.persistence.Table ;

@Entity
@Table(name="recreational_drug_lookup")
@NamedQueries({
  @NamedQuery(name="RecreationalDrug.findAll", 
          query="select object(a) from RecreationalDrug a order by a.orderNumber"),
  @NamedQuery(name="RecreationalDrug.findById",
        query="select object(a) from RecreationalDrug a where id=:id" )
})public class RecreationalDrug extends LookUp {

}
