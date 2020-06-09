package com.paranika.erp.heap_flow_reports.services.inventory;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.paranika.erp.heap_flow_reports.common.exceptions.HeapFlowReportException;
import com.paranika.erp.heap_flow_reports.common.models.dtos.AbcAnalysisInputParameters;

@Service
public interface InventoryServiceIX {
	public ByteArrayInputStream getIngressReport(Date startDate, Date endDate) throws HeapFlowReportException;

	public ByteArrayInputStream getEgressReport(Date startDate, Date endDate) throws HeapFlowReportException;

	public ByteArrayInputStream generateABCAnalysisReport(List<AbcAnalysisInputParameters> boundaries)
			throws HeapFlowReportException;

	public ByteArrayInputStream getProductSticker(String prodCode) throws HeapFlowReportException;
}
