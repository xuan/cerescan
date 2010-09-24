package com.loquatic.cerescan.api.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.loquatic.cerescan.api.entities.lookups.FamilyMemberType;
import com.loquatic.cerescan.api.entities.lookups.IncomingDiagnoses;

@Entity
@Table(name="family_member")
public class FamilyMember extends CerescanBaseEntity implements IAuditable {
	
	@ManyToOne( cascade=CascadeType.ALL)
	@JoinColumn(name="family_member_type_id", referencedColumnName="id") 
    private FamilyMemberType familyMemberType ;
	
	@ManyToMany( cascade=CascadeType.ALL) 
	@JoinTable(name="family_incoming_diagnoses_jt", 
	             joinColumns=@JoinColumn(name="family_member_id", 
	                                      referencedColumnName="id"), 
	             inverseJoinColumns=@JoinColumn(name="incoming_diagnoses_id", 
	                                             referencedColumnName="id"))
	private List<IncomingDiagnoses> incomingDiagnoses ;

	public FamilyMemberType getFamilyMemberType() {
		return familyMemberType;
	}

	public void setFamilyMemberType(FamilyMemberType familyMemberType) {
		this.familyMemberType = familyMemberType;
	}

	public List<IncomingDiagnoses> getIncomingDiagnoses() {
		return incomingDiagnoses;
	}

	public void setIncomingDiagnoses(List<IncomingDiagnoses> incomingDiagnoses) {
		this.incomingDiagnoses = incomingDiagnoses;
	}

	@Override
	public String toString() {
		return log() ;
	}
	
	public String log() {
		return "FamilyMember [familyMemberType=" + familyMemberType
				+ ", incomingDiagnoses=" + incomingDiagnoses 
				+ ", createdDate=" + createdDate + ", id=" + id
				+ ", lastModified=" + lastModified + "]";
	}


}
