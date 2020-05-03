package com.paranika.erp.heap_flow.common.models;

import com.google.gson.Gson;
import com.paranika.erp.heap_flow.common.exceptions.HeapFlowRuntimeException;

public class InventoryItemDescriptions {

	private String description;

	private String description2;

	private String description3;

	private String description4;

	private String description5;

	private String description6;

	public InventoryItemDescriptions() {

	}

	public InventoryItemDescriptions(String desc) {
		this.description = desc;
	}

	public InventoryItemDescriptions(String desc, String desc2, String desc3, String desc4, String desc5,
			String desc6) {
		this.description = desc;
		this.description2 = desc2;
		this.description3 = desc3;
		this.description4 = desc4;
		this.description5 = desc5;
		this.description6 = desc6;

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription2() {
		return description2;
	}

	public void setDescription2(String description2) {
		this.description2 = description2;
	}

	public String getDescription3() {
		return description3;
	}

	public void setDescription3(String description3) {
		this.description3 = description3;
	}

	public String getDescription4() {
		return description4;
	}

	public void setDescription4(String description4) {
		this.description4 = description4;
	}

	public String getDescription5() {
		return description5;
	}

	public void setDescription5(String description5) {
		this.description5 = description5;
	}

	public String getDescription6() {
		return description6;
	}

	public void setDescription6(String description6) {
		this.description6 = description6;
	}

	public String toJson() {
		Gson gson = new Gson();
		String jsonInString = gson.toJson(this);
		return jsonInString;
	}

	public static InventoryItemDescriptions fromJson(String json) {
		Gson gson = new Gson();
		InventoryItemDescriptions obj = null;
		try {
			obj = gson.fromJson(json, InventoryItemDescriptions.class);
		} catch (Exception e) {
			throw new HeapFlowRuntimeException(e);
		}
		return obj;

	}

}
