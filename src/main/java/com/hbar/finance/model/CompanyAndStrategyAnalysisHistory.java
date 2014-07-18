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
@Entity(name="CompanyAndStrategyAnalysisHistory")
@Table(name="company_strategy_analysis_history")
public class CompanyAndStrategyAnalysisHistory extends IdentityModel {
	private CompanyAndStrategyAnalysis theCompanyAndStrategyAnalysis;

	public CompanyAndStrategyAnalysis getTheCompanyAndStrategyAnalysis() {
		return theCompanyAndStrategyAnalysis;
	}

	public void setTheCompanyAndStrategyAnalysis(
			CompanyAndStrategyAnalysis theCompanyAndStrategyAnalysis) {
		this.theCompanyAndStrategyAnalysis = theCompanyAndStrategyAnalysis;
	} 
}
