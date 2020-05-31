package com.paranika.erp.heap_flow_reports.common;

public interface HeapFlowReportsApiEndPoints {

	public String BASE_END_POINT = "/api/rpts";

	// Ingress related Reports
	public String BASE_END_POINT_ACCEPTED_MAT = BASE_END_POINT + "/incoming-rpt";
	public String GET_INGRESS_RPT = "/fetch-material-ingress";

}
