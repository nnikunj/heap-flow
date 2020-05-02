package com.paranika.erp.heap_flow.common.models.dos;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//, uniqueConstraints = @UniqueConstraint(columnNames = { "CODE" })
@Entity
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

	@OneToMany(mappedBy = "consumingMachine")
	private Collection<EgressLedgerDO> usedStokcs = new ArrayList<EgressLedgerDO>();

	public Collection<EgressLedgerDO> getUsedStokcs() {
		return usedStokcs;
	}

	public void setUsedStokcs(Collection<EgressLedgerDO> usedStokcs) {
		this.usedStokcs = usedStokcs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((make == null) ? 0 : make.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((serialNo == null) ? 0 : serialNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MachineDO other = (MachineDO) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (make == null) {
			if (other.make != null)
				return false;
		} else if (!make.equals(other.make))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (serialNo == null) {
			if (other.serialNo != null)
				return false;
		} else if (!serialNo.equals(other.serialNo))
			return false;
		return true;
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
