package com.paranika.erp.heap_flow.daos;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paranika.erp.heap_flow.daos.BaseDao;

@Component
public class BaseDaoImpl implements BaseDao {
	@Autowired
	EntityManager mgr;

	public Session getSession() {
		Session s = (Session) mgr.unwrap(Session.class);
		return s;
	}

}
