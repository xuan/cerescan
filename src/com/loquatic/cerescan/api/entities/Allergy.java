package com.loquatic.cerescan.api.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.loquatic.cerescan.api.entities.lookups.AllergyType;

@Entity
@Table(name = "allergy")
@NamedQueries( {
		@NamedQuery(name = "Allergy.findAll", query = "select object(a) from Allergy a order by a.description"),
		@NamedQuery(name = "Allergy.findByDescription", query = "select object(a) from Allergy a where a.description=:description"),
		@NamedQuery(name = "Allergy.findAllMedicinalAllergies", query = "select object(a) from Allergy a where a.allergyType.id=1 order by a.description"),
		@NamedQuery(name = "Allergy.findAllOtherAllergies", query = "select object(a) from Allergy a where a.allergyType.id=2 order by a.description") })
public class Allergy extends CerescanBaseEntity implements IAuditable {

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "allergy_type_id", referencedColumnName = "id")
	private AllergyType allergyType;

	@Column(name = "description")
	private String description;
	
	@Column(name = "adverse_reaction")
	private String adverseReaction;

	public AllergyType getAllergyType() {
		return allergyType;
	}

	public void setAllergyType(AllergyType allergyType) {
		this.allergyType = allergyType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getAdverseReaction() {
		return adverseReaction;
	}

	public void setAdverseReaction(String adverseReaction) {
		this.adverseReaction = adverseReaction;
	}

	@Override
	public String toString() {
		return "Allergy [allergyType=" + allergyType + ", description="
				+ description + ", adverseReaction=" + adverseReaction + "]";
	}

	public String log(){
		return toString();
	}
}
