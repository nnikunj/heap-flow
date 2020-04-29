package com.paranika.erp.heap_flow.services.machines;

import org.springframework.stereotype.Service;

import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.InputExcelBook;

@Service
public interface MachineServiceIX {
	
	public void importAndUpdateMachinesInventory(InputExcelBook ieb) throws HeapFlowException;
	
}
