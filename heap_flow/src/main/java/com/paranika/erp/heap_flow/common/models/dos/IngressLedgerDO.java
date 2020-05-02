package com.paranika.erp.heap_flow.common.models.dos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

	@ManyToOne
	@JoinColumn(name = "vendor_fk")
	private VendorDO vendor;

}
