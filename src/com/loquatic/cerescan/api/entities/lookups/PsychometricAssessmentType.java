package com.loquatic.cerescan.api.entities.lookups;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="psychometric_assessment_type_lookup")
@NamedQueries({
	@NamedQuery(name="PsychometricAssementType.findAll",
			    query="select object(p) from PsychometricAssessmentType p order by p.orderNumber")

})
public class PsychometricAssessmentType extends LookUp {

}
