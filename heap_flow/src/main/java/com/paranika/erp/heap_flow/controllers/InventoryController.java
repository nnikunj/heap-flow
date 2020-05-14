package com.paranika.erp.heap_flow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.paranika.erp.heap_flow.common.HeapFlowApiEndPoints;
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

	@RequestMapping(method = RequestMethod.POST, value = HeapFlowApiEndPoints.ACCEPT_INVENTORY, produces = "text/plain")
	ResponseEntity<String> acceptMaterial(@RequestBody AcceptingMaterialData data) {
		ResponseEntity<String> response;
		if (data == null) {
			response = new ResponseEntity<String>(
					"Failed, cannot operate with null input of type AcceptingMaterialData", HttpStatus.BAD_REQUEST);

			return response;
		}
		try {
			service.acceptInventory(data);
			response = new ResponseEntity<String>("Success", HttpStatus.CREATED);
		} catch (HeapFlowException e) {
			e.printStackTrace();
			response = new ResponseEntity<String>("Failed " + e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
		}

		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = HeapFlowApiEndPoints.ISSUE_INVENTORY, produces = "text/plain")
	ResponseEntity<String> issueMaterial(@RequestBody IssuingMaterialDataDTO data) {
		ResponseEntity<String> response;
		if (data == null) {
			response = new ResponseEntity<String>(
					"Failed, cannot operate with null input of type AcceptingMaterialData", HttpStatus.BAD_REQUEST);

			return response;
		}
		try {
			service.issueInventory(data);
			response = new ResponseEntity<String>("Success", HttpStatus.CREATED);
		} catch (HeapFlowException e) {
			e.printStackTrace();
			response = new ResponseEntity<String>("Failed " + e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
		}

		return response;
	}
}
