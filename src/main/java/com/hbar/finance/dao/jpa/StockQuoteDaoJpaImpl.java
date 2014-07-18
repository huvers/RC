package com.hbar.finance.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.hbar.finance.dao.StockQuoteDao;
import com.hbar.finance.model.StockQuote;

public class StockQuoteDaoJpaImpl extends GenericDaoJpa<StockQuote, Long> implements StockQuoteDao{
	public StockQuote getStockQuoteWithOrderedOptions(Long id){
		EntityManager em=this.getTransactionalEntityManager();
		Query q=em.createNamedQuery("stockQuoteWithOrderedOptions");
		q.setParameter("stockQuoteId", id);
		return (StockQuote)q.getSingleResult();
	}
	
}
