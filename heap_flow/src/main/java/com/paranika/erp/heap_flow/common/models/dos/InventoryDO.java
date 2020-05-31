package com.paranika.erp.heap_flow.common.models.dos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "inventories")
public class InventoryDO extends BaseDO {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "inventory_item_fk")
	private InventoryItemDO item;

	@ManyToOne
	@JoinColumn(name = "inventory_type_fk")
	private InventoryTypeDO type;

	@Column(name = "average_unit_price", nullable = false)
	private double averageUnitPrice;

	@Column(name = "quantity", nullable = false)
	private double quantity;

	@Column(name = "notes", nullable = true)
	private String notes;

	@Formula("average_unit_price * quantity")
	private double value;

	public InventoryItemDO getItem() {
		return item;
	}

	public void setItem(InventoryItemDO item) {
		this.item = item;
	}

	public InventoryTypeDO getType() {
		return type;
	}

	public void setType(InventoryTypeDO type) {
		this.type = type;
	}

	public double getAverageUnitPrice() {
		return averageUnitPrice;
	}

	public void setAverageUnitPrice(double averageUnitPrice) {
		this.averageUnitPrice = averageUnitPrice;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public double getValue() {
		return value;
	}

}
