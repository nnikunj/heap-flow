package com.paranika.erp.heap_flow_reports.services.inventory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paranika.erp.heap_flow_reports.common.CommonUtil;
import com.paranika.erp.heap_flow_reports.common.exceptions.HeapFlowReportException;
import com.paranika.erp.heap_flow_reports.common.models.dos.EgressLedgerDO;
import com.paranika.erp.heap_flow_reports.common.models.dos.IngressLedgerDO;
import com.paranika.erp.heap_flow_reports.common.models.dtos.EgressLedgerDTO;
import com.paranika.erp.heap_flow_reports.common.models.dtos.IngressLedgerDTO;
import com.paranika.erp.heap_flow_reports.daos.inventory.InventoryDAO;

@Component
public class InventoryServiceImpl implements InventoryServiceIX {

	@Autowired
	CommonUtil util;

	@Autowired
	private InventoryDAO dao;

	private final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

	public ByteArrayInputStream getIngressReport(Date startDate, Date endDate) throws HeapFlowReportException {
		List<IngressLedgerDO> collectedData = null;
		List<IngressLedgerDTO> translatedData = new LinkedList<IngressLedgerDTO>();
		logger.debug("Entered getIngressReport");
		try {
			collectedData = dao.getIngressLedgers(startDate, endDate);
			logger.debug("Data collected collectedData size " + collectedData.size());
			for (IngressLedgerDO ingressLedgerDO : collectedData) {
				IngressLedgerDTO obj = new IngressLedgerDTO(ingressLedgerDO);
				translatedData.add(obj);
			}
			logger.debug("translatedData\n" + translatedData + "\n");
		} catch (Exception e) {
			logger.error("Could not get ledgers.", e);
			throw new HeapFlowReportException(e);
		}
		logger.debug("Exiting getIngressReport");
		return generateExcel(translatedData);
	}

	private ByteArrayInputStream generateExcel(List<IngressLedgerDTO> translatedData) throws HeapFlowReportException {

		String[] cols = { "srNo", "recordDate", "invNumber", "poNumber", "supplierName", "Item Code", "itemDescription",
				"quantity", "baseUnitMeasure", "rate", "amount", "remark", "checkedBy", "indentNumber" };
		List<List<String>> fillInData = new LinkedList<List<String>>();

		for (IngressLedgerDTO data : translatedData) {
			LinkedList<String> dataSet = new LinkedList<String>();
			dataSet.add(data.getSrNo());
			dataSet.add(data.getRecordDate());

			dataSet.add((data.getInvNumber() == null) ? " " : data.getInvNumber().trim());
			dataSet.add((data.getPoNumber() == null) ? " " : data.getPoNumber().trim());
			dataSet.add((data.getSupplierName() == null) ? " " : data.getSupplierName().trim());
			dataSet.add(data.getItemCode());
			dataSet.add((data.getItemDescription() == null) ? " " : data.getItemDescription().trim());
			dataSet.add((data.getQuantity() == null) ? " " : data.getQuantity().trim());
			dataSet.add((data.getBaseUnitMeasure() == null) ? " " : data.getBaseUnitMeasure().trim());
			dataSet.add((data.getRate() == null) ? " " : data.getRate().trim());
			dataSet.add((data.getAmount() == null) ? " " : data.getAmount().trim());
			dataSet.add((data.getRemark() == null) ? " " : data.getRemark().trim());
			dataSet.add((data.getCheckedBy() == null) ? " " : data.getCheckedBy().trim());
			dataSet.add((data.getIndentNumber() == null) ? " " : data.getIndentNumber().trim());
			fillInData.add(dataSet);
		}
		logger.debug("\nfillInData\n" + fillInData);
		ByteArrayInputStream stream = null;
		try {
			stream = util.genrateExcel(fillInData, cols, "AcceptedMaterialReport");
		} catch (IOException e) {

			logger.error("Excel genartion failed.", e);
			throw new HeapFlowReportException(e);
		}

		return stream;
	}

	private ByteArrayInputStream generateEgresExcel(List<EgressLedgerDTO> translatedData)
			throws HeapFlowReportException {

		String[] cols = { "srNo", "recordDate", "department", "itemGroup", "ved", "machineNummber", "engineer",
				"approvedBy", "category", "itemCode", "description", "quantity", "baseUnitMeasure", "issuedBy" };
		List<List<String>> fillInData = new LinkedList<List<String>>();

		for (EgressLedgerDTO data : translatedData) {
			LinkedList<String> dataSet = new LinkedList<String>();
			dataSet.add(data.getSrNo());
			dataSet.add(data.getRecordDate());
			dataSet.add(data.getDepartment());
			dataSet.add(data.getItemGroup());
			dataSet.add(data.getVed());
			dataSet.add(data.getMachineNummber());
			dataSet.add(data.getEngineer());
			dataSet.add(data.getApprovedBy());
			dataSet.add(data.getCategory());
			dataSet.add(data.getItemCode());
			dataSet.add(data.getDescription());
			dataSet.add(data.getQunatity());
			dataSet.add(data.getBaseUnitMeasure());
			dataSet.add(data.getIssuedBy());
			fillInData.add(dataSet);
		}
		logger.debug("\nfillInData\n" + fillInData);
		ByteArrayInputStream stream = null;
		try {
			stream = util.genrateExcel(fillInData, cols, "IssuedMaterialReport");
		} catch (IOException e) {

			logger.error("Excel genartion failed.", e);
			throw new HeapFlowReportException(e);
		}

		return stream;
	}

	@Override
	public ByteArrayInputStream getEgressReport(Date startDate, Date endDate) throws HeapFlowReportException {
		List<EgressLedgerDO> collectedData = null;
		List<EgressLedgerDTO> translatedData = new LinkedList<EgressLedgerDTO>();

		logger.debug("Entered getEgressReport");
		try {
			collectedData = dao.getEgressLedgers(startDate, endDate);
			logger.debug("Data collected collectedData size " + collectedData.size());
			for (EgressLedgerDO egressLedgerDO : collectedData) {
				EgressLedgerDTO obj = new EgressLedgerDTO(egressLedgerDO);

				translatedData.add(obj);
			}
			logger.debug("translatedData\n" + translatedData + "\n");
		} catch (Exception e) {
			logger.error("Could not get ledgers.", e);
			throw new HeapFlowReportException(e);
		}
		logger.debug("Exiting getEgressReport");
		return generateEgresExcel(translatedData);
	}
}
