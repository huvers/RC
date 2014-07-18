package com.hbar.finance.dao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.runner.RunWith;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.transaction.annotation.Transactional;

import com.hbar.finance.dao.CompanyAndStrategyAnalysisDao;
import com.hbar.finance.dao.CompanyDao;
import com.hbar.finance.dao.OptionQuoteDao;
import com.hbar.finance.dao.StockQuoteDao;
import com.hbar.finance.model.OptionQuote;
import com.hbar.finance.model.StockQuote;
import com.hbar.finance.neural.Plot;
import com.hbar.finance.service.FinanceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/applicationContext.xml"})
@Transactional
@Ignore
public class DataDump {
	@Autowired
	OptionQuoteDao optionQuoteDao;

	@Autowired
	StockQuoteDao stockQuoteDao;

	@Autowired
	CompanyDao companyDao;

	@Autowired
	CompanyAndStrategyAnalysisDao companyAndStrategyAnalysisDao;
	
	@Autowired 
	FinanceService financeService;
	
	@Test
	public void testSaveAndRetrieveStockQuotes(){
		
		try {
			
			StockQuote stockQuote=financeService.getStockQuoteWithOrderedOptions(4989l);
			List<OptionQuote> optionQuotes=stockQuote.getOptionQuotes();
			List<BigDecimal> listBdPrices=new ArrayList<BigDecimal>();
			listBdPrices.add(new BigDecimal(80));
			listBdPrices.add(new BigDecimal(81));
			listBdPrices.add(new BigDecimal(82));
			listBdPrices.add(new BigDecimal(83));
			listBdPrices.add(new BigDecimal(84));
			listBdPrices.add(new BigDecimal(85));
			listBdPrices.add(new BigDecimal(86));
			listBdPrices.add(new BigDecimal(87));
			listBdPrices.add(new BigDecimal(88));
			listBdPrices.add(new BigDecimal(89));
			listBdPrices.add(new BigDecimal(90));
			listBdPrices.add(new BigDecimal(91));
			StringBuffer sbColumns=new StringBuffer("ID,Name,Option Type,Option Desc,Option Ask,Strike Price,Stock Price,S/E,W/E,Created Stock Price,Premium");
			for(int i=0;i<listBdPrices.size();i++){
				sbColumns.append(",% Gain / Profit / Cost at "+listBdPrices.get(i).toPlainString());
			}
			
			Date date=stockQuote.getDateTimeCreated();
			DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			String formattedDate = df.format(date); 
			PrintWriter pw=new PrintWriter(new File("C:/Users/Admin/Google Drive/OptionsData/"+stockQuote.getSymbol()+"_sqId_"+stockQuote.getId()+"_"+formattedDate+".csv"));
			pw.println(sbColumns.toString());
			
			Map<String, List<double[]>> monthToXYCoords=new HashMap<String,List<double[]>>();
			Map<String, double[]> monthToMinMaxXCoord=new HashMap<String,double[]>();
			Map<String, double[]> monthToMinMaxYCoord=new HashMap<String,double[]>();
			
			for(int i=0;i<optionQuotes.size();i++){
				OptionQuote curOptionQuote=optionQuotes.get(i);
				if(curOptionQuote.getPutCall().equalsIgnoreCase("put")||curOptionQuote.getAsk().equals(BigDecimal.ZERO))
					continue;
					
				BigDecimal bdSE=null;
				BigDecimal askOrLastStockPrice=null;
				if(!stockQuote.getLast().equals(BigDecimal.ZERO)){
					askOrLastStockPrice=stockQuote.getLast();
					bdSE=stockQuote.getLast().divide(curOptionQuote.getStrikeprice(), 10, RoundingMode.HALF_EVEN);

				}else{
					askOrLastStockPrice=stockQuote.getAsk();
					bdSE=stockQuote.getAsk().divide(curOptionQuote.getStrikeprice(), 10, RoundingMode.HALF_EVEN);

				}
				BigDecimal bdWE=curOptionQuote.getAsk().divide(curOptionQuote.getStrikeprice(), 10, RoundingMode.HALF_EVEN);
				//price of option + strikeprice= stockprice + premium=created stock price
				BigDecimal bdCreatedStockPrice=curOptionQuote.getAsk().add(curOptionQuote.getStrikeprice());
				BigDecimal bdPremiumPrice=bdCreatedStockPrice.subtract(askOrLastStockPrice);
				StringBuffer sbColumnValues=new StringBuffer(curOptionQuote.getId()+","+ stockQuote.getName()+","+curOptionQuote.getPutCall()
				+","+curOptionQuote.getIssueDesc()+","+curOptionQuote.getAsk().toPlainString()+","+curOptionQuote.getStrikeprice().toPlainString()+","+askOrLastStockPrice
				+","+bdSE.toPlainString()+","+bdWE.toPlainString()+","+bdCreatedStockPrice.toPlainString()+","+bdPremiumPrice.toPlainString());
				
				List<double[]> curXYCoords=monthToXYCoords.get(curOptionQuote.getXmonth().toString());
				if(curXYCoords==null){
					curXYCoords=new ArrayList<double[]>();
					curXYCoords.add(new double[]{bdSE.doubleValue(),bdWE.doubleValue()});
					monthToXYCoords.put(curOptionQuote.getXmonth().toString(), curXYCoords);
				}else{
					curXYCoords.add(new double[]{bdSE.doubleValue(),bdWE.doubleValue()});
				}
				double[] curMinMaxXCoords=monthToMinMaxXCoord.get(curOptionQuote.getXmonth().toString());
				if(curMinMaxXCoords==null){
					curMinMaxXCoords=new double[]{bdSE.doubleValue(),bdSE.doubleValue()};
					monthToMinMaxXCoord.put(curOptionQuote.getXmonth().toString(), curMinMaxXCoords);
				}else{
					if(bdSE.doubleValue()<curMinMaxXCoords[0]){
						curMinMaxXCoords[0]=bdSE.doubleValue();
						monthToMinMaxXCoord.put(curOptionQuote.getXmonth().toString(), curMinMaxXCoords);
					}else if(curMinMaxXCoords[1]<bdSE.doubleValue()){
						curMinMaxXCoords[1]=bdSE.doubleValue();
						monthToMinMaxXCoord.put(curOptionQuote.getXmonth().toString(), curMinMaxXCoords);
					}
				}
				double[] curMinMaxYCoords=monthToMinMaxYCoord.get(curOptionQuote.getXmonth().toString());
				if(curMinMaxYCoords==null){
					curMinMaxYCoords=new double[]{bdSE.doubleValue(),bdSE.doubleValue()};
					monthToMinMaxYCoord.put(curOptionQuote.getXmonth().toString(), curMinMaxYCoords);
				}else{
					if(bdWE.doubleValue()<curMinMaxYCoords[0]){
						curMinMaxYCoords[0]=bdWE.doubleValue();
						monthToMinMaxYCoord.put(curOptionQuote.getXmonth().toString(), curMinMaxYCoords);
					}else if(curMinMaxYCoords[1]<bdWE.doubleValue()){
						curMinMaxYCoords[1]=bdWE.doubleValue();
						monthToMinMaxYCoord.put(curOptionQuote.getXmonth().toString(), curMinMaxYCoords);
					}
				}
				
				
				for(int j=0;j<listBdPrices.size();j++){
					BigDecimal profit=null;
					BigDecimal percentageGain=null;
					
					BigDecimal possiblePrice=listBdPrices.get(j);
					profit=possiblePrice.subtract(bdCreatedStockPrice);

					percentageGain=profit.divide(curOptionQuote.getAsk(), 10, RoundingMode.HALF_EVEN);
					
					
					sbColumnValues.append(","+percentageGain.multiply(new BigDecimal(100)).toPlainString()+"% / "+profit.multiply(new BigDecimal(100)).toPlainString()+" / "+curOptionQuote.getAsk().multiply(new BigDecimal(100)));
				}
				
				pw.println(sbColumnValues.toString());
			}
			pw.flush();
			pw.close();
			
			Set<String> setMonths=monthToXYCoords.keySet();
			for(String curMonth:setMonths){
				System.out.println("month="+curMonth);
			}
			String[] months=new String[]{"1","4"};
			
			for(String curMonth:months){
				double[] minMaxX=monthToMinMaxXCoord.get(curMonth);
				double[] minMaxY=monthToMinMaxYCoord.get(curMonth);
				System.out.println("Month "+curMonth+", MinX="+minMaxX[0]+", MaxX="+minMaxX[1]+", MinY="+minMaxY[0]+", MaxY="+minMaxY[1]);
			}
			Plot.displayChart("S/E vs W/E", "S/E vs W/E", "xAxis", "yAxis", .7, 2, -.5, 1, months[0], monthToXYCoords.get(months[0]), months[1],  monthToXYCoords.get(months[1]));
						
/*			List<double[]> expectedSeries = new ArrayList<double[]>();
			expectedSeries.add(new double[]{1.0, 0.679});
			expectedSeries.add(new double[]{2.0, 0.963});
			expectedSeries.add(new double[]{3.0, 0.760});
			
			List<double[]> outputSeries = new ArrayList<double[]>();
			outputSeries.add(new double[]{1.0, 0.0});
			outputSeries.add(new double[]{2.0, 0.557});
			outputSeries.add(new double[]{3.0, 0.830});

			
			
			Plot.displayChart("Committee", "Committee (RMSE: " + 1.2 + ")", "xAxis", "yAxis", 0, 29, -3, 3, "Expected", expectedSeries, "Output", outputSeries);*/
			System.out.println("test");
			
//			Plot.displayChart("S/E vs W/E", "S/E vs W/E", "xAxis", "yAxis", -1, 2, -1, 2, "11", values1, "12", values2);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

