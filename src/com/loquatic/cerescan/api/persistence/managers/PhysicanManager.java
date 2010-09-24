package com.loquatic.cerescan.api.persistence.managers;

import java.util.ArrayList;
import java.util.List;

import com.loquatic.cerescan.api.entities.Address;
import com.loquatic.cerescan.api.entities.Attorney;
import com.loquatic.cerescan.api.entities.Physician;

public class PhysicanManager extends CerescanPersistenceManager {
	
	public static void addPhysician( Physician phys ) {
		if( phys != null ) {
			Physician _phys = new Physician();
			if (phys.getAddresses().size() > 0) {
				Address _add = new Address();
				_add.setStreet1(phys.getAddresses().get(0).getStreet1());
				_add.setStreet2(phys.getAddresses().get(0).getStreet2());
				_add.setCity(phys.getAddresses().get(0).getCity());
				_add.setState(phys.getAddresses().get(0).getState());
				_add.setZipCode(phys.getAddresses().get(0).getZipCode());
				_add.setCityCode(phys.getAddresses().get(0).getCityCode());
				_add.setCountry(phys.getAddresses().get(0).getCountry());
				_add.setAsCurrentAddress(true);
				List addresses = new ArrayList<Address>();
				addresses.add(_add);
				_phys.setAddresses(addresses);
			}
			_phys.setDegree(phys.getDegree());
			_phys.setFirstName(phys.getFirstName());
			_phys.setLastName(phys.getLastName());
			_phys.setPracticeName(phys.getPracticeName());
			_phys.setSpeciality(phys.getSpeciality());
			_phys.setNpiNumber(phys.getNpiNumber());
			_phys.setWorkPhone(phys.getWorkPhone());
			_phys.setFaxPhone(phys.getFaxPhone());
			_phys.setEmail1(phys.getEmail1());
			persist( _phys ) ;
		}
	}
	
	public static void deletePhysician( Physician phys ) {
		if( phys != null ) {
			delete( phys ) ;
		}
	}
	
	public static void updatePhysician( Physician phys ) {
		if( phys != null ) {
			merge( phys ) ;
		}
	}
	
	public static Physician findById( long id ) {
		Physician phys = (Physician) findById( "Physician.findById", id ) ;
		return phys ;
	}
	
	public static List<Physician> findByLastName( String lastName ) {
		List<Physician> phys = 
			      genericOneParamFinder("Physician.findByLastName", "lastName", lastName ) ;
		return phys ;
	}
	
	public static List<Physician> searchByLastName( String lastName ) {
		List<Physician> phys = 
		      genericOneParamFinder("Physician.searchByLastName", "lastName", lastName + "%" ) ;
	return phys ;
	}
	
	public static List<Physician> findAll() {
		List<Physician> phys = genericFindAll( "Physician.findAll" ) ;
		return phys ;
	}


}
