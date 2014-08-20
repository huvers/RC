package com.hbar.finance.service;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.hbar.finance.model.Company;
import com.hbar.finance.model.OptionQuote;
import com.hbar.finance.model.StockQuote;

public interface FinanceService {
	
	public OptionQuote getOptionQuote(Long id);
	public void saveOptionQuoteInfo(OptionQuote optionQuote);
	
	public StockQuote getStockQuote(Long id);
	public void saveStockQuoteInfo(StockQuote stockQuote);
	
	/*public void executeStrategiesOnLatestStockData();
	public void executeStrategiesOnLatestOptionData();
	*/
	public void executeStocksDataRetrieval();

	public void executeStocksStreamingDataRetrieval();
		
	public StockQuote getStockQuoteWithOrderedOptions(Long id);

	public void retrieveAndPersistsOptionsInfo(String symbol);
	
	public List<StockQuote> retrieveAndPersistsStocksInfo(String symbol);
	
	public List<Company> getAllCompanies();

	public void executeBasicEquityRetrievalForCompanyFromDataSource( Company company, String equityDataSource )  throws Exception;
	
	public String executeBasicEquityDataAlignment(String targetSymbol, List<String> symbols, DateTime startDate, DateTime endDate, String equityDataSource, boolean isPercentagesFormat, boolean isAscending, boolean includeVolume) throws Exception;
	
	public void executeBasicEquityDataRetrievalForStrategyFromDataSource(String strategy, String equityDataSource) throws Exception;

	public String executeBollingerBandClassifierStrategy(String equityDataSource, String date);
}
