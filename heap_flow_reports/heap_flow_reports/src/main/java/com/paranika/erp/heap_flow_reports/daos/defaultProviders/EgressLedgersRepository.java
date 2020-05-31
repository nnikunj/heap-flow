package com.paranika.erp.heap_flow_reports.daos.defaultProviders;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paranika.erp.heap_flow_reports.common.models.dos.EgressLedgerDO;

public interface EgressLedgersRepository extends JpaRepository<EgressLedgerDO, Long> {

	@Query("from EgressLedgerDO egs where egs.recordDate BETWEEN :startDate and :endDate")
	List<EgressLedgerDO> findEgressLedgerBetweenDate(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

}
