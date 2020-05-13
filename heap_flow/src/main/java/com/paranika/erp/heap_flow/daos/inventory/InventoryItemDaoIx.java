package com.paranika.erp.heap_flow.daos.inventory;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.paranika.erp.heap_flow.common.models.dos.InventoryItemDO;

@Repository
public interface InventoryItemDaoIx {
	public List<InventoryItemDO> getAllInventoryItems() throws Exception;

	public List<InventoryItemDO> getAllInventoryItemsWithPagination(int startRecord, int pageSize) throws Exception;

	public void add(InventoryItemDO inventoryItemDO) throws Exception;

	public void saveAll(Collection<InventoryItemDO> inventoryItemDOs) throws Exception;

	public void mergeAll(Collection<InventoryItemDO> inventoryItemDOs) throws Exception;

	public List<InventoryItemDO> getAllInventoryItemsLikeItemCode(String idLike) throws Exception;

	public InventoryItemDO getInventoryItemswithCode(String prodCode) throws Exception;
}
