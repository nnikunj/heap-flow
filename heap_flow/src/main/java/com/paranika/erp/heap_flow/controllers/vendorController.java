package com.paranika.erp.heap_flow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.paranika.erp.heap_flow.common.HeapFlowApiEndPoints;
import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.InputExcelBook;
import com.paranika.erp.heap_flow.services.vendors.VendorServiceIX;

@RestController
@RequestMapping(HeapFlowApiEndPoints.BASE_END_POINT_VENDORS)
public class vendorController {
	@Autowired
	VendorServiceIX vendorService;

	@RequestMapping(method = RequestMethod.POST, value = HeapFlowApiEndPoints.VENDORS_IMPORT_ENDPOINT)
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
			vendorService.importAndUpdateVendorsList(book);
			response = new ResponseEntity<String>("Operation Successfull", HttpStatus.CREATED);
		} catch (HeapFlowException hfe) {

			response = new ResponseEntity<String>("Could not import vendors list.\n" + hfe.getMessage(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}

		return response;
	}

}
