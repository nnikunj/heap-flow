package com.paranika.erp.heap_flow.daos.defaultProviders;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.paranika.erp.heap_flow.common.models.dos.VendorDO;

public interface VendorsRepository extends CrudRepository<VendorDO, Long> {

	@Query("from VendorDO vendor where UPPER(vendor.searchName) LIKE UPPER(CONCAT('%',:nameLike,'%')) ORDER BY vendor.searchName")
	List<VendorDO> findVendorsWithNameLike(@Param("nameLike") String nameLike, Pageable pageable);

	@Query("from VendorDO vendor where UPPER(vendor.vendorId) = UPPER(:vendorCode)")
	VendorDO findVendorWithId(@Param("vendorCode") String vendorCode);

	@Query("from VendorDO vendor ORDER BY vendor.searchName")
	Page<VendorDO> findPagedAllVendors(Pageable paging);

	@Query("from VendorDO vendor where UPPER(vendor.searchName) LIKE UPPER(CONCAT('%',:searchNameLike,'%')) ORDER BY vendor.searchName")
	Page<VendorDO> findPagedVendorsWithSearchNameLike(String searchNameLike, Pageable paging);

	@Query("from VendorDO vendor where UPPER(vendor.searchName) = UPPER(:searchName)")
	VendorDO findVendorWithName(@Param("searchName") String searchName);
}
