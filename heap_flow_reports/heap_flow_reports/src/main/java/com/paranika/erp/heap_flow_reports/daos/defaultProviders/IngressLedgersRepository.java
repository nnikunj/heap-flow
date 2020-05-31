package com.paranika.erp.heap_flow_reports.daos.defaultProviders;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paranika.erp.heap_flow_reports.common.models.dos.IngressLedgerDO;

public interface IngressLedgersRepository extends JpaRepository<IngressLedgerDO, Long> {

	@Query("from IngressLedgerDO ing where ing.recordDate BETWEEN :startDate and :endDate")
	List<IngressLedgerDO> findIngressLedgerBetweenDate(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

}
