package com.hbar.finance.service;

import java.util.List;

import com.hbar.finance.model.StockQuote;

public interface StocksService {

	List<StockQuote> retrieveAndPersistsStocksInfo(String symbol);

}
