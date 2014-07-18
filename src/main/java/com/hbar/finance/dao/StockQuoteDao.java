package com.hbar.finance.dao;

import com.hbar.finance.model.StockQuote;

public interface StockQuoteDao extends GenericDao<StockQuote,Long> {
	public StockQuote getStockQuoteWithOrderedOptions(Long id);
}
