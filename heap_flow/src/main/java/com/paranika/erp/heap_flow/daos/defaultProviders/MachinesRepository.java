package com.paranika.erp.heap_flow.daos.defaultProviders;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.paranika.erp.heap_flow.common.models.dos.MachineDO;

@Repository
public interface MachinesRepository extends CrudRepository<MachineDO, Long> {

	@Query("from MachineDO machine where UPPER(machine.code) LIKE UPPER(CONCAT('%',:codeLike,'%')) ORDER BY machine.code")
	List<MachineDO> findMachinesWithCodeLike(@Param("codeLike") String codeLike, Pageable pageable);

	@Query("from MachineDO machine where UPPER(machine.code) = UPPER(:machineCode)")
	MachineDO findMachineWithCode(String machineCode);

}
