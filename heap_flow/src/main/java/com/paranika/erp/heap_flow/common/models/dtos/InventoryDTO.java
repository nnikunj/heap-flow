package com.paranika.erp.heap_flow.common.models.dtos;

public class InventoryDTO {

	private String inventoryTypeName;

	private double averageUnitPrice;

	private double quantity;

	public String getInventoryTypeName() {
		return inventoryTypeName;
	}

	public void setInventoryTypeName(String inventoryTypeName) {
		this.inventoryTypeName = inventoryTypeName;
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

}
