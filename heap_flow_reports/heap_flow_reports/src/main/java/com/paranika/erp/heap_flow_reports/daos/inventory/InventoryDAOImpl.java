package com.paranika.erp.heap_flow_reports.daos.inventory;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paranika.erp.heap_flow_reports.common.models.dos.IngressLedgerDO;
import com.paranika.erp.heap_flow_reports.daos.defaultProviders.IngressLedgersRepository;

@Component
public class InventoryDAOImpl implements InventoryDAO {
	@Autowired
	IngressLedgersRepository ingressRepo;

	public List<IngressLedgerDO> getIngressLedgers(Date startDate, Date endDate) throws Exception {

		return ingressRepo.findIngressLedgerBetweenDate(startDate, endDate);
	}

}