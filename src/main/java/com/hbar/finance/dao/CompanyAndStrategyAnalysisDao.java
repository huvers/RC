package com.hbar.finance.dao;

import java.util.List;

import com.hbar.finance.model.Company;
import com.hbar.finance.model.CompanyAndStrategyAnalysis;

public interface CompanyAndStrategyAnalysisDao extends GenericDao<CompanyAndStrategyAnalysis,Long> {
	List<Company> getCompaniesForStrategyId(Long strategyId);
}
