package com.loquatic.cerescan.api.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.loquatic.cerescan.api.entities.lookups.LossConsciousness;
import com.loquatic.cerescan.api.entities.lookups.PhysicalInjury;
import com.loquatic.cerescan.api.entities.lookups.TraumaticAmnesia;
import com.loquatic.cerescan.api.entities.lookups.TraumaticBrainInjuryType;

@Entity
@Table(name="traumatic_brain_injury")
@NamedQueries({
	@NamedQuery(name="TraumaticBrainInjury.findAll",
			    query="select object(b) from TraumaticBrainInjury b order by b.description")
})
public class TraumaticBrainInjury extends CerescanBaseEntity implements IAuditable {
	
	@ManyToOne( cascade=CascadeType.ALL)
	@JoinColumn(name="traumatic_brain_injury_type_id", referencedColumnName="id") 
    private TraumaticBrainInjuryType traumaticBrainInjuryType ;
	
	@Column(name="description", columnDefinition="LONGTEXT")
	private String description ;
	
	@ManyToOne( cascade=CascadeType.ALL)
	@JoinColumn(name="loss_onsciousness_id", referencedColumnName="id") 
	private LossConsciousness lossOfConsciousness ;
	
	@ManyToOne( cascade=CascadeType.ALL)
	@JoinColumn(name="traumatic_amnesia_id", referencedColumnName="id")
	private TraumaticAmnesia postTraumaticAmnesia ;
	
	@ManyToOne( cascade=CascadeType.ALL)
	@JoinColumn(name="physical_injury_id", referencedColumnName="id")
	private PhysicalInjury physicalInjury ;

	public TraumaticBrainInjuryType getTraumaticBrainInjuryType() {
		return traumaticBrainInjuryType;
	}

	public void setTraumaticBrainInjuryType(
			TraumaticBrainInjuryType traumaticBrainInjuryType) {
		this.traumaticBrainInjuryType = traumaticBrainInjuryType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LossConsciousness getLossOfConsciousness() {
		return lossOfConsciousness;
	}

	public void setLossOfConsciousness(LossConsciousness lossOfConsciousness) {
		this.lossOfConsciousness = lossOfConsciousness;
	}

	public TraumaticAmnesia getPostTraumaticAmnesia() {
		return postTraumaticAmnesia;
	}

	public void setPostTraumaticAmnesia(TraumaticAmnesia postTraumaticAmnesia) {
		this.postTraumaticAmnesia = postTraumaticAmnesia;
	}

	public PhysicalInjury getPhysicalInjury() {
		return physicalInjury;
	}

	public void setPhysicalInjury(PhysicalInjury physicalInjury) {
		this.physicalInjury = physicalInjury;
	}

	@Override
	public String toString() {
		return log() ;
	}
	
	public String log() {
				return "TraumaticBrainInjury [description=" + description
				+ ", traumaticBrainInjuryType=" + traumaticBrainInjuryType
				+ ", createdDate=" + createdDate + ", id=" + id
				+ ", lastModified=" + lastModified  + "]";
	}
	
}
