package com.paranika.erp.heap_flow_reports.services.inventory;

import java.io.ByteArrayInputStream;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.paranika.erp.heap_flow_reports.common.exceptions.HeapFlowReportException;

@Service
public interface InventoryServiceIX {
	public ByteArrayInputStream getIngressReport(Date startDate, Date endDate) throws HeapFlowReportException;
}
