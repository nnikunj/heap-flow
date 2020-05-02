package com.paranika.erp.heap_flow.common;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {

	public String getStringDataFromCell(Cell cell) {

		String retVal = null;
		if (cell != null) {
			switch (cell.getCellType()) {

			case STRING:
				retVal = cell.getStringCellValue();
				break;
			case BOOLEAN:
				retVal = String.valueOf(cell.getBooleanCellValue());
				break;
			case NUMERIC:
				retVal = String.valueOf(cell.getNumericCellValue());
			default:
				retVal = null;
				break;
			}
		}
		return retVal;

	}
}