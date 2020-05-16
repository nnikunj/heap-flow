package com.paranika.erp.heap_flow.common.models.dtos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import com.paranika.erp.heap_flow.common.AppConstants;
import com.paranika.erp.heap_flow.common.models.dos.InventoryDO;
import com.paranika.erp.heap_flow.common.models.dos.InventoryItemDO;

public class InventoryItemDTO {

	public InventoryItemDTO() {

	}

	public InventoryItemDTO(InventoryItemDO doOb) {
		this.inventoryItemCode = doOb.getInventoryItemCode();

		this.dbId = doOb.getId();

		this.descriptions = InventoryItemDescriptions.fromJson(doOb.getDescriptions());

		this.baseUnitMeasure = doOb.getBaseUnitMeasure();

		this.productGrpCode = doOb.getProductGrpCode();

		this.genProductPostingGrp = doOb.getGenProductPostingGrp();

		this.itemCategoryCode = doOb.getItemCategoryCode();

		this.gstGrpCode = doOb.getGstGrpCode();

		this.hsnSacCode = doOb.getHsnSacCode();
		DateFormat dateFormat = new SimpleDateFormat(AppConstants.commonAppDateFormat);
		this.creation = dateFormat.format(doOb.getCreation());
		this.modified = dateFormat.format(doOb.getModified());

		Collection<InventoryDO> stoks = doOb.getStocks();
		for (InventoryDO inventoryDO : stoks) {
			String typeName = inventoryDO.getType().getTypeName();
			Double avgPrice = inventoryDO.getAverageUnitPrice();
			Double quant = inventoryDO.getQuantity();
			InventoryDTO dto = new InventoryDTO();
			dto.setAverageUnitPrice(avgPrice);
			dto.setQuantity(quant);
			dto.setInventoryTypeName(typeName);
			this.stokcs.add(dto);
		}
	}

	private Collection<InventoryDTO> stokcs = new ArrayList<InventoryDTO>();
	private String inventoryItemCode;

	private long dbId;

	private InventoryItemDescriptions descriptions;

	private String creation;

	private String modified;

	private String baseUnitMeasure;

	private String productGrpCode;

	private String genProductPostingGrp;

	private String itemCategoryCode;

	private String gstGrpCode;

	private String hsnSacCode;

	public String getInventoryItemCode() {
		return inventoryItemCode;
	}

	public void setInventoryItemCode(String inventoryItemCode) {
		this.inventoryItemCode = inventoryItemCode;
	}

	public long getDbId() {
		return dbId;
	}

	public void setDbId(long dbId) {
		this.dbId = dbId;
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

	public Collection<InventoryDTO> getStokcs() {
		return stokcs;
	}

	public void setStokcs(Collection<InventoryDTO> stokcs) {
		this.stokcs = stokcs;
	}

}
