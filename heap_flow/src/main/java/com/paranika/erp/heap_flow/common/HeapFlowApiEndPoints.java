package com.paranika.erp.heap_flow.common;

public interface HeapFlowApiEndPoints {

	public String BASE_END_POINT = "/api/v1";

	// Machines related endpoints
	public String BASE_END_POINT_MACHINES = BASE_END_POINT + "/machines";
	public String MACHINES_IMPORT_ENDPOINT = "/import-and-update-machine-inventory";

	public String BASE_END_POINT_VENDORS = BASE_END_POINT + "/vendors";
	public String VENDORS_IMPORT_ENDPOINT = "/import-and-update-vendors-list";

}
