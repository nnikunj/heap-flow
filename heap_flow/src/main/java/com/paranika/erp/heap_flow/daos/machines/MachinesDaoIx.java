package com.paranika.erp.heap_flow.daos.machines;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.paranika.erp.heap_flow.common.models.dos.MachineDO;

@Repository
public interface MachinesDaoIx {
	public List<MachineDO> getAllMachines() throws Exception;

	public List<MachineDO> getAllMachinesWithPagination(int startRecord, int pageSize) throws Exception;

	public void add(MachineDO machineDO) throws Exception;

	public void saveAll(Collection<MachineDO> machineDOs) throws Exception;

	public void mergeAll(Collection<MachineDO> machineDOs) throws Exception;

	public List<MachineDO> getAllMachinesWithCodeLike(String nameLike) throws Exception;

	public MachineDO getMachinewithCode(String machineCode) throws Exception;

	public Page<MachineDO> getPagedMachinesWithIdLike(String codeLike, Pageable paging) throws Exception;

	public MachineDO addOrUpdate(MachineDO data) throws Exception;
}
