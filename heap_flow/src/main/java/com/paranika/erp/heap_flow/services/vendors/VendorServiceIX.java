package com.paranika.erp.heap_flow.services.vendors;

import org.springframework.stereotype.Service;

import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.InputExcelBook;

@Service
public interface VendorServiceIX {

	public void importAndUpdateVendorsList(InputExcelBook ieb) throws HeapFlowException;

}
