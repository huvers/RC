package com.hbar.finance.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.hbar.finance.dao.CompanyDao;
import com.hbar.finance.model.Company;

public class CompanyDaoJpaImpl extends GenericDaoJpa<Company, Long> implements CompanyDao{

	public Company findBySymbol(String symbol) {
		EntityManager em=this.getTransactionalEntityManager();
		Query q=em.createNamedQuery("getCompanyFromSymbol");
		q.setParameter("symbol", symbol);
		return (Company) q.getSingleResult();
	}

}
