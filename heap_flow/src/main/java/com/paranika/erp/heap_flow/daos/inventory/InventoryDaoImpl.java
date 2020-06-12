package com.paranika.erp.heap_flow.daos.inventory;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.paranika.erp.heap_flow.common.exceptions.HeapFlowException;
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
	private final Logger logger = LoggerFactory.getLogger(InventoryDaoImpl.class);

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

	@Override
	public Page<IngressLedgerDO> getPagedAcceptedMaterialWithIdLike(String idLike, Pageable paging) throws Exception {
		if (StringUtils.isEmpty(idLike)) {
			return ingressRepo.findAll(paging);
		} else {
			return ingressRepo.findByIncomingMaterial_InventoryItemCodeIgnoreCaseContaining(idLike, paging);
		}
	}

	@Override
	@Transactional
	public void deleteIsuedItem(Long dbId) throws Exception {
		Optional<EgressLedgerDO> dbObj = egressRepo.findById(dbId);
		EgressLedgerDO dbEgressObj = dbObj.get();
		InventoryItemDO item = dbEgressObj.getOutgoingMaterial();
		Double averageUnitPrice = dbEgressObj.getOutgoingMaterialPrice();

		double isuedQuantity = dbEgressObj.getOutgoingQuantity();
		InventoryTypeDO type = dbEgressObj.getInventoryType();
		InventoryDO inventory = invRepo.findInventoryWithProductAndType(item, type);
		logger.debug("Deleting Egress ledger item with dbId: " + dbId + " for item: " + item.getInventoryItemCode()
				+ " and type: " + type.getTypeName() + " isuedQuantity: " + isuedQuantity + " averageUnitPrice: "
				+ averageUnitPrice);
		logger.debug("Db operation started.");
		if (inventory != null) {
			logger.debug("Inventory pre-existing, Just altering quantity.");
			inventory.setQuantity(inventory.getQuantity() + isuedQuantity);
			em.merge(inventory);
		} else {
			if (averageUnitPrice == null) {
				throw new HeapFlowException(
						"Outgoing material price is null and hence cannot update deleted inventory price.");
			}
			logger.debug("Inventory not existing, Creating fresh inventory.");
			inventory = new InventoryDO();
			inventory.setAverageUnitPrice(averageUnitPrice);
			inventory.setItem(item);
			inventory.setNotes("Inventory Recreated by virtue of issued item deletion.");
			inventory.setQuantity(isuedQuantity);
			inventory.setType(type);
			invRepo.save(inventory);
		}
		egressRepo.delete(dbEgressObj);

	}

	@Override
	@Transactional
	public void deleteAcceptedItem(Long dbId) throws Exception {

		Optional<IngressLedgerDO> dbObj = ingressRepo.findById(dbId);
		IngressLedgerDO dbIngressObj = dbObj.get();
		InventoryItemDO item = dbIngressObj.getIncomingMaterial();
		Double averageUnitPrice = dbIngressObj.getPricePerUnit();

		double acceptedQuantity = dbIngressObj.getIncomingQuantity();
		InventoryTypeDO type = dbIngressObj.getInventoryType();
		InventoryDO inventory = invRepo.findInventoryWithProductAndType(item, type);
		logger.debug("Deleting Ingress ledger item with dbId: " + dbId + " for item: " + item.getInventoryItemCode()
				+ " and type: " + type.getTypeName() + " isuedQuantity: " + acceptedQuantity + " averageUnitPrice: "
				+ averageUnitPrice);
		logger.debug("Db operation started.");
		if (inventory != null) {
			if (inventory.getQuantity() < acceptedQuantity) {
				throw new HeapFlowException("Part of accepted material is already issued, "
						+ "cannot cancel it until issued material is accepted back.");
			}
			if (inventory.getQuantity() == acceptedQuantity) {
				logger.debug("Deleting simply inventory as there are only that much item as accepted.");
				invRepo.delete(inventory);
			} else {
				logger.debug("Altering quantity and price for inventory.");
				double existingAvgPrice = inventory.getAverageUnitPrice();
				double existingQuant = inventory.getQuantity();
				existingAvgPrice = (((existingQuant * existingAvgPrice) - (acceptedQuantity * averageUnitPrice))
						/ (existingQuant - acceptedQuantity));
				// New Quant
				existingQuant = existingQuant - acceptedQuantity;
				inventory.setAverageUnitPrice(existingAvgPrice);
				inventory.setQuantity(existingQuant);
				inventory.setNotes("Data altered by cancellation of accepted item.");
				em.merge(inventory);
			}
		} else {
			throw new HeapFlowException("Inventory not existing for given type cannot cancel accepted item.");
		}
		ingressRepo.delete(dbIngressObj);

	}

}
