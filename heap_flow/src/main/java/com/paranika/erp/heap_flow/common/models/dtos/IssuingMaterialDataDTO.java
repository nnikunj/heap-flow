package com.paranika.erp.heap_flow.common.models.dtos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.paranika.erp.heap_flow.common.AppConstants;

public class IssuingMaterialDataDTO {

	private String recordDate;
	private String machineCode;
	private String issuedViaEmp;

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
		// Thu May 14 2020
		data.setRecordDate((new SimpleDateFormat(AppConstants.commonAppDateFormat)).format(new Date()));
		data.setOutgoingItemsList(l);
		Gson gson = new Gson();
		System.out.println(gson.toJson(data));

	}

}
