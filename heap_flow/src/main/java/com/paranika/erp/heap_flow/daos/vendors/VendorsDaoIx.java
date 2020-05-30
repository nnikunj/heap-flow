package com.paranika.erp.heap_flow.daos.vendors;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.paranika.erp.heap_flow.common.models.dos.VendorDO;

@Repository
public interface VendorsDaoIx {
	public List<VendorDO> getAllVendors() throws Exception;

	public List<VendorDO> getAllvendorsWithPagination(int startRecord, int pageSize) throws Exception;

	public void add(VendorDO vendorDO) throws Exception;

	public void saveAll(Collection<VendorDO> vendorDOs) throws Exception;

	public void mergeAll(Collection<VendorDO> vendorDOs) throws Exception;

	public List<VendorDO> getAllVendorsWithNameLike(String nameLike) throws Exception;

	public VendorDO getVendorwithCode(String vendorCode) throws Exception;

	public Page<VendorDO> getPagedVendorsWithSearchNameLike(String searchNameLike, Pageable paging) throws Exception;

	public VendorDO addOrUpdate(VendorDO data) throws Exception;
}
