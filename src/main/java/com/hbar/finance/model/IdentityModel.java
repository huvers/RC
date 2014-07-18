package com.hbar.finance.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@MappedSuperclass
public class IdentityModel {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Version
	@Column(name="persistence_version")
	private Integer persistenceVersion;
	
	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name="created", nullable=false)
	private Date dateTimeCreated;
		
	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name="updated", nullable=false)
	private Date dateTimeUpdated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPersistenceVersion() {
		return persistenceVersion;
	}

	public void setPersistenceVersion(Integer persistenceVersion) {
		this.persistenceVersion = persistenceVersion;
	}

	public Date getDateTimeCreated() {
		return dateTimeCreated;
	}

	public void setDateTimeCreated(Date dateTimeCreated) {
		this.dateTimeCreated = dateTimeCreated;
	}

	public Date getDateTimeUpdated() {
		return dateTimeUpdated;
	}

	public void setDateTimeUpdated(Date dateTimeUpdated) {
		this.dateTimeUpdated = dateTimeUpdated;
	}
	
	
}
