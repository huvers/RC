package com.hbar.finance.providers.tradeking;
import com.hbar.finance.model.StockQuote;

import java.util.List;


public class StocksResponse {
	private List<StockQuote> quotes;

	public List<StockQuote> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<StockQuote> quotes) {
		this.quotes = quotes;
	}
}
