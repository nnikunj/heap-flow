package com.paranika.erp.heap_flow_reports.services.inventory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import com.paranika.erp.heap_flow_reports.common.CommonUtil;
import com.paranika.erp.heap_flow_reports.common.exceptions.HeapFlowReportException;
import com.paranika.erp.heap_flow_reports.common.models.dos.AbcAnalysisQResPojo;
import com.paranika.erp.heap_flow_reports.common.models.dos.EgressLedgerDO;
import com.paranika.erp.heap_flow_reports.common.models.dos.IngressLedgerDO;
import com.paranika.erp.heap_flow_reports.common.models.dos.InventoryDO;
import com.paranika.erp.heap_flow_reports.common.models.dos.InventoryItemDO;
import com.paranika.erp.heap_flow_reports.common.models.dtos.AbcAnalysisInputParameters;
import com.paranika.erp.heap_flow_reports.common.models.dtos.EgressLedgerDTO;
import com.paranika.erp.heap_flow_reports.common.models.dtos.IngressLedgerDTO;
import com.paranika.erp.heap_flow_reports.common.models.dtos.InventoryItemDTO;
import com.paranika.erp.heap_flow_reports.daos.inventory.InventoryDAO;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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

		String[] cols = { "SL NO.", "RECEIVE DATE", "INV NO", "INV DATE", "PO NO", "PO DATE", "SUPPLIER NAME",
				"ITEM CODE", "ITEM DESCRIPTION", "QTY", "UOM", "RATE", "AMOUNT", "REMARKS", "CHECKED BY", "INDENT NO.",
				"INDENT DEPT"

		};
		List<List<String>> fillInData = new LinkedList<List<String>>();

		for (IngressLedgerDTO data : translatedData) {
			LinkedList<String> dataSet = new LinkedList<String>();
			dataSet.add(data.getSrNo());
			dataSet.add(data.getRecordDate());
			dataSet.add((data.getInvNumber() == null) ? " " : data.getInvNumber().trim());
			dataSet.add((data.getInvDate() == null) ? " " : data.getInvDate().trim());
			dataSet.add((data.getPoNumber() == null) ? " " : data.getPoNumber().trim());
			dataSet.add((data.getPoDate() == null) ? " " : data.getPoDate().trim());
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
			dataSet.add((data.getDepartment() == null) ? " " : data.getDepartment().trim());
			fillInData.add(dataSet);
		}
		logger.debug("Ingress Ledger fillInData size" + fillInData.size());
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

		String[] cols = { "ISSUED DATE", "ISSUE SLIP", "DEPARTMENT", "ITEM GROUP", "VED", "MACHINE NUMBER", "ENGINEER",
				"APPROVED BY", "CATEGORY", "ITEM CODE", "DESCRIPTION", "QTY", "UOM", "ISSUED BY", "PRICE",
				"TOTAL AAMOUNT" };
		List<List<String>> fillInData = new LinkedList<List<String>>();

		for (EgressLedgerDTO data : translatedData) {
			LinkedList<String> dataSet = new LinkedList<String>();
			dataSet.add(data.getRecordDate());
			dataSet.add(data.getIssueSlipNumber());

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
			dataSet.add(data.getPerUnitPrice());
			dataSet.add(data.getPrice());
			fillInData.add(dataSet);
		}
		logger.debug("Egress Ledger fillInData size" + fillInData.size());
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

	@Override
	public ByteArrayInputStream generateABCAnalysisReport(List<AbcAnalysisInputParameters> boundaries)
			throws HeapFlowReportException {
		if (boundaries == null) {
			throw new HeapFlowReportException("ABC model not defined to formulate data.");
		}
		TreeMap<AbcAnalysisInputParameters, List<AbcAnalysisQResPojo>> analysisRptData = new TreeMap<AbcAnalysisInputParameters, List<AbcAnalysisQResPojo>>(
				Collections.reverseOrder());

		for (AbcAnalysisInputParameters abcAnalysisInputParameters : boundaries) {
			try {
				List<AbcAnalysisQResPojo> data = dao.getAbcAnalysis(abcAnalysisInputParameters.getMinValue(),
						abcAnalysisInputParameters.getMaxValue());
				if (data == null) {
					logger.debug("Type: " + abcAnalysisInputParameters.getCategoryName() + " obtained data size: null");
				} else {
					logger.debug("Type: " + abcAnalysisInputParameters.getCategoryName() + " obtained data size: "
							+ data.size());
				}
				analysisRptData.put(abcAnalysisInputParameters, data);
			} catch (Exception e) {
				logger.error("Could not fetch abc analysis data.", e);
				throw new HeapFlowReportException("Could not fetch abc analysis data.");
			}

		}
		ByteArrayInputStream retWb = null;
		try {
			retWb = util.formulateExcelRptForAbcAnalysis(analysisRptData);
		} catch (IOException e) {
			logger.error("Failed to genearte excel workbook.");
			throw new HeapFlowReportException(e);
		}
		logger.debug("Exiting generateABCAnalysisReport");
		return retWb;
	}

	@Override
	public ByteArrayInputStream getInventoryAgingReport() throws HeapFlowReportException {
		HashMap<String, List<InventoryDO>> analysisRptData = new HashMap<String, List<InventoryDO>>();
		logger.debug("Entered:  getInventoryAgingReport");
		Date oneMonth = util.getCurrentDateMinusDaysAsDate(-30);
		Date sixMonth = util.getCurrentDateMinusDaysAsDate(-180);
		Date oneYear = util.getCurrentDateMinusDaysAsDate(-365);
		Date oneAndHalfYear = util.getCurrentDateMinusDaysAsDate(-546);
		Date twoYear = util.getCurrentDateMinusDaysAsDate(-730);
		Date threeYear = util.getCurrentDateMinusDaysAsDate(-1095);
		Date fiveYear = util.getCurrentDateMinusDaysAsDate(-1825);
		Date now = util.getCurrentDateMinusDaysAsDate(0);
		List<InventoryDO> g_fiveAndThreeYrs = null;
		List<InventoryDO> f_threeAndTwoYrs = null;

		List<InventoryDO> e_twoAndoneAndHalfYrs = null;
		List<InventoryDO> d_oneAndHalfAndOneYrs = null;
		List<InventoryDO> c_oneAndSixMonth = null;
		List<InventoryDO> b_sixMonthAnd1Month = null;
		List<InventoryDO> a_nowAndOneMonth = null;
		try {
			g_fiveAndThreeYrs = dao.getInvModifiedBetween(fiveYear, threeYear);
			f_threeAndTwoYrs = dao.getInvModifiedBetween(threeYear, twoYear);

			e_twoAndoneAndHalfYrs = dao.getInvModifiedBetween(twoYear, oneAndHalfYear);

			d_oneAndHalfAndOneYrs = dao.getInvModifiedBetween(oneAndHalfYear, oneYear);

			c_oneAndSixMonth = dao.getInvModifiedBetween(oneYear, sixMonth);

			b_sixMonthAnd1Month = dao.getInvModifiedBetween(sixMonth, oneMonth);

			a_nowAndOneMonth = dao.getInvModifiedBetween(oneMonth, now);
			analysisRptData.put("07fiveAndThreeYrs", g_fiveAndThreeYrs);
			analysisRptData.put("06threeAndTwoYrs", f_threeAndTwoYrs);
			analysisRptData.put("05twoAndoneAndHalfYrs", e_twoAndoneAndHalfYrs);
			analysisRptData.put("04oneAndHalfAndOneYrs", d_oneAndHalfAndOneYrs);
			analysisRptData.put("03oneAndSixMonth", c_oneAndSixMonth);
			analysisRptData.put("02sixMonthAnd1Month", b_sixMonthAnd1Month);
			analysisRptData.put("01nowAndOneMonth", a_nowAndOneMonth);

		} catch (Exception e) {
			throw new HeapFlowReportException(e);
		}
		logger.debug("Exiting:  getInventoryAgingReport");
		ByteArrayInputStream rpt = null;
		try {
			rpt = util.generateAgingAnalysisRpt(analysisRptData);
		} catch (IOException e) {
			logger.error("Could not generate aging report ", e);
			throw new HeapFlowReportException(e);
		}
		return rpt;
	}

	@Override
	public ByteArrayInputStream getProductSticker(String prodCode) throws HeapFlowReportException {
		if (StringUtils.isEmpty(prodCode)) {
			logger.debug("Cannot operate with null product code as input.");
			return null;
		}
		ByteArrayInputStream retStream = null;
		InventoryItemDO invDo = null;
		try {
			invDo = dao.getInventoryItemswithCode(prodCode);
		} catch (Exception e) {
			logger.error("Could not fetch inventory Item ", e);
			throw new HeapFlowReportException(e);
		}
		if (invDo == null) {
			logger.error("No Inventory Item found in db with code: " + prodCode);
			throw new HeapFlowReportException("No Inventory Item found in db with code: " + prodCode);

		}
		InventoryItemDTO dtoObj = new InventoryItemDTO(invDo);
		HashMap<String, Object> parameters = new HashMap<String, Object>();

		ArrayList<InventoryItemDTO> dataList = new ArrayList<InventoryItemDTO>();
		dataList.add(dtoObj);
		File jrXml = null;
		JasperReport report = null;
		try {
			jrXml = ResourceUtils.getFile("classpath:jasperReports/ProductLabel.jrxml");
			report = JasperCompileManager.compileReport(jrXml.getAbsolutePath());

			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);

			JasperPrint printer = JasperFillManager.fillReport(report, parameters, dataSource);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(printer, outputStream);

			retStream = new ByteArrayInputStream(outputStream.toByteArray());

		} catch (FileNotFoundException | JRException e) {
			logger.error("Report file not found.", e);
			throw new HeapFlowReportException("Report file not found.");
		}

		return retStream;

	}

	@Override
	public ByteArrayInputStream getInventorySummaryReport(String idLike) throws HeapFlowReportException {
		List<InventoryDO> collectedData = null;
		logger.debug("Entered getInventorySummaryReport");
		try {
			collectedData = dao.getInvSummaryWithIdLike(idLike);
			logger.debug("Data collected collectedData size " + collectedData.size());

		} catch (Exception e) {
			logger.error("Could not get getInventorySummaryReport.", e);
			throw new HeapFlowReportException(e);
		}
		logger.debug("Exiting getInventorySummaryReport");
		return util.generateInvSummaryExcel(collectedData);
	}

}
