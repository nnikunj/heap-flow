package com.paranika.erp.heap_flow_reports.daos.defaultProviders;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.paranika.erp.heap_flow_reports.common.models.dos.FastMovingItemsPojo;

public interface FastMovingItemRepo extends JpaRepository<FastMovingItemsPojo, Long> {

	List<FastMovingItemsPojo> findFastMovingAnalysis(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

}
