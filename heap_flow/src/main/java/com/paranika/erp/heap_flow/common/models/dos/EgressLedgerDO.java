package com.paranika.erp.heap_flow.common.models.dos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "egress_ledgers")
public class EgressLedgerDO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "record_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date recordDate;

	@ManyToOne
	@JoinColumn(name = "inventory_type_fk")
	private InventoryTypeDO inventoryType;

	@Column(name = "outgoing_quantity", nullable = false)
	private double outgoingQuantity;

	@ManyToOne
	@JoinColumn(name = "inventory_item_fk")
	private InventoryItemDO outgoingMaterial;

	// column will hold R&D or Project(Although not used anywhere)
	@Column(name = "classification_category", nullable = true)
	private String classificationCategory;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "machine_fk")
	private MachineDO consumingMachine;

	@Column(name = "issued_to", nullable = true)
	private String issuedTo;

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public InventoryTypeDO getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(InventoryTypeDO inventoryType) {
		this.inventoryType = inventoryType;
	}

	public double getOutgoingQuantity() {
		return outgoingQuantity;
	}

	public void setOutgoingQuantity(double outgoingQuantity) {
		this.outgoingQuantity = outgoingQuantity;
	}

	public InventoryItemDO getOutgoingMaterial() {
		return outgoingMaterial;
	}

	public void setOutgoingMaterial(InventoryItemDO outgoingMaterial) {
		this.outgoingMaterial = outgoingMaterial;
	}

	public String getClassificationCategory() {
		return classificationCategory;
	}

	public void setClassificationCategory(String classificationCategory) {
		this.classificationCategory = classificationCategory;
	}

	public MachineDO getConsumingMachine() {
		return consumingMachine;
	}

	public void setConsumingMachine(MachineDO consumingMachine) {
		this.consumingMachine = consumingMachine;
	}

	public String getIssuedTo() {
		return issuedTo;
	}

	public void setIssuedTo(String issuedTo) {
		this.issuedTo = issuedTo;
	}

}
