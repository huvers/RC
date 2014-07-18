package com.hbar.finance.datasource;

public enum EquityDataSource {
	
	YAHOO(1), CSI(2);
	
	int equityDataSourceId;
	
	EquityDataSource(int equityDataSourceId){
		this.equityDataSourceId=equityDataSourceId;
	}
	
	public int getEquityDataSourceId(){
		return equityDataSourceId;
	}
		
}
