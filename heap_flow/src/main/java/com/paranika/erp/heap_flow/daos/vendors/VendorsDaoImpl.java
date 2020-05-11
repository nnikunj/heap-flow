package com.paranika.erp.heap_flow.daos.vendors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
		List<VendorDO> l = vendorsRepo.findVendorsWithNameLike(nameLike);
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
}
