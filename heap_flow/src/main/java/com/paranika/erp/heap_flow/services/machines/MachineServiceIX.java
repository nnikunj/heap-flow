package com.paranika.erp.heap_flow.services.machines;

import java.util.List;

import org.springframework.stereotype.Service;

import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.dos.MachineDO;
import com.paranika.erp.heap_flow.common.models.dtos.InputExcelBook;

@Service
public interface MachineServiceIX {

	public void importAndUpdateMachinesInventory(InputExcelBook ieb) throws HeapFlowException;

	public List<MachineDO> getMachineListWithCodeLike(String nameLike) throws HeapFlowException;

}
