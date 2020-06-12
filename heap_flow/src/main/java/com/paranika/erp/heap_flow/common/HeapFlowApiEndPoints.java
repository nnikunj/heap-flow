package com.paranika.erp.heap_flow.common;

public interface HeapFlowApiEndPoints {

	public String BASE_END_POINT = "/api/v1";

	// Machines related endpoints
	public String BASE_END_POINT_MACHINES = BASE_END_POINT + "/machines";
	public String MACHINES_IMPORT_ENDPOINT = "/import-and-update-machine-inventory";
	public String GET_MACHINES_LIST_WITH_CODE_LIKE = "/fetch-machines-with-code-like/{codeLike}";
	public String GET_MACHINES_PAGE_WISE = "/fetch-machines-page-wise";
	public String ADD_UPDATE_MACHINE = "add-update";
	public String GET_MACHINE_WITH_CODE = "/fetch-machine-with-code/{code}";

	public String BASE_END_POINT_VENDORS = BASE_END_POINT + "/vendors";
	public String VENDORS_IMPORT_ENDPOINT = "/import-and-update-vendors-list";
	public String GET_VENDORS_LIST_WITH_NAME_LIKE = "/fetch-vendors-with-name-like/{nameLike}";
	public String GET_VENDORS_PAGE_WISE = "/fetch-vendors-page-wise";
	public String ADD_UPDATE_VENDOR = "add-update";
	public String GET_VENDOR_WITH_SEARCH_NAME = "/fetch-vendor-with-search-name/{searchName}";

	public String BASE_END_POINT_INVENTORY = BASE_END_POINT + "/inventory";
	public String ACCEPT_INVENTORY = "/accept-materials";
	public String ISSUE_INVENTORY = "/issue-materials";
	public String GET_INVENTORYSUMMARY_PAGE_WISE = "/fetch-inventory-summary-page-wise";
	public String UPDATE_PRE_EXISTING_STOCKS = "/update-inventory-stocks";
	public String GET_ISSUED_ITEMS_LIST_PAGE_WISE = "/fetch-issued-materials-page-wise";

	public String BASE_END_POINT_INVENTORYITEM = BASE_END_POINT + "/inventory-items";
	public String ADD_UPDATE_ITEM = "modify-item";
	public String INVENTORYITEM_IMPORT_ENDPOINT = "/import-and-update-inventory-items-list";
	public String GET_INVENTORYITEM_PAGE_WISE = "/fetch-inventory-items-list-page-wise";
	public String GET_INVENTORYITEM_LIST_WITH_ID_LIKE = "/fetch-inventory-items-list-like-id/{idLike}";
	public String GET_PAGED_INVENTORYITEM_LIST_WITH_ID_LIKE = "/fetch-paged-inventory-items";
	public String GET_INVENTORYITEM_WITH_PRODUCT_CODE = "/fetch-inventory-item-with-product-code/{prodCode}";

}
