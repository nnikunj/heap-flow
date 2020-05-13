package com.paranika.erp.heap_flow.common.models.dos;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "inventory_types")
public class InventoryTypeDO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "type_name", nullable = false, length = 32)
	private String typeName;
	@Column(name = "description", nullable = false, length = 256)
	private String description;

	@Column(name = "is_considered_for_valuation", nullable = false)
	private boolean isConsideredForValuation;

	@JsonIgnore
	@OneToMany(mappedBy = "type")
	Collection<InventoryDO> inventories = new ArrayList<InventoryDO>();
	@JsonIgnore
	@OneToMany(mappedBy = "inventoryType")
	Collection<IngressLedgerDO> referringIngressLedger = new ArrayList<IngressLedgerDO>();

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isConsideredForValuation() {
		return isConsideredForValuation;
	}

	public void setConsideredForValuation(boolean isConsideredForValuation) {
		this.isConsideredForValuation = isConsideredForValuation;
	}

	public Collection<InventoryDO> getInventories() {
		return inventories;
	}

	public void setInventories(Collection<InventoryDO> inventories) {
		this.inventories = inventories;
	}

	public Collection<IngressLedgerDO> getReferringIngressLedger() {
		return referringIngressLedger;
	}

	public void setReferringIngressLedger(Collection<IngressLedgerDO> referringIngressLedger) {
		this.referringIngressLedger = referringIngressLedger;
	}

}
