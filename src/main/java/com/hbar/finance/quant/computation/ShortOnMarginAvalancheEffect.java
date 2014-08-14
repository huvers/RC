package com.hbar.finance.quant.computation;
/**
 * This class is used to computations for the Avalanche effect described in "Beat the Market by Edward Thorp". 
 * @author Gerardo.Alvarado
 *
 */
public class ShortOnMarginAvalancheEffect {
	public static double computeProfit(Double initialPrice, Double finalPrice, Double initialQuantity, Double margin, Double actionPriceChange){
		
		Double quantity=initialQuantity;
		Double curPrice=initialPrice;
		System.out.println("price "+curPrice+" quantity="+quantity+" profit="+0);
		
		double totalProfit=0.0;
		double changeInProfit=0.0;
		while(finalPrice<curPrice){
			curPrice=curPrice-actionPriceChange;
			changeInProfit=actionPriceChange*quantity;
			totalProfit+=changeInProfit;
			double additionalQuantity=(margin+1)*actionPriceChange*quantity/(margin*curPrice);
			
			System.out.println("price "+curPrice+" - quantity="+quantity+" - Increase In Profit="+(changeInProfit)+" - Total Profit "+totalProfit);
			
			//additional quantity that we can buy because of margin
			quantity+=additionalQuantity;
			
		}
		
		return 0.0d;
	}
	
	public static void main(String[] args){
		computeProfit(13d, 1d, 1000d, .5d, 1d);
		//computeProfit(500d, 5d, 1d, 1d, 1/1000d);
	}
}
