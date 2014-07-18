package com.hbar.finance.dao;

import com.hbar.finance.model.Company;

public interface CompanyDao extends GenericDao<Company,Long> {
	public Company findBySymbol(String symbol);
}
