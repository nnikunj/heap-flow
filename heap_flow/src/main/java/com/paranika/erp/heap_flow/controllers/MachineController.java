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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
		if (!StringUtils.isEmpty(codeLike)) {
			// Get Rid of all extra characters like \n etc
			codeLike = codeLike.trim();
		}
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

	@RequestMapping(method = RequestMethod.GET, value = HeapFlowApiEndPoints.GET_MACHINES_PAGE_WISE)
	ResponseEntity<Page<MachineDO>> getPagedOptionalCodeLikeMachineList(
			@RequestParam(required = false, name = "codeLike") String codeLike,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size) {
		Pageable paging = PageRequest.of(page, size);
		Page<MachineDO> fetchedList = null;
		ResponseEntity<Page<MachineDO>> response;
		if (!StringUtils.isEmpty(codeLike)) {
			// Get Rid of all extra characters like \n etc
			codeLike = codeLike.trim();
		}
		try {
			fetchedList = machinesService.getPagedMachinesWithCodeLike(codeLike, paging);
			response = new ResponseEntity<Page<MachineDO>>(fetchedList, HttpStatus.OK);
			logger.debug(HeapFlowApiEndPoints.GET_MACHINES_PAGE_WISE + " Success");
		} catch (HeapFlowException e) {

			logger.error(e.getMessage(), e);
			response = new ResponseEntity<Page<MachineDO>>((Page<MachineDO>) null, HttpStatus.SERVICE_UNAVAILABLE);
		}
		logger.debug("Exit method response: \n", response + " \n");
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = HeapFlowApiEndPoints.ADD_UPDATE_MACHINE)
	ResponseEntity<MachineDO> addUpdateItem(@RequestBody MachineDO data) {
		ResponseEntity<MachineDO> response = null;
		MachineDO persistItem = null;
		logger.debug(HeapFlowApiEndPoints.ADD_UPDATE_MACHINE + " invoked.");
		try {
			persistItem = machinesService.persistMachine(data);

			response = new ResponseEntity<MachineDO>(persistItem, HttpStatus.OK);
			logger.debug("Exiting addUpdateItem with repsonse code" + response.getStatusCodeValue());
		} catch (HeapFlowException e) {

			logger.error(e.getMessage(), e);
			response = new ResponseEntity<MachineDO>((MachineDO) null, HttpStatus.SERVICE_UNAVAILABLE);
		}

		return response;

	}

	@RequestMapping(method = RequestMethod.GET, value = HeapFlowApiEndPoints.GET_MACHINE_WITH_CODE)
	ResponseEntity<MachineDO> getMachineWithCode(@PathVariable("code") String code) {
		MachineDO fetchedobj = null;
		ResponseEntity<MachineDO> response;
		if (!StringUtils.isEmpty(code)) {
			// Get Rid of all extra characters like \n etc
			code = code.trim();
		}
		try {
			fetchedobj = machinesService.getMachineWithCode(code);
			response = new ResponseEntity<MachineDO>(fetchedobj, HttpStatus.OK);
			logger.debug(HeapFlowApiEndPoints.GET_MACHINE_WITH_CODE + " Success");
		} catch (HeapFlowException e) {
			logger.error(e.getMessage(), e);
			response = new ResponseEntity<MachineDO>((MachineDO) null, HttpStatus.SERVICE_UNAVAILABLE);
		}

		return response;
	}

	public static void main(String[] args) {
		MachineDO dbFetchedDO = new MachineDO();
		dbFetchedDO.setCategory("Categiry");
		dbFetchedDO.setName("big machine");
		dbFetchedDO.setkWKva("110");
		dbFetchedDO.setCode("localMachine");
		// dbFetchedDO.setMake(data.getMake());
		dbFetchedDO.setModel("S4+");
		dbFetchedDO.setSerialNo("sr1");

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(dbFetchedDO));

	}
}
