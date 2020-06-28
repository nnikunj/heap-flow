package com.paranika.erp.heap_flow.common.models.dos;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

import com.paranika.erp.heap_flow.common.models.dtos.InventoryItemDescriptions;

@Entity
@SqlResultSetMapping(name = "findReserveItemsMapping", classes = @ConstructorResult(targetClass = MinQuantNotifiationEntityProxy.class, //
		columns = { //
				@ColumnResult(name = "id", type = Long.class), //
				@ColumnResult(name = "code", type = String.class), //
				@ColumnResult(name = "category", type = String.class), //
				@ColumnResult(name = "uom", type = String.class), //
				@ColumnResult(name = "descriptions", type = String.class), //
				@ColumnResult(name = "available_quant", type = Double.class), //
				@ColumnResult(name = "reserve_qunatity", type = Double.class) //
		}))
@NamedNativeQuery(name = "MinQuantNotifiationEntityProxy.findReserveItems", query = "select items.id,"
		+ " items.inventory_item_code as code, " // --
		+ " items.item_category_code as category," // --
		+ " items.descriptions, " // --
		+ " items.base_unit_measure as uom ," // --
		+ " sum(inv.quantity) as available_quant, " // --
		+ " items.reserve_qunatity" // --
		+ " from inventory_items items" // --
		+ " left join inventories inv on items.id = inv.inventory_item_fk" // --
		+ " where items.reserve_qunatity is not null and items.reserve_qunatity >0" // --
		+ " group by items.id" // --
		+ "  having ((available_quant is null) or (available_quant < items.reserve_qunatity))", // --
		resultSetMapping = "findReserveItemsMapping"

)
public class MinQuantNotifiationEntityProxy {

	public MinQuantNotifiationEntityProxy() {

	}

	public MinQuantNotifiationEntityProxy(Long id, String code, String category, String uom, String descriptions,
			Double available_quant, Double reserve_qunatity) {
		this.id = id;
		this.itemCode = code;
		this.uom = uom;
		this.avialableQuantity = available_quant;
		this.reservQuantity = reserve_qunatity;
		this.category = category;
		this.descriptions = InventoryItemDescriptions.fromJson(descriptions).getDescription();
	}

	@Id
	private Long id;

	private String itemCode;

	private String uom;

	private String descriptions;

	private Double avialableQuantity;

	private Double reservQuantity;
	private String category;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public Double getAvialableQuantity() {
		return avialableQuantity;
	}

	public void setAvialableQuantity(Double avialableQuantity) {
		this.avialableQuantity = avialableQuantity;
	}

	public Double getReservQuantity() {
		return reservQuantity;
	}

	public void setReservQuantity(Double reservQuantity) {
		this.reservQuantity = reservQuantity;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
