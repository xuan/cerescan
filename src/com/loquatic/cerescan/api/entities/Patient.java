package com.loquatic.cerescan.api.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name="patient",
		   uniqueConstraints=
		       @UniqueConstraint(columnNames={"mr_number"}))
@NamedQueries({
	@NamedQuery(name="Patient.findAll", 
			    query="select object(o) from Patient o"),
    @NamedQuery(name="Patient.findByFirstName",
    			query="select object(o) from Patient o where o.firstName=:firstName"),
    @NamedQuery(name="Patient.findByLastName",
       			query="select object(o) from Patient o where o.lastName like :lastName"),
    @NamedQuery(name="Patient.findByMrNumber",
    			query="select object(o) from Patient o where o.mrNumber like :mrNumber"),
    @NamedQuery(name="Patient.findById",
    		    query="select object(o) from Patient o where o.id=:id")
})
public class Patient extends Person implements IAuditable {


	@Column(name="mr_number")
	private String mrNumber ;
	
	@Column(name="gender", length=1)
	private String gender ;
	
	@Column(name="date_of_birth")
	private Date dateOfBirth ;
	
	@Column(name="other_first_name")
	private String otherFirstName ;
	
	@Column(name="other_last_name")
	private String otherLastName ;
	
	@Column(name="notes", columnDefinition="LONGTEXT")
	private String notes ;
	
	@OneToMany( cascade={CascadeType.ALL} )
    @JoinTable(name = "patient_address_jt",
      joinColumns = {@JoinColumn(name = "patient_id")},
      inverseJoinColumns = {@JoinColumn(name = "address_id")})
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<Address> addresses ;
	
	@OneToMany(cascade={CascadeType.ALL} )
	private List<SessionInfo> sessions ;
	
	
	public String getMrNumber() {
		return mrNumber;
	}

	public void setMrNumber(String mrNumber) {
		this.mrNumber = mrNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	
	public void addAddress( Address addr ) {
		if( getAddresses() != null && 
		    !getAddresses().contains( addr ) ) {
			getAddresses().add( addr ) ;
		} else {
			addresses = new ArrayList<Address>() ;
			addresses.add( addr ) ;
		}
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
		
	public List<SessionInfo> getSessions() {
		return sessions;
	}

	public void setSessions(List<SessionInfo> sessions) {
		this.sessions = sessions;
	}
	
	public void addSession( SessionInfo ses ) {
		if ( sessions == null ) {
			sessions = new ArrayList<SessionInfo>() ;
		}
		sessions.add( ses ) ;
	}
	
	public String getOtherFirstName() {
		return otherFirstName;
	}

	public void setOtherFirstName(String otherFirstName) {
		this.otherFirstName = otherFirstName;
	}

	public String getOtherLastName() {
		return otherLastName;
	}

	public void setOtherLastName(String otherLastName) {
		this.otherLastName = otherLastName;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return log() ;
	}
	
	public String log() {
				return "Patient [addresses=" + addresses + ", dateOfBirth="
				+ dateOfBirth + ", gender=" + gender + ", mrNumber=" + mrNumber
				+ ", otherFirstName=" + otherFirstName + ", otherLastName="
				+ otherLastName + ", sessions=" + sessions + ", createdDate="
				+ createdDate + ", id=" + id + ", lastModified=" + lastModified + "]";
	}


	
}
