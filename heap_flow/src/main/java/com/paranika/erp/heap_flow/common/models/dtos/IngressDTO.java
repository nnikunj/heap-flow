package com.paranika.erp.heap_flow.common.models.dtos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.paranika.erp.heap_flow.common.AppConstants;
import com.paranika.erp.heap_flow.common.models.dos.IngressLedgerDO;

public class IngressDTO {

	private String dbId;
	private String entryCreatedOnDate;
	private String recievedDate;
	private String inventoryType;
	private String incomingQuantity;
	private String pricePerUnit;
	private String incomingMaterial;
	private String classificationCategory;
	private String invoiceNumber;
	private String invoiceDate;
	private String poNumber;
	private String poDate;
	private String intdentNumber;
	private String materialAcceptedBy;
	private String grnNumber;
	private String department;
	private String vendor;
	private String incomingMaterialDescription;
	private String uom;

	public IngressDTO(IngressLedgerDO dbObj) {
		DateFormat dateFormat = new SimpleDateFormat(AppConstants.REPORT_DATE_FORMAT);

		this.dbId = String.valueOf(dbObj.getId());
		this.recievedDate = (dbObj.getRecordDate() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: dateFormat.format(dbObj.getRecordDate());

		this.entryCreatedOnDate = (dbObj.getCreation() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: dateFormat.format(dbObj.getCreation());

		this.inventoryType = (dbObj.getInventoryType() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: dbObj.getInventoryType().getTypeName();
		this.incomingQuantity = String.valueOf(dbObj.getIncomingQuantity());
		this.pricePerUnit = String.valueOf(dbObj.getPricePerUnit());

		this.incomingMaterial = (dbObj.getIncomingMaterial() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: (dbObj.getIncomingMaterial().getInventoryItemCode());
		String strDesc = (dbObj.getIncomingMaterial() == null) ? null : dbObj.getIncomingMaterial().getDescriptions();

		this.incomingMaterialDescription = (strDesc == null) ? AppConstants.NO_DATA_FOUND_MSG
				: InventoryItemDescriptions.fromJson(strDesc).getDescription();

		this.uom = String.valueOf((((dbObj.getIncomingMaterial() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: dbObj.getIncomingMaterial().getBaseUnitMeasure()) == null) ? " "
						: dbObj.getIncomingMaterial().getBaseUnitMeasure());

		this.classificationCategory = (dbObj.getClassificationCategory() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: dbObj.getClassificationCategory();
		this.invoiceNumber = String.valueOf(dbObj.getInvoiceNumber());
		this.invoiceDate = (dbObj.getInvoiceDate() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: dateFormat.format(dbObj.getInvoiceDate());

		this.poNumber = String.valueOf(dbObj.getPoNumber());
		this.poDate = (dbObj.getPoDate() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: dateFormat.format(dbObj.getPoDate());
		this.intdentNumber = String.valueOf(dbObj.getIntentNumber());
		this.materialAcceptedBy = String.valueOf(dbObj.getMaterialAcceptedBy());
		this.grnNumber = String.valueOf(dbObj.getGrnNumber());
		this.department = String.valueOf(dbObj.getDepartment());

		this.vendor = (dbObj.getVendor() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: (dbObj.getVendor().getSearchName());

	}

	public String getDbId() {
		return dbId;
	}

	public void setDbId(String dbId) {
		this.dbId = dbId;
	}

	public String getEntryCreatedOnDate() {
		return entryCreatedOnDate;
	}

	public void setEntryCreatedOnDate(String entryCreatedOnDate) {
		this.entryCreatedOnDate = entryCreatedOnDate;
	}

	public String getRecievedDate() {
		return recievedDate;
	}

	public void setRecievedDate(String recievedDate) {
		this.recievedDate = recievedDate;
	}

	public String getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}

	public String getIncomingQuantity() {
		return incomingQuantity;
	}

	public void setIncomingQuantity(String incomingQuantity) {
		this.incomingQuantity = incomingQuantity;
	}

	public String getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(String pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public String getIncomingMaterial() {
		return incomingMaterial;
	}

	public void setIncomingMaterial(String incomingMaterial) {
		this.incomingMaterial = incomingMaterial;
	}

	public String getClassificationCategory() {
		return classificationCategory;
	}

	public void setClassificationCategory(String classificationCategory) {
		this.classificationCategory = classificationCategory;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getPoDate() {
		return poDate;
	}

	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}

	public String getIntdentNumber() {
		return intdentNumber;
	}

	public void setIntdentNumber(String intdentNumber) {
		this.intdentNumber = intdentNumber;
	}

	public String getMaterialAcceptedBy() {
		return materialAcceptedBy;
	}

	public void setMaterialAcceptedBy(String materialAcceptedBy) {
		this.materialAcceptedBy = materialAcceptedBy;
	}

	public String getGrnNumber() {
		return grnNumber;
	}

	public void setGrnNumber(String grnNumber) {
		this.grnNumber = grnNumber;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getIncomingMaterialDescription() {
		return incomingMaterialDescription;
	}

	public void setIncomingMaterialDescription(String incomingMaterialDescription) {
		this.incomingMaterialDescription = incomingMaterialDescription;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IngressDTO [dbId=");
		builder.append(dbId);
		builder.append(", entryCreatedOnDate=");
		builder.append(entryCreatedOnDate);
		builder.append(", recievedDate=");
		builder.append(recievedDate);
		builder.append(", inventoryType=");
		builder.append(inventoryType);
		builder.append(", incomingQuantity=");
		builder.append(incomingQuantity);
		builder.append(", pricePerUnit=");
		builder.append(pricePerUnit);
		builder.append(", incomingMaterial=");
		builder.append(incomingMaterial);
		builder.append(", classificationCategory=");
		builder.append(classificationCategory);
		builder.append(", invoiceNumber=");
		builder.append(invoiceNumber);
		builder.append(", invoiceDate=");
		builder.append(invoiceDate);
		builder.append(", poNumber=");
		builder.append(poNumber);
		builder.append(", poDate=");
		builder.append(poDate);
		builder.append(", intdentNumber=");
		builder.append(intdentNumber);
		builder.append(", materialAcceptedBy=");
		builder.append(materialAcceptedBy);
		builder.append(", grnNumber=");
		builder.append(grnNumber);
		builder.append(", department=");
		builder.append(department);
		builder.append(", vendor=");
		builder.append(vendor);
		builder.append(", incomingMaterialDescription=");
		builder.append(incomingMaterialDescription);
		builder.append(", uom=");
		builder.append(uom);
		builder.append("]");
		return builder.toString();
	}

}
