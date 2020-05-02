package com.paranika.erp.heap_flow.daos.defaultProviders;

import org.springframework.data.repository.CrudRepository;

import com.paranika.erp.heap_flow.common.models.dos.InventoryItemDO;

public interface InventoryItemsRepository extends CrudRepository<InventoryItemDO, Long> {

}
