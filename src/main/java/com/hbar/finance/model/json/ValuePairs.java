package com.hbar.finance.model.json;

import java.util.List;

public class ValuePairs {
	
	private double minXValue;
	private double maxXValue;

	private double minYValue;
	private double maxYValue;
	
	
	private List<ValuePair> valuePairs;
	

	public List<ValuePair> getValuePairs() {
		return valuePairs;
	}

	public void setValuePairs(List<ValuePair> valuePairs) {
		this.valuePairs = valuePairs;
	}

	public double getMinXValue() {
		return minXValue;
	}

	public void setMinXValue(double minXValue) {
		this.minXValue = minXValue;
	}

	public double getMaxXValue() {
		return maxXValue;
	}

	public void setMaxXValue(double maxXValue) {
		this.maxXValue = maxXValue;
	}

	public double getMinYValue() {
		return minYValue;
	}

	public void setMinYValue(double minYValue) {
		this.minYValue = minYValue;
	}

	public double getMaxYValue() {
		return maxYValue;
	}

	public void setMaxYValue(double maxYValue) {
		this.maxYValue = maxYValue;
	}

	
	
}
