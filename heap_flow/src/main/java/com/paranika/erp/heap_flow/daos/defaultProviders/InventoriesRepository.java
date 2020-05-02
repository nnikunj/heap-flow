package com.paranika.erp.heap_flow.daos.defaultProviders;

import org.springframework.data.repository.CrudRepository;

import com.paranika.erp.heap_flow.common.models.dos.InventoryDO;

public interface InventoriesRepository extends CrudRepository<InventoryDO, Long> {

}
