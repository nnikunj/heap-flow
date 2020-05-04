package com.paranika.erp.heap_flow.common.models;

import com.google.gson.Gson;

public class InputPagedFetchCallData {

	private int startRecord;

	private int pageSize;

	public int getStartRecord() {
		return startRecord;
	}

	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public static void main(String[] args) {
		InputPagedFetchCallData holder = new InputPagedFetchCallData();
		holder.setPageSize(10);
		holder.setStartRecord(1);
		Gson gson = new Gson();
		System.out.println(gson.toJson(holder));
	}
}
