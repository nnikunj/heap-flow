package com.paranika.erp.heap_flow.daos.inventoryItems;

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

import com.paranika.erp.heap_flow.common.models.dos.InventoryItemDO;
import com.paranika.erp.heap_flow.daos.BaseDaoImpl;
import com.paranika.erp.heap_flow.daos.defaultProviders.InventoryItemsRepository;

@Component
public class InventoryItemDaoImpl extends BaseDaoImpl implements InventoryItemDaoIx {

	@Autowired
	private InventoryItemsRepository inventoryItemsRepo;
	@Autowired
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<InventoryItemDO> getAllInventoryItems() throws Exception {

		ArrayList<InventoryItemDO> retList = new ArrayList<InventoryItemDO>();
		inventoryItemsRepo.findAll().forEach(retList::add);
		return retList;
	}

	@Override
	public List<InventoryItemDO> getAllInventoryItemsWithPagination(int startRecord, int pageSize) throws Exception {

		if (startRecord < 0 || pageSize == 0) {
			// False pagination criteria send them all records.
			return getAllInventoryItems();
		}
		Query q = em.createNamedQuery("InventoryItemDO.all");
		q.setFirstResult(startRecord);
		q.setMaxResults(pageSize);
		List<InventoryItemDO> retList = q.getResultList();
		return retList;
	}

	@Override
	public void add(InventoryItemDO inventoryItemDO) throws Exception {
		inventoryItemsRepo.save(inventoryItemDO);
	}

	@Override
	public void saveAll(Collection<InventoryItemDO> inventoryItemDOs) throws Exception {
		inventoryItemsRepo.saveAll(inventoryItemDOs);
	}

	@Override
	@Transactional
	public void mergeAll(Collection<InventoryItemDO> inventoryItemDOs) throws Exception {
		if (inventoryItemDOs == null || inventoryItemDOs.size() == 0) {
			throw new Exception("Empty collection recieved, cannot process with save or persist.");
		}
		Query q = em.createNamedQuery("InventoryItemDO.inventoryItemCode");

		for (InventoryItemDO inventoryItemDO : inventoryItemDOs) {
			String inventoryItemCode = inventoryItemDO.getInventoryItemCode();

			if (StringUtils.isEmpty(inventoryItemCode)) {
				continue;
			}
			q.setParameter("inventoryItemCode", inventoryItemCode);

			List<InventoryItemDO> retList = q.getResultList();
			if (retList != null && retList.size() == 1) {
				InventoryItemDO dbFetchedDO = retList.get(0);

				dbFetchedDO.setBaseUnitMeasure(inventoryItemDO.getBaseUnitMeasure());
				dbFetchedDO.setDescriptions(inventoryItemDO.getDescriptions());
				dbFetchedDO.setGenProductPostingGrp(inventoryItemDO.getGenProductPostingGrp());
				dbFetchedDO.setGstGrpCode(inventoryItemDO.getGstGrpCode());
				dbFetchedDO.setHsnSacCode(inventoryItemDO.getHsnSacCode());
				dbFetchedDO.setInventoryItemCode(inventoryItemCode);
				dbFetchedDO.setItemCategoryCode(inventoryItemDO.getItemCategoryCode());
				dbFetchedDO.setProductGrpCode(inventoryItemDO.getProductGrpCode());

				em.merge(dbFetchedDO);

			} else {
				em.persist(inventoryItemDO);
			}
		}
	}

}
