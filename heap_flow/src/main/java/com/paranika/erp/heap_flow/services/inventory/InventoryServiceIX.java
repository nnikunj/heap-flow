package com.paranika.erp.heap_flow.services.inventory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.dtos.AcceptingMaterialData;
import com.paranika.erp.heap_flow.common.models.dtos.EgressDTO;
import com.paranika.erp.heap_flow.common.models.dtos.InputExcelBook;
import com.paranika.erp.heap_flow.common.models.dtos.InventorySummaryDTO;
import com.paranika.erp.heap_flow.common.models.dtos.IssuingMaterialDataDTO;

@Service
public interface InventoryServiceIX {

	public void acceptInventory(AcceptingMaterialData incomingMaterials) throws HeapFlowException;

	public void issueInventory(IssuingMaterialDataDTO outgoingMaterials) throws HeapFlowException;

	public void updatePreviousStock(InputExcelBook book) throws HeapFlowException;

	public Page<InventorySummaryDTO> getPagedInvSummaryWithIdLike(String idLike, Pageable paging)
			throws HeapFlowException;

	public Page<EgressDTO> getPagedIssuedMaterialsWithIdLike(String idLike, Pageable paging) throws HeapFlowException;

	public void deleteIsuedItem(String dbId) throws HeapFlowException;

}
