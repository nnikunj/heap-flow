package com.paranika.erp.heap_flow_reports.common.models.dos;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

//, uniqueConstraints = @UniqueConstraint(columnNames = { "CODE" })
@Entity
@NamedQuery(name = "MachineDO.byCode", query = "from MachineDO where code=:code")
@NamedQuery(name = "MachineDO.all", query = "from MachineDO")
@Table(name = "machines")
public class MachineDO extends BaseDO {
	private static final long serialVersionUID = 1L;

	@Column(name = "serial_no", nullable = true, length = 32)
	private String serialNo;

	@Column(name = "name", nullable = false, length = 256)
	private String name;

	@Column(name = "code", nullable = true, length = 256)
	private String code;

	@Column(name = "model", nullable = true, length = 256)
	private String model;

	@Column(name = "make", nullable = true, length = 256)
	private String make;

	@Column(name = "category", nullable = true, length = 256)
	private String category;

	@Column(name = "kw_kva", nullable = true, length = 32)
	private String kWKva;

	@JsonIgnore
	@OneToMany(mappedBy = "consumingMachine")
	private Collection<EgressLedgerDO> usedStokcs = new ArrayList<EgressLedgerDO>();

	public Collection<EgressLedgerDO> getUsedStokcs() {
		return usedStokcs;
	}

	public void setUsedStokcs(Collection<EgressLedgerDO> usedStokcs) {
		this.usedStokcs = usedStokcs;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getkWKva() {
		return kWKva;
	}

	public void setkWKva(String kWKva) {
		this.kWKva = kWKva;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		String baseDo = super.toString();
		builder.append(baseDo);
		builder.append("\nMachineDO [serialNo=");
		builder.append(serialNo);
		builder.append(", name=");
		builder.append(name);
		builder.append(", code=");
		builder.append(code);
		builder.append(", model=");
		builder.append(model);
		builder.append(", make=");
		builder.append(make);
		builder.append(", category=");
		builder.append(category);
		builder.append(", kWKva=");
		builder.append(kWKva);
		builder.append("]");
		return builder.toString();
	}

}
