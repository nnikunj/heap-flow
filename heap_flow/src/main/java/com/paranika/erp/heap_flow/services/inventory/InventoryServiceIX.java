package com.paranika.erp.heap_flow.services.inventory;

import org.springframework.stereotype.Service;

import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.AcceptingMaterialData;

@Service
public interface InventoryServiceIX {

	public void acceptInventory(AcceptingMaterialData incomingMaterials) throws HeapFlowException;

}
