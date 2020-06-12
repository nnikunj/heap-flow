package com.paranika.erp.heap_flow.daos.defaultProviders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.paranika.erp.heap_flow.common.models.dos.EgressLedgerDO;

public interface EgressLedgersRepository extends JpaRepository<EgressLedgerDO, Long> {

	Page<EgressLedgerDO> findByOutgoingMaterial_InventoryItemCodeIgnoreCaseContaining(
			@Param("inventoryItemCode") String inventoryItemCode, Pageable pageable);

}
