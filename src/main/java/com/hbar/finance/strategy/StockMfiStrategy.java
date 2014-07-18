package com.hbar.finance.strategy;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class StockMfiStrategy {
	public static void main(String[] args){
		BigDecimal initialInvestment=new BigDecimal(10000);
		BigDecimal yearlyInvestment=new BigDecimal(36000);
		BigDecimal interestRate=new BigDecimal(.25);
		
		int years=10;
		BigDecimal totalValue=initialInvestment;
		for(int i=1;i<=years;i++){

			totalValue = totalValue.multiply(interestRate).add(
					totalValue);
			
			totalValue=totalValue.add(yearlyInvestment);

		}
		
		System.out.println("Value "+totalValue.toPlainString());
		
	}
	
}
