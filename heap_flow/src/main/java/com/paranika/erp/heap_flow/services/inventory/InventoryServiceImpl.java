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
			recordDate = (new SimpleDateFormat(AppConstants.commonAppDateFormat)).parse(strDate);
		} catch (ParseException e) {

			e.printStackTrace();
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
			e.printStackTrace();
			throw new HeapFlowException("Could find any Machine with code: " + machineCode);
		}

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
			}

			else {
				EgressLedgerDO ledgerDO = new EgressLedgerDO();
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
			recordDate = (new SimpleDateFormat(AppConstants.commonAppDateFormat)).parse(strDate);
		} catch (ParseException e) {

			e.printStackTrace();
			// Ignore and take current date
			recordDate = new Date();
		}
		String strInvoiceDate = incomingMaterials.getInvoiceDate();
		try {
			invoiceDate = (new SimpleDateFormat(AppConstants.commonAppDateFormat)).parse(strInvoiceDate);
		} catch (ParseException e) {
			logger.warn("Could not parse invoiceDate", strInvoiceDate);

		}
		String strPoDate = incomingMaterials.getPoDate();
		try {
			poDate = (new SimpleDateFormat(AppConstants.commonAppDateFormat)).parse(strPoDate);
		} catch (ParseException e) {
			logger.warn("Could not parse poDate", strPoDate);

		}
		String grn = incomingMaterials.getGrn();
		String invoice = incomingMaterials.getInvoice();

		String vendorCode = incomingMaterials.getVendorCode();
		String loggedInUser = incomingMaterials.getLoggedInUser();
		String intentNumber = incomingMaterials.getIntentNumber();
		String poNumber = incomingMaterials.getPoNumber();

		VendorDO vendor = null;
		try {
			vendor = vendorDao.getVendorwithCode(vendorCode);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HeapFlowException("Could find any vendor with code: " + vendorCode);
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
			inventoryDao.mergeAll(oldInventories);

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
		String description6 = null;

		InventoryItemDescriptions desc = new InventoryItemDescriptions(description, description2, description3,
				description4, description5, description6);
		String strDesc = desc.toJson();

		String baseUnitMeasure = util.getStringDataFromCell(nextRow.getCell(6));
		baseUnitMeasure = (baseUnitMeasure == null) ? null : baseUnitMeasure.trim();

		String productCodeGroup = util.getStringDataFromCell(nextRow.getCell(13));
		productCodeGroup = (productCodeGroup == null) ? null : productCodeGroup.trim().toUpperCase();

		String genProductPostingGrp = util.getStringDataFromCell(nextRow.getCell(14));
		genProductPostingGrp = (genProductPostingGrp == null) ? null : genProductPostingGrp.trim().toUpperCase();

		String itemcategoryCode = util.getStringDataFromCell(nextRow.getCell(15));
		itemcategoryCode = (itemcategoryCode == null) ? null : itemcategoryCode.trim().toUpperCase();

		String gstGrpCode = null;

		String hsnCode = null;

		InventoryItemDO inventoryItemDO = new InventoryItemDO();

		inventoryItemDO.setInventoryItemCode(productId);
		inventoryItemDO.setBaseUnitMeasure(baseUnitMeasure);
		inventoryItemDO.setDescriptions(strDesc);
		inventoryItemDO.setGenProductPostingGrp(genProductPostingGrp);
		inventoryItemDO.setGstGrpCode(gstGrpCode);
		inventoryItemDO.setHsnSacCode(hsnCode);

		inventoryItemDO.setItemCategoryCode(itemcategoryCode);
		inventoryItemDO.setProductGrpCode(productCodeGroup);

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
		// 10 total amt
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
		invItem.setNotes("Brought In directly from input, There wont be any ledger entry for it.");
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

}
