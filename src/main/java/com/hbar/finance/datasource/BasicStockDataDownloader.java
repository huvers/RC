package com.hbar.finance.datasource;

import java.math.BigDecimal;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.hbar.finance.model.BasicStockData;
import com.hbar.finance.model.Company;

public class BasicStockDataDownloader{
	private final String DEFAULT_START_MONTH="02";
	private final String DEFAULT_START_DAY="21";
	private final String DEFAULT_START_YEAR="1983";
	
	public List<BasicStockData> getLatestDataFromSource(Company company, DateTime date, String source) throws Exception {

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
		List<BasicStockData> stockData=createBasicStockDataFromYahooSource(retrievedData, company);
		
		return stockData;

	}
	
	private List<BasicStockData> createBasicStockDataFromYahooSource(List<String> updateLines, Company company) throws ParseException{
		List<BasicStockData> basicStockDataList=new ArrayList<BasicStockData>();
	    DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		for(int i=0;i<updateLines.size();i++){
			if(i==0)
				continue;
			String[] curStockData=updateLines.get(i).split(",");
			BasicStockData basicStockData=new BasicStockData();
			basicStockData.setCompanyId(company.getId());
			basicStockData.setDate(fmt.parseDateTime(curStockData[0]));
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
	
	/*
	private BasicStockData createBasicStockDataFromYahooSource(List<String> updateLines){
		Map<String,String[]> collectedData=new HashMap<String, String[]>();
		for(String curUpdateLine:updateLines){
		
			String[] arrStockData=curUpdateLine.split(",");
			String[] values=new String[arrStockData.length-1];
			for(int i=0;i<values.length;i++){
				values[i]=arrStockData[i+1].replaceAll("\"", "");
			}
			collectedData.put(arrStockData[0].replaceAll("\"", ""),values);
			
		}
		BasicStockData resultStockData=new BasicStockData();
		resultStockData.setStockToDataMap(collectedData);
		return resultStockData;
		
	}
	*/
	
	private String createFormattedSymbolsForYahoo(Set<String> stockQuotesSet){
		Iterator<String> iterStockQuotes=stockQuotesSet.iterator();
		StringBuffer sbFormattedStockSymbol=new StringBuffer();
		boolean isFirst=true;
		while(iterStockQuotes.hasNext()){
			if(isFirst==true){
				sbFormattedStockSymbol.append(iterStockQuotes.next());
				isFirst=false;
			}else{
				sbFormattedStockSymbol.append("+"+iterStockQuotes.next());
			}
		}
		return sbFormattedStockSymbol.toString();
	}
}