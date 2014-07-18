package com.hbar.finance.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@AttributeOverrides({
	@AttributeOverride(name="id",column=@Column(name="id")),
	@AttributeOverride(name="dateTimeCreated",column=@Column(name="created")),
	@AttributeOverride(name="dateTimeUpdated",column=@Column(name="last_update")),
})
@Entity(name="Strategy")
@Table(name="strategy")
public class Strategy extends IdentityModel {
	private String name;
	private String className;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	
}
