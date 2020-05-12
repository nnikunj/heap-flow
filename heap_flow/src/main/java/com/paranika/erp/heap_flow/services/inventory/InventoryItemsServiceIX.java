package com.paranika.erp.heap_flow.services.inventory;

import java.util.List;

import org.springframework.stereotype.Service;

import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.InputExcelBook;
import com.paranika.erp.heap_flow.common.models.InventoryItemDisplayDO;

@Service
public interface InventoryItemsServiceIX {

	public void importAndUpdateInventoryItemsList(InputExcelBook ieb) throws HeapFlowException;

	public List<InventoryItemDisplayDO> getPagedInventoryItemList(int startRecord, int pageSize)
			throws HeapFlowException;

	public List<InventoryItemDisplayDO> getItemListWithIdLike(String idLike) throws HeapFlowException;

	public InventoryItemDisplayDO getItemWithProdCode(String prodCode) throws HeapFlowException;

}
