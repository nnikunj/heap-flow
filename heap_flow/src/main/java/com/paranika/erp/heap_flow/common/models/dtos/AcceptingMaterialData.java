package com.paranika.erp.heap_flow.common.models.dtos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paranika.erp.heap_flow.common.AppConstants;

public class AcceptingMaterialData {

	private String recordDate;
	private String vendorCode;
	private String intentNumber;
	private String grn;

	private String invoice;
	private String invoiceDate;

	private String poNumber;
	private String poDate;
	private String loggedInUser;
	private String department;

	private List<MaterialData> incomingItemsList = new ArrayList<MaterialData>();

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getIntentNumber() {
		return intentNumber;
	}

	public void setIntentNumber(String intentNumber) {
		this.intentNumber = intentNumber;
	}

	public String getGrn() {
		return grn;
	}

	public void setGrn(String grn) {
		this.grn = grn;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public List<MaterialData> getIncomingItemsList() {
		return incomingItemsList;
	}

	public void setIncomingItemsList(List<MaterialData> incomingItemsList) {
		this.incomingItemsList = incomingItemsList;
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

	public String getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(String loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AcceptingMaterialData [recordDate=");
		builder.append(recordDate);
		builder.append(", vendorCode=");
		builder.append(vendorCode);
		builder.append(", intentNumber=");
		builder.append(intentNumber);
		builder.append(", grn=");
		builder.append(grn);
		builder.append(", invoice=");
		builder.append(invoice);
		builder.append(", invoiceDate=");
		builder.append(invoiceDate);
		builder.append(", poNumber=");
		builder.append(poNumber);
		builder.append(", poDate=");
		builder.append(poDate);
		builder.append(", loggedInUser=");
		builder.append(loggedInUser);
		builder.append(", department=");
		builder.append(department);
		builder.append(", incomingItemsList=");
		builder.append(incomingItemsList);
		builder.append("]");
		return builder.toString();
	}

	public static void main(String[] args) {
		MaterialData md1 = new MaterialData();
		md1.setClassification("PROJECT");
		md1.setInventoryType("PURCHASED");
		md1.setProductCode("ASBEL0039");
		md1.setPricePerUnit(867.98);
		md1.setQuantity(10.00);

		MaterialData md2 = new MaterialData();
		md2.setClassification("R&D");
		md2.setInventoryType("IMPORTED");
		md2.setProductCode("ASBEL0031");
		md2.setPricePerUnit(1867.98);
		md2.setQuantity(56.00);
		ArrayList<MaterialData> l = new ArrayList<MaterialData>();
		l.add(md1);
		l.add(md2);

		AcceptingMaterialData data = new AcceptingMaterialData();
		data.setGrn("GenratedGRN");
		data.setInvoice("invoice001");

		data.setDepartment("Engg");
		data.setInvoiceDate((new SimpleDateFormat(AppConstants.COMMON_APP_DATE_FORMAT)).format(new Date()));
		data.setVendorCode("VEND5857");
		// Thu May 14 2020
		data.setRecordDate((new SimpleDateFormat(AppConstants.COMMON_APP_DATE_FORMAT)).format(new Date()));
		data.setIncomingItemsList(l);
		data.setPoNumber("PoNumber");
		data.setPoDate((new SimpleDateFormat(AppConstants.COMMON_APP_DATE_FORMAT)).format(new Date()));
		data.setLoggedInUser("JaganNath");
		data.setIntentNumber("intynt001");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		System.out.println(gson.toJson(data));

	}
}
