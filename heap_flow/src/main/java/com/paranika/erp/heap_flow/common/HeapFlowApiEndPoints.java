package com.paranika.erp.heap_flow.common;

public interface HeapFlowApiEndPoints {

	public String BASE_END_POINT = "/api/v1";

	// Machines related endpoints
	public String BASE_END_POINT_MACHINES = BASE_END_POINT + "/machines";
	public String MACHINES_IMPORT_ENDPOINT = "/import-and-update-machine-inventory";

	public String BASE_END_POINT_VENDORS = BASE_END_POINT + "/vendors";
	public String VENDORS_IMPORT_ENDPOINT = "/import-and-update-vendors-list";

	public String BASE_END_POINT_INVENTORYITEM = BASE_END_POINT + "/inventory-items";
	public String INVENTORYITEM_IMPORT_ENDPOINT = "/import-and-update-inventory-items-list";
	public String GET_INVENTORYITEM_PAGE_WISE = "/fetch-inventory-items-list-page-wise";

	public String GET_INVENTORYITEM_LIST_WITH_ID_LIKE = "/fetch-inventory-items-list-like-id/{idLike}";

}
