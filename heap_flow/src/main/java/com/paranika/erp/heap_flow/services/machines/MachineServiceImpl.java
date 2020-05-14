package com.paranika.erp.heap_flow.services.machines;

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

import com.paranika.erp.heap_flow.common.CommonUtil;
import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
import com.paranika.erp.heap_flow.common.models.dos.MachineDO;
import com.paranika.erp.heap_flow.common.models.dtos.InputExcelBook;
import com.paranika.erp.heap_flow.daos.machines.MachinesDaoIx;

@Component
public class MachineServiceImpl implements MachineServiceIX {

	@Autowired
	MachinesDaoIx machinesDao;
	@Autowired
	CommonUtil util;

	private MachineDO extractDataFromRowIntoDo(Row nextRow) {
		String code = (util.getStringDataFromCell(nextRow.getCell(1)));
		code = (code == null) ? null : code.trim().toUpperCase();

		String name = util.getStringDataFromCell(nextRow.getCell(2));
		name = (name == null) ? null : name.trim().toUpperCase();

		String category = util.getStringDataFromCell(nextRow.getCell(3));
		category = (category == null) ? null : category.trim().toUpperCase();

		String kva = util.getStringDataFromCell(nextRow.getCell(4));
		kva = (kva == null) ? null : kva.trim().toUpperCase();

		String serialNumber = util.getStringDataFromCell(nextRow.getCell(5));
		serialNumber = (serialNumber == null) ? null : serialNumber.trim().toUpperCase();

		String model = util.getStringDataFromCell(nextRow.getCell(6));
		model = (model == null) ? null : model.trim().toUpperCase();

		String make = util.getStringDataFromCell(nextRow.getCell(7));
		make = (make == null) ? null : make.trim().toUpperCase();

		if (StringUtils.isEmpty(name)) {
			return null;
		}
		MachineDO machineDO = new MachineDO();
		machineDO.setCategory(category);
		machineDO.setCode(code);
		machineDO.setkWKva(kva);
		machineDO.setMake(make);
		machineDO.setModel(model);
		machineDO.setSerialNo(serialNumber);
		machineDO.setName(name);

		return machineDO;

	}

	@Override
	public void importAndUpdateMachinesInventory(InputExcelBook ieb) throws HeapFlowException {

		String base64EncodedWorkBook = ieb.getBase64EncodedWorkbook();

		byte[] byteArrayWorkBook = Base64.getDecoder().decode(base64EncodedWorkBook);

		InputStream targetStream = new ByteArrayInputStream(byteArrayWorkBook);

		Workbook workbook = null;

		try {
			workbook = new XSSFWorkbook(targetStream);
			Sheet machineSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = machineSheet.iterator();
			int rowCounter = 0;
			ArrayList<MachineDO> machineDos = new ArrayList<MachineDO>();
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				// ignore first 2 rows
				if (rowCounter++ < 2)
					continue;
				MachineDO machineDO = extractDataFromRowIntoDo(nextRow);
				if (machineDO != null) {
					machineDos.add(machineDO);
				}

			}
			machinesDao.mergeAll(machineDos);

		} catch (IOException e) {
			throw new HeapFlowException(e);
		} catch (Exception e) {
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

	@Override
	public List<MachineDO> getMachineListWithNameLike(String nameLike) throws HeapFlowException {
		List<MachineDO> retList = null;
		if (nameLike == null || nameLike.isEmpty()) {
			return null;
		}
		try {
			retList = machinesDao.getAllMachinesWithNameLike(nameLike);
		} catch (Exception e) {

			e.printStackTrace();
			throw new HeapFlowException(e);
		}

		return retList;
	}

}
