package com.loquatic.cerescan.api.entities.lookups;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.loquatic.cerescan.api.entities.SessionInfo;

@Entity
@Table(name = "education_level_lookup")
@NamedQueries( { @NamedQuery(name = "EducationLevel.findAll", query = "select object(e) from EducationLevel e order by e.orderNumber") })
public class EducationLevel extends LookUp {

}
