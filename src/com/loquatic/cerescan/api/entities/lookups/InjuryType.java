package com.loquatic.cerescan.api.entities.lookups;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.loquatic.cerescan.api.entities.SessionInfo;

@Entity
@Table(name = "injury_type_lookup")
@NamedQueries( { @NamedQuery(name = "InjuryType.findAll", query = "select object(i) from InjuryType i order by i.orderNumber") })
public class InjuryType extends LookUp {

}
