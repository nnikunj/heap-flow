package com.paranika.erp.heap_flow.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paranika.erp.heap_flow.common.HeapFlowApiEndPoints;
import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.dos.InventoryItemDO;
import com.paranika.erp.heap_flow.common.models.dtos.InputExcelBook;
import com.paranika.erp.heap_flow.common.models.dtos.InputPagedFetchCallData;
import com.paranika.erp.heap_flow.common.models.dtos.InventoryItemDTO;
import com.paranika.erp.heap_flow.services.inventory.InventoryItemsServiceIX;

@RestController
@RequestMapping(HeapFlowApiEndPoints.BASE_END_POINT_INVENTORYITEM)
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = { "Origin", "Content-Type", "Accept",
		"Authorization" }, exposedHeaders = { "Origin", "Content-Type", "Accept", "Authorization" })
public class InventoryItemController {

	@Autowired
	InventoryItemsServiceIX service;
	private final Logger logger = LoggerFactory.getLogger(InventoryItemController.class);

	@RequestMapping(method = RequestMethod.GET, value = HeapFlowApiEndPoints.GET_INVENTORYITEM_WITH_PRODUCT_CODE)
	ResponseEntity<InventoryItemDTO> getItemWithProd(@PathVariable("prodCode") String prodCode) {
		InventoryItemDTO fethcedObj = null;
		ResponseEntity<InventoryItemDTO> response;
		if (!StringUtils.isEmpty(prodCode)) {
			// Get Rid of all extra characters like \n etc
			prodCode = prodCode.trim();
		}
		try {
			fethcedObj = service.getItemWithProdCode(prodCode);
			response = new ResponseEntity<InventoryItemDTO>(fethcedObj, HttpStatus.OK);
			logger.debug(HeapFlowApiEndPoints.GET_INVENTORYITEM_WITH_PRODUCT_CODE + "Success");
		} catch (HeapFlowException e) {
			logger.error(e.getMessage(), e);

			response = new ResponseEntity<InventoryItemDTO>((InventoryItemDTO) null, HttpStatus.SERVICE_UNAVAILABLE);
		}

		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = HeapFlowApiEndPoints.GET_PAGED_INVENTORYITEM_LIST_WITH_ID_LIKE)
	ResponseEntity<Page<InventoryItemDTO>> getPagedOptionalIdLikeItemList(
			@RequestParam(required = false, name = "idLike") String idLike, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "25") int size) {
		Pageable paging = PageRequest.of(page, size);
		Page<InventoryItemDTO> fetchedList = null;

		ResponseEntity<Page<InventoryItemDTO>> response;
		if (!StringUtils.isEmpty(idLike)) {
			// Get Rid of all extra characters like \n etc
			idLike = idLike.trim();
		}
		try {
			fetchedList = service.getPagedItemsWithIdLike(idLike, paging);
			response = new ResponseEntity<Page<InventoryItemDTO>>(fetchedList, HttpStatus.OK);
			logger.debug(HeapFlowApiEndPoints.GET_PAGED_INVENTORYITEM_LIST_WITH_ID_LIKE + " Success");
		} catch (HeapFlowException e) {

			logger.error(e.getMessage(), e);
			response = new ResponseEntity<Page<InventoryItemDTO>>((Page<InventoryItemDTO>) null,
					HttpStatus.SERVICE_UNAVAILABLE);
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = HeapFlowApiEndPoints.GET_INVENTORYITEM_LIST_WITH_ID_LIKE)
	ResponseEntity<List<InventoryItemDTO>> getIdLikeItemList(@PathVariable("idLike") String idLike) {
		List<InventoryItemDTO> fetchedList = null;
		ResponseEntity<List<InventoryItemDTO>> response;
		if (!StringUtils.isEmpty(idLike)) {
			// Get Rid of all extra characters like \n etc
			idLike = idLike.trim();
		}
		try {
			fetchedList = service.getItemListWithIdLike(idLike);
			response = new ResponseEntity<List<InventoryItemDTO>>(fetchedList, HttpStatus.OK);
			logger.debug(HeapFlowApiEndPoints.GET_INVENTORYITEM_LIST_WITH_ID_LIKE + "Success");
		} catch (HeapFlowException e) {

			logger.error(e.getMessage(), e);
			response = new ResponseEntity<List<InventoryItemDTO>>((List<InventoryItemDTO>) null,
					HttpStatus.SERVICE_UNAVAILABLE);
		}

		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = HeapFlowApiEndPoints.ADD_UPDATE_ITEM)
	ResponseEntity<InventoryItemDTO> addUpdateItem(@RequestBody InventoryItemDTO data) {
		ResponseEntity<InventoryItemDTO> response = null;
		InventoryItemDO persistItem = null;
		logger.debug(HeapFlowApiEndPoints.ADD_UPDATE_ITEM + " invoked.");
		try {
			persistItem = service.persistItem(data);
			InventoryItemDTO item = new InventoryItemDTO(persistItem);
			response = new ResponseEntity<InventoryItemDTO>(item, HttpStatus.OK);
			logger.debug("Exiting addUpdateItem with repsonse code" + response.getStatusCodeValue());
		} catch (HeapFlowException e) {

			logger.error(e.getMessage(), e);
			response = new ResponseEntity<InventoryItemDTO>((InventoryItemDTO) null, HttpStatus.SERVICE_UNAVAILABLE);
		}
		return response;

	}

	@RequestMapping(method = RequestMethod.POST, value = HeapFlowApiEndPoints.GET_INVENTORYITEM_PAGE_WISE)
	ResponseEntity<List<InventoryItemDTO>> getPagedInventoryItemList(@RequestBody InputPagedFetchCallData pageInfo) {
		List<InventoryItemDTO> fetchedList = null;
		ResponseEntity<List<InventoryItemDTO>> response;
		try {
			fetchedList = service.getPagedInventoryItemList(pageInfo.getStartRecord(), pageInfo.getPageSize());
			response = new ResponseEntity<List<InventoryItemDTO>>(fetchedList, HttpStatus.OK);
			logger.debug(HeapFlowApiEndPoints.GET_INVENTORYITEM_PAGE_WISE + "Success");
		} catch (HeapFlowException e) {

			logger.error(e.getMessage(), e);
			response = new ResponseEntity<List<InventoryItemDTO>>((List<InventoryItemDTO>) null,
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
			logger.error("Input must be provided for base64 encoded excel sheet.", HttpStatus.BAD_REQUEST);
			return response;
		} else if (StringUtils.isEmpty(book.getBase64EncodedWorkbook())) {
			response = new ResponseEntity<String>("Input must be provided for base64 encoded excel sheet.",
					HttpStatus.BAD_REQUEST);
			logger.error("Input must be provided for base64 encoded excel sheet.", HttpStatus.BAD_REQUEST);
			return response;

		}
		try {
			service.importAndUpdateInventoryItemsList(book);
			response = new ResponseEntity<String>("Operation Successfull", HttpStatus.CREATED);
			logger.debug(HeapFlowApiEndPoints.INVENTORYITEM_IMPORT_ENDPOINT + "Success");
		} catch (HeapFlowException e) {
			logger.error(e.getMessage(), e);
			response = new ResponseEntity<String>("Could not import inventory items list.\n" + e.getMessage(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}

		return response;
	}

}
