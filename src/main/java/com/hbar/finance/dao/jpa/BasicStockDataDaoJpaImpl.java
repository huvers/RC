package com.hbar.finance.dao.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.joda.time.DateTime;

import com.hbar.finance.dao.BasicStockDataDao;
import com.hbar.finance.model.BasicStockData;
import com.hbar.finance.model.Company;
public class BasicStockDataDaoJpaImpl extends GenericDaoJpa<BasicStockData, Long> implements BasicStockDataDao{
	@Deprecated
	public List<BasicStockData> getLatestBasicStockDataForCompany(Long id, Integer limit){
		EntityManager em=this.getTransactionalEntityManager();
		Query q=em.createNamedQuery("getLatestBasicStockDateForCompany");
		q.setParameter("companyId", id);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	public List<BasicStockData> getBasicStockDataForCompanyBeforeDate(Long id,
			DateTime date, Integer limit, boolean isAscending, Integer equityDataSourceId) {
		EntityManager em=this.getTransactionalEntityManager();
		Query q=null;
		if(isAscending){
			q=em.createNamedQuery("getBasicStockDataForCompanyBeforeDateAscending");
		}else{
			q=em.createNamedQuery("getBasicStockDataForCompanyBeforeDate");	
		}
		
		
		q.setParameter("companyId", id);
		q.setParameter("date", date);
		q.setParameter("equityDataSourceId", equityDataSourceId);
		q.setParameter("versionIndex", getMaxVersionIndexForCompany(id, equityDataSourceId));
		q.setMaxResults(limit);
		return q.getResultList();
	}

	public List<BasicStockData> getBasicStockDataForCompanyBetweenDates(
			Long id, DateTime startDate, DateTime endDate, boolean isAscending, Integer equityDataSourceId) {
		EntityManager em=this.getTransactionalEntityManager();
		Query q=null;
		if(isAscending){
			q=em.createNamedQuery("getBasicStockDataForCompanyBetweenDatesAscending");
		}else{
			q=em.createNamedQuery("getBasicStockDataForCompanyBetweenDates");	
		}
		
		
		q.setParameter("companyId", id);
		q.setParameter("startDate", startDate);
		q.setParameter("endDate", endDate);
		q.setParameter("equityDataSourceId", equityDataSourceId);
		q.setParameter("versionIndex", getMaxVersionIndexForCompany(id, equityDataSourceId));
		return (List<BasicStockData>)q.getResultList();	
	}

	public Integer getMaxVersionIndexForCompany(Company company, Integer equityDataSourceId) {
		return this.getMaxVersionIndexForCompany(company.getId(), equityDataSourceId);
	}

	private Integer getMaxVersionIndexForCompany(Long id, Integer equityDataSourceId) {
		EntityManager em=this.getTransactionalEntityManager();
		Query q=em.createNamedQuery("getMaxVersionIndexForCompanyId");
		q.setParameter("companyId", id);
		q.setParameter("equityDataSourceId", equityDataSourceId);
		Integer maxVersionIndex=(Integer)q.getSingleResult();
		if(maxVersionIndex==null){
			return 0;
		}else{
			return maxVersionIndex;
		}
		
	}
}
