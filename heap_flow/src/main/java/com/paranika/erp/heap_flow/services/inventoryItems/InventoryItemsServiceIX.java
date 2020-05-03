package com.paranika.erp.heap_flow.services.inventoryItems;

import org.springframework.stereotype.Service;

import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.InputExcelBook;

@Service
public interface InventoryItemsServiceIX {

	public void importAndUpdateInventoryItemsList(InputExcelBook ieb) throws HeapFlowException;

}
