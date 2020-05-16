package com.paranika.erp.heap_flow.daos.defaultProviders;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.paranika.erp.heap_flow.common.models.dos.InventoryTypeDO;

public interface InventoryTypesRepository extends CrudRepository<InventoryTypeDO, Long> {

	@Query("from InventoryTypeDO iType where UPPER(iType.typeName) = UPPER(:iTypeName)")
	InventoryTypeDO findInventoryTypeWithName(@Param("iTypeName") String iTypeName);
}
