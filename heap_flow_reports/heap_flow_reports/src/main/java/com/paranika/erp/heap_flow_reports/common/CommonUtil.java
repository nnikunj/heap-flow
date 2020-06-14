package com.paranika.erp.heap_flow_reports.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.paranika.erp.heap_flow_reports.common.exceptions.HeapFlowReportException;
import com.paranika.erp.heap_flow_reports.common.models.dos.AbcAnalysisQResPojo;
import com.paranika.erp.heap_flow_reports.common.models.dos.InventoryDO;
import com.paranika.erp.heap_flow_reports.common.models.dos.InventoryItemDO;
import com.paranika.erp.heap_flow_reports.common.models.dtos.AbcAnalysisInputParameters;
import com.paranika.erp.heap_flow_reports.common.models.dtos.InventoryItemDescriptions;

@Component
public class CommonUtil {
	// private final Logger logger =
	// LoggerFactory.getLogger(InventoryServiceImpl.class);
	private final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	public String getStringDataFromCell(Cell cell) {

		String retVal = null;
		if (cell != null) {
			CellType type = cell.getCellType();
			// logger.debug("type: " + type.toString());
			switch (type) {

			case STRING:
				retVal = cell.getStringCellValue();
				break;
			case BOOLEAN:
				retVal = String.valueOf(cell.getBooleanCellValue());
				break;
			case NUMERIC:
				retVal = String.valueOf(cell.getNumericCellValue());
				break;
			default:
				retVal = null;
				break;
			}
		}
		// logger.debug("\nretVal: \n" + retVal);
		return retVal;

	}

	private void populateSheet(Workbook workbook, Sheet sheet, List<List<String>> data, String[] cols) {
		for (int index = 0; index < cols.length; index++) {
			sheet.autoSizeColumn(index);

		}
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 16);
		headerFont.setColor(IndexedColors.WHITE.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setFillBackgroundColor(IndexedColors.ROYAL_BLUE.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.BIG_SPOTS);
		// Row for Header
		Row headerRow = sheet.createRow(0);

		// Header
		for (int col = 0; col < cols.length; col++) {
			Cell cell = headerRow.createCell(col);
			cell.setCellValue(cols[col]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowIdx = 1;
		for (int i = 0; i < data.size(); i++) {

			List<String> actualdata = data.get(i);
			// logger.debug("actualdata" + actualdata);
			Row row = sheet.createRow(rowIdx++);
			for (int j = 0; j < actualdata.size(); j++) {
				row.createCell(j).setCellValue(actualdata.get(j));
			}
		}
	}

	public ByteArrayInputStream genrateExcel(List<List<String>> data, String[] cols, String sheetName)
			throws IOException {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream(); Workbook workbook = new XSSFWorkbook();) {
			// CreationHelper createHelper = workbook.getCreationHelper();

			Sheet sheet = workbook.createSheet(sheetName);

			for (int index = 0; index < cols.length; index++) {
				sheet.autoSizeColumn(index);

			}
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 16);
			headerFont.setColor(IndexedColors.WHITE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setFillBackgroundColor(IndexedColors.ROYAL_BLUE.getIndex());
			headerCellStyle.setFillPattern(FillPatternType.BIG_SPOTS);
			// Row for Header
			Row headerRow = sheet.createRow(0);

			// Header
			for (int col = 0; col < cols.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(cols[col]);
				cell.setCellStyle(headerCellStyle);
			}

			int rowIdx = 1;
			for (int i = 0; i < data.size(); i++) {

				List<String> actualdata = data.get(i);
				// logger.debug("actualdata" + actualdata);
				Row row = sheet.createRow(rowIdx++);
				for (int j = 0; j < actualdata.size(); j++) {
					row.createCell(j).setCellValue(actualdata.get(j));
				}
			}
			workbook.write(out);
			logger.debug("workbook created and returing from genrateExcel.");
			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	public Date getCurrentDateMinusDaysAsDate(int days) {
		logger.debug("days " + days);
		Date retDate = null;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, days);
		retDate = calendar.getTime();
		logger.debug("Calculate date: " + retDate);
		return retDate;
	}

	public Date extractDateFromInput(String inputDate, short offsetDays) {
		logger.debug("inputDate " + inputDate);
		Date retDate = null;
		DateFormat dateFormat = new SimpleDateFormat(AppConstants.COMMON_APP_DATE_FORMAT);
		if (!StringUtils.isEmpty(inputDate)) {
			try {
				retDate = dateFormat.parse(inputDate);
				logger.debug(" date paased parsed.");
			} catch (ParseException e) {
				logger.warn("Could not parse date.", e);
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, offsetDays);
				retDate = cal.getTime();
			}
		} else {
			logger.debug("Empty date paased.");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, offsetDays);
			retDate = cal.getTime();
		}
		logger.debug("Retrun Date " + retDate);
		return retDate;

	}

	public ByteArrayInputStream formulateExcelRptForAbcAnalysis(
			TreeMap<AbcAnalysisInputParameters, List<AbcAnalysisQResPojo>> analysisRptData) throws IOException {

		String[] cols = { "ITEM CODE", "ITEM CATEGORY", "DESCRIPTION", "TOTAL QUANTITY", "UOM", "TOTAL VALUE" };

		try (ByteArrayOutputStream out = new ByteArrayOutputStream(); Workbook workbook = new XSSFWorkbook();) {
			// CreationHelper createHelper = workbook.getCreationHelper();

			Set<AbcAnalysisInputParameters> keySet = analysisRptData.keySet();
			for (AbcAnalysisInputParameters abcAnalysisInputParameters : keySet) {

				Sheet sheet = workbook.createSheet(abcAnalysisInputParameters.getCategoryName() + " From "
						+ abcAnalysisInputParameters.getMinValue() + " to " + abcAnalysisInputParameters.getMaxValue());
				List<AbcAnalysisQResPojo> categoryResult = analysisRptData.get(abcAnalysisInputParameters);
				List<List<String>> fillInData = new LinkedList<List<String>>();

				for (AbcAnalysisQResPojo data : categoryResult) {
					LinkedList<String> dataSet = new LinkedList<String>();
					dataSet.add(data.getItemCode());
					dataSet.add(data.getCategory());
					dataSet.add(data.getDescriptions());

					dataSet.add(String.valueOf(data.getTotalQuantity()));
					dataSet.add(data.getUnitOfMeasurement());
					dataSet.add(String.valueOf(data.getTotalValue()));

					fillInData.add(dataSet);
				}
				populateSheet(workbook, sheet, fillInData, cols);
			}

			workbook.write(out);
			logger.debug("workbook created and returing from genrateExcel for formulateExcelRptForAbcAnalysis.");
			return new ByteArrayInputStream(out.toByteArray());
		}

	}

	public ByteArrayInputStream generateAgingAnalysisRpt(Map<String, List<InventoryDO>> analysisRptData)
			throws IOException {
		String[] cols = { "ITEM CODE", "CATEGORY", "DESCRIPTION1", "DESCRIPTION2", "TYPE", "QTY", "UOM",
				"AVERAGE UNIT PRICE", "TOTAL VALUE" };
		try (ByteArrayOutputStream out = new ByteArrayOutputStream(); Workbook workbook = new XSSFWorkbook();) {
			// CreationHelper createHelper = workbook.getCreationHelper();

			Set<String> keySet = analysisRptData.keySet();
			ArrayList<String> allKeys = new ArrayList<String>();
			allKeys.addAll(keySet);
			Collections.sort(allKeys);
			for (String key : allKeys) {
				List<InventoryDO> dataRows = analysisRptData.get(key);

				Sheet sheet = workbook.createSheet(key);

				List<List<String>> fillInData = new LinkedList<List<String>>();

				for (InventoryDO data : dataRows) {
					LinkedList<String> dataSet = new LinkedList<String>();
					String itemCode = AppConstants.NO_DATA_FOUND_MSG;
					String category = AppConstants.NO_DATA_FOUND_MSG;
					String desc1 = AppConstants.NO_DATA_FOUND_MSG;
					String desc2 = AppConstants.NO_DATA_FOUND_MSG;
					String uom = AppConstants.NO_DATA_FOUND_MSG;
					InventoryItemDO item = data.getItem();
					if (item != null) {
						InventoryItemDescriptions descriptions = InventoryItemDescriptions
								.fromJson(item.getDescriptions());
						itemCode = item.getInventoryItemCode();
						category = item.getItemCategoryCode();
						if (descriptions != null) {
							desc1 = descriptions.getDescription();
							desc2 = descriptions.getDescription2();
						}
						uom = item.getBaseUnitMeasure();
					}
					String type = data.getType().getTypeName();
					String qty = String.valueOf(data.getQuantity());
					String avgUnitPr = String.valueOf(data.getAverageUnitPrice());
					String tot = String.valueOf(data.getValue());
					dataSet.add(itemCode);
					dataSet.add(category);
					dataSet.add(desc1);
					dataSet.add(desc2);
					dataSet.add(type);
					dataSet.add(qty);
					dataSet.add(uom);
					dataSet.add(avgUnitPr);
					dataSet.add(tot);
					fillInData.add(dataSet);
				}
				populateSheet(workbook, sheet, fillInData, cols);
			}

			workbook.write(out);
			logger.debug("workbook created and returing from genrateExcel for generateAgingAnalysisRpt.");
			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	public ByteArrayInputStream generateInvSummaryExcel(List<InventoryDO> collectedData)
			throws HeapFlowReportException {

		String[] cols = { "ITEM CODE", "CATEGORY", "DESCRIPTION1", "DESCRIPTION2", "TYPE", "QTY", "UOM",
				"AVERAGE UNIT PRICE", "TOTAL VALUE" };
		List<List<String>> fillInData = new LinkedList<List<String>>();

		for (InventoryDO data : collectedData) {
			LinkedList<String> dataSet = new LinkedList<String>();
			String itemCode = AppConstants.NO_DATA_FOUND_MSG;
			String category = AppConstants.NO_DATA_FOUND_MSG;
			String desc1 = AppConstants.NO_DATA_FOUND_MSG;
			String desc2 = AppConstants.NO_DATA_FOUND_MSG;
			String uom = AppConstants.NO_DATA_FOUND_MSG;
			InventoryItemDO item = data.getItem();
			if (item != null) {
				InventoryItemDescriptions descriptions = InventoryItemDescriptions.fromJson(item.getDescriptions());
				itemCode = item.getInventoryItemCode();
				category = item.getItemCategoryCode();
				if (descriptions != null) {
					desc1 = descriptions.getDescription();
					desc2 = descriptions.getDescription2();
				}
				uom = item.getBaseUnitMeasure();
			}
			String type = data.getType().getTypeName();
			String qty = String.valueOf(data.getQuantity());
			String avgUnitPr = String.valueOf(data.getAverageUnitPrice());
			String tot = String.valueOf(data.getValue());
			dataSet.add(itemCode);
			dataSet.add(category);
			dataSet.add(desc1);
			dataSet.add(desc2);
			dataSet.add(type);
			dataSet.add(qty);
			dataSet.add(uom);
			dataSet.add(avgUnitPr);
			dataSet.add(tot);
			fillInData.add(dataSet);
		}
		logger.debug("Egress Ledger fillInData size" + fillInData.size());
		ByteArrayInputStream stream = null;
		try {
			stream = genrateExcel(fillInData, cols, "Stock Reports");
		} catch (IOException e) {

			logger.error("Excel genartion failed.", e);
			throw new HeapFlowReportException(e);
		}

		return stream;
	}
}