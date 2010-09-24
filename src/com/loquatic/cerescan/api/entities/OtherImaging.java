package com.loquatic.cerescan.api.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.loquatic.cerescan.api.entities.lookups.OtherImagingType;


@Entity
@Table(name="other_imaging")
public class OtherImaging  extends UploadEntity implements IAuditable {
	@ManyToOne( cascade=CascadeType.ALL)
	@JoinColumn(name="other_imaging_type_id", referencedColumnName="id") 
    private OtherImagingType otherImagingType ;

	public OtherImagingType getOtherImagingType() {
		return otherImagingType;
	}

	public void setOtherImagingType(OtherImagingType otherImagingType) {
		this.otherImagingType = otherImagingType;
	}

	@Override
	public String toString() {
		return log() ;
	}
	
	public String log() {
				return "OtherImagingType [otherImagingType="
				+ otherImagingType 
				+ ", createdDate=" + createdDate + ", id=" + id
				+ ", lastModified=" + lastModified + "]";
	}
}
