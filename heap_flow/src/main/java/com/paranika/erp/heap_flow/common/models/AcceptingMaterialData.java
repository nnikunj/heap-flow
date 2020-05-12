package com.paranika.erp.heap_flow.common.models;

import java.util.ArrayList;
import java.util.List;

public class AcceptingMaterialData {

	private String recordDate;
	private String vendorCode;
	private String grn;
	private String invoice;

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

}
