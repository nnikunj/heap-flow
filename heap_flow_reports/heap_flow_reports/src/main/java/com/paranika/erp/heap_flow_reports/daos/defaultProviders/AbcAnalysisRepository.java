package com.paranika.erp.heap_flow_reports.daos.defaultProviders;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.paranika.erp.heap_flow_reports.common.models.dos.AbcAnalysisQResPojo;

public interface AbcAnalysisRepository extends JpaRepository<AbcAnalysisQResPojo, Long> {

	List<AbcAnalysisQResPojo> findAbcAnalysis(@Param("lowerLimit") Double lowerLimit,
			@Param("upperLimit") Double upperLimit);
}
