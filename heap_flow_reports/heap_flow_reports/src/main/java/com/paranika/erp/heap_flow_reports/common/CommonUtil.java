package com.paranika.erp.heap_flow_reports.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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

	public ByteArrayInputStream genrateExcel(List<List<String>> data, String[] cols, String sheetName)
			throws IOException {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream(); Workbook workbook = new XSSFWorkbook();) {
			// CreationHelper createHelper = workbook.getCreationHelper();

			Sheet sheet = workbook.createSheet(sheetName);

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

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
}
