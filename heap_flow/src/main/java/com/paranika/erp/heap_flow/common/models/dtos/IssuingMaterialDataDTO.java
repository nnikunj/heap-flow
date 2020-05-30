package com.paranika.erp.heap_flow.common.models.dtos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paranika.erp.heap_flow.common.AppConstants;

public class IssuingMaterialDataDTO {

	private String recordDate;

	private String machineCode;
	private String issuedViaEmp;

	private String issuedForDept;

	private String approvedBy;

	private String issuedBy;

	private List<OutgoingMaterialDataDTO> outgoingItemsList = new ArrayList<OutgoingMaterialDataDTO>();

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public String getIssuedViaEmp() {
		return issuedViaEmp;
	}

	public void setIssuedViaEmp(String issuedViaEmp) {
		this.issuedViaEmp = issuedViaEmp;
	}

	public List<OutgoingMaterialDataDTO> getOutgoingItemsList() {
		return outgoingItemsList;
	}

	public void setOutgoingItemsList(List<OutgoingMaterialDataDTO> outgoingItemsList) {
		this.outgoingItemsList = outgoingItemsList;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IssuingMaterialDataDTO [recordDate=");
		builder.append(recordDate);
		builder.append(", machineCode=");
		builder.append(machineCode);
		builder.append(", issuedViaEmp=");
		builder.append(issuedViaEmp);
		builder.append(", issuedForDept=");
		builder.append(issuedForDept);
		builder.append(", approvedBy=");
		builder.append(approvedBy);
		builder.append(", issuedBy=");
		builder.append(issuedBy);
		builder.append(", outgoingItemsList=");
		builder.append(outgoingItemsList);
		builder.append("]");
		return builder.toString();
	}

	public static void main(String[] args) {
		OutgoingMaterialDataDTO md1 = new OutgoingMaterialDataDTO();
		md1.setClassification("PROJECT");
		md1.setInventoryType("PURCHASED");
		md1.setProductCode("ASBEL0039");

		md1.setQuantity(10.00);

		OutgoingMaterialDataDTO md2 = new OutgoingMaterialDataDTO();
		md2.setClassification("R&D");
		md2.setInventoryType("IMPORTED");
		md2.setProductCode("ASBEL0031");

		md2.setQuantity(56.00);
		ArrayList<OutgoingMaterialDataDTO> l = new ArrayList<OutgoingMaterialDataDTO>();
		l.add(md1);
		l.add(md2);

		IssuingMaterialDataDTO data = new IssuingMaterialDataDTO();
		data.setIssuedViaEmp("Emp001");
		data.setMachineCode("ASB - 43");
		data.setIssuedBy("JaganNath");
		data.setApprovedBy("Paneer Selvam");
		data.setIssuedForDept("Engineering");
		// Thu May 14 2020
		data.setRecordDate((new SimpleDateFormat(AppConstants.commonAppDateFormat)).format(new Date()));
		data.setOutgoingItemsList(l);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		System.out.println(gson.toJson(data));

	}

}
