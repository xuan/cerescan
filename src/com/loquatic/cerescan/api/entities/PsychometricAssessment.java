package com.loquatic.cerescan.api.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.loquatic.cerescan.api.entities.lookups.Mini;
import com.loquatic.cerescan.api.entities.lookups.PsychometricAssessmentType;

@Entity
@Table(name = "psychometric_assessment")
public class PsychometricAssessment extends UploadEntity implements IAuditable {

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "psychometric_assessment_type_id", referencedColumnName = "id")
	private PsychometricAssessmentType psychometricAssessmentType;

	public PsychometricAssessmentType getPsychometricAssessmentType() {
		return psychometricAssessmentType;
	}

	public void setPsychometricAssessmentType(
			PsychometricAssessmentType psychometricAssessmentType) {
		this.psychometricAssessmentType = psychometricAssessmentType;
	}

	@Override
	public String toString() {
		return log();
	}

	public String log() {
		return "PsychometricAssessment [psychometricAssessmentType="
				+ psychometricAssessmentType + ", createdDate=" + createdDate
				+ ", id=" + id + ", lastModified=" + lastModified + "]";
	}

}
