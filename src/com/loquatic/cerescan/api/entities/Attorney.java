package com.loquatic.cerescan.api.entities;

import java.util.List ;

import javax.persistence.CascadeType ;
import javax.persistence.Column ;
import javax.persistence.Entity ;
import javax.persistence.JoinColumn ;
import javax.persistence.JoinTable ;
import javax.persistence.ManyToMany ;
import javax.persistence.NamedQueries ;
import javax.persistence.NamedQuery ;
import javax.persistence.Table ;


@Entity
@Table(name="attorney")
@NamedQueries({
	@NamedQuery(name="Attorney.findAll", 
			    query="select object(a) from Attorney a"),
    @NamedQuery(name="Attorney.findByLastNameFirstNameFirmName",
    			query="select a from Attorney a where a.lastName=:lastName and a.firstName=:firstName and a.firmName=:firmName"),
    @NamedQuery(name="Attorney.findByLastName",
       			query="select object(a) from Attorney a where a.lastName=:lastName"),
    @NamedQuery(name="Attorney.searchByLastName",
       			query="select object(a) from Attorney a where a.lastName like :lastName"),
    @NamedQuery(name="Attorney.findById",
    		    query="select object(a) from Attorney a where a.id=:id")
})
public class Attorney extends Person implements IAuditable {

		
	@Column(name="firm_name")
	private String firmName ;
		
	@ManyToMany( cascade={CascadeType.ALL} )
    @JoinTable(name = "attorney_address_jt",
      joinColumns = {@JoinColumn(name = "attorney_id")},
      inverseJoinColumns = {@JoinColumn(name = "address_id")})
	private List<Address> addresses ;
	

	public List<Address> getAddresses() {
		return addresses;
	}
	
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	@Override
	public String toString() {
		return log() ;
	}
	
	public String log() {
		return "Attorney [addresses=" + addresses + ", firmName=" + firmName
				+ ", createdDate=" + createdDate + ", id=" + id
				+ ", lastModified=" + lastModified  + "]";
	}

	
	
}
