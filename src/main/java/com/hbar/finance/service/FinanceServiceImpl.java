package com.hbar.finance.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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
	private StocksService stocksService;
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
	
	public void setStocksService(StocksService stocksService){
		this.stocksService=stocksService;
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
	
	public void executeStocksDataRetrieval(){
		List<Company> companies=companyAndStrategyAnalysisDao.getCompaniesForStrategyId(StrategyEnum.BOLLINGER_CLASSIFIER.getStrategyId());
		
		for (int i=0;i<companies.size();i++){
			try {
				Company curCompany=companies.get(i);
				stocksService.retrieveAndPersistsStocksInfo(curCompany
						.getSymbol());
			} catch (Exception e) {
				e.printStackTrace();
			}
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
	public String executeBasicEquityDataAlignment(String targetSymbol, List<String> symbols, DateTime startDate, DateTime endDate, String equityDataSource, boolean percentagesFormat, boolean isAscending, boolean includeVolume) throws Exception{
		Company company=companyDao.findBySymbol(targetSymbol);
		/**
		 * NOTE: ALL LOGIC BELOW ASSUMES THAT DATES ARE IN DESCENDING ORDER - THE isAscending FLAG IS APPLIED AT THE END OF THE MAIN LOGIC
		 */
		List<BasicStockData> bsdList=null;
		if(percentagesFormat){
			bsdList=basicStockDataService.getPercentageBasicStockDataForCompanyBetweenDates(company.getId(), startDate, endDate, false, equityDataSource);	
		}else{
			bsdList=basicStockDataService.getBasicStockDataForCompanyBetweenDates(company.getId(), startDate, endDate, false, equityDataSource);
		}
		
		
		List<AssociatedTargetAndSignalCompanyDateData> associatedDataList=new ArrayList<AssociatedTargetAndSignalCompanyDateData>();
		for(String symbol:symbols){
			Company signalCompany=companyDao.findBySymbol(symbol);
			List<BasicStockData> signalBsdList=null;
			if(percentagesFormat){
				signalBsdList=basicStockDataService.getPercentageBasicStockDataForCompanyBetweenDates(signalCompany.getId(), startDate, endDate, false, equityDataSource);
			}else{
				signalBsdList=basicStockDataService.getBasicStockDataForCompanyBetweenDates(signalCompany.getId(), startDate, endDate, false, equityDataSource);
			}
				
			List<TargetAndSignalDateHolder> targetAndSignalDateHolderList=TargetAndSignalDateMatcher.matchDates(bsdList, signalBsdList);
			
			AssociatedTargetAndSignalCompanyDateData assocTargetAndSignalCompanyDateData=new AssociatedTargetAndSignalCompanyDateData();
			assocTargetAndSignalCompanyDateData.setTargetCompany(company);
			assocTargetAndSignalCompanyDateData.setSignalCompany(signalCompany);
			assocTargetAndSignalCompanyDateData.setTargetAndSignalDateHolder(targetAndSignalDateHolderList);
			
			associatedDataList.add(assocTargetAndSignalCompanyDateData);
		}
		return createDateAlignedEquityDataString(associatedDataList, isAscending, includeVolume).toString();	
	}
	
	private StringBuffer createDateAlignedEquityDataString(List<AssociatedTargetAndSignalCompanyDateData> associatedDataList, boolean isAscending, boolean includeVolume){
		StringBuffer sbDateAlignedEquityData=new StringBuffer();
		boolean isFirstIteration=true;
		for(int i=0;i<associatedDataList.size();i++){
			AssociatedTargetAndSignalCompanyDateData curAssocData=associatedDataList.get(i);
			if(isFirstIteration){
				sbDateAlignedEquityData.append("Date,");
				sbDateAlignedEquityData.append(curAssocData.getTargetCompany().getSymbol()+" Close,");
				sbDateAlignedEquityData.append(curAssocData.getTargetCompany().getSymbol()+" Open,");
				
				sbDateAlignedEquityData.append(curAssocData.getTargetCompany().getSymbol()+" High,");
				sbDateAlignedEquityData.append(curAssocData.getTargetCompany().getSymbol()+" Low,");
				if(includeVolume){
					sbDateAlignedEquityData.append(curAssocData.getTargetCompany().getSymbol()+" Volume,");
				}
				
				isFirstIteration=false;
			}
			sbDateAlignedEquityData.append(curAssocData.getSignalCompany().getSymbol()+" Close,");
			sbDateAlignedEquityData.append(curAssocData.getSignalCompany().getSymbol()+" Open,");
			sbDateAlignedEquityData.append(curAssocData.getSignalCompany().getSymbol()+" High,");
			if(includeVolume){
				sbDateAlignedEquityData.append(curAssocData.getSignalCompany().getSymbol()+" Low,");
				sbDateAlignedEquityData.append(curAssocData.getSignalCompany().getSymbol()+" Volume"+((i==associatedDataList.size()-1)?"\n":","));
			}else{
				sbDateAlignedEquityData.append(curAssocData.getSignalCompany().getSymbol()+" Low"+((i==associatedDataList.size()-1)?"\n":","));				
			}
		}
		
		AssociatedTargetAndSignalCompanyDateData firstAssocData=associatedDataList.get(0);
		List<TargetAndSignalDateHolder> firstTargetAndSignalDateHolderList=firstAssocData.getTargetAndSignalDateHolder();
		
		for(int i=0;i<firstTargetAndSignalDateHolderList.size();i++){
			TargetAndSignalDateHolder curFirstTargetAndSignalDateHolder=firstTargetAndSignalDateHolderList.get(isAscending ? firstTargetAndSignalDateHolderList.size()-1-i :i);
			BasicStockData targetBasicStockData=(BasicStockData)curFirstTargetAndSignalDateHolder.getTargetDateHolder();
			
			sbDateAlignedEquityData.append(DateUtils.createBasicStandardDateString(targetBasicStockData.getDateTime())+",");
			writeBasicEquityDataToStringBuffer(sbDateAlignedEquityData, targetBasicStockData, false,includeVolume);
			
			for(int j=0;j<associatedDataList.size();j++){
				TargetAndSignalDateHolder curTargetAndSignalDateHolder=associatedDataList.get(j).getTargetAndSignalDateHolder().get(isAscending ? firstTargetAndSignalDateHolderList.size()-1-i :i);
				BasicStockData curSignalBasicStockData=(BasicStockData)curTargetAndSignalDateHolder.getSignalDateHolder();
				//write line
				if(j==associatedDataList.size()-1){
					writeBasicEquityDataToStringBuffer(sbDateAlignedEquityData, curSignalBasicStockData,true, includeVolume);
				}else{
					writeBasicEquityDataToStringBuffer(sbDateAlignedEquityData, curSignalBasicStockData, false, includeVolume);
				}
			}
		}
		
		return sbDateAlignedEquityData;
	}
	
	private void writeBasicEquityDataToStringBuffer(StringBuffer sb, BasicStockData bsd, boolean isLastBsd, boolean includeVolume){
		if(bsd!=null){

			
			sb.append(bsd.getClose()+",");
			sb.append(bsd.getOpen()+",");
			sb.append(bsd.getHigh()+",");
			
			if(includeVolume){
				sb.append(bsd.getLow()+",");
				sb.append(bsd.getVolume()+((isLastBsd)?"\n":","));
			}else{
				sb.append(bsd.getLow()+((isLastBsd)?"\n":","));
			}
		}else{
			if(includeVolume){
				sb.append(",,,,"+((isLastBsd)?"\n":","));
			}else{
				sb.append(",,,"+((isLastBsd)?"\n":","));				
			}
			
		}
		
		
	}
	@Override
	public void executeStocksStreamingDataRetrieval(){
		TradeKingClient client=new TradeKingClient();
		List<String> urls=new ArrayList<String>();
		urls.add("KING");
		urls.add("COH");
		/*
		InputStream is=client.getStreamingQuotesForSymbols(urls);
		
		File file = new File("C:/tomcat-6.0.36/logs/TRADE_KING_STREAM.log");
		
		int n;
		
		FileWriter fw;
		BufferedWriter bw=null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);	
			
			while((n=is.read())!=-1){
				char ch=(char)n;
				bw.write(ch);
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				if(bw!=null){
					bw.close();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}*/
		
	}
	
}
