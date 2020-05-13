package com.paranika.erp.heap_flow.common.models;

public class MaterialData {

	private static final long serialVersionUID = 1L;
	private Double pricePerUnit;
	private Double quantity;
	private String inventoryType;
	private String classification;
	private String productCode;

	public MaterialData() {

	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

}
