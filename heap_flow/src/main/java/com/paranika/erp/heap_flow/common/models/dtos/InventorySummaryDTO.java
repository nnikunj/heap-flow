package com.paranika.erp.heap_flow.common.models.dtos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.paranika.erp.heap_flow.common.AppConstants;
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
		DateFormat dateFormat = new SimpleDateFormat(AppConstants.commonAppDateFormat);
		this.creation = dateFormat.format(doOb.getCreation());
		this.modified = dateFormat.format(doOb.getModified());

		this.type = doOb.getType();
		this.averageUnitPrice = doOb.getAverageUnitPrice();
		this.quantity = doOb.getQuantity();
		this.value = doOb.getValue();
	}

	private String inventoryItemCode;

	private InventoryItemDescriptions descriptions;

	private String creation;

	private String modified;

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

	public String getCreation() {
		return creation;
	}

	public void setCreation(String creation) {
		this.creation = creation;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
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
