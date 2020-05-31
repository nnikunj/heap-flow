package com.paranika.erp.heap_flow_reports.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.paranika.erp.heap_flow_reports.common.HeapFlowReportsApiEndPoints;
import com.paranika.erp.heap_flow_reports.common.ResponseBuilder;

@RestController
@RequestMapping(HeapFlowReportsApiEndPoints.BASE_END_POINT)
public class BaseController {
	private final Logger logger = LoggerFactory.getLogger(BaseController.class);
	@Autowired
	ResponseBuilder respBuild;

	@RequestMapping(method = RequestMethod.GET, value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getStatus() {
		logger.debug("Status Invoked");
		respBuild.setErrorMessage(null);
		respBuild.setMessage("Up and Running.");
		respBuild.setStatusCode(HttpStatus.OK.value());

		ResponseEntity<String> response = null;
		response = new ResponseEntity<String>(respBuild.getResponseText(), HttpStatus.OK);
		logger.debug("\n" + respBuild.getResponseText() + "\n");
		return response;
	}

}
