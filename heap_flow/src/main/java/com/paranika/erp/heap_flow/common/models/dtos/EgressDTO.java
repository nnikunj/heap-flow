package com.paranika.erp.heap_flow.common.models.dtos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.paranika.erp.heap_flow.common.AppConstants;
import com.paranika.erp.heap_flow.common.models.dos.EgressLedgerDO;

public class EgressDTO {

	private String dbId;

	private String entryCreatedOnDate;

	private String issueDate;

	private String inventoryType;

	private String outgoingQuantity;
	private String uom;
	private String outgoingMaterial;
	private String outgoingMaterialDescription;

	private String classificationCategory;

	private String consumingMachine;

	private String issuedToEngineer;

	private String issuedForDept;

	private String approvedBy;

	private String issuedBy;

	private String issueSlipNumber;

	private String outgoingMaterialPrice;

	public EgressDTO(EgressLedgerDO dbObj) {

		DateFormat dateFormat = new SimpleDateFormat(AppConstants.REPORT_DATE_FORMAT);

		this.dbId = String.valueOf(dbObj.getId());
		this.issueDate = dateFormat.format(dbObj.getRecordDate());
		this.entryCreatedOnDate = dateFormat.format(dbObj.getCreation());

		this.issuedForDept = (dbObj.getIssuedForDept() == null) ? " " : dbObj.getIssuedForDept().trim();

		this.consumingMachine = (dbObj.getConsumingMachine() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: dbObj.getConsumingMachine().getCode();
		this.issuedToEngineer = (dbObj.getIssuedTo() == null) ? " " : dbObj.getIssuedTo().trim();
		this.approvedBy = (dbObj.getApprovedBy() == null) ? " " : dbObj.getApprovedBy().trim();

		this.outgoingMaterial = (dbObj.getOutgoingMaterial() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: (dbObj.getOutgoingMaterial().getInventoryItemCode());
		String strDesc = (dbObj.getOutgoingMaterial() == null) ? null : dbObj.getOutgoingMaterial().getDescriptions();

		this.outgoingMaterialDescription = (strDesc == null) ? AppConstants.NO_DATA_FOUND_MSG
				: InventoryItemDescriptions.fromJson(strDesc).getDescription();
		this.outgoingQuantity = String.valueOf(dbObj.getOutgoingQuantity());

		this.uom = String.valueOf((((dbObj.getOutgoingMaterial() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: dbObj.getOutgoingMaterial().getBaseUnitMeasure()) == null) ? " "
						: dbObj.getOutgoingMaterial().getBaseUnitMeasure());
		this.issuedBy = (dbObj.getIssuedBy() == null) ? " " : dbObj.getIssuedBy();

		this.issueSlipNumber = (dbObj.getIssueSlipNumber() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: dbObj.getIssueSlipNumber().trim();

		this.outgoingMaterialPrice = (dbObj.getOutgoingMaterialPrice() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: String.valueOf(dbObj.getOutgoingMaterialPrice());

		this.inventoryType = (dbObj.getInventoryType() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: dbObj.getInventoryType().getTypeName();

		this.classificationCategory = (dbObj.getClassificationCategory() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: dbObj.getClassificationCategory();
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

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}

	public String getOutgoingQuantity() {
		return outgoingQuantity;
	}

	public void setOutgoingQuantity(String outgoingQuantity) {
		this.outgoingQuantity = outgoingQuantity;
	}

	public String getOutgoingMaterial() {
		return outgoingMaterial;
	}

	public void setOutgoingMaterial(String outgoingMaterial) {
		this.outgoingMaterial = outgoingMaterial;
	}

	public String getClassificationCategory() {
		return classificationCategory;
	}

	public void setClassificationCategory(String classificationCategory) {
		this.classificationCategory = classificationCategory;
	}

	public String getConsumingMachine() {
		return consumingMachine;
	}

	public void setConsumingMachine(String consumingMachine) {
		this.consumingMachine = consumingMachine;
	}

	public String getIssuedToEngineer() {
		return issuedToEngineer;
	}

	public void setIssuedToEngineer(String issuedToEngineer) {
		this.issuedToEngineer = issuedToEngineer;
	}

	public String getIssuedForDept() {
		return issuedForDept;
	}

	public void setIssuedForDept(String issuedForDept) {
		this.issuedForDept = issuedForDept;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	public String getIssueSlipNumber() {
		return issueSlipNumber;
	}

	public void setIssueSlipNumber(String issueSlipNumber) {
		this.issueSlipNumber = issueSlipNumber;
	}

	public String getOutgoingMaterialPrice() {
		return outgoingMaterialPrice;
	}

	public void setOutgoingMaterialPrice(String outgoingMaterialPrice) {
		this.outgoingMaterialPrice = outgoingMaterialPrice;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getOutgoingMaterialDescription() {
		return outgoingMaterialDescription;
	}

	public void setOutgoingMaterialDescription(String outgoingMaterialDescription) {
		this.outgoingMaterialDescription = outgoingMaterialDescription;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EgressDTO [dbId=");
		builder.append(dbId);
		builder.append(", entryCreatedOnDate=");
		builder.append(entryCreatedOnDate);
		builder.append(", issueDate=");
		builder.append(issueDate);
		builder.append(", inventoryType=");
		builder.append(inventoryType);
		builder.append(", outgoingQuantity=");
		builder.append(outgoingQuantity);
		builder.append(", uom=");
		builder.append(uom);
		builder.append(", outgoingMaterial=");
		builder.append(outgoingMaterial);
		builder.append(", outgoingMaterialDescription=");
		builder.append(outgoingMaterialDescription);
		builder.append(", classificationCategory=");
		builder.append(classificationCategory);
		builder.append(", consumingMachine=");
		builder.append(consumingMachine);
		builder.append(", issuedToEngineer=");
		builder.append(issuedToEngineer);
		builder.append(", issuedForDept=");
		builder.append(issuedForDept);
		builder.append(", approvedBy=");
		builder.append(approvedBy);
		builder.append(", issuedBy=");
		builder.append(issuedBy);
		builder.append(", issueSlipNumber=");
		builder.append(issueSlipNumber);
		builder.append(", outgoingMaterialPrice=");
		builder.append(outgoingMaterialPrice);
		builder.append("]");
		return builder.toString();
	}

}
