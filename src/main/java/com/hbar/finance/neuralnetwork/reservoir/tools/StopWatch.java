package com.hbar.finance.neuralnetwork.reservoir.tools;

public class StopWatch {
	long startTime;
	long stopTime;
	
	public void start() {
		startTime = System.currentTimeMillis();
	}
	
	public void stop() {
		stopTime = System.currentTimeMillis();
	}
	
	public long duration() {
		return stopTime - startTime;
	}
	
	public String toString() {
		return "" + duration();
	}
}
