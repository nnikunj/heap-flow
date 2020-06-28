package com.paranika.erp.heap_flow.daos.inventory;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.paranika.erp.heap_flow.common.models.dos.EgressLedgerDO;
import com.paranika.erp.heap_flow.common.models.dos.IngressLedgerDO;
import com.paranika.erp.heap_flow.common.models.dos.InventoryDO;

@Repository
public interface InventoryDaoIX {

	public void persistAllIngressLedgers(Collection<IngressLedgerDO> ledgers) throws Exception;

	public void persistAllEgressLedgers(Collection<EgressLedgerDO> ledgerList) throws Exception;

	public void mergeAll(Collection<InventoryDO> oldInventories, boolean isMergeRequired) throws Exception;

	public Page<InventoryDO> getPagedInvSummaryWithIdLike(String idLike, Pageable paging) throws Exception;

	public Page<EgressLedgerDO> getPagedIssuedMaterialWithIdLike(String idLike, Pageable paging) throws Exception;

	public void deleteIsuedItem(Long dbId) throws Exception;

	public Page<IngressLedgerDO> getPagedAcceptedMaterialWithIdLike(String idLike, Pageable paging) throws Exception;

	public void deleteAcceptedItem(Long lDbId) throws Exception;

}
