package com.loquatic.cerescan.api.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.JoinColumn;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }))
@NamedQueries( {
		@NamedQuery(name = "User.findAll", query = "select object(o) from User o"),
		@NamedQuery(name = "User.findByUsername", query = "select object(o) from User o where o.username=:username") })
public class User extends Person implements UserDetails {

	@Column(name = "username", length = 30, nullable = false, unique = true)
	private String username;
	@Column(name = "password", length = 30, nullable = false, unique = true)
	private String password;
	@Column(name = "account_non_expired", nullable = false)
	private boolean accountNonExpired;
	@Column(name = "account_non_locked", nullable = false)
	private boolean accountNonLocked;
	@Column(name = "credentials_non_expired", nullable = false)
	private boolean credentialsNonExpired;
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	
	@ManyToMany(cascade = { CascadeType.PERSIST })
	@JoinTable(name = "user_role_id", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	protected List<Role> roles = new ArrayList<Role>();

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> grantedAuthority = new ArrayList();
		grantedAuthority.addAll(roles);
		return grantedAuthority;
	}

	@Override
	public String toString() {
		return "User [accountNonExpired=" + accountNonExpired
				+ ", accountNonLocked=" + accountNonLocked
				+ ", credentialsNonExpired=" + credentialsNonExpired
				+ ", enabled=" + enabled + ", roles=" + roles + ", username="
				+ username + ", createdDate=" + createdDate + ", id=" + id
				+ ", lastModified=" + lastModified + "]";
	}

}
