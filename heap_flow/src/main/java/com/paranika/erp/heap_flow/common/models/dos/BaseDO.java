package com.paranika.erp.heap_flow.common.models.dos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@MappedSuperclass
public abstract class BaseDO implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Version
	@Column(name = "version", nullable = false)
	private Integer version;

	@Column(name = "modified", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	@Column(name = "creation", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	@PreUpdate
	@PrePersist
	public void prePersist() {
		modified = new Date();
		if (creation == null) {
			creation = new Date();
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BaseDO [id=");
		builder.append(id);
		builder.append(", version=");
		builder.append(version);
		builder.append(", modified=");
		builder.append(modified);
		builder.append(", creation=");
		builder.append(creation);
		builder.append("]\n");
		return builder.toString();
	}

}
