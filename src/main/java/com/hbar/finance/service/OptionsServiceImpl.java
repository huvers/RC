package com.hbar.finance.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hbar.finance.dao.OptionQuoteDao;
import com.hbar.finance.model.OptionQuote;
import com.hbar.finance.model.StockQuote;
import com.hbar.finance.providers.tradeking.TradeKingClient;

public class OptionsServiceImpl implements OptionsService{
	private OptionQuoteDao optionQuoteDao;
	private TradeKingClient tradeKingClient;

	public void setOptionQuoteDao(OptionQuoteDao optionQuoteDao) {
		this.optionQuoteDao = optionQuoteDao;
	}
	
	public void setTradeKingClient(TradeKingClient tradeKingClient){
		this.tradeKingClient = tradeKingClient;
	}

	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void retrieveAndPersistsOptionsInfo(StockQuote stockQuote) {
		
		List<OptionQuote> optionQuotes = tradeKingClient
				.getOptionQuotesForSymbol(stockQuote.getSymbol());
		//stockQuote.setOptionQuotes(optionQuotes);
		for (OptionQuote curOptionQuote : optionQuotes) {
			curOptionQuote.setStockQuote(stockQuote);
			optionQuoteDao.persist(curOptionQuote);
		}

	}


}
