package com.paranika.erp.heap_flow_reports.daos.defaultProviders;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paranika.erp.heap_flow_reports.common.models.dos.EgressLedgerDO;

public interface EgressLedgersRepository extends JpaRepository<EgressLedgerDO, Long> {

}
