package com.paranika.erp.heap_flow_reports.controllers;

import java.io.ByteArrayInputStream;

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
import org.springframework.web.bind.annotation.RestController;

import com.paranika.erp.heap_flow_reports.common.HeapFlowReportsApiEndPoints;
import com.paranika.erp.heap_flow_reports.common.exceptions.HeapFlowReportException;
import com.paranika.erp.heap_flow_reports.services.inventory.InventoryServiceIX;

@RestController
@RequestMapping(HeapFlowReportsApiEndPoints.BASE_END_POINT_INVENTORY)
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = { "Origin", "Content-Type", "Accept",
		"Authorization" }, exposedHeaders = { "Origin", "Content-Type", "Accept", "Authorization" })
public class InventoryItemController {
	@Autowired
	InventoryServiceIX service;
	private final Logger logger = LoggerFactory.getLogger(InventoryItemController.class);

	@RequestMapping(method = RequestMethod.GET, value = HeapFlowReportsApiEndPoints.GET_ITEM_QRCODE_RPT, produces = "application/pdf")
	public ResponseEntity<InputStreamResource> getIngressRpt(@PathVariable("prodCode") String prodCode) {

		if (!StringUtils.isEmpty(prodCode)) {
			// Get Rid of all extra characters like \n etc
			prodCode = prodCode.trim();
		}
		ByteArrayInputStream output = null;
		try {
			output = service.getProductSticker(prodCode);
		} catch (HeapFlowReportException e) {
			logger.error(HeapFlowReportsApiEndPoints.GET_EGRESS_RPT + " Failed ", e);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=\"product.pdf\"");
		headers.setContentType(MediaType.APPLICATION_PDF);

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)

				.body(new InputStreamResource(output));
	}
}