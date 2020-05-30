package com.paranika.erp.heap_flow.daos.machines;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.paranika.erp.heap_flow.common.models.dos.MachineDO;
import com.paranika.erp.heap_flow.daos.BaseDaoImpl;
import com.paranika.erp.heap_flow.daos.defaultProviders.MachinesRepository;

@Component
public class MachinesDaoImpl extends BaseDaoImpl implements MachinesDaoIx {

	@Autowired
	private MachinesRepository machinesRepo;
	@Autowired
	@PersistenceContext
	EntityManager em;
	private final Logger logger = LoggerFactory.getLogger(MachinesDaoImpl.class);

	@Override
	public List<MachineDO> getAllMachines() throws Exception {
		ArrayList<MachineDO> retList = new ArrayList<MachineDO>();
		machinesRepo.findAll().forEach(retList::add);
		return retList;
	}

	@Override
	public List<MachineDO> getAllMachinesWithPagination(int startRecord, int pageSize) throws Exception {
		if (startRecord < 0 || pageSize == 0) {
			// False pagination criteria send them all records.
			return getAllMachines();
		}
		Query q = em.createNamedQuery("MachineDO.all");
		q.setFirstResult(startRecord);
		q.setMaxResults(pageSize);
		List<MachineDO> retList = q.getResultList();
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

//	@Override
//	// @Transactional
//	public void mergeAll(Collection<MachineDO> machineDOs) throws Exception {
//		if (machineDOs == null || machineDOs.size() == 0) {
//			throw new Exception("Empty collection recieved, cannot process with save or persist.");
//		}
//		Session session = getSession();
//		try {
//			org.hibernate.Transaction tx = session.beginTransaction();
//			for (MachineDO machineDO : machineDOs) {
//
//				Query q = session.getNamedQuery("MachineDO.byName");
//				String machineName = machineDO.getName();
//				if (StringUtils.isEmpty(machineName)) {
//					continue;
//				}
//				q.setParameter("machineName", machineDO.getName());
//
//				List<MachineDO> retList = q.getResultList();
//				if (retList != null && retList.size() == 1) {
//					MachineDO dbFetchedDO = retList.get(0);
//					dbFetchedDO.setCategory(machineDO.getCategory());
//					dbFetchedDO.setCode(machineDO.getCode());
//					dbFetchedDO.setkWKva(machineDO.getkWKva());
//					dbFetchedDO.setMake(machineDO.getMake());
//					dbFetchedDO.setModel(machineDO.getModel());
//					dbFetchedDO.setSerialNo(machineDO.getSerialNo());
//					session.merge(dbFetchedDO);
//				} else {
//					// add(machineDO);
//					session.persist(machineDO);
//				}
//				// session.getTransaction().commit();
//			}
//			tx.commit();
//
//		} catch (Exception e) {
//			// Rollback the txn
//			session.getTransaction().rollback();
//		} finally {
//
//			session.close();
//
//		}
//
//	}
//	@Transactional
//	private void persist(MachineDO machineDO) throws Exception {
//
//		// machinesRepo.save(machineDO);
//		em.persist(machineDO);
//	}
//
	@Override
	@Transactional
	public void mergeAll(Collection<MachineDO> machineDOs) throws Exception {
		if (machineDOs == null || machineDOs.size() == 0) {
			throw new Exception("Empty collection recieved, cannot process with save or persist.");
		}
		Query q = em.createNamedQuery("MachineDO.byCode");

		for (MachineDO machineDO : machineDOs) {
			String machinecode = machineDO.getCode();
			if (StringUtils.isEmpty(machinecode)) {
				continue;
			}
			q.setParameter("code", machinecode);

			List<MachineDO> retList = q.getResultList();
			if (retList != null && retList.size() == 1) {
				MachineDO dbFetchedDO = retList.get(0);
				dbFetchedDO.setCategory(machineDO.getCategory());
				dbFetchedDO.setName(machineDO.getName());
				dbFetchedDO.setkWKva(machineDO.getkWKva());
				dbFetchedDO.setMake(machineDO.getMake());
				dbFetchedDO.setModel(machineDO.getModel());
				dbFetchedDO.setSerialNo(machineDO.getSerialNo());
				em.merge(dbFetchedDO);

			} else {
				em.persist(machineDO);
			}
		}

	}

	@Override
	public List<MachineDO> getAllMachinesWithCodeLike(String codeLike) throws Exception {
		if (codeLike == null || codeLike.isEmpty()) {
			return null;
		}
		List<MachineDO> l = machinesRepo.findMachinesWithCodeLike(codeLike, PageRequest.of(0, 20));
		return l;
	}

	@Override
	public MachineDO getMachinewithCode(String machineCode) throws Exception {

		if (machineCode == null || machineCode.isEmpty()) {
			return null;
		}
		MachineDO fetchedObj = machinesRepo.findMachineWithCode(machineCode);
		return fetchedObj;
	}

	@Override
	public Page<MachineDO> getPagedMachinesWithIdLike(String codeLike, Pageable paging) throws Exception {

		if (StringUtils.isEmpty(codeLike)) {
			return machinesRepo.findPagedAllMachines(paging);
		} else {
			return machinesRepo.findPagedMachinesWithCodeLike(codeLike, paging);
		}

	}

	@Override
	@Transactional
	public MachineDO addOrUpdate(MachineDO data) throws Exception {
		MachineDO dbFetchedDO = getMachinewithCode(data.getCode());
		MachineDO dataPersisted = null;
		if (dbFetchedDO == null) {
			logger.debug("Fresh save.");
			dataPersisted = machinesRepo.save(data);
		} else {
			logger.debug("Entity exists, Updating it.");
			dbFetchedDO.setCategory(data.getCategory());
			dbFetchedDO.setName(data.getName());
			dbFetchedDO.setkWKva(data.getkWKva());
			dbFetchedDO.setMake(data.getMake());
			dbFetchedDO.setModel(data.getModel());
			dbFetchedDO.setSerialNo(data.getSerialNo());
			dataPersisted = em.merge(dbFetchedDO);
		}
		return dataPersisted;
	}
}
