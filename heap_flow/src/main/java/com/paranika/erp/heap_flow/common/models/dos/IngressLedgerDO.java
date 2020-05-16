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
@Table(name = "ingress_ledgers")
public class IngressLedgerDO extends BaseDO {

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

	@Column(name = "grn_number", nullable = true)
	private String grnNumber;

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

}
