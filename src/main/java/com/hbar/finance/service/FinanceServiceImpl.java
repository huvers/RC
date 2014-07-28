package com.hbar.finance.service;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Transactional;

import com.hbar.finance.dao.CompanyAndStrategyAnalysisDao;
import com.hbar.finance.dao.CompanyDao;
import com.hbar.finance.dao.OptionQuoteDao;
import com.hbar.finance.dao.StockQuoteDao;
import com.hbar.finance.dao.StrategyDao;
import com.hbar.finance.datasource.EquityDataSource;
import com.hbar.finance.date.DateUtils;
import com.hbar.finance.date.TargetAndSignalDateHolder;
import com.hbar.finance.date.TargetAndSignalDateMatcher;
import com.hbar.finance.model.BasicStockData;
import com.hbar.finance.model.Company;
import com.hbar.finance.model.OptionQuote;
import com.hbar.finance.model.StockQuote;
import com.hbar.finance.providers.tradeking.TradeKingClient;
import com.hbar.finance.strategy.StrategyEnum;
import com.hbar.finance.structure.AssociatedTargetAndSignalCompanyDateData;

public class FinanceServiceImpl implements FinanceService {
	private OptionQuoteDao optionQuoteDao;
	private StockQuoteDao stockQuoteDao;
	private CompanyDao companyDao;
	private StrategyDao strategyDao;
	private OptionsService optionsService;
	private BollingerBandsService bollingerBandsService;
	private TradeKingClient tradeKingClient;
	
	private CompanyAndStrategyAnalysisDao companyAndStrategyAnalysisDao;
	
	private BasicStockDataService basicStockDataService;

	public void setTradeKingClient(TradeKingClient tradeKingClient){
		this.tradeKingClient = tradeKingClient;
	}
	
	public void setBasicStockDataService(BasicStockDataService basicStockDataService){
		this.basicStockDataService=basicStockDataService;
	}
	
	public void setOptionQuoteDao(OptionQuoteDao optionQuoteDao) {
		this.optionQuoteDao = optionQuoteDao;
	}

	public void setOptionQuoteDao(BasicStockDataService basicStockDataService) {
		this.basicStockDataService=basicStockDataService;
	}

	public void setStockQuoteDao(StockQuoteDao stockQuoteDao) {
		this.stockQuoteDao = stockQuoteDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	public void setCompanyAndStrategyAnalysisDao(CompanyAndStrategyAnalysisDao companyAndStrategyAnalysisDao) {
		this.companyAndStrategyAnalysisDao = companyAndStrategyAnalysisDao;
	}

	public void setStrategyDao(StrategyDao strategyDao) {
		this.strategyDao = strategyDao;
	}

	public void setOptionsService(OptionsService optionsService) {
		this.optionsService = optionsService;
	}
	
	public void setBollingerBandsService(BollingerBandsService bollingerBandsService){
		this.bollingerBandsService=bollingerBandsService;
	}
	
	@Transactional
	public void saveOptionQuoteInfo(OptionQuote optionQuote) {
		optionQuoteDao.persist(optionQuote);
	}

	@Transactional
	public OptionQuote getOptionQuote(Long id) {
		return optionQuoteDao.findById(id);
	}
	
	@Transactional
	public void saveStockQuoteInfo(StockQuote stockQuote) {
		stockQuoteDao.persist(stockQuote);
	}

	@Transactional
	public StockQuote getStockQuote(Long id) {
		return stockQuoteDao.findById(id);
	}
	
	@Transactional
	public List<StockQuote> retrieveAndPersistsStocksInfo(String symbol) {
		List<StockQuote> stockQuotes = tradeKingClient.getStockQuotesForSymbol(symbol);
		for (StockQuote curStockQuote : stockQuotes) {
			stockQuoteDao.persist(curStockQuote);
			optionsService.retrieveAndPersistsOptionsInfo(curStockQuote);
		}
		return stockQuotes;
	}

	@Transactional
	public void retrieveAndPersistsOptionsInfo(String symbol) {
		List<OptionQuote> optionQuotes = tradeKingClient
				.getOptionQuotesForSymbol(symbol);
		for (OptionQuote curOptionQuote : optionQuotes) {
			optionQuoteDao.persist(curOptionQuote);
		}

	}
	
	@Transactional
	public void executeStocksDataRetrieval(){
		List<Company> companies=companyAndStrategyAnalysisDao.getCompaniesForStrategyId(StrategyEnum.BOLLINGER_CLASSIFIER.getStrategyId());
		for(Company curCompany:companies){
			retrieveAndPersistsStocksInfo(curCompany.getSymbol());
		}
		
	}
	@Transactional
	public StockQuote getStockQuoteWithOrderedOptions(Long id){
		return stockQuoteDao.getStockQuoteWithOrderedOptions(id);
	}

	@Transactional
	public List<Company> getAllCompanies(){
		return companyDao.findAll();
	}

	@Transactional
	public String executeBollingerBandClassifierStrategy(String equityDataSource, String date){
		List<Company> companies=companyAndStrategyAnalysisDao.getCompaniesForStrategyId(StrategyEnum.BOLLINGER_CLASSIFIER.getStrategyId());
		return bollingerBandsService.getEquitiesClassifiedByBandSection(companies, date);
	}
	
	
	public void executeBasicEquityDataRetrievalForStrategyFromDataSource(String strategy, String dataSource) throws Exception{
		StrategyEnum strategyEnum=StrategyEnum.valueOf(strategy);
		List<Company> companies=companyAndStrategyAnalysisDao.getCompaniesForStrategyId(strategyEnum.getStrategyId());
		
		EquityDataSource equityDataSource=EquityDataSource.valueOf(dataSource);
		for(Company company:companies){
			List<BasicStockData> bsdList=basicStockDataService.getBasicEquityDataForCompanyFromDataSource(company, equityDataSource);	
			//edsTickerTimeInfoService.applyTimeZoneTransformation(company.getId(), bsdList, equityDataSource);
			basicStockDataService.saveBasicEquityData(company, bsdList, equityDataSource);
		}
	}
	
	public void executeBasicEquityRetrievalForCompanyFromDataSource( Company company, String dataSource ) throws Exception{
		EquityDataSource equityDataSource=EquityDataSource.valueOf(dataSource);
		List<BasicStockData> bsdList=basicStockDataService.getBasicEquityDataForCompanyFromDataSource(company, equityDataSource);	
		//edsTickerTimeInfoService.applyTimeZoneTransformation(company.getId(), bsdList, equityDataSource);
		basicStockDataService.saveBasicEquityData(company, bsdList, equityDataSource);
	}

	@Transactional
	public String executeBasicEquityDataAlignment(String targetSymbol, List<String> symbols, DateTime startDate, DateTime endDate, String equityDataSource) throws Exception{
		Company company=companyDao.findBySymbol(targetSymbol);
		List<BasicStockData> bsdList=basicStockDataService.getPercentageBasicStockDataForCompanyBetweenDates(company.getId(), startDate, endDate, false, equityDataSource);
		List<AssociatedTargetAndSignalCompanyDateData> associatedDataList=new ArrayList<AssociatedTargetAndSignalCompanyDateData>();
		for(String symbol:symbols){
			Company signalCompany=companyDao.findBySymbol(symbol);
			List<BasicStockData> signalBsdList=basicStockDataService.getPercentageBasicStockDataForCompanyBetweenDates(signalCompany.getId(), startDate, endDate, false, equityDataSource);	
			List<TargetAndSignalDateHolder> targetAndSignalDateHolderList=TargetAndSignalDateMatcher.matchDates(bsdList, signalBsdList);
			
			AssociatedTargetAndSignalCompanyDateData assocTargetAndSignalCompanyDateData=new AssociatedTargetAndSignalCompanyDateData();
			assocTargetAndSignalCompanyDateData.setTargetCompany(company);
			assocTargetAndSignalCompanyDateData.setSignalCompany(signalCompany);
			assocTargetAndSignalCompanyDateData.setTargetAndSignalDateHolder(targetAndSignalDateHolderList);
			
			associatedDataList.add(assocTargetAndSignalCompanyDateData);
		}
		return createDateAlignedEquityDataString(associatedDataList).toString();	
	}
	
	private StringBuffer createDateAlignedEquityDataString(List<AssociatedTargetAndSignalCompanyDateData> associatedDataList){
		StringBuffer sbDateAlignedEquityData=new StringBuffer();
		boolean isFirstIteration=true;
		for(int i=0;i<associatedDataList.size();i++){
			AssociatedTargetAndSignalCompanyDateData curAssocData=associatedDataList.get(i);
			if(isFirstIteration){
				sbDateAlignedEquityData.append("Date,");
				sbDateAlignedEquityData.append(curAssocData.getTargetCompany().getSymbol()+" Open,");
				sbDateAlignedEquityData.append(curAssocData.getTargetCompany().getSymbol()+" Close,");
				sbDateAlignedEquityData.append(curAssocData.getTargetCompany().getSymbol()+" High,");
				sbDateAlignedEquityData.append(curAssocData.getTargetCompany().getSymbol()+" Low,");
				sbDateAlignedEquityData.append(curAssocData.getTargetCompany().getSymbol()+" Volume,");
				
				isFirstIteration=false;
			}
			sbDateAlignedEquityData.append(curAssocData.getSignalCompany().getSymbol()+" Open,");
			sbDateAlignedEquityData.append(curAssocData.getSignalCompany().getSymbol()+" Close,");
			sbDateAlignedEquityData.append(curAssocData.getSignalCompany().getSymbol()+" High,");
			sbDateAlignedEquityData.append(curAssocData.getSignalCompany().getSymbol()+" Low,");
			
			sbDateAlignedEquityData.append(curAssocData.getSignalCompany().getSymbol()+" Volume"+((i==associatedDataList.size()-1)?"\n":","));
			
		}
		
		AssociatedTargetAndSignalCompanyDateData firstAssocData=associatedDataList.get(0);
		List<TargetAndSignalDateHolder> firstTargetAndSignalDateHolderList=firstAssocData.getTargetAndSignalDateHolder();
		
		for(int i=0;i<firstTargetAndSignalDateHolderList.size();i++){
			TargetAndSignalDateHolder curFirstTargetAndSignalDateHolder=firstTargetAndSignalDateHolderList.get(i);
			BasicStockData targetBasicStockData=(BasicStockData)curFirstTargetAndSignalDateHolder.getTargetDateHolder();
			
			sbDateAlignedEquityData.append(DateUtils.createBasicStandardDateString(targetBasicStockData.getDateTime())+",");
			writeBasicEquityDataToStringBuffer(sbDateAlignedEquityData, targetBasicStockData, false);
			BasicStockData signalBasicStockData=null;
			if( curFirstTargetAndSignalDateHolder.getSignalDateHolder() != null ){
				signalBasicStockData=(BasicStockData)curFirstTargetAndSignalDateHolder.getSignalDateHolder();
				//write line
				if(associatedDataList.size()==1){
					writeBasicEquityDataToStringBuffer(sbDateAlignedEquityData, signalBasicStockData,true);
				}else{
					writeBasicEquityDataToStringBuffer(sbDateAlignedEquityData, signalBasicStockData, false);
				}
			}else{
				//write empty line
				//write line
				if(associatedDataList.size()==1){
					writeBasicEquityDataToStringBuffer(sbDateAlignedEquityData, null,true);
				}else{
					writeBasicEquityDataToStringBuffer(sbDateAlignedEquityData, null, false);
				}
			}
			
			for(int j=1;j<associatedDataList.size();j++){
				TargetAndSignalDateHolder curTargetAndSignalDateHolder=associatedDataList.get(j).getTargetAndSignalDateHolder().get(i);
				BasicStockData curTargetBasicStockData=(BasicStockData)curTargetAndSignalDateHolder.getTargetDateHolder();
				//write line
				if(j==associatedDataList.size()-1){
					writeBasicEquityDataToStringBuffer(sbDateAlignedEquityData, curTargetBasicStockData,true);
				}else{
					writeBasicEquityDataToStringBuffer(sbDateAlignedEquityData, curTargetBasicStockData, false);
				}
			}
		}
		
		return sbDateAlignedEquityData;
	}
	
	private void writeBasicEquityDataToStringBuffer(StringBuffer sb, BasicStockData bsd, boolean isLastBsd){
		if(bsd!=null){

			sb.append(bsd.getOpen()+",");
			sb.append(bsd.getClose()+",");
			sb.append(bsd.getHigh()+",");
			sb.append(bsd.getLow()+",");
			sb.append(bsd.getVolume()+((isLastBsd)?"\n":","));
		}else{
			sb.append(",,,,"+((isLastBsd)?"\n":","));
			
		}
		
		
	}
	
	
}
