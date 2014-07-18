package com.hbar.finance.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

public class DateAlignedBasicStockDataContainer {
	private Map<String,Integer> columnNameToIndexMap=new HashMap<String,Integer>();
	private Map<DateTime, String[]> dateToDataMap=new HashMap<DateTime,String[]>();
	public Map<String, Integer> getColumnNameToIndexMap() {
		return columnNameToIndexMap;
	}
	public void setColumnNameToIndexMap(Map<String, Integer> columnNameToIndexMap) {
		this.columnNameToIndexMap = columnNameToIndexMap;
	}
	public Map<DateTime, String[]> getDateToDataMap() {
		return dateToDataMap;
	}
	public void setDateToDataMap(Map<DateTime, String[]> dateToDataMap) {
		this.dateToDataMap = dateToDataMap;
	}
	
	
}
