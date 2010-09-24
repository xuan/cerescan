package com.loquatic.cerescan.api.entities.lookups;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.loquatic.cerescan.api.entities.SessionInfo;


@Entity
@Table(name="traumatic_brain_injury_lookup")
@NamedQueries({
	@NamedQuery(name="TraumaticBrainInjuryType.findAll", 
			    query="select object(t) from TraumaticBrainInjuryType t order by t.orderNumber")
})
public class TraumaticBrainInjuryType extends LookUp {


}
