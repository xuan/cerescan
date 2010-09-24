package com.loquatic.cerescan.api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "address")
@NamedQueries( { @NamedQuery(name = "Address.findAll", 
                 query = "select object(a) from Address a") })
public class Address extends CerescanBaseEntity implements IAuditable {

	@Column(name = "street1")
	private String street1 = "";

	@Column(name = "street2")
	private String street2 = "";

	@Column(name = "city")
	private String city = "";

	@Column(name = "country")
	private String country = "";

	@Column(name = "city_code")
	private String cityCode = "";

	@Column(name = "state")
	private String state = "";

	@Column(name = "zip_code")
	private String zipCode = "";

	@Column(name = "currentAddr")
	private Boolean currentAddress = false;

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Boolean isCurrentAddress() {
		return currentAddress;
	}

	public void setAsCurrentAddress(Boolean defaultAddress) {
		this.currentAddress = defaultAddress;
	}

	@Override
	public String toString() {
		return log();
	}

	public String log() {
		return "Address [city=" + city + ", cityCode=" + cityCode
				+ ", currentAddress=" + currentAddress + ", state=" + state
				+ ", street1=" + street1 + ", street2=" + street2
				+ ", zipCode=" + zipCode + ", country=" + country
				+ ", createdDate=" + createdDate + ", id=" + id
				+ ", lastModified=" + lastModified + "]";
	}

}
