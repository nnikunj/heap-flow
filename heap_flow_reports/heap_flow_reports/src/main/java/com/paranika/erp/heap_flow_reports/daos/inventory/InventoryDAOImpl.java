package com.paranika.erp.heap_flow_reports.daos.inventory;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.paranika.erp.heap_flow_reports.common.exceptions.HeapFlowReportException;
import com.paranika.erp.heap_flow_reports.common.models.dos.AbcAnalysisQResPojo;
import com.paranika.erp.heap_flow_reports.common.models.dos.EgressLedgerDO;
import com.paranika.erp.heap_flow_reports.common.models.dos.FastMovingItemsPojo;
import com.paranika.erp.heap_flow_reports.common.models.dos.IngressLedgerDO;
import com.paranika.erp.heap_flow_reports.common.models.dos.InventoryDO;
import com.paranika.erp.heap_flow_reports.common.models.dos.InventoryItemDO;
import com.paranika.erp.heap_flow_reports.daos.defaultProviders.AbcAnalysisRepository;
import com.paranika.erp.heap_flow_reports.daos.defaultProviders.EgressLedgersRepository;
import com.paranika.erp.heap_flow_reports.daos.defaultProviders.FastMovingItemRepo;
import com.paranika.erp.heap_flow_reports.daos.defaultProviders.IngressLedgersRepository;
import com.paranika.erp.heap_flow_reports.daos.defaultProviders.InventoriesRepository;
import com.paranika.erp.heap_flow_reports.daos.defaultProviders.InventoryItemsRepository;

@Component
public class InventoryDAOImpl implements InventoryDAO {
	@Autowired
	IngressLedgersRepository ingressRepo;
	@Autowired
	EgressLedgersRepository eressRepo;
	@Autowired
	InventoriesRepository invRepo;
	@Autowired
	AbcAnalysisRepository abcRepo;
	@Autowired
	InventoryItemsRepository itemRepo;
	@Autowired
	FastMovingItemRepo fastItemRepo;
	private final Logger logger = LoggerFactory.getLogger(InventoryDAOImpl.class);

	public List<IngressLedgerDO> getIngressLedgers(Date startDate, Date endDate) throws Exception {

		return ingressRepo.findIngressLedgerBetweenDate(startDate, endDate);
	}

	@Override
	public List<EgressLedgerDO> getEgressLedgers(Date startDate, Date endDate) throws Exception {
		return eressRepo.findEgressLedgerBetweenDate(startDate, endDate);

	}

	@Override
	public List<AbcAnalysisQResPojo> getAbcAnalysis(Double lowerLimit, Double upperLimit) throws Exception {

		return abcRepo.findAbcAnalysis(lowerLimit, upperLimit);
	}

	@Override
	public List<FastMovingItemsPojo> getFastMovingAnalysis(Date startDate, Date endDate) throws Exception {
		return fastItemRepo.findFastMovingAnalysis(startDate, endDate);
	}

	@Override
	public InventoryItemDO getInventoryItemswithCode(String prodCode) throws Exception {
		if (prodCode == null || prodCode.isEmpty()) {
			return null;
		}
		InventoryItemDO fetchedObj = itemRepo.findItemWithId(prodCode);
		return fetchedObj;

	}

	@Override
	public List<InventoryDO> getInvSummaryWithIdLike(String idLike) throws Exception {
		if (StringUtils.isEmpty(idLike)) {
			return invRepo.findPagedAllInv();
		} else {
			return invRepo.findByItem_InventoryItemCodeIgnoreCaseContaining(idLike);
		}

	}

	@Override
	public List<InventoryDO> getInvModifiedBetween(Date startDate, Date endDate) throws Exception {
		logger.debug("Entering getInvModifiedDateAgo");
		if (startDate == null) {
			logger.debug("startDate date is null.");
			throw new HeapFlowReportException("startDate date is null.");
		}
		if (endDate == null) {
			logger.debug("endDate date is null.");
			throw new HeapFlowReportException("endDate date is null.");
		}
		List<InventoryDO> fetchedList = invRepo.findAllInventoryModifiedBetween(startDate, endDate);
		logger.debug("Fetched Item: " + ((fetchedList == null) ? "null" : fetchedList.size()));
		logger.debug("Exiting getInvModifiedDateAgo");
		return fetchedList;
	}

}
