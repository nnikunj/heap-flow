package com.paranika.erp.heap_flow.services.inventoryItems;

import java.util.List;

import org.springframework.stereotype.Service;

import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.InputExcelBook;
import com.paranika.erp.heap_flow.common.models.dos.InventoryItemDO;

@Service
public interface InventoryItemsServiceIX {

	public void importAndUpdateInventoryItemsList(InputExcelBook ieb) throws HeapFlowException;

	public List<InventoryItemDO> getPagedInventoryItemList(int startRecord, int pageSize) throws HeapFlowException;

}
