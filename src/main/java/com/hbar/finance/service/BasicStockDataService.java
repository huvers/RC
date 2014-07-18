package com.hbar.finance.service;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.hbar.finance.datasource.EquityDataSource;
import com.hbar.finance.model.BasicStockData;
import com.hbar.finance.model.Company;

public interface BasicStockDataService {
	public void saveBasicStockData(BasicStockData basicStockData);

	public List<BasicStockData> getLatestBasicStockDataForCompany(
			Long id, Integer limit);
	public List<BasicStockData> getBasicStockDataForCompanyBeforeDate(
			Long id, DateTime date, Integer limit, boolean isAscending, String equityDataSource);
	public List<BasicStockData> getPercentageBasicStockDataForCompanyBeforeDate(Long companyId, DateTime date, Integer limit, boolean isAscending, String equityDataSourceId);
	
	public List<BasicStockData> getBasicStockDataForCompanyBetweenDates(Long companyId, DateTime startDate, DateTime endDate, boolean isAscending, String equityDataSourceId);
	
	public List<BasicStockData> getPercentageBasicStockDataForCompanyBetweenDates(Long companyId, DateTime startDate, DateTime endDate, boolean isAscending, String equityDataSourceId);
	
	public List<BasicStockData> getBasicEquityDataForCompanyFromDataSource(Company company, EquityDataSource dataSource) throws Exception;
	
	public void saveBasicEquityData(Company company, List<BasicStockData> basicStockData, EquityDataSource equityDataSource) throws Exception;

	public List<BasicStockData> getBasicStockDataForCompanyBetweenDates(String targetSymbol,
			DateTime startDate, DateTime endDate, boolean isAscending, String equityDataSource);

}
