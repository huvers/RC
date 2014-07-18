package com.hbar.finance.studies;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.hbar.finance.model.BasicStockData;

public class BollingerBandsCalculator {
	
	public BollingerBandsData calculateBollingerBandsData(List<BasicStockData> basicStockData){
		if(basicStockData.size()==0)
			return null;
		
		BigDecimal bdMean=BigDecimal.ZERO;
		BigDecimal bdStdDeviation=BigDecimal.ZERO;
		for(int i=0;i<basicStockData.size();i++){
			bdMean=bdMean.add(basicStockData.get(i).getClose());
		}
		BigDecimal size=new BigDecimal(basicStockData.size());
		
		bdMean=bdMean.divide(size, RoundingMode.HALF_EVEN);
		
		for(int i=0;i<basicStockData.size();i++){
			BigDecimal bdStockValueMinusMean=basicStockData.get(i).getClose().subtract(bdMean);
			BigDecimal bdStockValueMinusMeanSquared=bdStockValueMinusMean.multiply(bdStockValueMinusMean);
			
			bdStdDeviation=bdStdDeviation.add(bdStockValueMinusMeanSquared);
		}
		bdStdDeviation=bdStdDeviation.divide(size, RoundingMode.HALF_EVEN);
		bdStdDeviation=new BigDecimal(Math.sqrt(bdStdDeviation.doubleValue()));
		
		
		BollingerBandsData bandsData=new BollingerBandsData();
		bandsData.setMean(bdMean.setScale(2, RoundingMode.HALF_EVEN));
		bandsData.setStandardDeviation(bdStdDeviation.setScale(2, RoundingMode.HALF_EVEN));
		BigDecimal bandFactor=new BigDecimal(2);
		
		bandsData.setLowerBand(bdMean.subtract(bdStdDeviation.multiply(bandFactor)).setScale(2, RoundingMode.HALF_EVEN));
		bandsData.setUpperBand(bdMean.add(bdStdDeviation.multiply(bandFactor)).setScale(2, RoundingMode.HALF_EVEN));
		
		return bandsData;	
			
	}
	
}
