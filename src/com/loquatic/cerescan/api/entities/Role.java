package com.loquatic.cerescan.api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role", uniqueConstraints = @UniqueConstraint(columnNames = { "authority" }))
@NamedQueries( {
		@NamedQuery(name = "Role.findAll", query = "select object(o) from Role o"),
		@NamedQuery(name = "Role.findByAuthority", query = "select object(o) from Role o where o.authority=:authority") })
public class Role extends CerescanBaseEntity implements GrantedAuthority {

	@Column(name = "authority", length = 30, nullable = false)
	private String authority;
	@Column(name = "description")
	private String description;
	
	@Override
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
