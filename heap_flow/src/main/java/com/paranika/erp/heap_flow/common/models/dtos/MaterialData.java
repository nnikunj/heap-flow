package com.paranika.erp.heap_flow.common.models.dtos;

public class MaterialData {

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MaterialData [pricePerUnit=");
		builder.append(pricePerUnit);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", inventoryType=");
		builder.append(inventoryType);
		builder.append(", classification=");
		builder.append(classification);
		builder.append(", productCode=");
		builder.append(productCode);
		builder.append("]");
		return builder.toString();
	}

}
