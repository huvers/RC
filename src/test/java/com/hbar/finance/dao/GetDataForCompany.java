package com.hbar.finance.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hbar.finance.model.StockQuote;
import com.hbar.finance.service.FinanceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/applicationContext.xml"})
@Transactional
@Ignore
public class GetDataForCompany {
	@Autowired 
	FinanceService financeService;
	
	@Test
	@Rollback(false)
	public void saveStockAndOptionQuotesForSymbol(){
		List<StockQuote> sqList=financeService.retrieveAndPersistsStocksInfo("JCP");
		for(StockQuote curStockQuote:sqList){
			System.out.println("Generated Stock Quote ID: "+curStockQuote.getId());
		}
		//financeService.executeStocksDataRetrieval();
	}
}
