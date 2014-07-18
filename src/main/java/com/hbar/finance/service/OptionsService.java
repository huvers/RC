package com.hbar.finance.service;

import com.hbar.finance.model.StockQuote;

public interface OptionsService {
	public void retrieveAndPersistsOptionsInfo(StockQuote stockQuote) ;
}
