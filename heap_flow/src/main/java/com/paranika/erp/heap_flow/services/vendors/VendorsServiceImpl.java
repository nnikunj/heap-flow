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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paranika.erp.heap_flow.common.CommonUtil;
import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.dos.VendorDO;
import com.paranika.erp.heap_flow.common.models.dtos.InputExcelBook;
import com.paranika.erp.heap_flow.daos.vendors.VendorsDaoIx;

@Component
public class VendorsServiceImpl implements VendorServiceIX {

	@Autowired
	VendorsDaoIx vendorsDao;
	@Autowired
	CommonUtil util;
	private final Logger logger = LoggerFactory.getLogger(VendorsServiceImpl.class);

	@Override
	public List<VendorDO> getVendorListWithNameLike(String nameLike) throws HeapFlowException {
		List<VendorDO> retList = null;
		if (nameLike == null || nameLike.isEmpty()) {
			return null;
		}
		try {
			retList = vendorsDao.getAllVendorsWithNameLike(nameLike);
		} catch (Exception e) {

			e.printStackTrace();
			throw new HeapFlowException(e);
		}

		return retList;
	}

	@Override
	public void importAndUpdateVendorsList(InputExcelBook ieb) throws HeapFlowException {
		String base64EncodedWorkBook = ieb.getBase64EncodedWorkbook();

		byte[] byteArrayWorkBook = Base64.getDecoder().decode(base64EncodedWorkBook);

		InputStream targetStream = new ByteArrayInputStream(byteArrayWorkBook);

		Workbook workbook = null;

		try {
			workbook = new XSSFWorkbook(targetStream);
			Sheet vendorSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = vendorSheet.iterator();
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

	@Override
	public Page<VendorDO> getPagedVendorsWithSearchNameLike(String searchNameLike, Pageable paging)
			throws HeapFlowException {
		logger.debug("Service call getPagedVendorsWithSearchNameLike");
		Page<VendorDO> collectedData = null;

		try {
			collectedData = vendorsDao.getPagedVendorsWithSearchNameLike(searchNameLike, paging);

		} catch (Exception e) {
			logger.error("getPagedVendorsWithSearchNameLike failed", e);
		}
		logger.debug("Service call exit getPagedVendorsWithSearchNameLike");
		return collectedData;
	}

	@Override
	public VendorDO persistVendor(VendorDO data) throws HeapFlowException {

		if (data == null) {
			logger.error("Cannot operate with null VendorDO.");
			throw new HeapFlowException("Cannot operate with null MachineDO.");

		} else if (data.getVendorId() == null || data.getVendorId().isEmpty()) {
			logger.error("Cannot operate with null vendorId");
			throw new HeapFlowException("Cannot operate with null vendorId");
		} else if (data.getSearchName() == null || data.getSearchName().isEmpty()) {
			logger.error("Cannot operate with null vendor search name");
			throw new HeapFlowException("Cannot operate with null vendor search name");
		}
		VendorDO persistedObj = null;
		try {
			persistedObj = vendorsDao.addOrUpdate(data);
		} catch (Exception e) {
			logger.error("Could not save obj to Db.", e);
			throw new HeapFlowException(e);
		}
		return persistedObj;
	}

	public static void main(String[] args) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String vendorId = "vend0045678";

		String name = "Parmanu bum";

		String searchName = "Parmanu bum";

		String gstRegNo = "";

		String panNumber = "";

		String address = "BhutBangla Hanuman Gali jai hind mohalla";

		String address2 = "";

		String city = "Bhitrampur";

		String stateCode = "KA";

		String contactPerson = "BhutNath";

		String phone = "420";

		String email = "jaiHo@JaiHind.com";
		VendorDO v = new VendorDO();
		v.setAddress(address);
		v.setAddress2(address2);
		v.setCity(city);
		v.setContactPerson(contactPerson);
		v.setEmail(email);
		v.setGstRegNo(gstRegNo);
		v.setName(name);
		v.setVendorId(vendorId);
		v.setSearchName(searchName);

		System.out.println(gson.toJson(v));
	}
}
