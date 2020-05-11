package com.paranika.erp.heap_flow.daos.defaultProviders;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.paranika.erp.heap_flow.common.models.dos.VendorDO;

public interface VendorsRepository extends CrudRepository<VendorDO, Long> {

	@Query("from VendorDO vendor where UPPER(vendor.searchName) LIKE UPPER(CONCAT('%',:nameLike,'%')) ORDER BY vendor.searchName")
	List<VendorDO> findVendorsWithNameLike(@Param("nameLike") String nameLike, Pageable pageable);

}
