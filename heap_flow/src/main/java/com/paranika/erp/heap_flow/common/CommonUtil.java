package com.paranika.erp.heap_flow.common;

import java.util.Base64;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;

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

	public String getDecodedTrimmedParameter(String param, String strIsDecodingRequired) {
		String decodedValue = null;
		boolean isDecodingRequired = true;
		if (!StringUtils.isEmpty(param)) {
			if (!StringUtils.isEmpty(strIsDecodingRequired)) {
				try {
					isDecodingRequired = Boolean.parseBoolean(strIsDecodingRequired);
				} catch (Exception e) {
					isDecodingRequired = true;
					logger.warn("Could not parse strIsDecodingRequired to boolean defaulting it to true.");
				}
			} else {
				isDecodingRequired = true;
				logger.debug("Using default value for isDecodingRequired = true");
			}
			if (isDecodingRequired) {
				decodedValue = new String(Base64.getDecoder().decode(param.trim()));
				logger.debug("decodedValue:" + decodedValue);
			} else {
				decodedValue = param.trim();
				logger.debug("No need to decode param, returning trimmed value. decodedValue:" + decodedValue);
			}
		}
		return decodedValue;
	}

	public void headerCellVaueCheck(Cell c, String value) throws HeapFlowException {
		logger.debug("headerCellVaueCheck invoked.");
		String fetchedCellValue = getStringDataFromCell(c);
		logger.debug("fetchedCellValue: " + fetchedCellValue);
		logger.debug("supplied value: " + value);
		if (fetchedCellValue == null || !fetchedCellValue.equalsIgnoreCase(value)) {
			throw new HeapFlowException("Expected value of header: " + value + " found value: " + fetchedCellValue);
		}
	}
}
