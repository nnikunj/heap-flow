package com.paranika.erp.heap_flow_reports.daos.inventory;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.paranika.erp.heap_flow_reports.common.models.dos.AbcAnalysisQResPojo;
import com.paranika.erp.heap_flow_reports.common.models.dos.EgressLedgerDO;
import com.paranika.erp.heap_flow_reports.common.models.dos.IngressLedgerDO;
import com.paranika.erp.heap_flow_reports.common.models.dos.InventoryItemDO;

@Repository
public interface InventoryDAO {

	public List<IngressLedgerDO> getIngressLedgers(Date startDate, Date endDate) throws Exception;

	public List<EgressLedgerDO> getEgressLedgers(Date startDate, Date endDate) throws Exception;

	public List<AbcAnalysisQResPojo> getAbcAnalysis(Double lowerLimit, Double upperLimit) throws Exception;

	public InventoryItemDO getInventoryItemswithCode(String prodCode) throws Exception;

}
