package com.loquatic.cerescan.api.entities;

import java.util.List ;

import javax.persistence.CascadeType ;
import javax.persistence.Column ;
import javax.persistence.Entity ;
import javax.persistence.JoinColumn ;
import javax.persistence.JoinTable ;
import javax.persistence.ManyToMany ;
import javax.persistence.ManyToOne ;
import javax.persistence.NamedQueries ;
import javax.persistence.NamedQuery ;
import javax.persistence.Table ;

import com.loquatic.cerescan.api.entities.lookups.Degree ;

@Entity
@Table(name="physician")
@NamedQueries({
	@NamedQuery(name="Physician.findAll", 
			    query="select object(p) from Physician p"),
    @NamedQuery(name="Physician.findByFirstName",
    			query="select object(p) from Physician p where p.firstName=:firstName"),
    @NamedQuery(name="Physician.findByLastName",
       			query="select object(p) from Physician p where p.lastName=:lastName"),
    @NamedQuery(name="Physician.searchByLastName",
       			query="select object(p) from Physician p where p.lastName like :lastName"),
    @NamedQuery(name="Physician.findById",
    		    query="select object(p) from Physician p where p.id=:id")
})
public class Physician extends Person implements IAuditable {
	
	@Column(name="practice_name")
	private String practiceName ;
	
	@Column(name="speciality")
	private String speciality ;
	
	@Column(name="npi_number")
	private String npiNumber ;
	
	@Column(name="clinical_ind_for_referral")
	private String clinicalIndicationsForReferral ;
	
	@ManyToOne
	@JoinColumn(name = "degree_id", referencedColumnName = "id")
	private Degree degree;
	
	@ManyToMany( cascade={CascadeType.ALL} )
    @JoinTable(name = "physician_address_jt",
      joinColumns = {@JoinColumn(name = "physician_id")},
      inverseJoinColumns = {@JoinColumn(name = "address_id")})
	private List<Address> addresses ;
	
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getClinicalIndicationsForReferral() {
		return clinicalIndicationsForReferral;
	}
	public void setClinicalIndicationsForReferral(
			String clinicalIndicationsForReferral) {
		this.clinicalIndicationsForReferral = clinicalIndicationsForReferral;
	}
	public List<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public String getPracticeName() {
		return practiceName;
	}
	public void setPracticeName(String practiceName) {
		this.practiceName = practiceName;
	}
	
	public String getNpiNumber() {
		return npiNumber;
	}
	public void setNpiNumber(String npiNumber) {
		this.npiNumber = npiNumber;
	}
	
	
	public Degree getDegree() {
		return degree;
	}
	public void setDegree(Degree degree) {
		this.degree = degree;
	}
	
	@Override
	public String toString() {
		return log() ;
	}	

	@Override
	public String log() {
		return "Physician [addresses=" + addresses
				+ ", clinicalIndicationsForReferral="
				+ clinicalIndicationsForReferral + ", degree=" + degree
				+ ", npiNumber=" + npiNumber + ", practiceName=" + practiceName
				+ ", speciality=" + speciality + "]";
	}
	
	
}
