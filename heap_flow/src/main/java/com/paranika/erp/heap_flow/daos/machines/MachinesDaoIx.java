package com.paranika.erp.heap_flow.daos.machines;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.paranika.erp.heap_flow.common.models.dos.MachineDO;

@Repository
public interface MachinesDaoIx {
	public List<MachineDO> getAllCategories() throws Exception;

	public void add(MachineDO machineDO) throws Exception;

	public void saveAll(Collection<MachineDO> machineDOs) throws Exception;
}
