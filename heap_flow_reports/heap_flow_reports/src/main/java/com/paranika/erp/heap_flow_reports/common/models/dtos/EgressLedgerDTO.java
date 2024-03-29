package com.paranika.erp.heap_flow_reports.common.models.dtos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paranika.erp.heap_flow_reports.common.AppConstants;
import com.paranika.erp.heap_flow_reports.common.models.dos.EgressLedgerDO;

public class EgressLedgerDTO {

	private String srNo;
	private String recordDate;
	private String department;
	private String itemGroup;
	private String ved;
	private String machineNummber;
	private String engineer;
	private String approvedBy;
	private String category;
	private String itemCode;
	private String description;
	private String qunatity;
	private String baseUnitMeasure;
	private String issuedBy;
	private String issueSlipNumber;
	private String price;
	private String perUnitPrice;
	private final Logger logger = LoggerFactory.getLogger(EgressLedgerDTO.class);

	public EgressLedgerDTO(EgressLedgerDO dbObj) {

		DateFormat dateFormat = new SimpleDateFormat(AppConstants.REPORT_DATE_FORMAT);

		this.srNo = String.valueOf(dbObj.getId());
		this.recordDate = dateFormat.format(dbObj.getRecordDate());

		this.department = (dbObj.getIssuedForDept() == null) ? " " : dbObj.getIssuedForDept().trim();
		this.itemGroup = String.valueOf(dbObj.getInventoryType().getTypeName());
		this.ved = " ";

		this.machineNummber = (dbObj.getConsumingMachine() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: dbObj.getConsumingMachine().getCode();
		this.engineer = (dbObj.getIssuedTo() == null) ? " " : dbObj.getIssuedTo().trim();
		this.approvedBy = (dbObj.getApprovedBy() == null) ? " " : dbObj.getApprovedBy().trim();

		this.category = (dbObj.getOutgoingMaterial() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: ((dbObj.getOutgoingMaterial().getItemCategoryCode() == null) ? " "
						: dbObj.getOutgoingMaterial().getItemCategoryCode());
		this.itemCode = (dbObj.getOutgoingMaterial() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: (dbObj.getOutgoingMaterial().getInventoryItemCode());
		String strDesc = (dbObj.getOutgoingMaterial() == null) ? null : dbObj.getOutgoingMaterial().getDescriptions();

		this.description = (strDesc == null) ? AppConstants.NO_DATA_FOUND_MSG
				: InventoryItemDescriptions.fromJson(strDesc).getDescription();
		this.qunatity = String.valueOf(dbObj.getOutgoingQuantity());

		this.baseUnitMeasure = String.valueOf((((dbObj.getOutgoingMaterial() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: dbObj.getOutgoingMaterial().getBaseUnitMeasure()) == null) ? " "
						: dbObj.getOutgoingMaterial().getBaseUnitMeasure());
		this.issuedBy = (dbObj.getIssuedBy() == null) ? " " : dbObj.getIssuedBy();

		this.issueSlipNumber = (dbObj.getIssueSlipNumber() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: dbObj.getIssueSlipNumber().trim();

		this.price = (dbObj.getOutgoingMaterialPrice() == null) ? AppConstants.NO_DATA_FOUND_MSG
				: String.valueOf(dbObj.getOutgoingMaterialPrice());
		calculatePerUnitPrice();
	}

	private void calculatePerUnitPrice() {
		try {
			double totalAmt = Double.parseDouble(getPrice());
			double quant = Double.parseDouble(getQunatity());
			double perUnPr = totalAmt / quant;
			this.perUnitPrice = String.valueOf(perUnPr);
		} catch (NumberFormatException nfe) {
			logger.debug("Number fomat exception, Could not parse either totalAmt or quant ", nfe);
			this.perUnitPrice = AppConstants.NO_DATA_FOUND_MSG;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			this.perUnitPrice = AppConstants.NO_DATA_FOUND_MSG;
		}
	}

	public String getIssueSlipNumber() {
		return issueSlipNumber;
	}

	public void setIssueSlipNumber(String issueSlipNumber) {
		this.issueSlipNumber = issueSlipNumber;
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getItemGroup() {
		return itemGroup;
	}

	public void setItemGroup(String itemGroup) {
		this.itemGroup = itemGroup;
	}

	public String getVed() {
		return ved;
	}

	public void setVed(String ved) {
		this.ved = ved;
	}

	public String getMachineNummber() {
		return machineNummber;
	}

	public void setMachineNummber(String machineNummber) {
		this.machineNummber = machineNummber;
	}

	public String getEngineer() {
		return engineer;
	}

	public void setEngineer(String engineer) {
		this.engineer = engineer;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQunatity() {
		return qunatity;
	}

	public void setQunatity(String qunatity) {
		this.qunatity = qunatity;
	}

	public String getBaseUnitMeasure() {
		return baseUnitMeasure;
	}

	public void setBaseUnitMeasure(String baseUnitMeasure) {
		this.baseUnitMeasure = baseUnitMeasure;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPerUnitPrice() {
		return perUnitPrice;
	}

	public void setPerUnitPrice(String perUnitPrice) {
		this.perUnitPrice = perUnitPrice;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EgressLedgerDTO [srNo=");
		builder.append(srNo);
		builder.append(", recordDate=");
		builder.append(recordDate);
		builder.append(", department=");
		builder.append(department);
		builder.append(", itemGroup=");
		builder.append(itemGroup);
		builder.append(", ved=");
		builder.append(ved);
		builder.append(", machineNummber=");
		builder.append(machineNummber);
		builder.append(", engineer=");
		builder.append(engineer);
		builder.append(", approvedBy=");
		builder.append(approvedBy);
		builder.append(", category=");
		builder.append(category);
		builder.append(", itemCode=");
		builder.append(itemCode);
		builder.append(", description=");
		builder.append(description);
		builder.append(", qunatity=");
		builder.append(qunatity);
		builder.append(", baseUnitMeasure=");
		builder.append(baseUnitMeasure);
		builder.append(", issuedBy=");
		builder.append(issuedBy);
		builder.append(", issueSlipNumber=");
		builder.append(issueSlipNumber);
		builder.append(", price=");
		builder.append(price);
		builder.append(", perUnitPrice=");
		builder.append(perUnitPrice);
		builder.append("]");
		return builder.toString();
	}
}
