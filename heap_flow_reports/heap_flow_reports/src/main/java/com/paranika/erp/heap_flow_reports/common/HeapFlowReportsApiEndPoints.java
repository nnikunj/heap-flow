package com.paranika.erp.heap_flow_reports.common;

public interface HeapFlowReportsApiEndPoints {

	public String BASE_END_POINT = "/api/rpts";

	public String BASE_END_POINT_INVENTORY = BASE_END_POINT + "/inventory";
	public String GET_ITEM_QRCODE_RPT = "/fetch-item-qrcode/{prodCode}";
	// Ingress related Reports
	public String BASE_END_POINT_ACCEPTED_MAT = BASE_END_POINT + "/incoming-rpt";
	public String GET_INGRESS_RPT = "/fetch-material-ingress";
	// Egress related Reports
	public String BASE_END_POINT_ISSUE_MAT = BASE_END_POINT + "/outgoing-rpt";
	public String GET_EGRESS_RPT = "/fetch-material-egress";

	public String BASE_END_POINT_ABC_ANALYSIS = BASE_END_POINT + "/abc-rpt";
	public String GENERATE_MAT_VALUE_RPT = "inventory-valuation";

}
