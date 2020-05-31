package com.paranika.erp.heap_flow_reports.common.models.dtos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.paranika.erp.heap_flow_reports.common.AppConstants;
import com.paranika.erp.heap_flow_reports.common.models.dos.IngressLedgerDO;

public class IngressLedgerDTO {

	private String srNo;
	private String recordDate;
	private String invNumber;
	private String poNumber;
	private String supplierName;
	private String itemDescription;
	private String quantity;
	private String baseUnitMeasure;
	private String rate;
	private String amount;
	private String remark;
	private String checkedBy;
	private String indentNumber;
	private String itemCode;

	public IngressLedgerDTO(IngressLedgerDO dbObj) {
		DateFormat dateFormat = new SimpleDateFormat(AppConstants.commonAppDateFormat);

		this.srNo = String.valueOf(dbObj.getId());
		this.recordDate = dateFormat.format(dbObj.getRecordDate());
		this.invNumber = String.valueOf(dbObj.getInvoiceNumber());
		this.poNumber = String.valueOf(dbObj.getPoNumber());
		this.supplierName = String.valueOf(dbObj.getVendor().getSearchName());
		this.itemCode = dbObj.getIncomingMaterial().getInventoryItemCode();
		this.itemDescription = InventoryItemDescriptions.fromJson(dbObj.getIncomingMaterial().getDescriptions())
				.getDescription();

		this.quantity = String.valueOf(dbObj.getIncomingQuantity());
		this.baseUnitMeasure = String.valueOf(dbObj.getIncomingMaterial().getBaseUnitMeasure());
		this.rate = String.valueOf(dbObj.getPricePerUnit());
		this.amount = String.valueOf(dbObj.getPricePerUnit() * dbObj.getIncomingQuantity());
		this.remark = "";
		this.checkedBy = String.valueOf(dbObj.getMaterialAcceptedBy());
		this.indentNumber = String.valueOf(dbObj.getIntentNumber());
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getSrNo() {
		return srNo;
	}

	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	public String getInvNumber() {
		return invNumber;
	}

	public void setInvNumber(String invNumber) {
		this.invNumber = invNumber;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getBaseUnitMeasure() {
		return baseUnitMeasure;
	}

	public void setBaseUnitMeasure(String baseUnitMeasure) {
		this.baseUnitMeasure = baseUnitMeasure;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCheckedBy() {
		return checkedBy;
	}

	public void setCheckedBy(String checkedBy) {
		this.checkedBy = checkedBy;
	}

	public String getIndentNumber() {
		return indentNumber;
	}

	public void setIndentNumber(String indentNumber) {
		this.indentNumber = indentNumber;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IngressLedgerDTO [srNo=");
		builder.append(srNo);
		builder.append(", recordDate=");
		builder.append(recordDate);
		builder.append(", invNumber=");
		builder.append(invNumber);
		builder.append(", poNumber=");
		builder.append(poNumber);
		builder.append(", supplierName=");
		builder.append(supplierName);
		builder.append(", itemDescription=");
		builder.append(itemDescription);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", baseUnitMeasure=");
		builder.append(baseUnitMeasure);
		builder.append(", rate=");
		builder.append(rate);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", checkedBy=");
		builder.append(checkedBy);
		builder.append(", indentNumber=");
		builder.append(indentNumber);
		builder.append(", itemCode=");
		builder.append(itemCode);
		builder.append("]");
		return builder.toString();
	}

}
