package com.paranika.erp.heap_flow_reports.common.exceptions;

public class HeapFlowReportRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HeapFlowReportRuntimeException() {
		super("Default exception created without any messages.");
	}

	public HeapFlowReportRuntimeException(String msg) {
		super(msg);
	}

	public HeapFlowReportRuntimeException(Exception e) {
		super(e);
	}
}
