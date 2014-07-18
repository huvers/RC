package com.hbar.finance.studies;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.hbar.finance.model.BasicStockData;

public class BollingerBandsSectionAnalyzer {
	public enum Section{
		LOWER, MIDLOWER, MID, MIDUPPER, UPPER
	}
	
	public Section getSectionForData(BasicStockData basicStockData, BollingerBandsData bandsData){

		BigDecimal bdPercentageLocation=basicStockData.getClose().subtract(bandsData.getLowerBand());
		bdPercentageLocation=bdPercentageLocation.divide(bandsData.getUpperBand().subtract(bandsData.getLowerBand()), RoundingMode.HALF_EVEN);
		if(bdPercentageLocation.doubleValue()<.20){
			return Section.LOWER;
			
		}else if(.2<=bdPercentageLocation.doubleValue()&&bdPercentageLocation.doubleValue()<.4){
			return Section.MIDLOWER;
		}else if(.4<=bdPercentageLocation.doubleValue()&&bdPercentageLocation.doubleValue()<.6){
			return Section.MID;
			
		}else if(.6<=bdPercentageLocation.doubleValue()&&bdPercentageLocation.doubleValue()<.8){
			return Section.MIDUPPER;
			
		}else{
			return Section.UPPER;
		}
		
	}
}
