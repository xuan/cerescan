package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OrderBy;

import com.loquatic.cerescan.api.entities.CerescanBaseEntity;

@MappedSuperclass
public abstract class LookUp extends CerescanBaseEntity {

	@Column(name = "name")
	private String name;

	@Column(name = "deleted")
	private Integer deleted = 0;
	
	@Column(name = "order_number")
	private Integer orderNumber;

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Override
	public String toString() {
		return "[id = " + getId() + ", deleted=" + deleted + ", name=" + name
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LookUp other = (LookUp) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
