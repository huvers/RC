package com.hbar.finance.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@AttributeOverrides({
	@AttributeOverride(name="id",column=@Column(name="id")),
	@AttributeOverride(name="dateTimeCreated",column=@Column(name="created")),
	@AttributeOverride(name="dateTimeUpdated",column=@Column(name="last_update")),
})
@NamedQueries({
	@NamedQuery( name="getCompaniesForStrategyId", query="SELECT c.theCompany FROM CompanyAndStrategyAnalysis c "+
			"WHERE c.theStrategy.id=:strategyId AND c.active=1")
})
@Entity(name="CompanyAndStrategyAnalysis")
@Table(name="company_strategy_analysis")
public class CompanyAndStrategyAnalysis extends IdentityModel{
	
	@OneToOne
	@JoinColumn(name="company_id")
	private Company theCompany;
	
	@OneToOne
	@JoinColumn(name="strategy_id")	
	private Strategy theStrategy;
	
	private Boolean active=Boolean.TRUE;
	
	public Company getTheCompany() {
		return theCompany;
	}
	public void setTheCompany(Company theCompany) {
		this.theCompany = theCompany;
	}
	public Boolean getActive() {
		return active;
	}
	public void setEnable(Boolean active) {
		this.active = active;
	}
	public Strategy getTheStrategy() {
		return theStrategy;
	}
	public void setTheStrategy(Strategy theStrategy) {
		this.theStrategy = theStrategy;
	}
}
