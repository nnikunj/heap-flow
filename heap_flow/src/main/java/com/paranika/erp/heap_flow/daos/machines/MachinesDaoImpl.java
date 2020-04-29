package com.paranika.erp.heap_flow.daos.machines;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paranika.erp.heap_flow.common.models.dos.MachineDO;

@Component
public class MachinesDaoImpl implements MachinesDaoIx {

	@Autowired
	private MachinesRepository machinesRepo;

	@Override
	public List<MachineDO> getAllCategories() throws Exception {
		ArrayList<MachineDO> retList = new ArrayList<MachineDO>();
		machinesRepo.findAll().forEach(retList::add);
		return retList;
	}

	@Override
	public void add(MachineDO machineDO) throws Exception {

		machinesRepo.save(machineDO);

	}

	@Override
	public void saveAll(Collection<MachineDO> machineDOs) throws Exception {
		machinesRepo.saveAll(machineDOs);
	}

}
