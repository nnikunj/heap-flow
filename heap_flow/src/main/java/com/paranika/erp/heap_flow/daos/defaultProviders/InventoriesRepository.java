package com.paranika.erp.heap_flow.daos.defaultProviders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paranika.erp.heap_flow.common.models.dos.InventoryDO;
import com.paranika.erp.heap_flow.common.models.dos.InventoryItemDO;
import com.paranika.erp.heap_flow.common.models.dos.InventoryTypeDO;

public interface InventoriesRepository extends JpaRepository<InventoryDO, Long> {

	@Query("from InventoryDO inv where inv.item = :product and inv.type= :invType")
	InventoryDO findInventoryWithProductAndType(@Param("product") InventoryItemDO product,
			@Param("invType") InventoryTypeDO invType);

	@Query("from InventoryDO inv ORDER BY inv.item")
	Page<InventoryDO> findPagedAllInv(Pageable pageable);

	Page<InventoryDO> findByItem_InventoryItemCodeIgnoreCaseContaining(
			@Param("inventoryItemCode") String inventoryItemCode, Pageable pageable);

}
