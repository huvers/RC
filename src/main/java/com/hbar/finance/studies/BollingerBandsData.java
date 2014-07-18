package com.hbar.finance.studies;

import java.math.BigDecimal;

public class BollingerBandsData {
	private BigDecimal mean;
	private BigDecimal standardDeviation;
	private BigDecimal lowerBand;
	private BigDecimal upperBand;
	public BigDecimal getMean() {
		return mean;
	}
	public void setMean(BigDecimal mean) {
		this.mean = mean;
	}
	public BigDecimal getStandardDeviation() {
		return standardDeviation;
	}
	public void setStandardDeviation(BigDecimal standardDeviation) {
		this.standardDeviation = standardDeviation;
	}
	public BigDecimal getLowerBand() {
		return lowerBand;
	}
	public void setLowerBand(BigDecimal lowerBand) {
		this.lowerBand = lowerBand;
	}
	public BigDecimal getUpperBand() {
		return upperBand;
	}
	public void setUpperBand(BigDecimal upperBand) {
		this.upperBand = upperBand;
	}
}
