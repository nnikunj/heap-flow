package com.paranika.erp.heap_flow.daos.inventory;

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

import com.paranika.erp.heap_flow.common.models.dos.InventoryItemDO;
import com.paranika.erp.heap_flow.common.models.dos.MinQuantNotifiationEntityProxy;
import com.paranika.erp.heap_flow.daos.BaseDaoImpl;
import com.paranika.erp.heap_flow.daos.defaultProviders.InventoryItemsRepository;
import com.paranika.erp.heap_flow.daos.defaultProviders.MinQuantNotifiationEntityProxyRepository;

@Component
public class InventoryItemDaoImpl extends BaseDaoImpl implements InventoryItemDaoIx {

	@Autowired
	private InventoryItemsRepository inventoryItemsRepo;

	@Autowired
	MinQuantNotifiationEntityProxyRepository minQuantRepo;
	@Autowired
	@PersistenceContext
	private EntityManager em;
	private final Logger logger = LoggerFactory.getLogger(InventoryItemDaoImpl.class);

	@Override
	public List<InventoryItemDO> getAllInventoryItems() throws Exception {

		ArrayList<InventoryItemDO> retList = new ArrayList<InventoryItemDO>();
		inventoryItemsRepo.findAll().forEach(retList::add);
		return retList;
	}

	@Override
	public InventoryItemDO getInventoryItemswithCode(String prodCode) throws Exception {
		if (prodCode == null || prodCode.isEmpty()) {
			return null;
		}
		InventoryItemDO fetchedObj = inventoryItemsRepo.findItemWithId(prodCode);
		return fetchedObj;

	}

	@Override
	public List<InventoryItemDO> getAllInventoryItemsLikeItemCode(String idLike) throws Exception {
		if (idLike == null || idLike.isEmpty()) {
			return null;
		}
		List<InventoryItemDO> l = inventoryItemsRepo.findItemsWithIdLike(idLike.toUpperCase(), PageRequest.of(0, 20));
		return l;
	}

	@Override
	public Page<InventoryItemDO> getAllPagedInventoryItemsLikeItemCode(String optionalIdLike, Pageable pageable)
			throws Exception {
		logger.debug("getAllPagedInventoryItemsLikeItemCode invoked optionalIdLike: " + optionalIdLike);
		Page<InventoryItemDO> retColl = null;
		if (optionalIdLike == null || optionalIdLike.isEmpty()) {
			retColl = inventoryItemsRepo.findPagedAllItems(pageable);
		} else {
			retColl = inventoryItemsRepo.findPagedItemsWithIdLike(optionalIdLike, pageable);
		}
		logger.debug("Exiting getAllPagedInventoryItemsLikeItemCode");
		return retColl;
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
	public InventoryItemDO add(InventoryItemDO inventoryItemDO) throws Exception {
		InventoryItemDO savedItem = inventoryItemsRepo.save(inventoryItemDO);
		return savedItem;

	}

	@Override
	@Transactional
	public InventoryItemDO addOrUpdate(InventoryItemDO inventoryItemDO) throws Exception {
		InventoryItemDO itemFetched = getInventoryItemswithCode(inventoryItemDO.getInventoryItemCode());
		InventoryItemDO dataPersisted = null;
		if (itemFetched == null) {
			dataPersisted = add(inventoryItemDO);
		} else {
			itemFetched.setBaseUnitMeasure(inventoryItemDO.getBaseUnitMeasure());
			itemFetched.setDescriptions(inventoryItemDO.getDescriptions());
			itemFetched.setGenProductPostingGrp(inventoryItemDO.getGenProductPostingGrp());
			itemFetched.setGstGrpCode(inventoryItemDO.getGstGrpCode());
			itemFetched.setHsnSacCode(inventoryItemDO.getHsnSacCode());
			itemFetched.setItemCategoryCode(inventoryItemDO.getItemCategoryCode());
			itemFetched.setProductGrpCode(inventoryItemDO.getProductGrpCode());
			itemFetched.setReserveQuantAlert(inventoryItemDO.getReserveQuantAlert());
			itemFetched.setReOrderQuant(inventoryItemDO.getReOrderQuant());
			itemFetched.setMaxOrderQuant(inventoryItemDO.getMaxOrderQuant());
			dataPersisted = em.merge(itemFetched);
		}
		return dataPersisted;
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

	@Override
	public Page<InventoryItemDO> getPagedItemsWithIdLike(String idLike, Pageable paging) throws Exception {

		if (StringUtils.isEmpty(idLike)) {
			return inventoryItemsRepo.findPagedAllItems(paging);
		} else {
			return inventoryItemsRepo.findPagedItemsWithIdLike(idLike, paging);
		}

	}

	@Override
	public Page<MinQuantNotifiationEntityProxy> getPagedReservedItems(Pageable paging) throws Exception {
		return minQuantRepo.findReserveItems(paging);
	}
}
