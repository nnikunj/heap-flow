package com.paranika.erp.heap_flow.common.models.dtos;

public class OutgoingMaterialDataDTO {

	private Double quantity;
	private String inventoryType;
	private String classification;
	private String productCode;

	public OutgoingMaterialDataDTO() {

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

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OutgoingMaterialDataDTO [quantity=");
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
