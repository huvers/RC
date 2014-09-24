package com.hbar.finance.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import com.hbar.finance.dao.StockQuoteDao;
import com.hbar.finance.model.StockQuote;
import com.hbar.finance.providers.tradeking.TradeKingClient;

public class StocksServiceImpl implements StocksService {
	private StockQuoteDao stockQuoteDao;
	private OptionsService optionsService;
	private TradeKingClient tradeKingClient;

	
	@Override
	@Transactional
	public List<StockQuote> retrieveAndPersistsStocksInfo(String symbol) {
		List<StockQuote> stockQuotes = tradeKingClient.getStockQuotesForSymbol(symbol);
		for (StockQuote curStockQuote : stockQuotes) {
			//StockQuote sq=stockQuoteDao.getStockForSymbolAndDate(symbol, curStockQuote.getDate());
			//if(sq==null){
			stockQuoteDao.persist(curStockQuote);
			optionsService.retrieveAndPersistsOptionsInfo(curStockQuote);
			//}
		}
		return stockQuotes;
	}


	public void setStockQuoteDao(StockQuoteDao stockQuoteDao) {
		this.stockQuoteDao = stockQuoteDao;
	}


	public void setOptionsService(OptionsService optionsService) {
		this.optionsService = optionsService;
	}


	public void setTradeKingClient(TradeKingClient tradeKingClient) {
		this.tradeKingClient = tradeKingClient;
	}
	
	
}
