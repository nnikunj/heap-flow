package com.paranika.erp.heap_flow.common.exceptions;

public class HeapFlowRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HeapFlowRuntimeException() {
		super("Default exception created without any messages.");
	}

	public HeapFlowRuntimeException(String msg) {
		super(msg);
	}

	public HeapFlowRuntimeException(Exception e) {
		super(e);
	}
}
