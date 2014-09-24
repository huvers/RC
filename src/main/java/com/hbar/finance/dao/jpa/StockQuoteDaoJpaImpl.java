package com.hbar.finance.dao.jpa;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.hbar.finance.dao.StockQuoteDao;
import com.hbar.finance.model.StockQuote;

public class StockQuoteDaoJpaImpl extends GenericDaoJpa<StockQuote, Long> implements StockQuoteDao{
	@Override
	public StockQuote getStockQuoteWithOrderedOptions(Long id){
		EntityManager em=this.getTransactionalEntityManager();
		Query q=em.createNamedQuery("stockQuoteWithOrderedOptions");
		q.setParameter("stockQuoteId", id);
		return (StockQuote)q.getSingleResult();
	}
	@Override
	public StockQuote getStockForSymbolAndDate(String symbol, Date date){
		EntityManager em=this.getTransactionalEntityManager();
		Query q=em.createNamedQuery("stockQuoteForSymbolAndDate");
		q.setParameter("symbol", symbol);
		q.setParameter("date", date);
		try{
			return (StockQuote)q.getSingleResult();	
		}catch(NoResultException exc){
			return null;
		}
		
	}
	
}
