package com.paranika.erp.heap_flow.common.models.dtos;

import java.util.List;

import com.paranika.erp.heap_flow.common.models.dos.InventoryDO;

public class InventorySummaryDTO {

	public InventorySummaryDTO() {

	}

	public InventorySummaryDTO(InventoryDO invDbObj) {

	}

	private InventoryItemDTO item;
	private List<InventoryDTO> contents;

	private Double totalMonitoryValue;

	private Double totalQuantity;

	public InventoryItemDTO getItem() {
		return item;
	}

	public void setItem(InventoryItemDTO item) {
		this.item = item;
	}

	public Double getTotalMonitoryValue() {
		return totalMonitoryValue;
	}

	public void setTotalMonitoryValue(Double totalMonitoryValue) {
		this.totalMonitoryValue = totalMonitoryValue;
	}

	public Double getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public List<InventoryDTO> getContents() {
		return contents;
	}

	public void setContents(List<InventoryDTO> contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventorySummaryDTO [item=");
		builder.append(item);
		builder.append(", contents=");
		builder.append(contents);
		builder.append(", totalMonitoryValue=");
		builder.append(totalMonitoryValue);
		builder.append(", totalQuantity=");
		builder.append(totalQuantity);
		builder.append("]");
		return builder.toString();
	}

}
