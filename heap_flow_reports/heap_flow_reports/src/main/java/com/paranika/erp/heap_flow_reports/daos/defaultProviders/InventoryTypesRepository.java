package com.paranika.erp.heap_flow_reports.daos.defaultProviders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paranika.erp.heap_flow_reports.common.models.dos.InventoryTypeDO;

public interface InventoryTypesRepository extends JpaRepository<InventoryTypeDO, Long> {

	@Query("from InventoryTypeDO iType where UPPER(iType.typeName) = UPPER(:iTypeName)")
	InventoryTypeDO findInventoryTypeWithName(@Param("iTypeName") String iTypeName);
}
