package com.paranika.erp.heap_flow_reports.common.exceptions;

public class HeapFlowReportException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HeapFlowReportException() {
		super("Default exception created without any messages.");
	}

	public HeapFlowReportException(String msg) {
		super(msg);
	}

	public HeapFlowReportException(Exception e) {
		super(e);
	}
}
