package com.loquatic.cerescan.controller.global;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.loquatic.cerescan.api.entities.Physician;
import com.loquatic.cerescan.api.entities.lookups.Degree;
import com.loquatic.cerescan.api.persistence.managers.AttorneyManager;
import com.loquatic.cerescan.api.persistence.managers.LookupEntityManager;
import com.loquatic.cerescan.api.persistence.managers.PhysicanManager;
import com.loquatic.cerescan.view.ErrorLabel;

public class EditPhysicianController extends GenericForwardComposer {
	private static Log log = LogFactory.getLog(EditPhysicianController.class);
	private Physician physician = new Physician();
	private Address address = new Address();
	private List<Physician> physicians;
	private List<Degree> allDegrees;
	private Div searchResult;
	private Listbox searchResultLb, degreeLb;
	private Textbox searchLastNameTb, lastNameTb, firstNameTb, practiceNameTb,
			specialityTb, npiNumberTb, phoneTb, faxTb, emailTb, street1Tb,
			street2Tb, cityTb, zipCodeTb, cityCodeTb, countryTb;
	private ErrorLabel errLbl;
	private Button updateBtn, clearBtn;
	private Label successLbl;
	private Listbox stateLb;

	public Physician getPhysician() {
		return physician;
	}

	public Address getAddress() {
		return address;
	}

	public List<Physician> getPhysicians() {
		return physicians;
	}

	public List<Degree> getAllDegrees() {
		return allDegrees;
	}

	public void setAllDegrees(List<Degree> allDegrees) {
		this.allDegrees = allDegrees;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute("controller", this);
		allDegrees = LookupEntityManager.getAllDegrees();
	}

	public void lastNameSearch() {
		successLbl.setValue("");
		if (searchLastNameTb.getValue().length() < 3) {
			errLbl
					.setValue("Please enter at least 3 characters when searching");
			searchResult.setVisible(false);
		} else {
			errLbl.setValue("");
			this.physicians = PhysicanManager.searchByLastName(searchLastNameTb
					.getValue());
			if (physicians != null && physicians.size() > 0) {
				List<Physician> physicianList = (List<Physician>) searchResultLb
						.getModel();
				physicianList.clear();
				physicianList.addAll(physicians);
				errLbl.setValue("");
				searchResult.setVisible(true);
			} else {
				errLbl.setValue("No Physician Found");
				searchResult.setVisible(false);
			}
		}
	}

	public void onSelect$searchResultLb(Event event) {
		this.physician = (Physician) searchResultLb.getSelectedItem()
				.getValue();
		lastNameTb.setValue(physician.getLastName());
		firstNameTb.setValue(physician.getFirstName());
		phoneTb.setValue(physician.getWorkPhone());
		faxTb.setValue(physician.getFaxPhone());
		emailTb.setValue(physician.getEmail1());
		practiceNameTb.setValue(physician.getPracticeName());
		specialityTb.setValue(physician.getSpeciality());
		npiNumberTb.setValue(physician.getNpiNumber());

		if (physician.getAddresses() != null
				&& !physician.getAddresses().isEmpty()) {
			this.address = physician.getAddresses().get(0);
		} else {
			this.address = new Address();
		}

		List<Listitem> li = degreeLb.getItems();
		for (Listitem item : li) {
			Degree value = (Degree) item.getValue();
			if (value.getName().equals(physician.getDegree().getName())) {
				item.setSelected(true);
			}
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

	public void onClick$updateBtn(MouseEvent event) {
		successLbl.setValue("");
		if (isValid()) {
			List<Physician> _phys = PhysicanManager.searchByLastName(lastNameTb
					.getValue());

			if (_phys.size() > 0 && _phys.get(0).getId() != physician.getId()) {
				try {
					String msg = "Physician with last name "
							+ lastNameTb.getValue()
							+ " already exist.  Would you still like to update the physician?";
					Messagebox.show(msg, "Prompt", Messagebox.YES
							| Messagebox.NO, Messagebox.QUESTION,
							new EventListener() {
								public void onEvent(Event evt) {
									switch (((Integer) evt.getData())
											.intValue()) {
									case Messagebox.YES:
										doPhysUpdate();
										break;
									case Messagebox.NO:
										errLbl
												.addErr("Physician already exist");
									}
								}
							});

				} catch (InterruptedException e) {
					log.error(e);
				}
			} else {
				doPhysUpdate();
			}
		}
	}

	public void doPhysUpdate() {
		setAddress();
		physician.setDegree((Degree) degreeLb.getSelectedItem().getValue());
		PhysicanManager.updatePhysician(physician);
		errLbl.setValue("");
		successLbl.setValue("Successfully Updated: " + physician.getLastName());
	}

	public void onClick$addBtn(MouseEvent event) {
		successLbl.setValue("");
		if (isValid()) {
			List<Physician> _phys = PhysicanManager.searchByLastName(lastNameTb
					.getValue());

			if (_phys.size() > 0 && _phys.get(0).getId() != physician.getId()) {
				try {
					String msg = "Physician with last name "
							+ lastNameTb.getValue()
							+ " already exist.  Would you still like to add the physician?";
					Messagebox.show(msg, "Prompt", Messagebox.YES
							| Messagebox.NO, Messagebox.QUESTION,
							new EventListener() {
								public void onEvent(Event evt) {
									switch (((Integer) evt.getData())
											.intValue()) {
									case Messagebox.YES:
										doAddPhys();
										break;
									case Messagebox.NO:
										errLbl
												.addErr("Physician already exist");
									}
								}
							});

				} catch (InterruptedException e) {
					log.error(e);
				}
			} else {
				doAddPhys();
			}
		}
	}

	private void doAddPhys() {
		setAddress();
		physician.setDegree((Degree) degreeLb.getSelectedItem().getValue());
		PhysicanManager.addPhysician(physician);
		toggleClear();
		errLbl.setValue("");
		successLbl.setValue("Successfully Added");
	}

	public void onClick$searchLastNameBtn(MouseEvent event) {
		successLbl.setValue("");
		errLbl.setValue("");
		lastNameSearch();
	}

	private void setAddress() {
		List<Address> addresses = physician.getAddresses();
		if (physician.getAddresses() != null
				&& !physician.getAddresses().isEmpty()) {
			addresses.set(0, address);
		} else {
			addresses = new ArrayList<Address>();
			addresses.add(address);
		}
		physician.setAddresses(addresses);
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

	private void toggleClear() {
		physician = new Physician();
		address = new Address();
		lastNameTb.setValue("");
		firstNameTb.setValue("");
		phoneTb.setValue("");
		faxTb.setValue("");
		emailTb.setValue("");

		practiceNameTb.setValue("");
		specialityTb.setValue("");
		npiNumberTb.setValue("");

		street1Tb.setValue("");
		street2Tb.setValue("");
		cityTb.setValue("");
		zipCodeTb.setValue("");
		cityCodeTb.setValue("");
		setState("");
		searchResultLb.clearSelection();
		updateBtn.setVisible(false);
		clearBtn.setVisible(false);
	}

	private boolean isValid() {
		successLbl.setValue("");
		errLbl.reset();
		if (lastNameTb.getValue().isEmpty()) {
			errLbl.addErr("Last Name cannot be empty.");
		}

		if (firstNameTb.getValue().isEmpty()) {
			errLbl.addErr("First Name cannot be empty.");
		}

		if (degreeLb.getSelectedItem() == null) {
			errLbl.addErr("A Degree must be selected.");
		} else {
			Degree _d = (Degree) degreeLb.getSelectedItem().getValue();
			if (_d.getName().equals("______")
					|| _d.getName().equals("Please Select a Degree")) {
				errLbl.addErr("Invalid degree selection.");
			}
		}

		errLbl.refresh();

		if (errLbl.getErrs().size() > 0) {
			return false;
		} else {
			return true;
		}
	}
}
