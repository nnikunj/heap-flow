package com.paranika.erp.heap_flow_reports.daos.defaultProviders;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.paranika.erp.heap_flow_reports.common.models.dos.MachineDO;

@Repository
public interface MachinesRepository extends JpaRepository<MachineDO, Long> {

	@Query("from MachineDO machine where UPPER(machine.code) LIKE UPPER(CONCAT('%',:codeLike,'%')) ORDER BY machine.code")
	List<MachineDO> findMachinesWithCodeLike(@Param("codeLike") String codeLike, Pageable pageable);

	@Query("from MachineDO machine where UPPER(machine.code) = UPPER(:machineCode)")
	MachineDO findMachineWithCode(String machineCode);

	@Query("from MachineDO machine ORDER BY machine.name")
	Page<MachineDO> findPagedAllMachines(Pageable paging);

	@Query("from MachineDO machine where UPPER(machine.code) LIKE UPPER(CONCAT('%',:codeLike,'%')) ORDER BY machine.code")
	Page<MachineDO> findPagedMachinesWithCodeLike(String codeLike, Pageable paging);

}
