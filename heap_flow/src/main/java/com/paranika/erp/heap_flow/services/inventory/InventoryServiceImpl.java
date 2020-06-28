package com.paranika.erp.heap_flow.services.inventory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
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

import com.paranika.erp.heap_flow.common.AppConstants;
import com.paranika.erp.heap_flow.common.CommonUtil;
import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.dos.EgressLedgerDO;
import com.paranika.erp.heap_flow.common.models.dos.IngressLedgerDO;
import com.paranika.erp.heap_flow.common.models.dos.InventoryDO;
import com.paranika.erp.heap_flow.common.models.dos.InventoryItemDO;
import com.paranika.erp.heap_flow.common.models.dos.InventoryTypeDO;
import com.paranika.erp.heap_flow.common.models.dos.MachineDO;
import com.paranika.erp.heap_flow.common.models.dos.VendorDO;
import com.paranika.erp.heap_flow.common.models.dtos.AcceptingMaterialData;
import com.paranika.erp.heap_flow.common.models.dtos.EgressDTO;
import com.paranika.erp.heap_flow.common.models.dtos.IngressDTO;
import com.paranika.erp.heap_flow.common.models.dtos.InputExcelBook;
import com.paranika.erp.heap_flow.common.models.dtos.InventoryItemDescriptions;
import com.paranika.erp.heap_flow.common.models.dtos.InventorySummaryDTO;
import com.paranika.erp.heap_flow.common.models.dtos.IssuingMaterialDataDTO;
import com.paranika.erp.heap_flow.common.models.dtos.MaterialData;
import com.paranika.erp.heap_flow.common.models.dtos.OutgoingMaterialDataDTO;
import com.paranika.erp.heap_flow.daos.defaultProviders.InventoriesRepository;
import com.paranika.erp.heap_flow.daos.defaultProviders.InventoryTypesRepository;
import com.paranika.erp.heap_flow.daos.inventory.InventoryDaoIX;
import com.paranika.erp.heap_flow.daos.inventory.InventoryItemDaoIx;
import com.paranika.erp.heap_flow.daos.machines.MachinesDaoIx;
import com.paranika.erp.heap_flow.daos.vendors.VendorsDaoIx;

@Component
public class InventoryServiceImpl implements InventoryServiceIX {

	@Autowired
	VendorsDaoIx vendorDao;
	@Autowired
	InventoryItemDaoIx inventoryItemDao;

	@Autowired
	MachinesDaoIx machineDao;

	@Autowired
	InventoryTypesRepository inventoryTypesRepository;

	@Autowired
	InventoryDaoIX inventoryDao;

	@Autowired
	InventoriesRepository invRepo;

	@Autowired
	CommonUtil util;

	private final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

	@Override
	public void issueInventory(IssuingMaterialDataDTO outgoingMaterials) throws HeapFlowException {
		Date recordDate = null;
		String strDate = (outgoingMaterials.getRecordDate() == null) ? null : outgoingMaterials.getRecordDate().trim();
		try {
			logger.debug("strDate " + strDate);
			recordDate = (new SimpleDateFormat(AppConstants.COMMON_APP_DATE_FORMAT)).parse(strDate);
			logger.debug("recordDate " + recordDate);
		} catch (ParseException e) {
			logger.debug("Failed parsing of date", e);

			// Ignore and take current date
			recordDate = new Date();
		}
		String empID = outgoingMaterials.getIssuedViaEmp();
		String machineCode = outgoingMaterials.getMachineCode();
		String approvedBy = outgoingMaterials.getApprovedBy();
		String issuedBy = outgoingMaterials.getLoggedInUser();
		String issuedForDept = outgoingMaterials.getIssuedForDept();
		MachineDO machineDO = null;

		try {
			machineDO = machineDao.getMachinewithCode(machineCode);
		} catch (Exception e) {
			logger.error("Could find any Machine with code: " + machineCode, e);
			throw new HeapFlowException("Could find any Machine with code: " + machineCode);
		}
		if (machineDO == null) {
			throw new HeapFlowException("Could find any Machine with code: " + machineCode);
		}
		String issueSlipNumber = outgoingMaterials.getIssueSlipNumber();
		logger.debug("Issuing materials for issueSlipNumber: " + issueSlipNumber);
		ArrayList<EgressLedgerDO> ledgerList = new ArrayList<EgressLedgerDO>();

		List<OutgoingMaterialDataDTO> outgoinMats = outgoingMaterials.getOutgoingItemsList();
		for (OutgoingMaterialDataDTO outgoingMaterialDataDTO : outgoinMats) {
			String productCode = outgoingMaterialDataDTO.getProductCode();

			InventoryItemDO inventoryItemDO = null;
			try {
				inventoryItemDO = inventoryItemDao.getInventoryItemswithCode(productCode);
			} catch (Exception e) {
				e.printStackTrace();
				throw new HeapFlowException("Could not find product with code " + productCode);
			}

			InventoryTypeDO inventoryTypeDO;

			inventoryTypeDO = inventoryTypesRepository
					.findInventoryTypeWithName(outgoingMaterialDataDTO.getInventoryType());
			if (inventoryTypeDO == null) {
				throw new HeapFlowException(
						"Could not find InventoryTypeDO with name " + outgoingMaterialDataDTO.getInventoryType());
			}

			InventoryDO inventory = invRepo.findInventoryWithProductAndType(inventoryItemDO, inventoryTypeDO);
			if (inventory == null) {
				throw new HeapFlowException("Stock does not exists for product with code: " + productCode);
			} else if (inventory.getQuantity() < outgoingMaterialDataDTO.getQuantity()) {
				throw new HeapFlowException("Insufficient Stock for product with code: " + productCode
						+ ", We only have " + inventory.getQuantity() + " " + inventoryItemDO.getBaseUnitMeasure()
						+ " and demand is for " + outgoingMaterialDataDTO.getQuantity() + " "
						+ inventoryItemDO.getBaseUnitMeasure());
			} else {
				EgressLedgerDO ledgerDO = new EgressLedgerDO();
				Double priceOutgoingMat = inventory.getAverageUnitPrice() * outgoingMaterialDataDTO.getQuantity();
				logger.debug("Calculated price for outgoing material: " + inventoryItemDO.getInventoryItemCode()
						+ " is " + priceOutgoingMat);
				ledgerDO.setClassificationCategory(outgoingMaterialDataDTO.getClassification());
				ledgerDO.setConsumingMachine(machineDO);
				ledgerDO.setInventoryType(inventoryTypeDO);
				ledgerDO.setIssuedTo(empID);
				ledgerDO.setOutgoingMaterial(inventoryItemDO);
				ledgerDO.setOutgoingQuantity(outgoingMaterialDataDTO.getQuantity());
				ledgerDO.setRecordDate(recordDate);
				ledgerDO.setIssuedForDept(issuedForDept);
				ledgerDO.setIssuedBy(issuedBy);
				ledgerDO.setApprovedBy(approvedBy);
				ledgerDO.setIssueSlipNumber(issueSlipNumber);
				ledgerDO.setOutgoingMaterialPrice(priceOutgoingMat);
				ledgerList.add(ledgerDO);

			}
		}
		try {
			inventoryDao.persistAllEgressLedgers(ledgerList);
		} catch (Exception e) {

			e.printStackTrace();
			throw new HeapFlowException(e);
		}
	}

	@Override
	public void acceptInventory(AcceptingMaterialData incomingMaterials) throws HeapFlowException {

		Date recordDate = null;
		Date poDate = null;
		Date invoiceDate = null;
		String strDate = (incomingMaterials.getRecordDate() == null) ? null : incomingMaterials.getRecordDate().trim();
		try {
			recordDate = (new SimpleDateFormat(AppConstants.COMMON_APP_DATE_FORMAT)).parse(strDate);
		} catch (ParseException e) {

			e.printStackTrace();
			// Ignore and take current date
			recordDate = new Date();
		}
		String strInvoiceDate = incomingMaterials.getInvoiceDate();
		try {
			invoiceDate = (new SimpleDateFormat(AppConstants.COMMON_APP_DATE_FORMAT)).parse(strInvoiceDate);
		} catch (ParseException e) {
			logger.warn("Could not parse invoiceDate", strInvoiceDate);

		}
		String strPoDate = incomingMaterials.getPoDate();
		try {
			poDate = (new SimpleDateFormat(AppConstants.COMMON_APP_DATE_FORMAT)).parse(strPoDate);
		} catch (ParseException e) {
			logger.warn("Could not parse poDate", strPoDate);

		}
		String grn = incomingMaterials.getGrn();
		String invoice = incomingMaterials.getInvoice();
		String department = incomingMaterials.getDepartment();
		String vendorCode = incomingMaterials.getVendorCode();
		String loggedInUser = incomingMaterials.getLoggedInUser();
		String intentNumber = incomingMaterials.getIntentNumber();
		String poNumber = incomingMaterials.getPoNumber();

		VendorDO vendor = null;
		try {
			vendor = vendorDao.getVendorwithCode(vendorCode);
		} catch (Exception e) {
			logger.error("Could find any vendor with code: " + vendorCode, e);
			throw new HeapFlowException("Could find any vendor with code: " + vendorCode);
		}
		if (vendor == null) {
			throw new HeapFlowException("Could find any Vendor with code: " + vendorCode);
		}
		ArrayList<IngressLedgerDO> ledgerList = new ArrayList<IngressLedgerDO>();

		List<MaterialData> items = incomingMaterials.getIncomingItemsList();

		for (MaterialData materialData : items) {
			String productCode = materialData.getProductCode();
			InventoryItemDO inventoryItemDO = null;
			try {
				inventoryItemDO = inventoryItemDao.getInventoryItemswithCode(productCode);
			} catch (Exception e) {
				e.printStackTrace();
				throw new HeapFlowException("Could not find product with code " + productCode);
			}

			InventoryTypeDO inventoryTypeDO;

			inventoryTypeDO = inventoryTypesRepository.findInventoryTypeWithName(materialData.getInventoryType());
			if (inventoryTypeDO == null) {
				throw new HeapFlowException(
						"Could not find InventoryTypeDO with name " + materialData.getInventoryType());
			}
			IngressLedgerDO ledgerDO = new IngressLedgerDO();
			if (!inventoryTypeDO.isConsideredForValuation()) {
				// We will not take price for such items
				ledgerDO.setPricePerUnit(0.0);
			} else {
				ledgerDO.setPricePerUnit(materialData.getPricePerUnit());
			}

			ledgerDO.setClassificationCategory(materialData.getClassification());
			ledgerDO.setGrnNumber(grn);
			ledgerDO.setIncomingMaterial(inventoryItemDO);
			ledgerDO.setIncomingQuantity(materialData.getQuantity());
			ledgerDO.setDepartment(department);
			ledgerDO.setInvoiceNumber(invoice);
			ledgerDO.setRecordDate(recordDate);
			ledgerDO.setInventoryType(inventoryTypeDO);
			ledgerDO.setVendor(vendor);
			ledgerDO.setIntentNumber(intentNumber);
			ledgerDO.setPoNumber(poNumber);
			ledgerDO.setPoDate(poDate);
			ledgerDO.setInvoiceDate(invoiceDate);
			ledgerDO.setMaterialAcceptedBy(loggedInUser);
			ledgerList.add(ledgerDO);
		}
		try {
			inventoryDao.persistAllIngressLedgers(ledgerList);
		} catch (Exception e) {

			e.printStackTrace();
			throw new HeapFlowException(e);
		}
	}

	@Override
	public void updatePreviousStock(InputExcelBook book) throws HeapFlowException {

		String base64EncodedWorkBook = book.getBase64EncodedWorkbook();
		logger.debug("Formulated base64EncodedWorkBook");
		byte[] byteArrayWorkBook = Base64.getDecoder().decode(base64EncodedWorkBook);
		logger.debug("Formulated byteArrayWorkBook");
		InputStream targetStream = new ByteArrayInputStream(byteArrayWorkBook);

		logger.debug((targetStream == null) ? "Could not create targetStream " : "Formulated targetStream");
		Workbook workbook = null;

		try {
			workbook = new XSSFWorkbook(targetStream);
			validateStockWorkBook(workbook);
			Sheet inventorySheet = workbook.getSheetAt(0);
			if (inventorySheet == null) {
				throw new HeapFlowException("Could not find sheet");
			}
			logger.debug("inventorySheet");
			Iterator<Row> iterator = inventorySheet.iterator();
			int rowCounter = 0;
			ArrayList<InventoryDO> oldInventories = new ArrayList<InventoryDO>();
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				// ignore first 1 row, header row
				if (rowCounter++ < 1)
					continue;
				InventoryDO invDO = extractDataFromRowIntoDo(nextRow);
				if (invDO != null) {
					oldInventories.add(invDO);
				}

			}
			// Merge of data not required.
			inventoryDao.mergeAll(oldInventories, false);

		} catch (IOException e) {
			throw new HeapFlowException(e);
		} catch (Exception e) {
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

	private void headerCellVaueCheck(Cell c, String value) throws HeapFlowException {
		String fetchedCellValue = util.getStringDataFromCell(c);
		if (fetchedCellValue == null || !fetchedCellValue.equalsIgnoreCase(value)) {
			throw new HeapFlowException("Expected value of header: " + value + " found value: " + fetchedCellValue);
		}
	}

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
		headerCellVaueCheck(itemCodeCell, "ITEM CODE");
		headerCellVaueCheck(headerRow.getCell(1), "DESCRIPTION");
		headerCellVaueCheck(headerRow.getCell(2), "Description 2");
		headerCellVaueCheck(headerRow.getCell(3), "Description 3");
		headerCellVaueCheck(headerRow.getCell(4), "Description 4");
		headerCellVaueCheck(headerRow.getCell(5), "Description 5");
		headerCellVaueCheck(headerRow.getCell(6), "Description 6");
		headerCellVaueCheck(headerRow.getCell(7), "UOM");
		headerCellVaueCheck(headerRow.getCell(8), "CLOSING STOCK");
		headerCellVaueCheck(headerRow.getCell(9), "RATE");
		headerCellVaueCheck(headerRow.getCell(10), "CATEGORY");
		headerCellVaueCheck(headerRow.getCell(11), "Product Group Code");
	}

	private InventoryItemDO createItem(Row nextRow) throws HeapFlowException {
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

		String productCodeGroup = util.getStringDataFromCell(nextRow.getCell(11));
		productCodeGroup = (productCodeGroup == null) ? null : productCodeGroup.trim().toUpperCase();
		String itemcategoryCode = util.getStringDataFromCell(nextRow.getCell(10));
		itemcategoryCode = (itemcategoryCode == null) ? null : itemcategoryCode.trim().toUpperCase();

		String gstGrpCode = null;

		String hsnCode = null;

		InventoryItemDO inventoryItemDO = new InventoryItemDO();

		inventoryItemDO.setInventoryItemCode(productId);
		inventoryItemDO.setBaseUnitMeasure(baseUnitMeasure);
		inventoryItemDO.setDescriptions(strDesc);
		inventoryItemDO.setGenProductPostingGrp(null);
		inventoryItemDO.setGstGrpCode(gstGrpCode);
		inventoryItemDO.setHsnSacCode(hsnCode);

		inventoryItemDO.setItemCategoryCode(itemcategoryCode);
		inventoryItemDO.setProductGrpCode(productCodeGroup);
		inventoryItemDO.setReOrderQuant(0.00);
		inventoryItemDO.setReserveQuantAlert(0.00);
		inventoryItemDO.setMaxOrderQuant(0.00);

		try {
			inventoryItemDO = inventoryItemDao.add(inventoryItemDO);
		} catch (Exception e) {
			logger.error("Could not persist inventoryItemDO:  " + inventoryItemDO.getInventoryItemCode());
			throw new HeapFlowException(e);
		}

		return inventoryItemDO;

	}

	private InventoryDO extractDataFromRowIntoDo(Row nextRow) throws HeapFlowException {
		logger.debug("Extracting DO from excel row");
		if (nextRow == null) {
			return null;
		}
		String itemCode = (util.getStringDataFromCell(nextRow.getCell(0)));
		logger.debug("itemCode = " + itemCode);
		itemCode = (itemCode == null) ? null : itemCode.trim().toUpperCase();
		InventoryItemDO itemAsDo = null;
		try {
			itemAsDo = inventoryItemDao.getInventoryItemswithCode(itemCode);
		} catch (Exception e) {
			logger.error("Could not get item from db", e);
			return null;
		}
		if (itemAsDo == null) {
			logger.error("\n" + itemCode + " does not exists in our db\n");
			// Creating entry for the same
			itemAsDo = createItem(nextRow);

		}
		// 8 stock
		// 9 rate

		String strStockCount = (util.getStringDataFromCell(nextRow.getCell(8)));
		logger.debug("strStockCount = " + strStockCount);
		String strRate = (util.getStringDataFromCell(nextRow.getCell(9)));
		logger.debug("strRate = " + strRate);
		if (strStockCount == null || strRate == null) {
			logger.error("Cannot import stock for itemCode: " + itemCode + " as stock or price for it is null.");
			return null;
		}
		Double stockCount = null;
		Double rateD = null;

		try {
			stockCount = Double.parseDouble(strStockCount);
		} catch (NumberFormatException e) {

			logger.error("\nStock is not a number, for itemCode: " + itemCode);
			return null;
		}

		try {
			rateD = Double.parseDouble(strRate);
		} catch (NumberFormatException e) {

			logger.error("\nRate is not a number, for itemCode: " + itemCode);
			return null;
		}

		InventoryTypeDO inventoryTypeDO;
		if (rateD != 0.0) {
			inventoryTypeDO = inventoryTypesRepository.findInventoryTypeWithName("PURCHASED");
		} else {
			inventoryTypeDO = inventoryTypesRepository.findInventoryTypeWithName("FOC");
		}
		InventoryDO invItem = new InventoryDO();
		invItem.setAverageUnitPrice(rateD);
		invItem.setItem(itemAsDo);
		invItem.setNotes("Stock Updated via Bulk upload on " + new Date());
		invItem.setQuantity(stockCount);
		invItem.setType(inventoryTypeDO);
		logger.debug("Created and sent InventoryDO for itemCode: " + itemCode);
		return invItem;
	}

	@Override
	public Page<InventorySummaryDTO> getPagedInvSummaryWithIdLike(String idLike, Pageable paging)
			throws HeapFlowException {

		logger.debug("Service call getPagedInvSummaryWithIdLike");
		Page<InventoryDO> collectedData = null;
		Page<InventorySummaryDTO> dtoPage = null;

		try {
			collectedData = inventoryDao.getPagedInvSummaryWithIdLike(idLike, paging);
			dtoPage = collectedData.map(obj -> new InventorySummaryDTO(obj));
		} catch (Exception e) {
			logger.error("getPagedInvSummaryWithIdLike failed", e);
		}
		logger.debug("Service call exit getPagedInvSummaryWithIdLike");
		return dtoPage;
	}

	@Override
	public Page<EgressDTO> getPagedIssuedMaterialsWithIdLike(String idLike, Pageable paging) throws HeapFlowException {
		logger.debug("Service call getPagedIssuedMaterialsWithIdLike");
		Page<EgressLedgerDO> collectedData = null;
		Page<EgressDTO> dtoPage = null;
		try {
			collectedData = inventoryDao.getPagedIssuedMaterialWithIdLike(idLike, paging);
			dtoPage = collectedData.map(obj -> new EgressDTO(obj));
		} catch (Exception e) {
			logger.error("getPagedIssuedMaterialsWithIdLike failed", e);
		}
		logger.debug("Service call exit getPagedIssuedMaterialsWithIdLike");
		return dtoPage;
	}

	@Override
	public Page<IngressDTO> getPagedAcceptedMaterialsWithIdLike(String idLike, Pageable paging)
			throws HeapFlowException {
		logger.debug("Service call getPagedAcceptedMaterialsWithIdLike");
		Page<IngressLedgerDO> collectedData = null;
		Page<IngressDTO> dtoPage = null;
		try {
			collectedData = inventoryDao.getPagedAcceptedMaterialWithIdLike(idLike, paging);
			dtoPage = collectedData.map(obj -> new IngressDTO(obj));
		} catch (Exception e) {
			logger.error("getPagedAcceptedMaterialsWithIdLike failed", e);
		}
		logger.debug("Service call exit getPagedAcceptedMaterialsWithIdLike");
		return dtoPage;
	}

	@Override
	public void deleteIsuedItem(String dbId) throws HeapFlowException {
		logger.debug("Service call deleteIsuedItem");

		try {
			Long lDbId = Long.parseLong(dbId);
			logger.debug("Converted long dbId: " + lDbId);
			inventoryDao.deleteIsuedItem(lDbId);
		} catch (NumberFormatException e) {
			logger.error("Supplied DB Id is not of long type.", e);
			throw new HeapFlowException(e);
		} catch (Exception e) {
			logger.error("deleteIsuedItem failed", e);
			throw new HeapFlowException(e);
		}
		logger.debug("Service call exit deleteIsuedItem");
	}

	@Override
	public void deleteAcceptedItem(String dbId) throws HeapFlowException {
		logger.debug("Service call deleteAcceptedItem");
		try {
			Long lDbId = Long.parseLong(dbId);
			logger.debug("Converted long dbId: " + lDbId);
			inventoryDao.deleteAcceptedItem(lDbId);
		} catch (NumberFormatException e) {
			logger.error("Supplied DB Id is not of long type.", e);
			throw new HeapFlowException(e);
		} catch (Exception e) {
			logger.error("deleteAcceptedItem failed", e);
			throw new HeapFlowException(e);
		}
		logger.debug("Service call exit deleteAcceptedItem");
	}

}
