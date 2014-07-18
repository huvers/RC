package com.hbar.finance.datasource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.hbar.finance.model.BasicStockData;
import com.hbar.finance.model.Company;

public class YahooBasicStockDataDownloader implements BasicEquityDataDownloader{
	private final String DEFAULT_START_MONTH="02";
	private final String DEFAULT_START_DAY="21";
	private final String DEFAULT_START_YEAR="1983";
	private final String DEFAULT_CLOSE_TIME="13:00:00 -0700";

	public List<BasicStockData> getBasicEquityDataForCompany(Company company, String closeTime) throws Exception {
		return getDataForCompany(company, null, closeTime);
	}
	/**
	 * Returns stock data that is after the provided <code>date</code> for the given <code>Company</code>. 
	 * 
	 * @param company
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public List<BasicStockData> getDataForCompany(Company company, DateTime date, String closeTime) throws Exception {

		//String theUrl = "http://finance.yahoo.com/d/quotes.csv?s=%5EGDAXI+%5EN225+%5EFTSE+USDEUR=X+USDJPY=X+USDGBP=X+USDSEK=X+USDCHF=X&f=sd1ol1gh";
		//String theUrl = "http://finance.yahoo.com/d/quotes.csv?s="+createFormattedSymbolsForYahoo(stockQuotesSet)+"&f=sd1ol1gh";
		//String theUrl = "http://ichart.finance.yahoo.com/table.csv?s=AMD&a=02&b=21&c=1983&d=00&e=30&f=2014&g=d&ignore=.csv";
		StringBuffer sbStartDay=new StringBuffer();
		if(date==null){
			sbStartDay.append("a=").append(DEFAULT_START_MONTH).append("&b=").append(DEFAULT_START_DAY)
			.append("&c=").append(DEFAULT_START_YEAR);
		}else{
			
			int startYear=date.getYear();
			int startDay=date.getDayOfMonth();
			int startMonth=date.getMonthOfYear();
			sbStartDay.append("a=").append(startMonth<10?"0"+startMonth:startMonth).append("&b=").append(startDay)
			.append("&c=").append(startYear);
			
		}
		Date now=new Date();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(now);
		int endYear=calendar.get(Calendar.YEAR);
		int endDay=calendar.get(Calendar.DAY_OF_MONTH);
		int endMonth=calendar.get(Calendar.MONTH);
		StringBuffer sbEndDay=new StringBuffer("");
		sbEndDay.append("d=").append(endMonth<10?"0"+endMonth:endMonth).append("&e=").append(endDay)
		.append("&f=").append(endYear);

		//3-21-1983 to 2-3-2014
		//a=start month
		//b=start day
		//c=start year
		//e=end month
		//f=end day
		//end year
		
		String theUrl="http://ichart.finance.yahoo.com/table.csv?s="+company.getSymbol()+"&"+sbStartDay.toString()+"&"+sbEndDay.toString()+"&g=d&ignore=.csv";
		
		List<String> retrievedData = new ArrayList<String>();
		try{
			URL oracle = new URL(theUrl);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					oracle.openStream()));
	
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				retrievedData.add(inputLine);
			}
			in.close();
		}catch(FileNotFoundException exc){
			return null;
		}
		System.out.println("updateLine=" + retrievedData);
		List<BasicStockData> stockData=createBasicStockDataFromYahooSource(retrievedData, company, closeTime);
		
		return stockData;

	}
	
	private List<BasicStockData> createBasicStockDataFromYahooSource(List<String> updateLines, Company company, String closeTime) throws ParseException{
		List<BasicStockData> basicStockDataList=new ArrayList<BasicStockData>();
	    DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss Z");
		for(int i=0;i<updateLines.size();i++){
			if(i==0)
				continue;
			String[] curStockData=updateLines.get(i).split(",");
			BasicStockData basicStockData=new BasicStockData();
			basicStockData.setCompanyId(company.getId());
			if(closeTime==null){
				basicStockData.setDate(fmt.parseDateTime(curStockData[0]+" "+DEFAULT_CLOSE_TIME));
			}else{
				basicStockData.setDate(fmt.parseDateTime(curStockData[0]+" "+closeTime));
			}
			basicStockData.setOpen(new BigDecimal(curStockData[1]));
			basicStockData.setHigh(new BigDecimal(curStockData[2]));
			basicStockData.setLow(new BigDecimal(curStockData[3]));
			basicStockData.setClose(new BigDecimal(curStockData[4]));
			basicStockData.setVolume(new BigDecimal(curStockData[5]));
			basicStockData.setVersionIndex(1);
			
			basicStockData.setEquityDataSourceId(EquityDataSource.YAHOO.getEquityDataSourceId());
			basicStockDataList.add(basicStockData);
			
		}
		return basicStockDataList;
		
	}
}
