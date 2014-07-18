package com.hbar.finance.datadump;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hbar.finance.model.BasicStockData;
import com.hbar.finance.service.BasicStockDataService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/applicationContext.xml"})
@Transactional
@Ignore
public class BasicStockDataPercentagesDataDump {
	
	@Autowired
	BasicStockDataService basicStockDataService;

	@Test
	public void testPercentageChangesDataDump() throws ParseException{
	    DateTimeFormatter fmt = DateTimeFormat.forPattern("MM/dd/yyyy");
		DateTime startDate=fmt.parseDateTime("01/01/2014");
		DateTime endDate=new DateTime();
		
		List<BasicStockData> percBasicStockData=basicStockDataService.getPercentageBasicStockDataForCompanyBetweenDates(5l, startDate, endDate, true, "YAHOO");
		for(int i=0;i<percBasicStockData.size();i++){
			BasicStockData curPercBsd=percBasicStockData.get(i);
			StringBuffer sb=new StringBuffer();
			sb.append("Date "+curPercBsd.getDateTime());
			sb.append(", Close="+curPercBsd.getClose().toPlainString());
			sb.append(", Open="+curPercBsd.getOpen().toPlainString());
			sb.append(", High="+curPercBsd.getHigh().toPlainString());
			sb.append(", Low="+curPercBsd.getLow().toPlainString());
			sb.append(", Volume="+curPercBsd.getVolume().toPlainString());
			System.out.println(sb.toString());
		}
		
	
	}
	
}
