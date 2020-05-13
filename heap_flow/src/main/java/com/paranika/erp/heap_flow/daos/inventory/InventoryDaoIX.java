package com.paranika.erp.heap_flow.daos.inventory;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.paranika.erp.heap_flow.common.models.dos.IngressLedgerDO;

@Repository
public interface InventoryDaoIX {

	public void persistAllIngressLedgers(Collection<IngressLedgerDO> ledgers) throws Exception;

}
