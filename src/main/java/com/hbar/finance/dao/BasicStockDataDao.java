package com.hbar.finance.dao;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.hbar.finance.model.BasicStockData;
import com.hbar.finance.model.Company;

public interface BasicStockDataDao extends GenericDao<BasicStockData,Long> {
	public List<BasicStockData> getLatestBasicStockDataForCompany(Long id, Integer limit);

	public List<BasicStockData> getBasicStockDataForCompanyBeforeDate(Long id,
			DateTime date, Integer limit, boolean isAscending, Integer equityDataSourceId);

	public List<BasicStockData> getBasicStockDataForCompanyBetweenDates(
			Long id, DateTime startDate, DateTime endDate, boolean isAscending, Integer equityDataSourceId);
	/**
	 * Get the latest version index for the given company.
	 * @param company
	 * @return Integer
	 */
	public Integer getMaxVersionIndexForCompany(Company company, Integer equityDataSourceId);

}
