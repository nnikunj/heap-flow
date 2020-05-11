package com.paranika.erp.heap_flow.daos.defaultProviders;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.paranika.erp.heap_flow.common.models.dos.InventoryItemDO;

public interface InventoryItemsRepository extends CrudRepository<InventoryItemDO, Long> {

	@Query("from InventoryItemDO item where item.inventoryItemCode LIKE CONCAT('%',:idLike,'%')")
	List<InventoryItemDO> findItemsWithIdLike(@Param("idLike") String idLike);

	@Query("from InventoryItemDO item where item.inventoryItemCode = UPPER(:prodCode)")
	InventoryItemDO findItemWithId(@Param("prodCode") String prodCode);

}
