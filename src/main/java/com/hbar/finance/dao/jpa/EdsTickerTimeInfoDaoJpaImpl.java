package com.hbar.finance.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.hbar.finance.dao.EdsTickerTimeInfoDao;
import com.hbar.finance.model.EdsTickerTimeInfo;

public class EdsTickerTimeInfoDaoJpaImpl extends GenericDaoJpa<EdsTickerTimeInfo, Long> implements EdsTickerTimeInfoDao {
	public EdsTickerTimeInfo findByTickerAndDataSource(Long tickerId, Integer equityDataSourceId){
		EntityManager em=this.getTransactionalEntityManager();
		Query q=em.createNamedQuery("getEdsTickerTimeInfo");
		q.setParameter("tickerId", tickerId);
		q.setParameter("equityDataSourceId", equityDataSourceId);
		try{
			return (EdsTickerTimeInfo) q.getSingleResult();
		}catch(NoResultException exc){}
		
		return null;
	}
}
