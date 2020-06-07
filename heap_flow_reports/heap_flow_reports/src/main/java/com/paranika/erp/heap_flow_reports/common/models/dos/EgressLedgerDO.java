package com.paranika.erp.heap_flow_reports.common.models.dos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "egress_ledgers")
public class EgressLedgerDO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "record_date", nullable = false)
	// @Temporal(TemporalType.DATE)
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

	@Column(name = "issued_for_dept", nullable = true)
	private String issuedForDept;

	@Column(name = "approved_by", nullable = true)
	private String approvedBy;

	@Column(name = "issued_by", nullable = true)
	private String issuedBy;

	@Column(name = "issue_slip_number", nullable = true)
	private String issueSlipNumber;
	@Column(name = "outgoing_mat_price", nullable = true)
	private Double outgoingMaterialPrice;

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

	public String getIssuedForDept() {
		return issuedForDept;
	}

	public void setIssuedForDept(String issuedForDept) {
		this.issuedForDept = issuedForDept;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	public String getIssueSlipNumber() {
		return issueSlipNumber;
	}

	public void setIssueSlipNumber(String issueSlipNumber) {
		this.issueSlipNumber = issueSlipNumber;
	}

	public Double getOutgoingMaterialPrice() {
		return outgoingMaterialPrice;
	}

	public void setOutgoingMaterialPrice(Double outgoingMaterialPrice) {
		this.outgoingMaterialPrice = outgoingMaterialPrice;
	}

}
