package com.paranika.erp.heap_flow_reports.common.models.dos;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NamedQuery(name = "InventoryItemDO.inventoryItemCode", query = "from InventoryItemDO where inventoryItemCode=:inventoryItemCode")
@NamedQuery(name = "InventoryItemDO.all", query = "from InventoryItemDO")
@Table(name = "inventory_items")
public class InventoryItemDO extends BaseDO {
	private static final long serialVersionUID = 1L;

	@Column(name = "inventory_item_code", nullable = false, length = 32)
	private String inventoryItemCode;

	// This column will hold json form of descriptions
	@Column(name = "descriptions", nullable = true)
	@Lob
	private String descriptions;

	@Column(name = "base_unit_measure", nullable = true, length = 32)
	private String baseUnitMeasure;

	@Column(name = "product_group_code", nullable = true, length = 32)
	private String productGrpCode;
	@Column(name = "gen_product_posting_group", nullable = true, length = 32)
	private String genProductPostingGrp;
	@Column(name = "item_category_code", nullable = true, length = 32)
	private String itemCategoryCode;
	@Column(name = "gst_grp_code", nullable = true, length = 32)
	private String gstGrpCode;
	@Column(name = "hsn_sac_code", nullable = true, length = 10)
	private String hsnSacCode;

	@Column(name = "reserve_qunatity", nullable = true)
	private Double reserveQuantAlert;
	@Column(name = "max_order_qunatity", nullable = true)
	private Double maxOrderQuant;

	@Column(name = "re_order_qunatity", nullable = true)
	private Double reOrderQuant;

	public String getInventoryItemCode() {
		return inventoryItemCode;
	}

	public void setInventoryItemCode(String inventoryItemCode) {
		this.inventoryItemCode = inventoryItemCode;
	}

	@OneToMany(mappedBy = "item")
	private Collection<InventoryDO> stocks = new ArrayList<InventoryDO>();

	@JsonIgnore
	@OneToMany(mappedBy = "incomingMaterial")
	private Collection<IngressLedgerDO> ingressLedgers = new ArrayList<IngressLedgerDO>();

	@JsonIgnore
	@OneToMany(mappedBy = "outgoingMaterial")
	private Collection<EgressLedgerDO> egressLedgers = new ArrayList<EgressLedgerDO>();

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public String getBaseUnitMeasure() {
		return baseUnitMeasure;
	}

	public void setBaseUnitMeasure(String baseUnitMeasure) {
		this.baseUnitMeasure = baseUnitMeasure;
	}

	public String getProductGrpCode() {
		return productGrpCode;
	}

	public void setProductGrpCode(String productGrpCode) {
		this.productGrpCode = productGrpCode;
	}

	public String getGenProductPostingGrp() {
		return genProductPostingGrp;
	}

	public void setGenProductPostingGrp(String genProductPostingGrp) {
		this.genProductPostingGrp = genProductPostingGrp;
	}

	public String getItemCategoryCode() {
		return itemCategoryCode;
	}

	public void setItemCategoryCode(String itemCategoryCode) {
		this.itemCategoryCode = itemCategoryCode;
	}

	public String getGstGrpCode() {
		return gstGrpCode;
	}

	public void setGstGrpCode(String gstGrpCode) {
		this.gstGrpCode = gstGrpCode;
	}

	public String getHsnSacCode() {
		return hsnSacCode;
	}

	public void setHsnSacCode(String hsnSacCode) {
		this.hsnSacCode = hsnSacCode;
	}

	public Collection<InventoryDO> getStocks() {
		return stocks;
	}

	public void setStocks(Collection<InventoryDO> stocks) {
		this.stocks = stocks;
	}

	public Collection<IngressLedgerDO> getIngressLedgers() {
		return ingressLedgers;
	}

	public void setIngressLedgers(Collection<IngressLedgerDO> ingressLedgers) {
		this.ingressLedgers = ingressLedgers;
	}

	public Collection<EgressLedgerDO> getEgressLedgers() {
		return egressLedgers;
	}

	public void setEgressLedgers(Collection<EgressLedgerDO> egressLedgers) {
		this.egressLedgers = egressLedgers;
	}

	public Double getReserveQuantAlert() {
		return reserveQuantAlert;
	}

	public void setReserveQuantAlert(Double reserveQuantAlert) {
		this.reserveQuantAlert = reserveQuantAlert;
	}

	public Double getMaxOrderQuant() {
		return maxOrderQuant;
	}

	public void setMaxOrderQuant(Double maxOrderQuant) {
		this.maxOrderQuant = maxOrderQuant;
	}

	public Double getReOrderQuant() {
		return reOrderQuant;
	}

	public void setReOrderQuant(Double reOrderQuant) {
		this.reOrderQuant = reOrderQuant;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryItemDO [inventoryItemCode=");
		builder.append(inventoryItemCode);
		builder.append(", descriptions=");
		builder.append(descriptions);
		builder.append(", baseUnitMeasure=");
		builder.append(baseUnitMeasure);
		builder.append(", productGrpCode=");
		builder.append(productGrpCode);
		builder.append(", genProductPostingGrp=");
		builder.append(genProductPostingGrp);
		builder.append(", itemCategoryCode=");
		builder.append(itemCategoryCode);
		builder.append(", gstGrpCode=");
		builder.append(gstGrpCode);
		builder.append(", hsnSacCode=");
		builder.append(hsnSacCode);
		builder.append(", reserveQuantAlert=");
		builder.append(reserveQuantAlert);
		builder.append(", maxOrderQuant=");
		builder.append(maxOrderQuant);
		builder.append(", reOrderQuant=");
		builder.append(reOrderQuant);
		builder.append(", stocks=");
		builder.append(stocks);
		builder.append(", ingressLedgers=");
		builder.append(ingressLedgers);
		builder.append(", egressLedgers=");
		builder.append(egressLedgers);
		builder.append("]");
		return builder.toString();
	}

}
