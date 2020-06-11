package com.paranika.erp.heap_flow_reports.common.models.dos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ingress_ledgers")
public class IngressLedgerDO extends BaseDO {

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

	@Column(name = "incoming_quantity", nullable = false)
	private double incomingQuantity;

	@Column(name = "price_per_unit", nullable = false)
	private double pricePerUnit;

	@ManyToOne
	@JoinColumn(name = "inventory_item_fk")
	private InventoryItemDO incomingMaterial;

	// column will hold R&D or Project(Although not used anywhere)
	@Column(name = "classification_category", nullable = true)
	private String classificationCategory;

	@Column(name = "invoice_number", nullable = true)
	private String invoiceNumber;

	@Column(name = "invoice_date", nullable = true)
	// @Temporal(TemporalType.DATE)
	private Date invoiceDate;

	@Column(name = "po_number", nullable = true)
	private String poNumber;

	@Column(name = "po_date", nullable = true)
	// @Temporal(TemporalType.DATE)
	private Date poDate;

	@Column(name = "intent_number", nullable = true)
	private String intentNumber;

	@Column(name = "accepted_by", nullable = true)
	private String materialAcceptedBy;

	@Column(name = "grn_number", nullable = true)
	private String grnNumber;

	@Column(name = "department", nullable = true)
	private String department;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "vendor_fk")
	private VendorDO vendor;

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

	public double getIncomingQuantity() {
		return incomingQuantity;
	}

	public void setIncomingQuantity(double incomingQuantity) {
		this.incomingQuantity = incomingQuantity;
	}

	public double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public InventoryItemDO getIncomingMaterial() {
		return incomingMaterial;
	}

	public void setIncomingMaterial(InventoryItemDO incomingMaterial) {
		this.incomingMaterial = incomingMaterial;
	}

	public String getClassificationCategory() {
		return classificationCategory;
	}

	public void setClassificationCategory(String classificationCategory) {
		this.classificationCategory = classificationCategory;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getGrnNumber() {
		return grnNumber;
	}

	public void setGrnNumber(String grnNumber) {
		this.grnNumber = grnNumber;
	}

	public VendorDO getVendor() {
		return vendor;
	}

	public void setVendor(VendorDO vendor) {
		this.vendor = vendor;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public Date getPoDate() {
		return poDate;
	}

	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}

	public String getIntentNumber() {
		return intentNumber;
	}

	public void setIntentNumber(String intentNumber) {
		this.intentNumber = intentNumber;
	}

	public String getMaterialAcceptedBy() {
		return materialAcceptedBy;
	}

	public void setMaterialAcceptedBy(String materialAcceptedBy) {
		this.materialAcceptedBy = materialAcceptedBy;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

}
