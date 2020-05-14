package com.paranika.erp.heap_flow.services.inventory;

import java.util.List;

import org.springframework.stereotype.Service;

import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.dtos.InputExcelBook;
import com.paranika.erp.heap_flow.common.models.dtos.InventoryItemDTO;

@Service
public interface InventoryItemsServiceIX {

	public void importAndUpdateInventoryItemsList(InputExcelBook ieb) throws HeapFlowException;

	public List<InventoryItemDTO> getPagedInventoryItemList(int startRecord, int pageSize)
			throws HeapFlowException;

	public List<InventoryItemDTO> getItemListWithIdLike(String idLike) throws HeapFlowException;

	public InventoryItemDTO getItemWithProdCode(String prodCode) throws HeapFlowException;

}
