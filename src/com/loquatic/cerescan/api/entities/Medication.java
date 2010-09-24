package com.loquatic.cerescan.api.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.loquatic.cerescan.api.entities.lookups.Dosage;
import com.loquatic.cerescan.api.entities.lookups.Effective;
import com.loquatic.cerescan.api.entities.lookups.MedicationType;
import com.loquatic.cerescan.api.entities.lookups.Medicine;
import com.loquatic.cerescan.api.entities.lookups.Schedule;

@Entity
@Table(name = "medications")
public class Medication extends CerescanBaseEntity implements IAuditable{

	@Column(name = "adverse_reaction")
	private String adverseReaction;

	@Column(name = "strength")
	private String strength;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "dosage_id", referencedColumnName = "id")
	private Dosage dosage;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "schedule_id", referencedColumnName = "id")
	private Schedule schedule;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "medicine_id", referencedColumnName = "id")
	private Medicine medicine;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "medication_type_id", referencedColumnName = "id")
	private MedicationType type;

	@ManyToOne
	@JoinColumn(name = "effective_id", referencedColumnName = "id")
	private Effective effective;

	public Medication() {
	}

	public Medication(Dosage dos, Schedule sched, Medicine med) {
		super();
		this.dosage = dos;
		this.schedule = sched;
		this.medicine = med;
	}

	public Dosage getDosage() {
		return dosage;
	}

	public void setDosage(Dosage dosage) {
		this.dosage = dosage;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	public MedicationType getType() {
		return type;
	}

	public void setType(MedicationType type) {
		this.type = type;
	}

	public Effective getEffective() {
		return effective;
	}

	public void setEffective(Effective effective) {
		this.effective = effective;
	}

	public String getAdverseReaction() {
		return adverseReaction;
	}

	public void setAdverseReaction(String adverseReaction) {
		this.adverseReaction = adverseReaction;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	@Override
	public String toString() {
		return log();
	}

	public String log() {
		return "Medication [strength=" + strength + ", dosage=" + dosage
				+ ", medicine=" + medicine + ", schedule=" + schedule
				+ ", type=" + type + ", createdDate=" + createdDate + ", id="
				+ id + ", lastModified=" + lastModified + ", effective="
				+ effective + ", adverseReaction=" + adverseReaction + "]";
	}

}
