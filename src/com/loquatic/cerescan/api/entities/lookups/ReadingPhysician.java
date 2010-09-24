package com.loquatic.cerescan.api.entities.lookups;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.loquatic.cerescan.api.entities.SessionInfo;

@Entity
@Table(name = "reading_physician_lookup")
@NamedQueries( { @NamedQuery(name = "ReadingPhysician.findAll", query = "select object(r) from ReadingPhysician r order by r.orderNumber") })
public class ReadingPhysician extends LookUp {

}
