package com.paranika.erp.heap_flow_reports.daos.defaultProviders;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paranika.erp.heap_flow_reports.common.models.dos.InventoryItemDO;

public interface InventoryItemsRepository extends JpaRepository<InventoryItemDO, Long> {

	@Query("from InventoryItemDO item where item.inventoryItemCode LIKE CONCAT('%',:idLike,'%') ORDER BY item.inventoryItemCode")
	List<InventoryItemDO> findItemsWithIdLike(@Param("idLike") String idLike, Pageable pageable);

	@Query("from InventoryItemDO item where UPPER(item.inventoryItemCode) LIKE UPPER(CONCAT('%',:idLike,'%')) ORDER BY item.inventoryItemCode")
	Page<InventoryItemDO> findPagedItemsWithIdLike(@Param("idLike") String idLike, Pageable pageable);

	@Query("from InventoryItemDO item ORDER BY item.inventoryItemCode")
	Page<InventoryItemDO> findPagedAllItems(Pageable pageable);

	@Query("from InventoryItemDO item where item.inventoryItemCode = UPPER(:prodCode)")
	InventoryItemDO findItemWithId(@Param("prodCode") String prodCode);

}
