package com.hbar.finance.dao;

import java.util.Date;

import com.hbar.finance.model.StockQuote;

public interface StockQuoteDao extends GenericDao<StockQuote,Long> {
	public StockQuote getStockQuoteWithOrderedOptions(Long id);
	
	public StockQuote getStockForSymbolAndDate(String symbol, Date date);
}
