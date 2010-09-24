package com.loquatic.cerescan.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.lang.Objects;
import org.zkoss.zul.Label;
import org.zkoss.zul.impl.api.XulElement;

public class ErrorLabel extends Label {
	private static Log log = LogFactory.getLog(ErrorLabel.class);
	private List<String> errs = new ArrayList<String>();

	public ErrorLabel() {
		super();
	}
	
	public void addErr(String msg){
		errs.add(msg);
	}

	public List<String> getErrs() {
		return errs;
	}

	public void setErrs(List<String> errs) {
		this.errs = errs;
		refresh();
	}
	
	public void refresh(){
		StringBuffer sb = new StringBuffer("");
		if (errs.size() > 0) {
			sb.append("Please fix the following error(s)\n");
			for (String err : errs) {
				sb.append("* " + err + "\n");
			}
		}
		super.setValue(sb.toString());
	}
	
	public void reset(){
		errs = new ArrayList<String>();
		super.setValue("");
	}
}
