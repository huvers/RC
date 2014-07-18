package com.hbar.finance.math.models;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.distribution.NormalDistribution;
public class CentralLimitTheorem {
	List<Bucket> buckets=new ArrayList<Bucket>();
	
	public void createBuckets(int numberOfBuckets, double width){
		double start=-numberOfBuckets*width/2;
		
		for(int i=0;i<numberOfBuckets;i++){
			Bucket bucket=new Bucket();
			bucket.start=start;
			bucket.end=bucket.start+width;
			System.out.println("start="+bucket.start+", end="+bucket.end);
			
			start=bucket.end;
			buckets.add(bucket);
		}
	}
	
	public void createBuckets(int numberOfBuckets, double width, double start){
		
		
		for(int i=0;i<numberOfBuckets;i++){
			Bucket bucket=new Bucket();
			bucket.start=start;
			bucket.end=bucket.start+width;
			System.out.println("start="+bucket.start+", end="+bucket.end);
			
			start=bucket.end;
			buckets.add(bucket);
		}
	}
	
	public void printTheDistributionToConsole(int nVariables, int bucketSize, double width){
		createBuckets( bucketSize, width);
		Double Z=0.0;
		Double mean=5d;
		Double stdDev=2d;
		Double X=0.0d;
		for(int i=0;i<nVariables;i++){
			NormalDistribution normalDistribution=new NormalDistribution(mean, stdDev);
			normalDistribution.reseedRandomGenerator((long)(10000*Math.random()));
			X+=normalDistribution.sample();
			Z=(X-(i+1)*mean)/(stdDev*Math.sqrt(((double)(i+1))));
			addToBucket(Z);
			System.out.println(Z);
		}
		
		for(Bucket bucket:buckets){
			//System.out.println("bucket.start="+bucket.start+", bucket.end="+bucket.end+", bucket.count="+bucket.count);
			for(int i=0;i<bucket.count/10;i++){
				System.out.print("X");
			}
			System.out.println();
		}
		
	}
	
	public void printBuckets(){
		for(Bucket bucket:buckets){
			//System.out.println("bucket.start="+bucket.start+", bucket.end="+bucket.end+", bucket.count="+bucket.count);
			System.out.print(bucket.start+"--");
			for(int i=0;i<bucket.count/10;i++){
				System.out.print("X");
			}
			System.out.println();
		}
		
	}
	
	public void printBuckets(double normalize){
		for(Bucket bucket:buckets){
			//System.out.println("bucket.start="+bucket.start+", bucket.end="+bucket.end+", bucket.count="+bucket.count);
			System.out.print(bucket.start+"--");
			for(double i=0;i/normalize<bucket.count/normalize;i++){
				System.out.print("X");
			}
			System.out.println();
		}
		
	}
	public void addToBucket(double Z){
		if(Z==6.0d){
			System.out.println("break");
		}
		
		if(Z<=buckets.get(0).start){
			buckets.get(0).count++;
			return;
		}
		if(buckets.get(buckets.size()-1).end<Z){
			buckets.get(buckets.size()-1).count++;
			return;
		}
		
		for(int i=0;i<buckets.size();i++){
			if(buckets.get(i).start<=Z&&Z<buckets.get(i).end){
				buckets.get(i).count++;
				break;
			}
		}
	}
	
	public void twoDiceDistribution(int nRolls){
		this.createBuckets(11, .5, 1);
		Dice dice=new Dice();
		for(int i=0;i<nRolls;i++){
			double value1=(double)dice.getNextRollValue();
			double value2=(double)dice.getNextRollValue();
			this.addToBucket((value1+value2)/2);
			
		}
	
		printBuckets();
	}
	
	public void zDiceDistribution(int nRolls){
		this.createBuckets(80, .05, -2);
		Dice dice=new Dice();
		double totalDiceSumX=0.0d;
		double zValue=0.0d;
		for(int i=0;i<nRolls;i++){
//			double value=(double)dice.getNextRollValue();
			double value1=(double)dice.getNextRollValue();
			double value2=(double)dice.getNextRollValue();

			
			totalDiceSumX+=(value1+value2)/2;
			
			zValue=(totalDiceSumX-(i+1)*Dice.MEAN)/(Dice.STD_DEV*Math.sqrt(nRolls));
			this.addToBucket(zValue);
			System.out.println(zValue);
			
		}
	
		printBuckets();//nRolls);
	}
	
	public static void main(String args[]){
		CentralLimitTheorem centralLimitTheorem=new CentralLimitTheorem();
		centralLimitTheorem.zDiceDistribution(100000);
		//centralLimitTheorem.printTheDistributionToConsole(10000000, 2000, .05);
	}
}
class Bucket{
	double start;
	double end;
	double count;
	
}
