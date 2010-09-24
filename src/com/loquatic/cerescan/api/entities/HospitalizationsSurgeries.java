package com.loquatic.cerescan.api.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="hospitalizations_and_surgeries")
public class HospitalizationsSurgeries extends CerescanBaseEntity implements IAuditable {
	
	@Column(name="description")
	private String description ;
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return log() ;
	}
	
	public String log() {
				return "HospitalizationsSurgeries [description=" + description
				+ ", createdDate="
				+ createdDate + ", id=" + id + ", lastModified=" + lastModified + "]";
	}

	
}
