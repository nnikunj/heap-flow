package com.paranika.erp.heap_flow.services.inventory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.paranika.erp.heap_flow.common.CommonUtil;
import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.dos.InventoryItemDO;
import com.paranika.erp.heap_flow.common.models.dos.MinQuantNotifiationEntityProxy;
import com.paranika.erp.heap_flow.common.models.dtos.InputExcelBook;
import com.paranika.erp.heap_flow.common.models.dtos.InventoryItemDTO;
import com.paranika.erp.heap_flow.common.models.dtos.InventoryItemDescriptions;
import com.paranika.erp.heap_flow.daos.inventory.InventoryItemDaoIx;

@Component
public class InventoryItemsServiceImpl implements InventoryItemsServiceIX {

	@Autowired
	InventoryItemDaoIx inventoryItemDao;
	@Autowired
	CommonUtil util;
	private final Logger logger = LoggerFactory.getLogger(InventoryItemsServiceImpl.class);

	private void validateStockWorkBook(Workbook workbook) throws HeapFlowException {
		// Check for Number of Sheets.
		int sheetNumber = workbook.getNumberOfSheets();
		if (sheetNumber > 1) {
			throw new HeapFlowException("Suplied Excel is having more than one sheet, cannot accept.");
		}
		Sheet s = workbook.getSheetAt(0);
		Row headerRow = s.getRow(0);
		if (headerRow == null) {
			throw new HeapFlowException("No header row found in the sheet.");
		}
		Cell itemCodeCell = headerRow.getCell(0);
		util.headerCellVaueCheck(itemCodeCell, "ITEM CODE");
		util.headerCellVaueCheck(headerRow.getCell(1), "DESCRIPTION");
		util.headerCellVaueCheck(headerRow.getCell(2), "Description 2");
		util.headerCellVaueCheck(headerRow.getCell(3), "Description 3");
		util.headerCellVaueCheck(headerRow.getCell(4), "Description 4");
		util.headerCellVaueCheck(headerRow.getCell(5), "Description 5");
		util.headerCellVaueCheck(headerRow.getCell(6), "Description 6");
		util.headerCellVaueCheck(headerRow.getCell(7), "UOM");

		util.headerCellVaueCheck(headerRow.getCell(8), "Product Group Code");
		util.headerCellVaueCheck(headerRow.getCell(9), "Gen. Prod. Posting Group");
		util.headerCellVaueCheck(headerRow.getCell(10), "CATEGORY");
		util.headerCellVaueCheck(headerRow.getCell(11), "GST Group Code");
		util.headerCellVaueCheck(headerRow.getCell(12), "HSN/SAC Code");
		util.headerCellVaueCheck(headerRow.getCell(13), "Minimum Reserve Quantity");
	}

	@Override
	public void importAndUpdateInventoryItemsList(InputExcelBook ieb) throws HeapFlowException {
		String base64EncodedWorkBook = ieb.getBase64EncodedWorkbook();

		byte[] byteArrayWorkBook = Base64.getDecoder().decode(base64EncodedWorkBook);

		InputStream targetStream = new ByteArrayInputStream(byteArrayWorkBook);

		Workbook workbook = null;

		try {
			workbook = new XSSFWorkbook(targetStream);
			validateStockWorkBook(workbook);
			Sheet inventoryItemSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = inventoryItemSheet.iterator();
			int rowCounter = 0;
			ArrayList<InventoryItemDO> inventoryItemDOs = new ArrayList<InventoryItemDO>();
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				// ignore first header row
				if (rowCounter++ < 1)
					continue;
				InventoryItemDO invnetoryItemDO = extractDataFromRowIntoDo(nextRow);
				if (invnetoryItemDO != null) {
					inventoryItemDOs.add(invnetoryItemDO);
				}

			}
			// processing in batch of 500 since list is huge
			List<List<InventoryItemDO>> batchedList = Lists.partition(inventoryItemDOs, 500);
			for (List<InventoryItemDO> list : batchedList) {
				inventoryItemDao.mergeAll(list);
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new HeapFlowException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HeapFlowException(e);
		}

		finally {
			try {
				if (workbook != null)
					workbook.close();
				if (targetStream != null)
					targetStream.close();
			} catch (IOException e) {
				// Ignore

			}
		}

	}

	private InventoryItemDO extractDataFromRowIntoDo(Row nextRow) {

		String productId = util.getStringDataFromCell(nextRow.getCell(0));
		productId = (productId == null) ? null : productId.trim().toUpperCase();
		if (productId == null || productId.isEmpty()) {
			return null;
		}
		String description = util.getStringDataFromCell(nextRow.getCell(1));
		String description2 = util.getStringDataFromCell(nextRow.getCell(2));
		String description3 = util.getStringDataFromCell(nextRow.getCell(3));
		String description4 = util.getStringDataFromCell(nextRow.getCell(4));
		String description5 = util.getStringDataFromCell(nextRow.getCell(5));
		String description6 = util.getStringDataFromCell(nextRow.getCell(6));

		InventoryItemDescriptions desc = new InventoryItemDescriptions(description, description2, description3,
				description4, description5, description6);
		String strDesc = desc.toJson();

		String baseUnitMeasure = util.getStringDataFromCell(nextRow.getCell(7));
		baseUnitMeasure = (baseUnitMeasure == null) ? null : baseUnitMeasure.trim();

		String productCodeGroup = util.getStringDataFromCell(nextRow.getCell(8));
		productCodeGroup = (productCodeGroup == null) ? null : productCodeGroup.trim().toUpperCase();

		String genProductPostingGrp = util.getStringDataFromCell(nextRow.getCell(9));
		genProductPostingGrp = (genProductPostingGrp == null) ? null : genProductPostingGrp.trim().toUpperCase();

		String itemcategoryCode = util.getStringDataFromCell(nextRow.getCell(10));
		itemcategoryCode = (itemcategoryCode == null) ? null : itemcategoryCode.trim().toUpperCase();

		String gstGrpCode = util.getStringDataFromCell(nextRow.getCell(11));
		gstGrpCode = (gstGrpCode == null) ? null : gstGrpCode.trim().toUpperCase();

		String hsnCode = (util.getStringDataFromCell(nextRow.getCell(12)));
		hsnCode = (hsnCode == null) ? null : hsnCode.trim();

		String minReserveQuant = (util.getStringDataFromCell(nextRow.getCell(13)));
		Double minRQuant = 0.0;
		try {
			minRQuant = Double.parseDouble(minReserveQuant);
			logger.debug("Minimum Reserve Quantity set to " + minRQuant);
		} catch (NumberFormatException e) {
			logger.warn("Could not parse minimum Reserve Quant for item: " + productId + " defaulting it to 0.0");
		}
		InventoryItemDO inventoryItemDO = new InventoryItemDO();

		inventoryItemDO.setInventoryItemCode(productId);
		inventoryItemDO.setBaseUnitMeasure(baseUnitMeasure);
		inventoryItemDO.setDescriptions(strDesc);
		inventoryItemDO.setGenProductPostingGrp(genProductPostingGrp);
		inventoryItemDO.setGstGrpCode(gstGrpCode);
		inventoryItemDO.setHsnSacCode(hsnCode);

		inventoryItemDO.setItemCategoryCode(itemcategoryCode);
		inventoryItemDO.setProductGrpCode(productCodeGroup);
		inventoryItemDO.setReOrderQuant(0.00);
		inventoryItemDO.setReserveQuantAlert(minRQuant);
		inventoryItemDO.setMaxOrderQuant(0.00);

		return inventoryItemDO;
	}

	@Override
	public List<InventoryItemDTO> getPagedInventoryItemList(int startRecord, int pageSize) throws HeapFlowException {
		List<InventoryItemDO> retList = null;
		ArrayList<InventoryItemDTO> convertedDataList = new ArrayList<InventoryItemDTO>();
		try {
			retList = inventoryItemDao.getAllInventoryItemsWithPagination(startRecord, pageSize);
			for (InventoryItemDO inventoryItemDO : retList) {
				InventoryItemDTO convertedObj = new InventoryItemDTO(inventoryItemDO);
				convertedDataList.add(convertedObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new HeapFlowException(e);
		}

		return convertedDataList;
	}

	@Override
	public List<InventoryItemDTO> getItemListWithIdLike(String idLike) throws HeapFlowException {
		List<InventoryItemDO> retList = null;
		ArrayList<InventoryItemDTO> convertedDataList = new ArrayList<InventoryItemDTO>();
		try {
			retList = inventoryItemDao.getAllInventoryItemsLikeItemCode(idLike);
			for (InventoryItemDO inventoryItemDO : retList) {
				InventoryItemDTO convertedObj = new InventoryItemDTO(inventoryItemDO);
				convertedDataList.add(convertedObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new HeapFlowException(e);
		}

		return convertedDataList;
	}

	@Override
	public InventoryItemDTO getItemWithProdCode(String prodCode) throws HeapFlowException {
		InventoryItemDO retItem = null;
		InventoryItemDTO convertedObj = null;
		try {
			retItem = inventoryItemDao.getInventoryItemswithCode(prodCode);
			if (retItem != null)
				convertedObj = new InventoryItemDTO(retItem);

		} catch (Exception e) {
			e.printStackTrace();
			throw new HeapFlowException(e);
		}

		return convertedObj;
	}

	@Override
	public Page<InventoryItemDTO> getPagedItemsWithIdLike(String idLike, Pageable paging) throws HeapFlowException {
		logger.debug("Service call getPagedItemsWithIdLike");
		Page<InventoryItemDO> collectedData = null;
		Page<InventoryItemDTO> dtoPage = null;

		try {
			collectedData = inventoryItemDao.getPagedItemsWithIdLike(idLike, paging);

			dtoPage = collectedData.map(obj -> new InventoryItemDTO(obj));

		} catch (Exception e) {
			logger.error("getPagedItemsWithIdLike failed", e);
		}
		logger.debug("Service call exit getPagedItemsWithIdLike");
		return dtoPage;
	}

	@Override
	public Page<MinQuantNotifiationEntityProxy> getPagedReservedItems(Pageable paging) throws HeapFlowException {

		logger.debug("Service call getPagedReservedItems");
		Page<MinQuantNotifiationEntityProxy> collectedData = null;

		try {
			collectedData = inventoryItemDao.getPagedReservedItems(paging);

		} catch (Exception e) {
			logger.error("getPagedReservedItems failed", e);
		}
		logger.debug("Service call exit getPagedReservedItems");
		return collectedData;
	}

	@Override
	public InventoryItemDO persistItem(InventoryItemDTO data) throws HeapFlowException {
		if (data == null) {
			logger.error("Cannot operate with null InventoryItemDTO.");
			throw new HeapFlowException("Cannot operate with null InventoryItemDTO.");

		} else if (data.getInventoryItemCode() == null || data.getInventoryItemCode().isEmpty()) {
			logger.error("Cannot operate with null product code");
			throw new HeapFlowException("Cannot operate with null product code");
		}
		InventoryItemDO persistedObj = null;
		try {
			persistedObj = inventoryItemDao.addOrUpdate(data.getDoObj());
		} catch (Exception e) {
			logger.error("Could not save obj to Db.", e);
			throw new HeapFlowException(e);
		}
		return persistedObj;
	}

}
