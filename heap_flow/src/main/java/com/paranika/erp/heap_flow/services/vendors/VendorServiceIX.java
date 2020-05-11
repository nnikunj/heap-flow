package com.paranika.erp.heap_flow.services.vendors;

import java.util.List;

import org.springframework.stereotype.Service;

import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.InputExcelBook;
import com.paranika.erp.heap_flow.common.models.dos.VendorDO;

@Service
public interface VendorServiceIX {

	public void importAndUpdateVendorsList(InputExcelBook ieb) throws HeapFlowException;

	public List<VendorDO> getVendorListWithNameLike(String nameLike) throws HeapFlowException;

}
