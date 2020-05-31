package com.paranika.erp.heap_flow_reports.daos.inventory;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.paranika.erp.heap_flow_reports.common.models.dos.IngressLedgerDO;

@Repository
public interface InventoryDAO {

	public List<IngressLedgerDO> getIngressLedgers(Date startDate, Date endDate) throws Exception;

}