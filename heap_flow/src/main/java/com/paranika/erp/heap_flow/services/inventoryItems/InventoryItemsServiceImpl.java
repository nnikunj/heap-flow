package com.paranika.erp.heap_flow.services.inventoryItems;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.paranika.erp.heap_flow.common.CommonUtil;
import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.InputExcelBook;
import com.paranika.erp.heap_flow.common.models.InventoryItemDescriptions;
import com.paranika.erp.heap_flow.common.models.InventoryItemDisplayDO;
import com.paranika.erp.heap_flow.common.models.dos.InventoryItemDO;
import com.paranika.erp.heap_flow.daos.inventoryItems.InventoryItemDaoIx;

@Component
public class InventoryItemsServiceImpl implements InventoryItemsServiceIX {

	@Autowired
	InventoryItemDaoIx inventoryItemDao;
	@Autowired
	CommonUtil util;

	@Override
	public void importAndUpdateInventoryItemsList(InputExcelBook ieb) throws HeapFlowException {
		String base64EncodedWorkBook = ieb.getBase64EncodedWorkbook();

		byte[] byteArrayWorkBook = Base64.getDecoder().decode(base64EncodedWorkBook);

		InputStream targetStream = new ByteArrayInputStream(byteArrayWorkBook);

		Workbook workbook = null;

		try {
			workbook = new XSSFWorkbook(targetStream);
			Sheet inventoryItemSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = inventoryItemSheet.iterator();
			int rowCounter = 0;
			ArrayList<InventoryItemDO> inventoryItemDOs = new ArrayList<InventoryItemDO>();
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				// ignore first row
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
		String description6 = util.getStringDataFromCell(nextRow.getCell(15));

		InventoryItemDescriptions desc = new InventoryItemDescriptions(description, description2, description3,
				description4, description5, description6);
		String strDesc = desc.toJson();

		String baseUnitMeasure = util.getStringDataFromCell(nextRow.getCell(6));
		baseUnitMeasure = (baseUnitMeasure == null) ? null : baseUnitMeasure.trim();

		String productCodeGroup = util.getStringDataFromCell(nextRow.getCell(9));
		productCodeGroup = (productCodeGroup == null) ? null : productCodeGroup.trim().toUpperCase();

		String genProductPostingGrp = util.getStringDataFromCell(nextRow.getCell(10));
		genProductPostingGrp = (genProductPostingGrp == null) ? null : genProductPostingGrp.trim().toUpperCase();

		String itemcategoryCode = util.getStringDataFromCell(nextRow.getCell(11));
		itemcategoryCode = (itemcategoryCode == null) ? null : itemcategoryCode.trim().toUpperCase();

		String gstGrpCode = util.getStringDataFromCell(nextRow.getCell(12));
		gstGrpCode = (gstGrpCode == null) ? null : gstGrpCode.trim().toUpperCase();

		String hsnCode = (util.getStringDataFromCell(nextRow.getCell(13)));
		hsnCode = (hsnCode == null) ? null : hsnCode.trim();

		InventoryItemDO inventoryItemDO = new InventoryItemDO();

		inventoryItemDO.setInventoryItemCode(productId);
		inventoryItemDO.setBaseUnitMeasure(baseUnitMeasure);
		inventoryItemDO.setDescriptions(strDesc);
		inventoryItemDO.setGenProductPostingGrp(genProductPostingGrp);
		inventoryItemDO.setGstGrpCode(gstGrpCode);
		inventoryItemDO.setHsnSacCode(hsnCode);

		inventoryItemDO.setItemCategoryCode(itemcategoryCode);
		inventoryItemDO.setProductGrpCode(productCodeGroup);

		// System.out.println("\n----------------------\n" + inventoryItemDO +
		// "\n----------------------\n");
		return inventoryItemDO;
	}

	@Override
	public List<InventoryItemDisplayDO> getPagedInventoryItemList(int startRecord, int pageSize)
			throws HeapFlowException {
		List<InventoryItemDO> retList = null;
		ArrayList<InventoryItemDisplayDO> convertedDataList = new ArrayList<InventoryItemDisplayDO>();
		try {
			retList = inventoryItemDao.getAllInventoryItemsWithPagination(startRecord, pageSize);
			for (InventoryItemDO inventoryItemDO : retList) {
				InventoryItemDisplayDO convertedObj = new InventoryItemDisplayDO(inventoryItemDO);
				convertedDataList.add(convertedObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new HeapFlowException(e);
		}

		return convertedDataList;
	}

	@Override
	public List<InventoryItemDisplayDO> getItemListWithIdLike(String idLike) throws HeapFlowException {
		List<InventoryItemDO> retList = null;
		ArrayList<InventoryItemDisplayDO> convertedDataList = new ArrayList<InventoryItemDisplayDO>();
		try {
			retList = inventoryItemDao.getAllInventoryItemsLikeItemCode(idLike);
			for (InventoryItemDO inventoryItemDO : retList) {
				InventoryItemDisplayDO convertedObj = new InventoryItemDisplayDO(inventoryItemDO);
				convertedDataList.add(convertedObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new HeapFlowException(e);
		}

		return convertedDataList;
	}
}
