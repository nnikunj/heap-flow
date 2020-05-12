package com.paranika.erp.heap_flow.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.paranika.erp.heap_flow.common.HeapFlowApiEndPoints;
import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.InputExcelBook;
import com.paranika.erp.heap_flow.common.models.InputPagedFetchCallData;
import com.paranika.erp.heap_flow.common.models.InventoryItemDisplayDO;
import com.paranika.erp.heap_flow.services.inventory.InventoryItemsServiceIX;

@RestController
@RequestMapping(HeapFlowApiEndPoints.BASE_END_POINT_INVENTORYITEM)
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = { "Origin", "Content-Type", "Accept",
		"Authorization" }, exposedHeaders = { "Origin", "Content-Type", "Accept", "Authorization" })
public class InventoryItemController {

	@Autowired
	InventoryItemsServiceIX service;

	@RequestMapping(method = RequestMethod.GET, value = HeapFlowApiEndPoints.GET_INVENTORYITEM_WITH_PRODUCT_CODE)
	ResponseEntity<InventoryItemDisplayDO> getItemWithProd(@PathVariable("prodCode") String prodCode) {
		InventoryItemDisplayDO fethcedObj = null;
		ResponseEntity<InventoryItemDisplayDO> response;
		try {
			fethcedObj = service.getItemWithProdCode(prodCode);
			response = new ResponseEntity<InventoryItemDisplayDO>(fethcedObj, HttpStatus.OK);
		} catch (HeapFlowException e) {

			e.printStackTrace();
			response = new ResponseEntity<InventoryItemDisplayDO>((InventoryItemDisplayDO) null,
					HttpStatus.SERVICE_UNAVAILABLE);
		}

		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = HeapFlowApiEndPoints.GET_INVENTORYITEM_LIST_WITH_ID_LIKE)
	ResponseEntity<List<InventoryItemDisplayDO>> getIdLikeItemList(@PathVariable("idLike") String idLike) {
		List<InventoryItemDisplayDO> fetchedList = null;
		ResponseEntity<List<InventoryItemDisplayDO>> response;
		try {
			fetchedList = service.getItemListWithIdLike(idLike);
			response = new ResponseEntity<List<InventoryItemDisplayDO>>(fetchedList, HttpStatus.OK);
		} catch (HeapFlowException e) {

			e.printStackTrace();
			response = new ResponseEntity<List<InventoryItemDisplayDO>>((List<InventoryItemDisplayDO>) null,
					HttpStatus.SERVICE_UNAVAILABLE);
		}

		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = HeapFlowApiEndPoints.GET_INVENTORYITEM_PAGE_WISE)
	ResponseEntity<List<InventoryItemDisplayDO>> getPagedInventoryItemList(
			@RequestBody InputPagedFetchCallData pageInfo) {
		List<InventoryItemDisplayDO> fetchedList = null;
		ResponseEntity<List<InventoryItemDisplayDO>> response;
		try {
			fetchedList = service.getPagedInventoryItemList(pageInfo.getStartRecord(), pageInfo.getPageSize());
			response = new ResponseEntity<List<InventoryItemDisplayDO>>(fetchedList, HttpStatus.OK);
		} catch (HeapFlowException e) {

			e.printStackTrace();
			response = new ResponseEntity<List<InventoryItemDisplayDO>>((List<InventoryItemDisplayDO>) null,
					HttpStatus.SERVICE_UNAVAILABLE);
		}

		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = HeapFlowApiEndPoints.INVENTORYITEM_IMPORT_ENDPOINT)
	public ResponseEntity<String> importAndUpdateVendorList(@RequestBody InputExcelBook book) {

		ResponseEntity<String> response;

		if (book == null) {
			response = new ResponseEntity<String>("Input must be provided for base64 encoded excel sheet.",
					HttpStatus.BAD_REQUEST);
			return response;
		} else if (StringUtils.isEmpty(book.getBase64EncodedWorkbook())) {
			response = new ResponseEntity<String>("Input must be provided for base64 encoded excel sheet.",
					HttpStatus.BAD_REQUEST);
			return response;

		}
		try {
			service.importAndUpdateInventoryItemsList(book);
			response = new ResponseEntity<String>("Operation Successfull", HttpStatus.CREATED);
		} catch (HeapFlowException hfe) {

			response = new ResponseEntity<String>("Could not import inventory items list.\n" + hfe.getMessage(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}

		return response;
	}

}
