package com.paranika.erp.heap_flow.daos.machines;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.paranika.erp.heap_flow.common.models.dos.MachineDO;

@Repository
public interface MachinesDaoIx {
	public List<MachineDO> getAllMachines() throws Exception;

	public List<MachineDO> getAllMachinesWithPagination(int startRecord, int pageSize) throws Exception;

	public void add(MachineDO machineDO) throws Exception;

	public void saveAll(Collection<MachineDO> machineDOs) throws Exception;

	public void mergeAll(Collection<MachineDO> machineDOs) throws Exception;

	public List<MachineDO> getAllMachinesWithNameLike(String nameLike) throws Exception;
}
