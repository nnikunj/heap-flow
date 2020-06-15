package com.paranika.erp.heap_flow_reports.common.models.dos;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

import com.paranika.erp.heap_flow_reports.common.models.dtos.InventoryItemDescriptions;

@Entity

@SqlResultSetMapping(name = "findFastMovingAnalysisMapping", classes = @ConstructorResult(targetClass = FastMovingItemsPojo.class, //
		columns = { //
				@ColumnResult(name = "id", type = Long.class), //
				@ColumnResult(name = "code", type = String.class), //
				@ColumnResult(name = "category", type = String.class), //
				@ColumnResult(name = "uom", type = String.class), //
				@ColumnResult(name = "descriptions", type = String.class), //
				@ColumnResult(name = "totalQuantity", type = Double.class), //
				@ColumnResult(name = "totalValue", type = Double.class) //
		}))
@NamedNativeQuery(name = "FastMovingItemsPojo.findFastMovingAnalysis", query = "select "
		+ " i.id as id ,i.inventory_item_code as code "
		+ "		 , i.item_category_code as category,  i.base_unit_measure as uom,"
		+ "		 i.descriptions as descriptions, sum(eg.outgoing_quantity) as totalQuantity,  "
		+ "		 sum(eg.outgoing_mat_price) as totalValue   from egress_ledgers eg  "
		+ "		 inner join inventory_items i on i.id=eg.inventory_item_fk  "
		+ "         where  (eg.record_date >(:startDate)  and record_date <=(:endDate)) "
		+ "		  group by inventory_item_fk "
		+ "             order by totalValue DESC, totalQuantity DESC ;", resultSetMapping = "findFastMovingAnalysisMapping")
public class FastMovingItemsPojo {
	public FastMovingItemsPojo() {

	}

	public FastMovingItemsPojo(Long id, String code, String category, String uom, String descriptions,
			Double totalQuantity, Double totalValue) {
		this.id = id;
		this.itemCode = code;
		this.unitOfMeasurement = uom;
		this.totalQuantity = totalQuantity;
		this.totalValue = totalValue;
		this.category = category;
		this.descriptions = InventoryItemDescriptions.fromJson(descriptions).getDescription();
	}

	@Id
	private Long id;

	private String itemCode;

	private String unitOfMeasurement;

	private String descriptions;

	private Double totalQuantity;

	private Double totalValue;
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

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public Double getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
