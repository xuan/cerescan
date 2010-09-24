package com.loquatic.cerescan.api.entities;

import java.util.List ;

import javax.persistence.CascadeType ;
import javax.persistence.Entity ;
import javax.persistence.JoinColumn ;
import javax.persistence.JoinTable ;
import javax.persistence.ManyToMany ;
import javax.persistence.ManyToOne ;
import javax.persistence.NamedQueries ;
import javax.persistence.NamedQuery ;
import javax.persistence.OneToMany ;
import javax.persistence.Table ;

import com.loquatic.cerescan.api.entities.lookups.BrainArea ;
import com.loquatic.cerescan.api.entities.lookups.BrainHemisphere ;

@Entity
@Table(name = "brain_hemisphere_area")
@NamedQueries( { @NamedQuery(name = "BrainHemisphereArea.findAll", 
                 query = "select object(b) from BrainHemisphereArea b") })
public class BrainHemisphereArea extends CerescanBaseEntity {

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "brain_hemisphere_brain_area_jt", 
     joinColumns = @JoinColumn(name = "brain_hemisphere_area_id", referencedColumnName = "id"), 
     inverseJoinColumns = @JoinColumn(name = "brain_area_id", referencedColumnName = "id"))
  private List<BrainArea> brainArea ;
  
  @ManyToOne
  @JoinColumn(name = "brain_hemisphere_lookup_id", referencedColumnName = "id")
  private BrainHemisphere brainHemisphere ;
  
  
  public List<BrainArea> getBrainArea() {
    return brainArea ;
  }
  public void setBrainArea( List<BrainArea> brainArea ) {
    this.brainArea = brainArea ;
  }
  public BrainHemisphere getBrainHemisphere() {
    return brainHemisphere ;
  }
  public void setBrainHemisphere( BrainHemisphere brainHemisphere ) {
    this.brainHemisphere = brainHemisphere ;
  }
  
  
}
