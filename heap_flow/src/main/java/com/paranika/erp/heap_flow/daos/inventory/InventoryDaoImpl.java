package com.paranika.erp.heap_flow.daos.inventory;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paranika.erp.heap_flow.common.models.dos.IngressLedgerDO;
import com.paranika.erp.heap_flow.common.models.dos.InventoryDO;
import com.paranika.erp.heap_flow.common.models.dos.InventoryItemDO;
import com.paranika.erp.heap_flow.common.models.dos.InventoryTypeDO;
import com.paranika.erp.heap_flow.daos.BaseDaoImpl;
import com.paranika.erp.heap_flow.daos.defaultProviders.IngressLedgersRepository;
import com.paranika.erp.heap_flow.daos.defaultProviders.InventoriesRepository;

@Component
public class InventoryDaoImpl extends BaseDaoImpl implements InventoryDaoIX {

	@Autowired
	InventoriesRepository invRepo;

	@Autowired
	IngressLedgersRepository ingressRepo;
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

}
