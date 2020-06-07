package com.paranika.erp.heap_flow_reports.controllers;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.paranika.erp.heap_flow_reports.common.AppConstants;
import com.paranika.erp.heap_flow_reports.common.CommonUtil;
import com.paranika.erp.heap_flow_reports.common.HeapFlowReportsApiEndPoints;
import com.paranika.erp.heap_flow_reports.common.exceptions.HeapFlowReportException;
import com.paranika.erp.heap_flow_reports.common.models.dtos.AbcAnalysisInput;
import com.paranika.erp.heap_flow_reports.common.models.dtos.AbcAnalysisInputParameters;
import com.paranika.erp.heap_flow_reports.services.inventory.InventoryServiceIX;

@RestController
@RequestMapping(HeapFlowReportsApiEndPoints.BASE_END_POINT_ABC_ANALYSIS)
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = { "Origin", "Content-Type", "Accept",
		"Authorization" }, exposedHeaders = { "Origin", "Content-Type", "Accept", "Authorization" })
public class ABCAnalysisController {

	@Autowired
	InventoryServiceIX service;
	@Autowired
	CommonUtil util;
	private final Logger logger = LoggerFactory.getLogger(ABCAnalysisController.class);

	@RequestMapping(method = RequestMethod.POST, value = HeapFlowReportsApiEndPoints.GENERATE_MAT_VALUE_RPT, produces = "application/vnd.ms-excel;charset=UTF-8")
	public ResponseEntity<InputStreamResource> getIngressRpt(@RequestBody AbcAnalysisInput boundaries) {
		List<AbcAnalysisInputParameters> inputList = null;

		// logger.debug("Input Boundary: " + boundaries);
		if (boundaries == null || boundaries.getInputList() == null || boundaries.getInputList().size() == 0) {

			inputList = new ArrayList<AbcAnalysisInputParameters>();
			new ArrayList<AbcAnalysisInputParameters>();
			AbcAnalysisInputParameters typeA = new AbcAnalysisInputParameters("Type A", Double.MIN_VALUE,
					AppConstants.DEFAULT_MAX_A_VALUE);
			AbcAnalysisInputParameters typeB = new AbcAnalysisInputParameters("Type B",
					AppConstants.DEFAULT_MAX_A_VALUE, AppConstants.DEFAULT_MAX_B_VALUE);
			AbcAnalysisInputParameters typeC = new AbcAnalysisInputParameters("Type C",
					AppConstants.DEFAULT_MIN_C_VALUE, Double.MAX_VALUE);
			inputList.add(typeA);
			inputList.add(typeB);
			inputList.add(typeC);
			logger.debug("No Data from input, formulating default set.");
		} else {
			logger.debug("Got data from input.");
			inputList = boundaries.getInputList();
			// logger.debug("XX" + inputList);
		}
		ByteArrayInputStream output = null;
		try {
			output = service.generateABCAnalysisReport(inputList);
		} catch (HeapFlowReportException e) {
			logger.error(HeapFlowReportsApiEndPoints.GENERATE_MAT_VALUE_RPT + " Failed ", e);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=\"abcAnalysis.xlsx\"");
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return ResponseEntity.ok().headers(headers)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel;charset=UTF-8"))

				.body(new InputStreamResource(output));

	}

}
