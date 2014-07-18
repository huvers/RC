package com.hbar.finance.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.hbar.finance.dao.CompanyAndStrategyAnalysisDao;
import com.hbar.finance.model.Company;
import com.hbar.finance.model.CompanyAndStrategyAnalysis;

public class CompanyAndStrategyAnalysisDaoJpaImpl extends GenericDaoJpa<CompanyAndStrategyAnalysis, Long> implements CompanyAndStrategyAnalysisDao{
	@Transactional
	public List<Company> getCompaniesForStrategyId(Long strategyId) {
		EntityManager em=getTransactionalEntityManager();
		Query q=em.createNamedQuery("getCompaniesForStrategyId");
		q.setParameter("strategyId", strategyId);
		return q.getResultList();
	}
}
