package com.loquatic.cerescan.api.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@MappedSuperclass
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class CerescanBaseEntity {
	
	@Transient
	Log log = LogFactory.getLog("audit.logger.category") ;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	protected long id ;

	@Column(name="created_date")
	protected Timestamp createdDate ;
	
	@Column(name="last_modified")
	protected Timestamp lastModified ;
	
//	@Version
//	protected Timestamp version ;
	
	@PrePersist
	public void setCreateDate() {
		createdDate = new Timestamp( System.currentTimeMillis() ) ;
		if( this instanceof IAuditable ) {
			log.info( "PRESAVE(new): " + ((IAuditable)this).log() ) ;
		}
	}
	
	@PreUpdate
	public void setModifiedDate() {
		lastModified = new Timestamp( System.currentTimeMillis() ) ;
		if( this instanceof IAuditable ) {
			log.info( "PRESAVE(existing): " +((IAuditable)this).log() ) ;	

		}
	}
	
	@PostPersist
	public void postPersist() {
		if( this instanceof IAuditable ) {
			log.info( "POSTSAVE(new): " + ((IAuditable)this).log() ) ;
		}
	}
	
	@PostUpdate
	public void postUpdate() {
		if( this instanceof IAuditable ) {
			log.info( "POSTSAVE(Existing): " + ((IAuditable)this).log() ) ;
		}
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp cDate) {
		createdDate = cDate;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp mDate) {
		lastModified = mDate;
	}

//	public Timestamp getVersion() {
//		return version;
//	}
//
//	public void setVersion(Timestamp version) {
//		this.version = version;
//	}

	@Override
	public String toString() {
		return log() ;
	}
	
	public String log() {
		return "createdDate=" + createdDate + ", id=" + id
				+ ", lastModified=" + lastModified ;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CerescanBaseEntity other = (CerescanBaseEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
