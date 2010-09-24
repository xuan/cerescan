package com.loquatic.cerescan.api.entities;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Person extends CerescanBaseEntity implements IAuditable {

	
	@Column(name="first_name")
	private String firstName ;
	
	@Column(name="last_name")
	private String lastName ;
	
	@Column(name="middle_name")
	private String middleName ;
	
	@Column(name="home_phone")
	private String homePhone;
	
	@Column(name="work_phone")
	private String workPhone;
	
	@Column(name="mobile_phone")
	private String mobilePhone;
	
	@Column(name="fax_phone")
	private String faxPhone ;
	
	@Column(name="email1")
	private String email1 ;
	
	@Column(name="email2")
	private String email2 ;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String mn) {
		middleName = mn;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getFaxPhone() {
		return faxPhone;
	}

	public void setFaxPhone(String faxPhone) {
		this.faxPhone = faxPhone;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	@Override
	public String toString() {
		return log() ;
	}
	
	public String log() {
				return "Person [email1=" + email1 + ", email2=" + email2
				+ ", faxPhone=" + faxPhone + ", firstName=" + firstName
				+ ", homePhone=" + homePhone + ", lastName=" + lastName
				+ ", middleName=" + middleName + ", mobilePhone=" + mobilePhone
				+ ", workPhone=" + workPhone + ", createdDate=" + createdDate
				+ ", id=" + id + ", lastModified=" + lastModified + "]";
	}

	
	
	
}
