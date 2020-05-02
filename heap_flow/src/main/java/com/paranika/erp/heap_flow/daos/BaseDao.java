package com.paranika.erp.heap_flow.daos;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseDao {
	public Session getSession();

}
