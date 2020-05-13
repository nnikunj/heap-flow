package com.paranika.erp.heap_flow.common;

public interface HeapFlowApiEndPoints {

	public String BASE_END_POINT = "/api/v1";

	// Machines related endpoints
	public String BASE_END_POINT_MACHINES = BASE_END_POINT + "/machines";
	public String MACHINES_IMPORT_ENDPOINT = "/import-and-update-machine-inventory";

	public String BASE_END_POINT_VENDORS = BASE_END_POINT + "/vendors";
	public String VENDORS_IMPORT_ENDPOINT = "/import-and-update-vendors-list";
	public String GET_VENDORS_LIST_WITH_NAME_LIKE = "/fetch-vendors-with-name-like/{nameLike}";

	public String BASE_END_POINT_INVENTORY = BASE_END_POINT + "/inventory";
	public String ACCEPT_INVENTORY = "/accept-materials";

	public String BASE_END_POINT_INVENTORYITEM = BASE_END_POINT + "/inventory-items";
	public String INVENTORYITEM_IMPORT_ENDPOINT = "/import-and-update-inventory-items-list";
	public String GET_INVENTORYITEM_PAGE_WISE = "/fetch-inventory-items-list-page-wise";

	public String GET_INVENTORYITEM_LIST_WITH_ID_LIKE = "/fetch-inventory-items-list-like-id/{idLike}";
	public String GET_INVENTORYITEM_WITH_PRODUCT_CODE = "/fetch-inventory-item-with-product-code/{prodCode}";

}
