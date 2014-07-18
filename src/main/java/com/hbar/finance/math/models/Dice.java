package com.hbar.finance.math.models;

public class Dice {
	
	public static final double MEAN=3.5d;
	public static final double STD_DEV=1.71d;
	
	public Integer getNextRollValue(){
		double random=Math.random();
		if(random<1.0d/6){
			return 1;
		}else if ( 1.0d/6 <= random && random < 2d/6 ){
			return 2;
		}else if ( 2d/6 <= random && random < 3d/6 ){
			return 3;
		}else if ( 3d/6 <= random && random < 4d/6 ){
			return 4;
		}else if ( 4d/6 <= random && random < 5d/6 ){
			return 5;
		}
		return 6;
	}
	public static void main(String[] args){
		int nTrials=1000;
		double nSum=0;
		Dice dice=new Dice();
		for(int i=0;i<nTrials;i++){
			nSum+=dice.getNextRollValue();
		}
		System.out.println("average="+((double)nSum)/((double)nTrials));
	}
	
}
