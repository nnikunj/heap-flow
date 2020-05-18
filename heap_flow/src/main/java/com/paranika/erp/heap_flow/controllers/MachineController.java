package com.paranika.erp.heap_flow.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.paranika.erp.heap_flow.common.models.dos.MachineDO;
import com.paranika.erp.heap_flow.common.models.dtos.InputExcelBook;
import com.paranika.erp.heap_flow.services.machines.MachineServiceIX;

@RestController
@RequestMapping(HeapFlowApiEndPoints.BASE_END_POINT_MACHINES)
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = { "Origin", "Content-Type", "Accept",
		"Authorization" }, exposedHeaders = { "Origin", "Content-Type", "Accept", "Authorization" })
public class MachineController {
	@Autowired
	MachineServiceIX machinesService;
	private final Logger logger = LoggerFactory.getLogger(MachineController.class);

	@RequestMapping(method = RequestMethod.POST, value = HeapFlowApiEndPoints.MACHINES_IMPORT_ENDPOINT)
	public ResponseEntity<String> importAndUpdateMachineInventory(@RequestBody InputExcelBook book) {

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
			machinesService.importAndUpdateMachinesInventory(book);
			response = new ResponseEntity<String>("Operation Successfull", HttpStatus.CREATED);
			logger.debug(HeapFlowApiEndPoints.MACHINES_IMPORT_ENDPOINT + " Success");
		} catch (HeapFlowException hfe) {
			logger.error(hfe.getMessage(), hfe);
			response = new ResponseEntity<String>("Could not import machines list.\n" + hfe.getMessage(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}

		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = HeapFlowApiEndPoints.GET_MACHINES_LIST_WITH_CODE_LIKE)
	ResponseEntity<List<MachineDO>> getNameLikeMachinesList(@PathVariable("codeLike") String codeLike) {
		logger.debug("Invoked: " + HeapFlowApiEndPoints.GET_MACHINES_LIST_WITH_CODE_LIKE);
		logger.debug("codeLike: " + codeLike);
		List<MachineDO> fetchedList = null;
		ResponseEntity<List<MachineDO>> response;
		try {
			fetchedList = machinesService.getMachineListWithCodeLike(codeLike);
			response = new ResponseEntity<List<MachineDO>>(fetchedList, HttpStatus.OK);
			logger.debug(HeapFlowApiEndPoints.GET_MACHINES_LIST_WITH_CODE_LIKE + " Success");
		} catch (HeapFlowException e) {
			logger.error(e.getMessage(), e);
			response = new ResponseEntity<List<MachineDO>>((List<MachineDO>) null, HttpStatus.SERVICE_UNAVAILABLE);
		}
		logger.debug("response: \n", response + " \n");
		return response;
	}
}
