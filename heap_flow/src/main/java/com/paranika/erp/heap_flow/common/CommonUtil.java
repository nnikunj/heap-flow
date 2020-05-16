package com.paranika.erp.heap_flow.common;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {
	// private final Logger logger =
	// LoggerFactory.getLogger(InventoryServiceImpl.class);

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
}