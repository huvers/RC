package com.hbar.finance.strategy;
/**
 * This class is used to test ideas outlined in "The Kelly Criterion In Blackjack Sports Betting, and The
 * Stock Market" by Edward Thorp (2007), section 7.1.
 * 
 * @author Admin
 *
 */
public class ContinousApproximation {
	private double initialCapital;
	
	private double currentCapital;
	
	public ContinousApproximation(double initialCapital){
		this.initialCapital=initialCapital;
		this.currentCapital=initialCapital;
		
	}
	
	public double getNextYearCapitalAmount(double kellyFraction, double riskFreeRate, double m, double s){
		currentCapital=currentCapital*(1+(1-kellyFraction)*riskFreeRate+kellyFraction*RandomVariable.getNextRandomValue(m, s));
		return currentCapital;
	}
	
	public static void main(String[] args){
		int nYears=100;
		double m=.11;
		double s=.15;
		double riskFreeRate=.06;
		
		double kellyFraction=(m-riskFreeRate)/(s*s);
		System.out.println("kelly Fraction = "+kellyFraction);
		
		for (int j = 0; j < 10; j++) {
			double currentCapital = 0;
			ContinousApproximation continousApproximation=new ContinousApproximation(10000d);
			for (int i = 0; i < nYears; i++) {
				currentCapital = continousApproximation
						.getNextYearCapitalAmount(kellyFraction, riskFreeRate,
								m, s);
				
			}
			
			System.out.println("Current Capital for trial["+j+"] = " + currentCapital);
			
		}
		
		System.out.println("---------------------");
		kellyFraction += 1.0;
		System.out.println("Fraction = "+kellyFraction);
		
		
		for (int j = 0; j < 10; j++) {
			double currentCapital = 0;
			ContinousApproximation continousApproximation=new ContinousApproximation(10000d);
			for (int i = 0; i < nYears; i++) {
				currentCapital = continousApproximation
						.getNextYearCapitalAmount(kellyFraction, riskFreeRate,
								m, s);
				
			}
			
			System.out.println("Current Capital for trial["+j+"] = " + currentCapital);
			
		}
		

		System.out.println("---------------------");
		kellyFraction = 7;
		System.out.println("Fraction = "+kellyFraction);
		
		
		for (int j = 0; j < 10; j++) {
			double currentCapital = 0;
			ContinousApproximation continousApproximation=new ContinousApproximation(10000d);
			for (int i = 0; i < nYears; i++) {
				currentCapital = continousApproximation
						.getNextYearCapitalAmount(kellyFraction, riskFreeRate,
								m, s);
				
			}
			
			System.out.println("Current Capital for trial["+j+"] = " + currentCapital);
			
		}
		
		System.out.println("---------------------");
		kellyFraction = 0;
		System.out.println("Fraction = "+kellyFraction);
		
		
		for (int j = 0; j < 10; j++) {
			double currentCapital = 0;
			ContinousApproximation continousApproximation=new ContinousApproximation(10000d);
			for (int i = 0; i < nYears; i++) {
				currentCapital = continousApproximation
						.getNextYearCapitalAmount(kellyFraction, riskFreeRate,
								m, s);
				
			}
			
			System.out.println("Current Capital for trial["+j+"] = " + currentCapital);
			
		}

	}
}

class RandomVariable{
	
	public static double getNextRandomValue(double m, double s){
		if(Math.random()>.5){
			return m+s;
		}else{
			return m-s;
		}
	}
	
}