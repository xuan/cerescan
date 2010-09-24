package com.loquatic.cerescan.controller.global;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.loquatic.cerescan.api.entities.Address;
import com.loquatic.cerescan.api.entities.Attorney;
import com.loquatic.cerescan.api.entities.Medication;
import com.loquatic.cerescan.api.persistence.managers.AttorneyManager;
import com.loquatic.cerescan.view.ErrorLabel;

public class EditAttorneyController extends GenericForwardComposer {
	private static Log log = LogFactory.getLog(EditAttorneyController.class);
	private Attorney attorney = new Attorney();
	private Address address = new Address();
	private List<Attorney> attornies;
	private Div searchResult;
	private Listbox searchResultLb;
	private Textbox searchLastNameTb, lastNameTb, firstNameTb, firmNameTb,
			phoneTb, faxTb, emailTb, street1Tb, street2Tb, cityTb, zipCodeTb,
			countryTb, cityCodeTb;
	private ErrorLabel errLbl;
	private Button updateBtn, clearBtn;
	private Label successLbl;
	private Listbox stateLb;

	public Attorney getAttorney() {
		return attorney;
	}

	public Address getAddress() {
		return address;
	}

	public List<Attorney> getAttornies() {
		return attornies;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute("controller", this);
	}

	public void lastNameSearch() {
		successLbl.setValue("");
		if (searchLastNameTb.getValue().length() < 3) {
			errLbl
					.setValue("Please enter at least 3 characters when searching");
			searchResult.setVisible(false);
		} else {
			errLbl.setValue("");
			this.attornies = AttorneyManager.searchByLastName(searchLastNameTb
					.getValue());
			List<Attorney> attorneyList = (List<Attorney>) searchResultLb
					.getModel();
			if (attornies != null && attornies.size() > 0) {

				attorneyList.clear();
				attorneyList.addAll(attornies);
				errLbl.setValue("");
				searchResult.setVisible(true);
			} else {
				errLbl.setValue("No Attorney Found for: "
						+ searchLastNameTb.getValue());
				searchResult.setVisible(false);
			}
		}
	}

	private void setAddress() {
		List<Address> addresses = attorney.getAddresses();
		if (attorney.getAddresses() != null
				&& !attorney.getAddresses().isEmpty()) {
			addresses.set(0, address);
		} else {
			addresses = new ArrayList<Address>();
			addresses.add(address);
		}
		attorney.setAddresses(addresses);
	}

	private void setState(String selectedState) {
		List<Listitem> li = stateLb.getItems();
		for (Listitem item : li) {
			String value = (String) item.getValue();
			if (value.equals(selectedState)) {
				item.setSelected(true);
			}
		}
	}

	public void onSelect$searchResultLb(Event event) {
		this.attorney = (Attorney) searchResultLb.getSelectedItem().getValue();
		lastNameTb.setValue(attorney.getLastName());
		firstNameTb.setValue(attorney.getFirstName());
		firmNameTb.setValue(attorney.getFirmName());
		phoneTb.setValue(attorney.getWorkPhone());
		faxTb.setValue(attorney.getFaxPhone());
		emailTb.setValue(attorney.getEmail1());

		if (attorney.getAddresses() != null
				&& !attorney.getAddresses().isEmpty()) {
			this.address = attorney.getAddresses().get(0);
		} else {
			this.address = new Address();
		}

		street1Tb.setValue(address.getStreet1());
		street2Tb.setValue(address.getStreet2());
		cityTb.setValue(address.getCity());
		zipCodeTb.setValue(address.getZipCode());
		cityCodeTb.setValue(address.getCityCode());
		countryTb.setValue(address.getCountry());
		setState(address.getState());
		updateBtn.setVisible(true);
		clearBtn.setVisible(true);
	}

	public void onClick$clearBtn(MouseEvent event) {
		toggleClear();
	}

	private void toggleClear() {
		attorney = new Attorney();
		address = new Address();
		lastNameTb.setValue("");
		firstNameTb.setValue("");
		firmNameTb.setValue("");
		phoneTb.setValue("");
		faxTb.setValue("");
		emailTb.setValue("");
		street1Tb.setValue("");
		street2Tb.setValue("");
		cityTb.setValue("");
		zipCodeTb.setValue("");
		cityCodeTb.setValue("");
		countryTb.setValue("");
		setState("");
		searchResultLb.clearSelection();
		updateBtn.setVisible(false);
		clearBtn.setVisible(false);
	}

	private boolean isValid() {
		errLbl.reset();
		boolean noCheck = false;
		if (lastNameTb.getValue().isEmpty()) {
			errLbl.addErr("Last Name cannot be empty.");
			noCheck = true;
		}

		if (firstNameTb.getValue().isEmpty()) {
			errLbl.addErr("First Name cannot be empty.");
			noCheck = true;
		}

		if (firmNameTb.getValue().isEmpty()) {
			errLbl.addErr("Firm cannot be empty.");
			noCheck = true;
		}

		errLbl.refresh();

		if (errLbl.getErrs().size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	public void onClick$updateBtn(MouseEvent event) {
		successLbl.setValue("");
		if (isValid()) {
			List<Attorney> _atts = AttorneyManager.searchByLastName(lastNameTb
					.getValue());

			if (_atts.size() > 0 && _atts.get(0).getId() != attorney.getId()) {
				try {
					String msg = "Attorney with last name "
							+ lastNameTb.getValue()
							+ " already exist.  Would you still like to update the attorney?";
					Messagebox.show(msg, "Prompt", Messagebox.YES
							| Messagebox.NO, Messagebox.QUESTION,
							new EventListener() {
								public void onEvent(Event evt) {
									switch (((Integer) evt.getData())
											.intValue()) {
									case Messagebox.YES:
										doAttorneyUpdate();
										break;
									case Messagebox.NO:
										errLbl.addErr("Attorney already exist");
									}
								}
							});

				} catch (InterruptedException e) {
					log.error(e);
				}
			} else {
				doAttorneyUpdate();
			}
		}
	}

	private void doAttorneyUpdate() {
		setAddress();
		AttorneyManager.updateAttorney(attorney);
		populatePatientAttorneySearch();
		successLbl.setValue("Successfully Updated");
	}

	public void onClick$addBtn(MouseEvent event) {
		successLbl.setValue("");
		if (isValid()) {

			List<Attorney> _atts = AttorneyManager.searchByLastName(lastNameTb
					.getValue());

			if (_atts.size() > 0 && _atts.get(0).getId() != attorney.getId()) {
				try {
					String msg = "Attorney with last name "
							+ lastNameTb.getValue()
							+ " already exist.  Would you still like to add the attorney?";
					Messagebox.show(msg, "Prompt", Messagebox.YES
							| Messagebox.NO, Messagebox.QUESTION,
							new EventListener() {
								public void onEvent(Event evt) {
									switch (((Integer) evt.getData())
											.intValue()) {
									case Messagebox.YES:
										doAddAttorney();
										break;
									case Messagebox.NO:
										errLbl.addErr("Attorney already exist");
									}
								}
							});

				} catch (InterruptedException e) {
					log.error(e);
				}
			} else {
				doAddAttorney();
			}
		}
	}

	private void doAddAttorney() {
		setAddress();
		AttorneyManager.addAttorney(attorney);
		toggleClear();
		populatePatientAttorneySearch();
		successLbl.setValue("Attorney has been saved");
	}

	public void onClick$searchLastNameBtn(MouseEvent event) {
		lastNameSearch();
	}

	private void populatePatientAttorneySearch() {
		for (Object com : desktop.getComponents()) {
			if (com instanceof Textbox) {
				Textbox ib = (Textbox) com;
				if (ib.getId().equals("searchLastNameTb")) {
					ib.setValue(lastNameTb.getValue());
				}
			}
		}
	}
}
