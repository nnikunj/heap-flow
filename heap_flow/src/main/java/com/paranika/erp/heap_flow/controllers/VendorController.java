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
import com.paranika.erp.heap_flow.common.models.dos.VendorDO;
import com.paranika.erp.heap_flow.common.models.dtos.InputExcelBook;
import com.paranika.erp.heap_flow.services.vendors.VendorServiceIX;

@RestController
@RequestMapping(HeapFlowApiEndPoints.BASE_END_POINT_VENDORS)
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = { "Origin", "Content-Type", "Accept",
		"Authorization" }, exposedHeaders = { "Origin", "Content-Type", "Accept", "Authorization" })
public class VendorController {
	@Autowired
	VendorServiceIX service;
	private final Logger logger = LoggerFactory.getLogger(VendorController.class);

	@RequestMapping(method = RequestMethod.POST, value = HeapFlowApiEndPoints.VENDORS_IMPORT_ENDPOINT)
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
			service.importAndUpdateVendorsList(book);
			response = new ResponseEntity<String>("Operation Successfull", HttpStatus.CREATED);
			logger.debug(HeapFlowApiEndPoints.VENDORS_IMPORT_ENDPOINT + " Success");
		} catch (HeapFlowException hfe) {
			logger.error(hfe.getMessage(), hfe);
			response = new ResponseEntity<String>("Could not import vendors list.\n" + hfe.getMessage(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}

		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = HeapFlowApiEndPoints.GET_VENDORS_LIST_WITH_NAME_LIKE)
	ResponseEntity<List<VendorDO>> getNameLikeVendorsList(@PathVariable("nameLike") String nameLike) {
		List<VendorDO> fetchedList = null;
		ResponseEntity<List<VendorDO>> response;
		try {
			fetchedList = service.getVendorListWithNameLike(nameLike);
			response = new ResponseEntity<List<VendorDO>>(fetchedList, HttpStatus.OK);
			logger.debug(HeapFlowApiEndPoints.GET_VENDORS_LIST_WITH_NAME_LIKE + " Success");
		} catch (HeapFlowException e) {
			logger.error(e.getMessage(), e);
			response = new ResponseEntity<List<VendorDO>>((List<VendorDO>) null, HttpStatus.SERVICE_UNAVAILABLE);
		}

		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = HeapFlowApiEndPoints.GET_VENDORS_PAGE_WISE)
	ResponseEntity<Page<VendorDO>> getPagedOptionalSearchNameLikeVendorsList(
			@RequestParam(required = false, name = "searchNameLike") String searchNameLike,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size) {
		Pageable paging = PageRequest.of(page, size);
		Page<VendorDO> fetchedList = null;
		ResponseEntity<Page<VendorDO>> response;
		try {
			fetchedList = service.getPagedVendorsWithSearchNameLike(searchNameLike, paging);
			response = new ResponseEntity<Page<VendorDO>>(fetchedList, HttpStatus.OK);
			logger.debug(HeapFlowApiEndPoints.GET_VENDORS_PAGE_WISE + " Success");
		} catch (HeapFlowException e) {

			logger.error(e.getMessage(), e);
			response = new ResponseEntity<Page<VendorDO>>((Page<VendorDO>) null, HttpStatus.SERVICE_UNAVAILABLE);
		}
		logger.debug("Exit method response: \n", response + " \n");
		return response;
	}

}
