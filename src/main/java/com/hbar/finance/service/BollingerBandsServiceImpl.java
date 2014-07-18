package com.hbar.finance.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.hbar.finance.date.DateUtils;
import com.hbar.finance.model.BasicStockData;
import com.hbar.finance.model.Company;
import com.hbar.finance.studies.BollingerBandsCalculator;
import com.hbar.finance.studies.BollingerBandsData;

public class BollingerBandsServiceImpl implements BollingerBandsService {
	@Autowired
	private BasicStockDataService basicStockDataService;
	
	@Autowired
	private FinanceService financeService;
	
	public void setBasicStockDataService(BasicStockDataService basicStockDataService) {
		this.basicStockDataService = basicStockDataService;
	}
	
	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	
	public String getEquitiesClassifiedByBandSection(List<Company> companies, String date){
		
		BollingerBandsCalculator bollingerBandsCalculator= new BollingerBandsCalculator();
		//dump each item in proper slot
		//PrintWriter pw=new PrintWriter(new File("C:/Users/Admin/Google Drive/OptionsData/"+stockQuote.getSymbol()+"_sqId_"+stockQuote.getId()+"_"+formattedDate+".csv"));
		//PrintWriter pw=new PrintWriter(new File("C:/Users/Admin/Desktop/BollingerData/Bollinger"+date.replaceAll("/", "_")+".csv"));
		StringBuffer sbBandLocation=new StringBuffer("");
		//pw.println("Symbol,Stock Price,Lower Band, Mean, Upper Band");
		sbBandLocation.append("Lower,MidLower,Mid,MidUpper,Upper\n");

		Map<String,List<String>> bandLocationMap=new HashMap<String,List<String>>();
		bandLocationMap.put("Lower", new ArrayList<String>());
		bandLocationMap.put("MidLower", new ArrayList<String>());
		bandLocationMap.put("Mid", new ArrayList<String>());
		bandLocationMap.put("MidUpper", new ArrayList<String>());
		bandLocationMap.put("Upper", new ArrayList<String>());

		for(Company curCompany:companies){
			
			DateTime endDate=DateUtils.createDateTimeFromString(date);
			List<BasicStockData> basicStockData = basicStockDataService.getBasicStockDataForCompanyBeforeDate(curCompany.getId(), endDate , 20, false, "YAHOO");
			
			if(basicStockData.size()==0)
				continue;
			
			BollingerBandsData bandsData=bollingerBandsCalculator.calculateBollingerBandsData(basicStockData);
			
			BasicStockData latestData=basicStockData.get(0);
			StringBuffer dataLine=new StringBuffer("");
			dataLine.append(curCompany.getSymbol());
			dataLine.append(","+latestData.getClose().toPlainString());
			dataLine.append(","+bandsData.getLowerBand());
			dataLine.append(","+bandsData.getMean());
			dataLine.append(","+bandsData.getUpperBand());
			
			BigDecimal bdPercentageLocation=latestData.getClose().subtract(bandsData.getLowerBand());
			bdPercentageLocation=bdPercentageLocation.divide(bandsData.getUpperBand().subtract(bandsData.getLowerBand()), RoundingMode.HALF_EVEN);
			
			if(bdPercentageLocation.doubleValue()<.20){
				List<String> lower=bandLocationMap.get("Lower");
				lower.add(curCompany.getSymbol());
				
			}else if(.2<=bdPercentageLocation.doubleValue()&&bdPercentageLocation.doubleValue()<.4){
				List<String> midLower=bandLocationMap.get("MidLower");
				midLower.add(curCompany.getSymbol());
				
			}else if(.4<=bdPercentageLocation.doubleValue()&&bdPercentageLocation.doubleValue()<.6){
				List<String> mid=bandLocationMap.get("Mid");
				mid.add(curCompany.getSymbol());
				
			}else if(.6<=bdPercentageLocation.doubleValue()&&bdPercentageLocation.doubleValue()<.8){
				List<String> midUpper=bandLocationMap.get("MidUpper");
				midUpper.add(curCompany.getSymbol());
				
			}else if(.8<=bdPercentageLocation.doubleValue()){
				List<String> upper=bandLocationMap.get("Upper");
				upper.add(curCompany.getSymbol());
				
			}
			//pw.println(dataLine.toString());
		}
		
		/*pw.flush();
		pw.close();
		*/
		List<String> lower=bandLocationMap.get("Lower");
		List<String> midLower=bandLocationMap.get("MidLower");
		List<String> mid=bandLocationMap.get("Mid");
		List<String> midUpper=bandLocationMap.get("MidUpper");
		List<String> upper=bandLocationMap.get("Upper");
		
		for(int i=0;;i++){
			if(i<lower.size()||i<midLower.size()||i<mid.size()||i<midUpper.size()||i<upper.size()){
				StringBuffer sbLine=new StringBuffer("");
				if(i<lower.size()){
					sbLine.append(lower.get(i));
				}else{
					sbLine.append("");
				}
				if(i<midLower.size()){
					sbLine.append(","+midLower.get(i));
				}else{
					sbLine.append(",");
				}
				if(i<mid.size()){
					sbLine.append(","+mid.get(i));
				}else{
					sbLine.append(",");
				}
				if(i<midUpper.size()){
					sbLine.append(","+midUpper.get(i));
				}else{
					sbLine.append(",");
				}
				if(i<upper.size()){
					sbLine.append(","+upper.get(i));
				}else{
					sbLine.append(",");
				}
				sbBandLocation.append(sbLine.toString()).append("\n");
			}else{
				break;
			}
			
		}
		return sbBandLocation.toString();
	
	}

}
