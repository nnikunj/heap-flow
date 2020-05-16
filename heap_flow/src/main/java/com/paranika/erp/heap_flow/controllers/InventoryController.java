package com.paranika.erp.heap_flow.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.paranika.erp.heap_flow.common.HeapFlowApiEndPoints;
import com.paranika.erp.heap_flow.common.ResponseBuilder;
import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.dtos.AcceptingMaterialData;
import com.paranika.erp.heap_flow.common.models.dtos.IssuingMaterialDataDTO;
import com.paranika.erp.heap_flow.services.inventory.InventoryServiceIX;

@RestController
@RequestMapping(HeapFlowApiEndPoints.BASE_END_POINT_INVENTORY)
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = { "Origin", "Content-Type", "Accept",
		"Authorization" }, exposedHeaders = { "Origin", "Content-Type", "Accept", "Authorization" })
public class InventoryController {

	@Autowired
	InventoryServiceIX service;

	@Autowired
	ResponseBuilder respBuild;

	private final Logger logger = LoggerFactory.getLogger(InventoryController.class);

	@RequestMapping(method = RequestMethod.POST, value = HeapFlowApiEndPoints.ACCEPT_INVENTORY, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> acceptMaterial(@RequestBody AcceptingMaterialData data) {
		logger.debug(HeapFlowApiEndPoints.ACCEPT_INVENTORY + " invoked");
		logger.debug("Incoming AcceptingMaterialData: " + data);
		ResponseEntity<String> response;
		if (data == null) {
			respBuild.setErrorMessage("Failed, cannot operate with null input of type AcceptingMaterialData");
			respBuild.setMessage(null);
			respBuild.setStatusCode(HttpStatus.BAD_REQUEST.value());
			response = new ResponseEntity<String>(respBuild.getResponseText(), HttpStatus.BAD_REQUEST);

			return response;
		}
		try {
			service.acceptInventory(data);
			logger.debug("Inventory Accepted.");
			respBuild.setErrorMessage(null);
			respBuild.setMessage("Success");
			respBuild.setStatusCode(HttpStatus.CREATED.value());

			response = new ResponseEntity<String>(respBuild.getResponseText(), HttpStatus.CREATED);
		} catch (HeapFlowException e) {
			logger.error(e.getMessage(), e);

			respBuild.setErrorMessage("Failed " + e.getMessage());
			respBuild.setMessage(null);
			respBuild.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value());

			response = new ResponseEntity<String>(respBuild.getResponseText(), HttpStatus.SERVICE_UNAVAILABLE);
		}

		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = HeapFlowApiEndPoints.ISSUE_INVENTORY, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> issueMaterial(@RequestBody IssuingMaterialDataDTO data) {
		logger.debug(HeapFlowApiEndPoints.ISSUE_INVENTORY + " invoked");
		logger.debug("Incoming IssuingMaterialDataDTO: " + data);
		ResponseEntity<String> response;
		if (data == null) {
			respBuild.setErrorMessage("Failed, cannot operate with null input of type AcceptingMaterialData");
			respBuild.setMessage(null);
			respBuild.setStatusCode(HttpStatus.BAD_REQUEST.value());
			response = new ResponseEntity<String>(respBuild.getResponseText(), HttpStatus.BAD_REQUEST);

			return response;
		}
		try {
			service.issueInventory(data);
			logger.debug("Material issued.");
			respBuild.setErrorMessage(null);
			respBuild.setMessage("Success");
			respBuild.setStatusCode(HttpStatus.CREATED.value());

			response = new ResponseEntity<String>(respBuild.getResponseText(), HttpStatus.CREATED);
		} catch (HeapFlowException e) {
			logger.error(e.getMessage(), e);
			respBuild.setErrorMessage("Failed " + e.getMessage());
			respBuild.setMessage(null);
			respBuild.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value());

			response = new ResponseEntity<String>(respBuild.getResponseText(), HttpStatus.SERVICE_UNAVAILABLE);
		}

		return response;
	}
}
