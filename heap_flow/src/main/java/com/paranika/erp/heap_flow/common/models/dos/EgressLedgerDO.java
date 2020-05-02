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

	@ManyToOne
	@JoinColumn(name = "machine_fk")
	private MachineDO consumingMachine;

}
