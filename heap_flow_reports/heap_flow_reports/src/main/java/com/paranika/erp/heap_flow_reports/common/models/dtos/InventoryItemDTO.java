package com.paranika.erp.heap_flow_reports.common.models.dtos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paranika.erp.heap_flow_reports.common.AppConstants;
import com.paranika.erp.heap_flow_reports.common.models.dos.InventoryDO;
import com.paranika.erp.heap_flow_reports.common.models.dos.InventoryItemDO;

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
		DateFormat dateFormat = new SimpleDateFormat(AppConstants.COMMON_APP_DATE_FORMAT);
		this.creation = dateFormat.format(doOb.getCreation());
		this.modified = dateFormat.format(doOb.getModified());

		this.reserveQuantAlert = doOb.getReserveQuantAlert();
		this.reOrderQuant = doOb.getReOrderQuant();
		this.maxOrderQuant = doOb.getMaxOrderQuant();

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

	private Double reserveQuantAlert;

	private Double maxOrderQuant;

	private Double reOrderQuant;

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

	public Double getReserveQuantAlert() {
		return reserveQuantAlert;
	}

	public void setReserveQuantAlert(Double reserveQuantAlert) {
		this.reserveQuantAlert = reserveQuantAlert;
	}

	public Double getMaxOrderQuant() {
		return maxOrderQuant;
	}

	public void setMaxOrderQuant(Double maxOrderQuant) {
		this.maxOrderQuant = maxOrderQuant;
	}

	public Double getReOrderQuant() {
		return reOrderQuant;
	}

	public void setReOrderQuant(Double reOrderQuant) {
		this.reOrderQuant = reOrderQuant;
	}

	@JsonIgnore
	public InventoryItemDO getDoObj() {
		InventoryItemDO inventoryItemDO = new InventoryItemDO();

		inventoryItemDO.setInventoryItemCode(this.inventoryItemCode.trim().toUpperCase());

		inventoryItemDO.setBaseUnitMeasure((this.baseUnitMeasure == null) ? null : this.baseUnitMeasure.trim());
		inventoryItemDO.setDescriptions(this.descriptions.toJson());
		inventoryItemDO
				.setGenProductPostingGrp((this.genProductPostingGrp == null) ? null : this.genProductPostingGrp.trim());
		inventoryItemDO.setGstGrpCode((this.gstGrpCode == null) ? null : this.gstGrpCode.trim());
		inventoryItemDO.setHsnSacCode((this.hsnSacCode == null) ? null : this.hsnSacCode.trim());

		inventoryItemDO.setItemCategoryCode((this.itemCategoryCode == null) ? null : this.itemCategoryCode.trim());
		inventoryItemDO.setProductGrpCode((this.productGrpCode == null) ? null : this.productGrpCode.trim());

		inventoryItemDO.setReserveQuantAlert((this.reserveQuantAlert == null) ? 0.00 : this.reserveQuantAlert);
		inventoryItemDO.setReOrderQuant((this.reOrderQuant == null) ? 0.00 : this.reOrderQuant);
		inventoryItemDO.setMaxOrderQuant((this.maxOrderQuant == null) ? 0.00 : this.maxOrderQuant);

		return inventoryItemDO;
	}

	public static void main(String[] args) {
		InventoryItemDTO dto = new InventoryItemDTO();
		dto.setInventoryItemCode("ASBEL0030");
		dto.setGenProductPostingGrp("SP");
		dto.setGstGrpCode("");
		dto.setHsnSacCode("998519");
		dto.setItemCategoryCode("ASB M/C");
		dto.setProductGrpCode("ELECTRICAL");

		InventoryItemDescriptions desc = new InventoryItemDescriptions();
		desc.setDescription("CONNECTOR");
		desc.setDescription2("MOTOR CONNECTOR");
		desc.setDescription3("6 WAY");
		desc.setDescription4("desc4");
		desc.setDescription5("desc5");
		desc.setDescription6("desc6");
		dto.setDescriptions(desc);
		dto.setBaseUnitMeasure("NO.");
		dto.setReserveQuantAlert(100.00);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(dto));

	}
}
