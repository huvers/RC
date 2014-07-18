package com.hbar.finance.studies;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.hbar.finance.model.Company;
import com.hbar.finance.service.BasicStockDataService;
import com.hbar.finance.service.FinanceService;
import com.hbar.finance.studies.BollingerBandsCalculator;
import com.hbar.finance.studies.BollingerBandsData;
import com.hbar.finance.studies.BollingerBandsSectionAnalyzer;
import com.hbar.finance.studies.BollingerBandsSectionAnalyzer.Section;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/applicationContext.xml"})
@Transactional
@Ignore
public class BollingerBandsClassifierStressTest {
	@Autowired
	private BollingerBandsCalculator bollingerBands;

	@Autowired
	private BasicStockDataService basicStockDataService;

	@Autowired
	private FinanceService financeService;

	public void setBollingerBands(BollingerBandsCalculator bollingerBands){
		this.bollingerBands=bollingerBands;
	}

	public void setBasicStockDataService(BasicStockDataService basicStockDataService){
		this.basicStockDataService=basicStockDataService;
	}	
	
	@Test
	public void testBollingerBands() throws ParseException, FileNotFoundException{
		try{
			BollingerBandsSectionAnalyzer analyzer=new BollingerBandsSectionAnalyzer();
			List<Company> companies=financeService.getAllCompanies();
	
			int yearAnalyzed=2013;
			PrintWriter pwAllCompanies = new PrintWriter(new File(
					"C:/Users/Admin/Desktop/BollingerData/Bollinger_Stress/BollingerStressProfit_"
							+ yearAnalyzed + "_long.csv"));
			pwAllCompanies.println("Symbol,Initial Buy,Final Sell,Total Bought,Total Sold,Profit");
			BigDecimal totalLongBought=BigDecimal.ZERO;
			BigDecimal totalLongSold=BigDecimal.ZERO;
			
			for(int i=0;i<companies.size();i++){
				
				Company curCompany=companies.get(i);
				//if(curCompany.getId()!=5)
				//	continue;
				
				Calendar cal=Calendar.getInstance();
				String strStartDate="01/01/"+yearAnalyzed;
			    //USE THIS ONCE COMPLETELY CONVERTED TO JODA TIME
				//DateTimeFormatter fmt = DateTimeFormat.forPattern("MM/dd/yyyy");

				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				Date curDate=formatter.parse(strStartDate);
				
				String strEndDate="01/01/"+(yearAnalyzed+1); 
				Date endDate=formatter.parse(strEndDate);
				
				cal.setTime(curDate);
				boolean isLong=false;				
				boolean isShort=false;
				DateTime lastDateAnalyzed=null;
				BigDecimal currentValue=null;
				BigDecimal currentValueShort=null;
				BigDecimal profit=BigDecimal.ZERO;
				BasicStockData initialStockData = null;
				BasicStockData lastSellStockData = null;
				
				BasicStockData latestStockData = null;
				BigDecimal profitShort = BigDecimal.ZERO;
				PrintWriter pw = new PrintWriter(new File(
						"C:/Users/Admin/Desktop/BollingerData/Bollinger_Stress/BollingerStress_"
								+ curCompany.getSymbol() + "_long.txt"));
				PrintWriter pwCsv = new PrintWriter(new File(
						"C:/Users/Admin/Desktop/BollingerData/Bollinger_Stress/BollingerStress_"
								+ curCompany.getSymbol() + "_long_.csv"));
	
				pwCsv.println("DATE BOUGHT,DATE SOLD,BUY PRICE,SELL PRICE,PROFIT");
				StringBuffer sbCurLongCsv=new StringBuffer("");
				PrintWriter pwShort = new PrintWriter(new File(
						"C:/Users/Admin/Desktop/BollingerData/Bollinger_Stress/BollingerStress_"
								+ curCompany.getSymbol() + "_short.txt"));
				boolean bNoData=false;
				while (curDate.before(endDate)&&!bNoData) {
					System.out.println("Analyzing date " + curDate);
					DateTime jtCurDate=new DateTime(curDate.getTime());
					List<BasicStockData> basicStockData = basicStockDataService
							.getBasicStockDataForCompanyBeforeDate(curCompany.getId(),
									jtCurDate, 20, false,"YAHOO");
					if(basicStockData.size()==0){
						bNoData=true;
						continue;
					}
					latestStockData = basicStockData.get(0);
					if (lastDateAnalyzed != null
							&& lastDateAnalyzed.equals(latestStockData.getDateTime())) {
						cal.add(Calendar.DAY_OF_YEAR, 1);
						curDate = cal.getTime();
						continue;
					}
	
					BollingerBandsData bandsData = bollingerBands
							.calculateBollingerBandsData(basicStockData);
					Section section = analyzer.getSectionForData(latestStockData,
							bandsData);
	
					if (section.equals(Section.LOWER) && !isLong) {
						// buy
						if(initialStockData==null){
							initialStockData=latestStockData;
						}
						
						currentValue = latestStockData.getClose();
						isLong = true;
						totalLongBought=totalLongBought.add(currentValue);
						pw.println("BUY ON " + latestStockData.getDateTime() + " FOR "
								+ currentValue);
						sbCurLongCsv.append(latestStockData.getDateTime());
						
	
					} else if (section.equals(Section.UPPER) && isLong) {
						// sell
						lastSellStockData=latestStockData;
						BigDecimal curProfit=latestStockData.getClose().subtract(currentValue);
						
						
						sbCurLongCsv.append(","+latestStockData.getDateTime()+","+currentValue+","+latestStockData.getClose()+","+curProfit);
						pwCsv.println(sbCurLongCsv);
						sbCurLongCsv=new StringBuffer("");
						
						totalLongSold=totalLongSold.add(latestStockData.getClose());
						profit = profit.add(curProfit);
						pw.println("SELL ON " + latestStockData.getDateTime() + " FOR "
								+ latestStockData.getClose());
						pw.println("----PROFIT AS OF " + latestStockData.getDateTime()
								+ " " + profit);
						isLong = false;
	
					}
	
					if (section.equals(Section.LOWER) && isShort) {
						// buy
	
						isShort = false;
						profitShort = profitShort.add(currentValueShort
								.subtract(latestStockData.getClose()));
						pwShort.println("BUY TO CLOSE ON "
								+ latestStockData.getDateTime() + " FOR "
								+ latestStockData.getClose());
						pwShort.println("----PROFIT AS OF "
								+ latestStockData.getDateTime() + " " + profitShort);
	
					} else if (section.equals(Section.UPPER) && !isShort) {
						// sell short
						currentValueShort = latestStockData.getClose();
						pwShort.println("SELL SHORT ON "
								+ latestStockData.getDateTime() + " FOR "
								+ latestStockData.getClose());
	
						isShort = true;
	
					}
	
					lastDateAnalyzed = latestStockData.getDateTime();
					cal.add(Calendar.DAY_OF_YEAR, 1);
					curDate = cal.getTime();
				}
	
				if(isLong){
					BigDecimal curProfit=latestStockData.getClose().subtract(currentValue);
					sbCurLongCsv.append(","+latestStockData.getDateTime()+","+currentValue+","+latestStockData.getClose()+","+curProfit);
					totalLongSold=totalLongSold.add(latestStockData.getClose());
					profit = profit.add(curProfit);
					pwCsv.println(sbCurLongCsv);
					if(initialStockData!=null){
						//initial stock data == null means we never bought anything
						pwAllCompanies.println(curCompany.getSymbol()+","+initialStockData.getClose()+","+latestStockData.getClose()+","+totalLongBought+","+totalLongSold+","+profit);
					}
						
				
				}else{
					//fix here, should not use latestStockData.getClose() since the position is closed. 
					if(initialStockData!=null){
						//initial stock data == null means we never bought anything
						pwAllCompanies.println(curCompany.getSymbol()+","+initialStockData.getClose()+","+lastSellStockData.getClose()+","+totalLongBought+","+totalLongSold+","+profit);
					}
					
				}
				pw.println("\n\n\n\nFINAL PROFIT " + profit + " FINAL POSITION "
						+ (isLong ? "LONG VALUE " + currentValue : "NOT LONG"));
				pw.println("\n\n\n\nTOTAL LONG BOUGHT " + totalLongBought + " TOTAL LONG SOLD "
						+totalLongSold);
				
				pw.flush();
				pw.close();
	
				pwCsv.flush();
				pwCsv.close();
	
				
				pwShort.println("\n\n\n\nFINAL PROFIT " + profitShort
						+ " FINAL POSITION "
						+ (isShort ? "SHORT " + currentValueShort : "NOT SHORT"));
				pwShort.flush();
				pwShort.close();
				// basicStockDataService.getBasicStockDataForCompanyBeforeDate(companyId,
				// date, limit)
		
			}
			
			pwAllCompanies.flush();
			pwAllCompanies.close();
		}catch(Exception exc){
			exc.printStackTrace();
		}
	}
}

