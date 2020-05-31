package com.paranika.erp.heap_flow.common.models.dtos;

import java.util.Date;

import com.paranika.erp.heap_flow.common.models.dos.InventoryDO;
import com.paranika.erp.heap_flow.common.models.dos.InventoryTypeDO;

public class InventorySummaryDTO {

	public InventorySummaryDTO(InventoryDO doOb) {

		this.inventoryItemCode = doOb.getItem().getInventoryItemCode();

		this.descriptions = InventoryItemDescriptions.fromJson(doOb.getItem().getDescriptions());

		this.baseUnitMeasure = doOb.getItem().getBaseUnitMeasure();

		this.productGrpCode = doOb.getItem().getProductGrpCode();

		this.genProductPostingGrp = doOb.getItem().getGenProductPostingGrp();

		this.itemCategoryCode = doOb.getItem().getItemCategoryCode();

		this.gstGrpCode = doOb.getItem().getGstGrpCode();

		this.hsnSacCode = doOb.getItem().getHsnSacCode();

		this.creation = doOb.getCreation();
		this.modified = doOb.getModified();
		this.type = doOb.getType();
		this.averageUnitPrice = doOb.getAverageUnitPrice();
		this.quantity = doOb.getQuantity();
		this.value = doOb.getValue();
	}

	private String inventoryItemCode;

	private InventoryItemDescriptions descriptions;

	private Date creation;

	private Date modified;

	private String baseUnitMeasure;

	private String productGrpCode;

	private String genProductPostingGrp;

	private String itemCategoryCode;

	private String gstGrpCode;

	private String hsnSacCode;

	private InventoryTypeDO type;

	private double averageUnitPrice;

	private double quantity;

	private double value;

	public String getInventoryItemCode() {
		return inventoryItemCode;
	}

	public void setInventoryItemCode(String inventoryItemCode) {
		this.inventoryItemCode = inventoryItemCode;
	}

	public InventoryItemDescriptions getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(InventoryItemDescriptions descriptions) {
		this.descriptions = descriptions;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getBaseUnitMeasure() {
		return baseUnitMeasure;
	}

	public void setBaseUnitMeasure(String baseUnitMeasure) {
		this.baseUnitMeasure = baseUnitMeasure;
	}

	public String getProductGrpCode() {
		return productGrpCode;
	}

	public void setProductGrpCode(String productGrpCode) {
		this.productGrpCode = productGrpCode;
	}

	public String getGenProductPostingGrp() {
		return genProductPostingGrp;
	}

	public void setGenProductPostingGrp(String genProductPostingGrp) {
		this.genProductPostingGrp = genProductPostingGrp;
	}

	public String getItemCategoryCode() {
		return itemCategoryCode;
	}

	public void setItemCategoryCode(String itemCategoryCode) {
		this.itemCategoryCode = itemCategoryCode;
	}

	public String getGstGrpCode() {
		return gstGrpCode;
	}

	public void setGstGrpCode(String gstGrpCode) {
		this.gstGrpCode = gstGrpCode;
	}

	public String getHsnSacCode() {
		return hsnSacCode;
	}

	public void setHsnSacCode(String hsnSacCode) {
		this.hsnSacCode = hsnSacCode;
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

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
