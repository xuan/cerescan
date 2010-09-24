package com.loquatic.cerescan.common;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Label;

import com.loquatic.cerescan.api.entities.Address;
import com.loquatic.cerescan.controller.patient.EditPatientDemographicsController;

public class AddressConverter implements TypeConverter {
	private static Log log = LogFactory.getLog(AddressConverter.class);

	@Override
	public Object coerceToBean(Object val, Component comp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address coerceToUi(Object val, Component comp) {
		List<Address> addresses = (List<Address>) val;
		if (addresses.size() > 0) {
			Address _add = addresses.get(0);
			StringBuffer sb = new StringBuffer();
			sb.append(_add.getStreet1() + "\n");
			sb.append(_add.getStreet2() + "\n");
			sb.append(_add.getCity() + ", " + _add.getState() + " "
					+ _add.getZipCode() + "\n");
			sb.append(_add.getCountry() + " " + _add.getCityCode() + "\n");
			((Label) comp).setValue(sb.toString());
			return addresses.get(0);
		} else {
			return null;
		}
	}
}
