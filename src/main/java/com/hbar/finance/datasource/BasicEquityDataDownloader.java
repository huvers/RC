package com.hbar.finance.datasource;

import java.util.List;

import com.hbar.finance.model.BasicStockData;
import com.hbar.finance.model.Company;

public interface BasicEquityDataDownloader {
	public List<BasicStockData> getBasicEquityDataForCompany(Company company, String closeTime) throws Exception;

}
