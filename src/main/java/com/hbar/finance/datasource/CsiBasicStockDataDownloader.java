package com.hbar.finance.datasource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.hbar.finance.model.BasicStockData;
import com.hbar.finance.model.Company;

public class CsiBasicStockDataDownloader implements BasicEquityDataDownloader{
	private static final int DATE_COLUMN_INDEX=1;
	private static final int OPEN_COLUMN_INDEX=2;
	private static final int HIGH_COLUMN_INDEX=3;
	private static final int LOW_COLUMN_INDEX=4;
	private static final int CLOSE_COLUMN_INDEX=5;
	private static final int VOLUME_COLUMN_INDEX=6;
	private final String DEFAULT_CLOSE_TIME="13:00:00 -0700";
	
	public List<BasicStockData> getBasicEquityDataForCompany(Company company, String closeTime) throws Exception {
		File file=new File("C:\\ua\\Files\\CSI Stock Sample\\"+company.getSymbol()+".txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		
	    DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		List<BasicStockData> basicStockData=new ArrayList<BasicStockData>();
		BigDecimal bdDivisor=new BigDecimal(10);
 		while ((line = br.readLine()) != null) {
		   System.out.println(line);
		   String[] columns=line.split(",");
		   DateTime equityDate=fmt.parseDateTime(columns[DATE_COLUMN_INDEX]);
		   BasicStockData bsd=new BasicStockData();
		   bsd.setDate(equityDate);
		   BigDecimal bdOpen=new BigDecimal(columns[OPEN_COLUMN_INDEX].trim()).divide(bdDivisor, 2, RoundingMode.HALF_EVEN);
		   BigDecimal bdHigh=new BigDecimal(columns[HIGH_COLUMN_INDEX].trim()).divide(bdDivisor, 2, RoundingMode.HALF_EVEN);
		   BigDecimal bdLow=new BigDecimal(columns[LOW_COLUMN_INDEX].trim()).divide(bdDivisor, 2, RoundingMode.HALF_EVEN);
		   BigDecimal bdClose=new BigDecimal(columns[CLOSE_COLUMN_INDEX].trim()).divide(bdDivisor, 2, RoundingMode.HALF_EVEN);
		   BigDecimal bdVolume=new BigDecimal(columns[VOLUME_COLUMN_INDEX].trim()).divide(bdDivisor, 2, RoundingMode.HALF_EVEN);
		   
		   bsd.setOpen(bdOpen);
		   bsd.setHigh(bdHigh);
		   bsd.setLow(bdLow);
		   bsd.setClose(bdClose);
		   bsd.setVolume(bdVolume);
		   bsd.setCompanyId(company.getId());
		   bsd.setEquityDataSourceId(EquityDataSource.CSI.getEquityDataSourceId());
		   basicStockData.add(bsd);
		   
		}
		br.close();
		return basicStockData;
	}
	
	public static void main(String[] args) throws Exception{
		CsiBasicStockDataDownloader csiBasicStockDataDownloader=new CsiBasicStockDataDownloader();
		//csiBasicStockDataDownloader.getLatestDataFromSource(null, null, null,1);
	}
}
