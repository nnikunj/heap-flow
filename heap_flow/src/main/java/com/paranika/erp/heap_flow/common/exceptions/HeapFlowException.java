package com.paranika.erp.heap_flow.common.exceptions;

public class HeapFlowException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HeapFlowException() {
		super("Default exception created without any messages.");
	}

	public HeapFlowException(String msg) {
		super(msg);
	}

	public HeapFlowException(Exception e) {
		super(e);
	}
}
