package com.hbar.finance.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Transactional;

import com.hbar.finance.dao.BasicStockDataDao;
import com.hbar.finance.dao.CompanyDao;
import com.hbar.finance.datasource.CsiBasicStockDataDownloader;
import com.hbar.finance.datasource.EquityDataSource;
import com.hbar.finance.datasource.YahooBasicStockDataDownloader;
import com.hbar.finance.model.BasicStockData;
import com.hbar.finance.model.Company;
import com.hbar.finance.model.EdsTickerTimeInfo;

public class BasicStockDataServiceImpl implements BasicStockDataService {
	
	private BasicStockDataDao basicStockDataDao;
	
	private CompanyDao companyDao;
	
	private EdsTickerTimeInfoService edsTickerTimeInfoService;
	
	public void setBasicStockDataDao(BasicStockDataDao basicStockDataDao){
		this.basicStockDataDao=basicStockDataDao;
	}
	public void setEdsTickerTimeInfoService(EdsTickerTimeInfoService edsTickerTimeInfoService){
		this.edsTickerTimeInfoService=edsTickerTimeInfoService;
	}
	public void setCompanyDao(CompanyDao companyDao){
		this.companyDao=companyDao;
	}
	
	
	@Transactional
	public void saveBasicStockData(BasicStockData basicStockData){
		basicStockDataDao.persist(basicStockData);
	}
	public List<BasicStockData> getLatestBasicStockDataForCompany(Long id,
			Integer limit) {
		
		return basicStockDataDao.getLatestBasicStockDataForCompany(id, limit);
	}
	public List<BasicStockData> getBasicStockDataForCompanyBeforeDate(
			Long id, DateTime date, Integer limit, boolean isAscending, String equityDataSource){
		return basicStockDataDao.getBasicStockDataForCompanyBeforeDate(id, date, limit, isAscending, EquityDataSource.valueOf(equityDataSource).getEquityDataSourceId());
	}
	public List<BasicStockData> getBasicStockDataForCompanyBetweenDates(
			Long id, DateTime startDate, DateTime endDate, boolean isAscending, String equityDataSource){
		return basicStockDataDao.getBasicStockDataForCompanyBetweenDates(id, startDate, endDate, isAscending, EquityDataSource.valueOf(equityDataSource).getEquityDataSourceId());
	}

	public List<BasicStockData> getPercentageBasicStockDataForCompanyBeforeDate(Long id, DateTime date, Integer limit, boolean isAscending, String equityDataSource){
		List<BasicStockData> basicStockData=getBasicStockDataForCompanyBeforeDate( id, date, limit, isAscending, equityDataSource);
		return getPercentageChangeList(basicStockData);
	}


	public List<BasicStockData> getPercentageBasicStockDataForCompanyBetweenDates(Long id, DateTime startDate, DateTime endDate, boolean isAscending, String equityDataSourceId){
		List<BasicStockData> basicStockData=getBasicStockDataForCompanyBetweenDates( id, startDate, endDate, isAscending, equityDataSourceId);
		return getPercentageChangeList(basicStockData);
	} 
	
	private List<BasicStockData> getPercentageChangeList(List<BasicStockData> basicStockData){
		List<BasicStockData> percentagesBasicStockData=new ArrayList<BasicStockData>();
 		BasicStockData priorBasicStockData=null;
 		for(int i=0;i<basicStockData.size();i++){
 			if(i==0){
 				priorBasicStockData=basicStockData.get(i);
 				continue;
 			}
 			BasicStockData curBasicStockData=basicStockData.get(i);
 			BasicStockData curPerBasicStockData=new BasicStockData();
 			curPerBasicStockData.setCompanyId(curBasicStockData.getCompanyId());
 			curPerBasicStockData.setDate(curBasicStockData.getDateTime());
 			
 			curPerBasicStockData.setClose(BigDecimal.ONE.subtract(priorBasicStockData.getClose().divide(curBasicStockData.getClose(), 8, RoundingMode.HALF_EVEN)));
 			curPerBasicStockData.setHigh(BigDecimal.ONE.subtract(priorBasicStockData.getHigh().divide(curBasicStockData.getHigh(), 8, RoundingMode.HALF_EVEN)));
 			curPerBasicStockData.setLow(BigDecimal.ONE.subtract(priorBasicStockData.getLow().divide(curBasicStockData.getLow(), 8, RoundingMode.HALF_EVEN)));
 			if(!curBasicStockData.getVolume().equals(BigDecimal.ZERO)){
 				curPerBasicStockData.setVolume(BigDecimal.ONE.subtract(priorBasicStockData.getVolume().divide(curBasicStockData.getVolume(), 8, RoundingMode.HALF_EVEN)));
 			}else{
 				curPerBasicStockData.setVolume(null);
 			}
 			curPerBasicStockData.setOpen(BigDecimal.ONE.subtract(priorBasicStockData.getOpen().divide(curBasicStockData.getOpen(), 8, RoundingMode.HALF_EVEN)));
 			
 			
 			percentagesBasicStockData.add(curPerBasicStockData);
 			priorBasicStockData=curBasicStockData;
 			
 		}
		return percentagesBasicStockData;
	}
	
	public DateAlignedBasicStockDataContainer dateAlignBasicStockData(List<Company> companies, List<List<BasicStockData>> basicStockDataForCompanies, List<DateTime> targetDates){
		//create set of "ignored" dates. An ignored date is a date that is not found in 
		//any given list of BasicStockData for a company.
		
		
		//for a given target date, look for the BasicStockData.date that matches it.
		Map<Long,Integer> companyIdToBasicStockDataIndex=new HashMap<Long,Integer>();
		
		DateAlignedBasicStockDataContainer dateAlignedBasicStockDataContainer=new DateAlignedBasicStockDataContainer();
		Set<DateTime> ignoredDates=new HashSet<DateTime>();
		Set<DateTime> targetDatesSet=new HashSet<DateTime>();
		targetDatesSet.addAll(targetDates);
		
		/**
		 * Get indexes 
		 */
		for(int i=0;i<companies.size();i++){
			Company curCompany=companies.get(i);
			companyIdToBasicStockDataIndex.put(curCompany.getId(), 0);
			dateAlignedBasicStockDataContainer.getColumnNameToIndexMap().put(curCompany.getSymbol()+" Close", i*5+0);
			dateAlignedBasicStockDataContainer.getColumnNameToIndexMap().put(curCompany.getSymbol()+" Open", i*5+1);
			dateAlignedBasicStockDataContainer.getColumnNameToIndexMap().put(curCompany.getSymbol()+" Low", i*5+2);
			dateAlignedBasicStockDataContainer.getColumnNameToIndexMap().put(curCompany.getSymbol()+" High", i*5+3);
			dateAlignedBasicStockDataContainer.getColumnNameToIndexMap().put(curCompany.getSymbol()+" Volume", i*5+4);
			
			List<BasicStockData> basicStockData=basicStockDataForCompanies.get(i);
			Set<DateTime> datesSetForCompany=new HashSet<DateTime>();
			//check that the date for the company is in the target dates list
			for(BasicStockData curBsd:basicStockData){
				if(!targetDatesSet.contains(curBsd.getDateTime())){
					ignoredDates.add(curBsd.getDateTime());
				}else{
					datesSetForCompany.add(curBsd.getDateTime());
				}
			}
			//check that the date for the target list is in the company dates set			
			for(DateTime curTargetDate:targetDatesSet){
				if(datesSetForCompany.contains(curTargetDate)){
					ignoredDates.add(curTargetDate);
				}
			}
			
			
		}
		
		
		
		for(DateTime curTargetDate:targetDates){
			if(ignoredDates.contains(curTargetDate)){
				continue;
			}
			
			String[] dataForDate=new String[companies.size()*5];
			
			
			for(int i=0;i<companies.size();i++){
				Company curCompany=companies.get(i);
				Integer companyLastDataIndex=companyIdToBasicStockDataIndex.get(curCompany.getId());
				List<BasicStockData> basicStockData=basicStockDataForCompanies.get(i);
				
				for(int j=companyLastDataIndex;j<basicStockData.size();j++){
					BasicStockData curBsd=basicStockData.get(j);
					
					if(curTargetDate.equals(curBsd.getDateTime())){
						dataForDate[i*5+0]=curBsd.getClose().toPlainString();
						dataForDate[i*5+1]=curBsd.getOpen().toPlainString();		
						dataForDate[i*5+2]=curBsd.getLow().toPlainString();
						dataForDate[i*5+3]=curBsd.getHigh().toPlainString();						
						dataForDate[i*5+4]=curBsd.getVolume().toPlainString();
						companyIdToBasicStockDataIndex.put(curCompany.getId(), j+1);
						
						break;
						
					}
				}
				
			}
			dateAlignedBasicStockDataContainer.getDateToDataMap().put(curTargetDate, dataForDate);
			
		}
		
		
		
		return null;
	}
	public List<BasicStockData> getBasicEquityDataForCompanyFromDataSource(Company company, EquityDataSource equityDataSource) throws Exception{
		
		EdsTickerTimeInfo timeInfo=edsTickerTimeInfoService.getEdsTickerTimeInfo(company.getId(), equityDataSource);
		if(equityDataSource.equals(EquityDataSource.CSI)){
			
			CsiBasicStockDataDownloader csiBasicStockDataDownloader=new CsiBasicStockDataDownloader();
			return csiBasicStockDataDownloader.getBasicEquityDataForCompany(company, timeInfo!=null?timeInfo.getCloseTimeUtc():null);
			
		}else if(equityDataSource.equals(EquityDataSource.YAHOO)){
			
			YahooBasicStockDataDownloader yahooBasicStockDataDownloader=new YahooBasicStockDataDownloader();
			return yahooBasicStockDataDownloader.getBasicEquityDataForCompany(company, timeInfo!=null?timeInfo.getCloseTimeUtc():null);
			
		}
		throw new Exception("NO SUCH DATA SOURCE");
	}
	
	@Transactional
	public void saveBasicEquityData(Company company, List<BasicStockData> basicStockData, EquityDataSource equityDataSource) throws Exception{
		Integer maxVersionIndex=basicStockDataDao.getMaxVersionIndexForCompany(company, equityDataSource.getEquityDataSourceId());
		
		for(BasicStockData curBasicStockData:basicStockData){
			curBasicStockData.setVersionIndex(maxVersionIndex+1);
			curBasicStockData.setEquityDataSourceId(equityDataSource.getEquityDataSourceId());
			basicStockDataDao.persist(curBasicStockData);
		}
		
		
	}
	public List<BasicStockData> getBasicStockDataForCompanyBetweenDates(String targetSymbol,
			DateTime startDate, DateTime endDate, boolean isAscending, String equityDataSource) {
		Company company=companyDao.findBySymbol(targetSymbol);
		return basicStockDataDao.getBasicStockDataForCompanyBetweenDates(company.getId(), startDate, endDate, isAscending, EquityDataSource.valueOf(equityDataSource).getEquityDataSourceId());
		
	}
	
	
}

