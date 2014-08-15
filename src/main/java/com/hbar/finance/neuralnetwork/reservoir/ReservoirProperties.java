package com.hbar.finance.neuralnetwork.reservoir;

public class ReservoirProperties {
	public static class Dimensions {
		public int input = 1;
		public int output = 1;
	}

	public static class Using {
		public boolean backPropagation = false;
		public boolean inputStates = true;
		public boolean previousState = true;
	}

	public static class Properties {
		public int nodes = 10;
		public double spectralRadius = 1;
		public double connectivity = 0.2; // between 0 and 1
		public double leakDecay = 1; // between 0 and 1

	}

	public Dimensions dimensions = new Dimensions();
	public Using using = new Using();
	public Properties properties = new Properties();

}
