package com.paranika.erp.heap_flow.daos.inventory;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.paranika.erp.heap_flow.common.models.dos.EgressLedgerDO;
import com.paranika.erp.heap_flow.common.models.dos.IngressLedgerDO;
import com.paranika.erp.heap_flow.common.models.dos.InventoryDO;
import com.paranika.erp.heap_flow.common.models.dos.InventoryItemDO;
import com.paranika.erp.heap_flow.common.models.dos.InventoryTypeDO;
import com.paranika.erp.heap_flow.daos.BaseDaoImpl;
import com.paranika.erp.heap_flow.daos.defaultProviders.EgressLedgersRepository;
import com.paranika.erp.heap_flow.daos.defaultProviders.IngressLedgersRepository;
import com.paranika.erp.heap_flow.daos.defaultProviders.InventoriesRepository;

@Component
public class InventoryDaoImpl extends BaseDaoImpl implements InventoryDaoIX {

	@Autowired
	InventoriesRepository invRepo;

	@Autowired
	IngressLedgersRepository ingressRepo;

	@Autowired
	EgressLedgersRepository egressRepo;
	@Autowired
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void persistAllIngressLedgers(Collection<IngressLedgerDO> ledgers) throws Exception {
		for (IngressLedgerDO ingressLedgerDO : ledgers) {
			InventoryItemDO item = ingressLedgerDO.getIncomingMaterial();
			InventoryTypeDO type = ingressLedgerDO.getInventoryType();
			Double price = ingressLedgerDO.getPricePerUnit();
			Double quant = ingressLedgerDO.getIncomingQuantity();
			InventoryDO inventory = invRepo.findInventoryWithProductAndType(item, type);
			if (inventory == null) {
				inventory = new InventoryDO();
				inventory.setItem(item);
				inventory.setType(type);
				inventory.setAverageUnitPrice(price);
				inventory.setQuantity(quant);
				invRepo.save(inventory);
			} else {
				Double existingAvgPrice = inventory.getAverageUnitPrice();
				Double existingQuant = inventory.getQuantity();
				// New Avg Price
				existingAvgPrice = ((existingQuant * existingAvgPrice) + (quant * price)) / (existingQuant + quant);
				// New Quant
				existingQuant = existingQuant + quant;
				inventory.setAverageUnitPrice(existingAvgPrice);
				inventory.setQuantity(existingQuant);
				em.merge(inventory);
			}
			ingressRepo.save(ingressLedgerDO);
		}
	}

	@Override
	@Transactional
	public void persistAllEgressLedgers(Collection<EgressLedgerDO> ledgerList) throws Exception {
		for (EgressLedgerDO egressLedgerDO : ledgerList) {
			InventoryItemDO item = egressLedgerDO.getOutgoingMaterial();
			InventoryTypeDO type = egressLedgerDO.getInventoryType();

			Double quant = egressLedgerDO.getOutgoingQuantity();

			InventoryDO inventory = invRepo.findInventoryWithProductAndType(item, type);
			Double newQuant = inventory.getQuantity() - quant;
			if (newQuant == 0) {
				invRepo.delete(inventory);
			} else {
				inventory.setQuantity(newQuant);
				em.merge(inventory);
			}
			egressRepo.save(egressLedgerDO);
		}

	}

	@Override
	@Transactional
	public void mergeAll(Collection<InventoryDO> oldInventories) throws Exception {

		for (InventoryDO inventoryDO : oldInventories) {

			InventoryDO inventory = invRepo.findInventoryWithProductAndType(inventoryDO.getItem(),
					inventoryDO.getType());
			if (inventory != null) {
				Double price = inventoryDO.getAverageUnitPrice();
				Double quant = inventoryDO.getQuantity();
				Double existingAvgPrice = inventory.getAverageUnitPrice();
				Double existingQuant = inventory.getQuantity();
				// New Avg Price
				existingAvgPrice = ((existingQuant * existingAvgPrice) + (quant * price)) / (existingQuant + quant);
				// New Quant
				existingQuant = existingQuant + quant;
				inventory.setAverageUnitPrice(existingAvgPrice);
				inventory.setQuantity(existingQuant);
				inventory.setNotes("Excel data Merged with old inventory detail");
				em.merge(inventory);
			} else {
				invRepo.save(inventoryDO);
			}
		}

	}

	@Override
	public Page<InventoryDO> getPagedInvSummaryWithIdLike(String idLike, Pageable paging) throws Exception {
		if (StringUtils.isEmpty(idLike)) {
			return invRepo.findPagedAllInv(paging);
		} else {
			return invRepo.findByItem_InventoryItemCodeIgnoreCaseContaining(idLike, paging);
		}
	}

	@Override
	public Page<EgressLedgerDO> getPagedIssuedMaterialWithIdLike(String idLike, Pageable paging) throws Exception {
		if (StringUtils.isEmpty(idLike)) {
			return egressRepo.findAll(paging);
		} else {
			return egressRepo.findByOutgoingMaterial_InventoryItemCodeIgnoreCaseContaining(idLike, paging);
		}
	}
}
