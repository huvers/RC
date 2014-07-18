package com.hbar.finance.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class StockDividendStrategy {
	public static void main(String[] args){
		BigDecimal initialInvestment=new BigDecimal(10000);
		BigDecimal monthlyInvestment=new BigDecimal(3600);
		BigDecimal interestRate=new BigDecimal(.17).divide(new BigDecimal(12), RoundingMode.HALF_EVEN);
		
		int months=6*12;
		BigDecimal totalValue=initialInvestment;
		for(int i=1;i<=months;i++){

			totalValue = totalValue.multiply(interestRate).add(
					totalValue);
			
			totalValue=totalValue.add(monthlyInvestment);

		}
		
		System.out.println("Value "+totalValue.toPlainString());
		
	}
	
}
