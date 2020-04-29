package com.paranika.erp.heap_flow.daos.machines;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paranika.erp.heap_flow.common.models.dos.MachineDO;

@Repository
public interface MachinesRepository extends CrudRepository<MachineDO, Long> {

}
