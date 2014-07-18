package com.hbar.finance.providers.tradeking;
import com.hbar.finance.model.OptionQuote;

import java.util.List;

public class OptionsResponse {
	private List<OptionQuote> quotes;

	public List<OptionQuote> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<OptionQuote> quotes) {
		this.quotes = quotes;
	}
	
}
