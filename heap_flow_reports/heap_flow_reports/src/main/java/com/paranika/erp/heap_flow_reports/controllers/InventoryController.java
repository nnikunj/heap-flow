package com.paranika.erp.heap_flow_reports.controllers;

import java.io.ByteArrayInputStream;
import java.util.Base64;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paranika.erp.heap_flow_reports.common.CommonUtil;
import com.paranika.erp.heap_flow_reports.common.HeapFlowReportsApiEndPoints;
import com.paranika.erp.heap_flow_reports.common.exceptions.HeapFlowReportException;
import com.paranika.erp.heap_flow_reports.services.inventory.InventoryServiceIX;

@RestController
@RequestMapping(HeapFlowReportsApiEndPoints.BASE_END_POINT_INVENTORY)
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = { "Origin", "Content-Type", "Accept",
		"Authorization" }, exposedHeaders = { "Origin", "Content-Type", "Accept", "Authorization" })
public class InventoryController {
	@Autowired
	InventoryServiceIX service;
	private final Logger logger = LoggerFactory.getLogger(InventoryController.class);

	@Autowired
	CommonUtil util;

	@RequestMapping(method = RequestMethod.GET, value = HeapFlowReportsApiEndPoints.GET_ITEM_QRCODE_RPT, produces = "application/pdf")
	public ResponseEntity<InputStreamResource> getIngressRpt(@PathVariable("prodCode") String prodCode,
			@RequestParam(name = "isEncoded", required = false) String isEncoded) {
		String decodedProdCode = null;
		boolean isDecodingRequired = true;
		if (isEncoded != null && !isEncoded.isEmpty()) {
			// Default we will consider it is Encoded
			try {
				isDecodingRequired = Boolean.parseBoolean(isEncoded);
			} catch (Exception e) {
				logger.warn("Could not parse value of isDecodingRequired and hence will have default value of true.");
			}

		}
		if (!StringUtils.isEmpty(prodCode)) {
			// Get Rid of all extra characters like \n etc
			decodedProdCode = prodCode.trim();
			if (isDecodingRequired) {
				decodedProdCode = new String(Base64.getDecoder().decode(decodedProdCode));
			}
		} else {
			decodedProdCode = prodCode;
		}

		ByteArrayInputStream output = null;
		try {
			output = service.getProductSticker(decodedProdCode);
		} catch (HeapFlowReportException e) {
			logger.error(HeapFlowReportsApiEndPoints.GET_EGRESS_RPT + " Failed ", e);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=\"product.pdf\"");
		headers.setContentType(MediaType.APPLICATION_PDF);

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)

				.body(new InputStreamResource(output));
	}

	@RequestMapping(method = RequestMethod.GET, value = HeapFlowReportsApiEndPoints.GET_INVENTORY_SUMMARY_RPT, produces = "application/vnd.ms-excel;charset=UTF-8")
	public ResponseEntity<InputStreamResource> getInventorySummaryRpt(
			@RequestParam(required = false, name = "itemCodeLike") String itemCodeLike) {

		logger.debug(HeapFlowReportsApiEndPoints.GET_INVENTORY_SUMMARY_RPT + " Invoked.");
		if (!StringUtils.isEmpty(itemCodeLike)) {
			logger.debug("itemCodeLike: " + itemCodeLike);
			itemCodeLike = itemCodeLike.trim();
		}
		ByteArrayInputStream output = null;
		try {
			output = service.getInventorySummaryReport(itemCodeLike);
		} catch (HeapFlowReportException e) {
			logger.error(HeapFlowReportsApiEndPoints.GET_INVENTORY_SUMMARY_RPT + " Failed ", e);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=\"inventorySummaryReport.xlsx\"");

		return ResponseEntity.ok().headers(headers)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel;charset=UTF-8"))
				.body(new InputStreamResource(output));

	}

	@RequestMapping(method = RequestMethod.GET, value = HeapFlowReportsApiEndPoints.GET_FASTMOVING_RPT, produces = "application/vnd.ms-excel;charset=UTF-8")
	public ResponseEntity<InputStreamResource> getFastMovingItemsRpt(
			@RequestParam(required = false, name = "startDate") String startDate,
			@RequestParam(required = false, name = "endDate") String endDate) {
		Date stDate = null;
		Date enDate = null;

		logger.debug("startDate " + startDate);
		logger.debug("endDate " + endDate);
		stDate = util.extractDateFromInput(startDate, (short) -30);
		enDate = util.extractDateFromInput(endDate, (short) 0);

		logger.debug("startDate after check " + stDate);
		logger.debug("endDate after check " + enDate);
		ByteArrayInputStream output = null;
		try {
			output = service.getFastMovingItemsReport(stDate, enDate);
		} catch (HeapFlowReportException e) {
			logger.error(HeapFlowReportsApiEndPoints.GET_FASTMOVING_RPT + " Failed ", e);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=\"fastMovingRpt.xlsx\"");
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return ResponseEntity.ok().headers(headers)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel;charset=UTF-8"))

				.body(new InputStreamResource(output));

	}

	@RequestMapping(method = RequestMethod.GET, value = HeapFlowReportsApiEndPoints.GET_AGING_RPT, produces = "application/vnd.ms-excel;charset=UTF-8")
	public ResponseEntity<InputStreamResource> getAgingRpt() {

		logger.debug(HeapFlowReportsApiEndPoints.GET_AGING_RPT + " Invoked.");

		ByteArrayInputStream output = null;
		try {
			output = service.getInventoryAgingReport();
		} catch (HeapFlowReportException e) {
			logger.error(HeapFlowReportsApiEndPoints.GET_AGING_RPT + " Failed ", e);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=\"agingRpt.xlsx\"");

		return ResponseEntity.ok().headers(headers)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel;charset=UTF-8"))
				.body(new InputStreamResource(output));

	}

}
