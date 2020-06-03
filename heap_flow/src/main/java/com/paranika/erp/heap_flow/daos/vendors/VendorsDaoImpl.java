package com.paranika.erp.heap_flow.daos.vendors;

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

import com.paranika.erp.heap_flow.common.models.dos.VendorDO;
import com.paranika.erp.heap_flow.daos.BaseDaoImpl;
import com.paranika.erp.heap_flow.daos.defaultProviders.VendorsRepository;

@Component
public class VendorsDaoImpl extends BaseDaoImpl implements VendorsDaoIx {

	@Autowired
	private VendorsRepository vendorsRepo;
	@Autowired
	@PersistenceContext
	EntityManager em;

	private final Logger logger = LoggerFactory.getLogger(VendorsDaoImpl.class);

	@Override
	public List<VendorDO> getAllVendors() throws Exception {
		ArrayList<VendorDO> retList = new ArrayList<VendorDO>();
		vendorsRepo.findAll().forEach(retList::add);
		return retList;
	}

	@Override
	public List<VendorDO> getAllVendorsWithNameLike(String nameLike) throws Exception {
		if (nameLike == null || nameLike.isEmpty()) {
			return null;
		}
		List<VendorDO> l = vendorsRepo.findVendorsWithNameLike(nameLike, PageRequest.of(0, 20));
		return l;
	}

	@Override
	public List<VendorDO> getAllvendorsWithPagination(int startRecord, int pageSize) throws Exception {
		if (startRecord < 0 || pageSize == 0) {
			// False pagination criteria send them all records.
			return getAllVendors();
		}
		Query q = em.createNamedQuery("VendorDO.all");
		q.setFirstResult(startRecord);
		q.setMaxResults(pageSize);
		List<VendorDO> retList = q.getResultList();
		return retList;
	}

	@Override
	public void add(VendorDO vendorDO) throws Exception {
		vendorsRepo.save(vendorDO);

	}

	@Override
	public void saveAll(Collection<VendorDO> vendorsDOs) throws Exception {
		vendorsRepo.saveAll(vendorsDOs);
	}

	@Override
	@Transactional
	public void mergeAll(Collection<VendorDO> vendorsDOs) throws Exception {
		if (vendorsDOs == null || vendorsDOs.size() == 0) {
			throw new Exception("Empty collection recieved, cannot process with save or persist.");
		}
		Query q = em.createNamedQuery("VendorDO.bySearchName");

		for (VendorDO vendorDO : vendorsDOs) {
			String vendorSearchName = vendorDO.getSearchName();
			if (StringUtils.isEmpty(vendorSearchName)) {
				continue;
			}
			q.setParameter("searchName", vendorSearchName);

			List<VendorDO> retList = q.getResultList();
			if (retList != null && retList.size() == 1) {
				VendorDO dbFetchedDO = retList.get(0);
				dbFetchedDO.setAddress(vendorDO.getAddress());
				dbFetchedDO.setAddress2(vendorDO.getAddress2());
				dbFetchedDO.setCity(vendorDO.getCity());
				dbFetchedDO.setContactPerson(vendorDO.getContactPerson());
				dbFetchedDO.setEmail(vendorDO.getEmail());
				dbFetchedDO.setGstRegNo(vendorDO.getGstRegNo());
				dbFetchedDO.setName(vendorDO.getName());
				dbFetchedDO.setPanNumber(vendorDO.getPanNumber());
				dbFetchedDO.setPhone(vendorDO.getPhone());
				dbFetchedDO.setStateCode(vendorDO.getStateCode());
				dbFetchedDO.setVendorId(vendorDO.getVendorId());

				em.merge(dbFetchedDO);

			} else {
				em.persist(vendorDO);
			}
		}

	}

	@Override
	public VendorDO getVendorwithCode(String vendorCode) throws Exception {

		if (vendorCode == null || vendorCode.isEmpty()) {
			return null;
		}
		VendorDO fetchedObj = vendorsRepo.findVendorWithId(vendorCode);
		return fetchedObj;
	}

	@Override
	public Page<VendorDO> getPagedVendorsWithSearchNameLike(String searchNameLike, Pageable paging) throws Exception {
		logger.debug("Entered getPagedVendorsWithSearchNameLike searchNameLike: " + searchNameLike);
		if (StringUtils.isEmpty(searchNameLike)) {
			return vendorsRepo.findPagedAllVendors(paging);
		} else {
			return vendorsRepo.findPagedVendorsWithSearchNameLike(searchNameLike, paging);
		}

	}

	@Override
	@Transactional
	public VendorDO addOrUpdate(VendorDO vendorDO) throws Exception {

		VendorDO dbFetchedDO = getVendorwithCode(vendorDO.getVendorId());
		VendorDO dataPersisted = null;
		if (dbFetchedDO == null) {
			logger.debug("Fresh save.");
			dataPersisted = vendorsRepo.save(vendorDO);
		} else {
			logger.debug("Entity exists, Updating it.");
			dbFetchedDO.setAddress(vendorDO.getAddress());
			dbFetchedDO.setAddress2(vendorDO.getAddress2());
			dbFetchedDO.setCity(vendorDO.getCity());
			dbFetchedDO.setContactPerson(vendorDO.getContactPerson());
			dbFetchedDO.setEmail(vendorDO.getEmail());
			dbFetchedDO.setGstRegNo(vendorDO.getGstRegNo());
			dbFetchedDO.setName(vendorDO.getName());
			dbFetchedDO.setPanNumber(vendorDO.getPanNumber());
			dbFetchedDO.setPhone(vendorDO.getPhone());
			dbFetchedDO.setStateCode(vendorDO.getStateCode());
			dataPersisted = em.merge(dbFetchedDO);
		}
		return dataPersisted;
	}

	@Override
	public VendorDO getNamedVendor(String searchName) throws Exception {
		logger.debug("Searching exact vendor with searchName: " + searchName);
		return vendorsRepo.findVendorWithName(searchName);
	}
}
