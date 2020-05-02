package com.paranika.erp.heap_flow.services.vendors;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.paranika.erp.heap_flow.common.CommonUtil;
import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.InputExcelBook;
import com.paranika.erp.heap_flow.common.models.dos.VendorDO;
import com.paranika.erp.heap_flow.daos.vendors.VendorsDaoIx;

@Component
public class VendorsServiceImpl implements VendorServiceIX {

	@Autowired
	VendorsDaoIx vendorsDao;
	@Autowired
	CommonUtil util;

	@Override
	public void importAndUpdateVendorsList(InputExcelBook ieb) throws HeapFlowException {
		String base64EncodedWorkBook = ieb.getBase64EncodedWorkbook();

		byte[] byteArrayWorkBook = Base64.getDecoder().decode(base64EncodedWorkBook);

		InputStream targetStream = new ByteArrayInputStream(byteArrayWorkBook);

		Workbook workbook = null;

		try {
			workbook = new XSSFWorkbook(targetStream);
			Sheet machineSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = machineSheet.iterator();
			int rowCounter = 0;
			ArrayList<VendorDO> vendorsDos = new ArrayList<VendorDO>();
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				// ignore first 2 rows
				if (rowCounter++ < 1)
					continue;
				VendorDO vendorDO = extractDataFromRowIntoDo(nextRow);
				if (vendorDO != null) {
					vendorsDos.add(vendorDO);
				}

			}
			// processing in batch of 500 since list is huge
			List<List<VendorDO>> batchedList = Lists.partition(vendorsDos, 500);
			for (List<VendorDO> list : batchedList) {
				vendorsDao.mergeAll(list);
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new HeapFlowException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HeapFlowException(e);
		}

		finally {
			try {
				if (workbook != null)
					workbook.close();
				if (targetStream != null)
					targetStream.close();
			} catch (IOException e) {
				// Ignore

			}
		}

	}

	private VendorDO extractDataFromRowIntoDo(Row nextRow) {

		String vendorId = util.getStringDataFromCell(nextRow.getCell(0));
		vendorId = (vendorId == null) ? null : vendorId.trim().toUpperCase();

		String name = util.getStringDataFromCell(nextRow.getCell(1));
		name = (name == null) ? null : name.trim().toUpperCase();
		String gstNumber = util.getStringDataFromCell(nextRow.getCell(2));
		gstNumber = (gstNumber == null) ? null : gstNumber.trim().toUpperCase();

		String address = (util.getStringDataFromCell(nextRow.getCell(3)));
		address = (address == null) ? null : address.trim();

		String address2 = util.getStringDataFromCell(nextRow.getCell(4));
		address2 = (address2 == null) ? null : address2.trim();

		String city = util.getStringDataFromCell(nextRow.getCell(5));
		city = (city == null) ? null : city.trim().toUpperCase();

		String contactPerson = util.getStringDataFromCell(nextRow.getCell(6));
		contactPerson = (contactPerson == null) ? null : contactPerson.trim();

		String phone = util.getStringDataFromCell(nextRow.getCell(7));
		phone = (phone == null) ? null : phone.trim();
		String stateCode = util.getStringDataFromCell(nextRow.getCell(9));
		stateCode = (stateCode == null) ? null : stateCode.trim().toUpperCase();
		String panNumber = util.getStringDataFromCell(nextRow.getCell(11));
		panNumber = (panNumber == null) ? null : panNumber.trim().toUpperCase();
		String searchName = util.getStringDataFromCell(nextRow.getCell(12));
		searchName = (searchName == null) ? null : searchName.trim();
		String email = util.getStringDataFromCell(nextRow.getCell(13));
		email = (email == null) ? null : email.trim();

		if (StringUtils.isEmpty(searchName)) {
			return null;
		}
		VendorDO vendorDO = new VendorDO();
		vendorDO.setAddress2(address2);
		vendorDO.setCity(city);
		vendorDO.setContactPerson(contactPerson);
		vendorDO.setEmail(email);
		vendorDO.setGstRegNo(gstNumber);
		vendorDO.setName(name);
		vendorDO.setPanNumber(panNumber);
		vendorDO.setPhone(phone);
		vendorDO.setSearchName(searchName);
		vendorDO.setVendorId(vendorId);
		vendorDO.setAddress(address);
		// System.out.println("\n----------------------\n" + vendorDO +
		// "\n----------------------\n");
		return vendorDO;
	}

}
