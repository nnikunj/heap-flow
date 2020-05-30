package com.paranika.erp.heap_flow.services.vendors;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.dos.VendorDO;
import com.paranika.erp.heap_flow.common.models.dtos.InputExcelBook;

@Service
public interface VendorServiceIX {

	public void importAndUpdateVendorsList(InputExcelBook ieb) throws HeapFlowException;

	public List<VendorDO> getVendorListWithNameLike(String nameLike) throws HeapFlowException;

	public Page<VendorDO> getPagedVendorsWithSearchNameLike(String searchNameLike, Pageable paging)
			throws HeapFlowException;

	public VendorDO persistVendor(VendorDO data) throws HeapFlowException;

}
