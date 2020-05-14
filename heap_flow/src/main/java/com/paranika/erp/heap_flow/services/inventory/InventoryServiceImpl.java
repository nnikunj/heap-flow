package com.paranika.erp.heap_flow.services.inventory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paranika.erp.heap_flow.common.AppConstants;
import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.AcceptingMaterialData;
import com.paranika.erp.heap_flow.common.models.MaterialData;
import com.paranika.erp.heap_flow.common.models.dos.IngressLedgerDO;
import com.paranika.erp.heap_flow.common.models.dos.InventoryItemDO;
import com.paranika.erp.heap_flow.common.models.dos.InventoryTypeDO;
import com.paranika.erp.heap_flow.common.models.dos.VendorDO;
import com.paranika.erp.heap_flow.daos.defaultProviders.InventoryTypesRepository;
import com.paranika.erp.heap_flow.daos.inventory.InventoryDaoIX;
import com.paranika.erp.heap_flow.daos.inventory.InventoryItemDaoIx;
import com.paranika.erp.heap_flow.daos.vendors.VendorsDaoIx;

@Component
public class InventoryServiceImpl implements InventoryServiceIX {

	@Autowired
	VendorsDaoIx vendorDao;
	@Autowired
	InventoryItemDaoIx inventoryItemDao;

	@Autowired
	InventoryTypesRepository inventoryTypesRepository;

	@Autowired
	InventoryDaoIX inventoryDao;

	@Override
	public void acceptInventory(AcceptingMaterialData incomingMaterials) throws HeapFlowException {

		Date recordDate = null;
		String strDate = (incomingMaterials.getRecordDate() == null) ? null : incomingMaterials.getRecordDate().trim();
		try {
			recordDate = (new SimpleDateFormat(AppConstants.commonAppDateFormat)).parse(strDate);
		} catch (ParseException e) {

			e.printStackTrace();
			// Ignore and take current date
			recordDate = new Date();
		}

		String grn = incomingMaterials.getGrn();
		String invoice = incomingMaterials.getInvoice();
		String vendorCode = incomingMaterials.getVendorCode();

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
			ledgerList.add(ledgerDO);
		}
		try {
			inventoryDao.persistAllIngressLedgers(ledgerList);
		} catch (Exception e) {

			e.printStackTrace();
			throw new HeapFlowException(e);
		}
	}

}
