package com.paranika.erp.heap_flow.services.inventory;

import org.springframework.stereotype.Service;

import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.dtos.AcceptingMaterialData;
import com.paranika.erp.heap_flow.common.models.dtos.IssuingMaterialDataDTO;

@Service
public interface InventoryServiceIX {

	public void acceptInventory(AcceptingMaterialData incomingMaterials) throws HeapFlowException;

	public void issueInventory(IssuingMaterialDataDTO outgoingMaterials) throws HeapFlowException;

}
