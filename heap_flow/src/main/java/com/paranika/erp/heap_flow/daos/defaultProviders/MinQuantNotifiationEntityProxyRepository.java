package com.paranika.erp.heap_flow.daos.defaultProviders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.paranika.erp.heap_flow.common.models.dos.MinQuantNotifiationEntityProxy;

public interface MinQuantNotifiationEntityProxyRepository extends JpaRepository<MinQuantNotifiationEntityProxy, Long> {

	public Page<MinQuantNotifiationEntityProxy> findReserveItems(Pageable pageable);

}
