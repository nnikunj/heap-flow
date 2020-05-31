package com.paranika.erp.heap_flow_reports.controllers;

import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paranika.erp.heap_flow_reports.common.AppConstants;
import com.paranika.erp.heap_flow_reports.common.HeapFlowReportsApiEndPoints;
import com.paranika.erp.heap_flow_reports.common.exceptions.HeapFlowReportException;
import com.paranika.erp.heap_flow_reports.services.inventory.InventoryServiceIX;

@RestController
@RequestMapping(HeapFlowReportsApiEndPoints.BASE_END_POINT_ACCEPTED_MAT)
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = { "Origin", "Content-Type", "Accept",
		"Authorization" }, exposedHeaders = { "Origin", "Content-Type", "Accept", "Authorization" })
public class IngressInventoryController {

	@Autowired
	InventoryServiceIX service;
	private final Logger logger = LoggerFactory.getLogger(IngressInventoryController.class);

	@RequestMapping(method = RequestMethod.GET, value = HeapFlowReportsApiEndPoints.GET_INGRESS_RPT, produces = "application/vnd.ms-excel;charset=UTF-8")
	public ResponseEntity<InputStreamResource> getIngressRpt(
			@RequestParam(required = false, name = "startDate") String startDate,
			@RequestParam(required = false, name = "endDate") String endDate) {
		Date stDate = null;
		Date enDate = null;
		DateFormat dateFormat = new SimpleDateFormat(AppConstants.commonAppDateFormat);
		logger.debug("startDate " + startDate);
		logger.debug("endDate " + endDate);
		if (!StringUtils.isEmpty(startDate)) {
			try {
				stDate = dateFormat.parse(startDate);
			} catch (ParseException e) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -30);
				stDate = cal.getTime();
			}
		}
		if (!StringUtils.isEmpty(endDate)) {
			try {
				enDate = dateFormat.parse(endDate);
			} catch (ParseException e) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, +30);
				enDate = cal.getTime();
			}
		}
		ByteArrayInputStream output = null;
		try {
			output = service.getIngressReport(stDate, enDate);
		} catch (HeapFlowReportException e) {
			logger.error(HeapFlowReportsApiEndPoints.GET_INGRESS_RPT + " Failed ", e);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=rpt.xlsx");

		return ResponseEntity.ok().headers(headers)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel;charset=UTF-8"))
				.body(new InputStreamResource(output));

	}
}