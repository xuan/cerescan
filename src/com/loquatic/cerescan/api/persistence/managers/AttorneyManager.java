package com.loquatic.cerescan.api.persistence.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.loquatic.cerescan.api.entities.Address;
import com.loquatic.cerescan.api.entities.Attorney;

public class AttorneyManager extends CerescanPersistenceManager {

	public static void addAttorney(Attorney atty) {
		if (atty != null) {
			Attorney _att = new Attorney();
			if (atty.getAddresses().size() > 0) {
				Address _add = new Address();
				_add.setStreet1(atty.getAddresses().get(0).getStreet1());
				_add.setStreet2(atty.getAddresses().get(0).getStreet2());
				_add.setCity(atty.getAddresses().get(0).getCity());
				_add.setState(atty.getAddresses().get(0).getState());
				_add.setZipCode(atty.getAddresses().get(0).getZipCode());
				_add.setCityCode(atty.getAddresses().get(0).getCityCode());
				_add.setCountry(atty.getAddresses().get(0).getCountry());
				_add.setAsCurrentAddress(true);
				List addresses = new ArrayList<Address>();
				addresses.add(_add);
				_att.setAddresses(addresses);
			}
			_att.setFirstName(atty.getFirstName());
			_att.setLastName(atty.getLastName());
			_att.setFirmName(atty.getFirmName());
			_att.setWorkPhone(atty.getWorkPhone());
			_att.setFaxPhone(atty.getFaxPhone());
			_att.setEmail1(atty.getEmail1());
			persist(_att);
		}
	}

	public static void deleteAttorney(Attorney atty) {
		if (atty != null) {
			delete(atty);
		}
	}

	public static void updateAttorney(Attorney atty) {
		if (atty != null) {
			merge(atty);
		}
	}

	public static Attorney findById(long id) {
		Attorney attny = (Attorney) findById("Attorney.findById", id);
		return attny;
	}

	public static List<Attorney> findByLastName(String lastName) {
		List<Attorney> attorneys = genericOneParamFinder(
				"Attorney.findByLastName", "lastName", lastName);
		return attorneys;
	}

	public static List<Attorney> findByLastNameFirstNameFirmName(
			String lastName, String firstName, String firmName) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("lastName", lastName);
		params.put("firstName", firstName);
		params.put("firmName", firmName);
		List<Attorney> attorneys = genericMultiParamMultiResultFinder(
				"Attorney.findByLastNameFirstNameFirmName", params);
		return attorneys;
	}

	public static List<Attorney> searchByLastName(String lastName) {
		List<Attorney> attorneys = genericOneParamFinder(
				"Attorney.searchByLastName", "lastName", lastName + "%");
		return attorneys;
	}

	public static List<Attorney> findAll() {
		List<Attorney> attorneys = genericFindAll("Attorney.findAll");
		return attorneys;
	}

}
