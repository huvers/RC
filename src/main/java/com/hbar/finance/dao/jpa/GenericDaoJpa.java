/**
 * 
 */
package com.hbar.finance.dao.jpa;

import java.io.Serializable;
import java.util.List;
import java.lang.reflect.ParameterizedType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.hibernate.ejb.QueryImpl;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.transaction.annotation.Transactional;

import com.hbar.finance.dao.GenericDao;

/**
 * @author Admin
 *
 */
public class GenericDaoJpa<T, ID extends Serializable> implements GenericDao<T, ID>{
	private EntityManagerFactory entityManagerFactory;
	private Class<T> type;
	private boolean queryCacheEnabled;
	
	public GenericDaoJpa(){
		Class<T> theType=(Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		type=theType;
	}
	
	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public Class<T> getType() {
		return type;
	}

	public void setType(Class<T> type) {
		this.type = type;
	}

	public boolean isQueryCacheEnabled() {
		return queryCacheEnabled;
	}

	public void setQueryCacheEnabled(boolean queryCacheEnabled) {
		this.queryCacheEnabled = queryCacheEnabled;
	}
	@Transactional
	public T findById(ID id) {
		return this.getTransactionalEntityManager().find(type, id);
		
	}
	@Transactional
	public List<T> findAll() {
		return findAll(0,0,null);
	}
	@Transactional
	public List<T> findAll(String orderBy){
		return findAll(0,0,orderBy);
	}
	@Transactional
	public List<T> findAll(int offset, int limit, String orderBy){
		StringBuilder sb=new StringBuilder();
		sb.append("select e from ").append(type.getSimpleName()).append(" e");
		if(orderBy!=null){
			sb.append(" order by ").append(orderBy);
		}
		
		Query q=getTransactionalEntityManager().createQuery(sb.toString());
		
		((QueryImpl<?>)q).getHibernateQuery().setCacheable(queryCacheEnabled);
		if(offset>0){
			q.setFirstResult(offset);
		}
		if(limit>0){
			q.setMaxResults(limit);
		}
		List<T> results=q.getResultList();
		return results;
	}
	@Transactional
	public void persist(T entity) {
		getTransactionalEntityManager().persist(entity);
	}
	@Transactional
	public void delete(T entity) {
		getTransactionalEntityManager().remove(entity);
	}

	public void flush() {
		getTransactionalEntityManager().flush();
	}

	public void clear() {
		getTransactionalEntityManager().clear();
	}
	protected EntityManager getTransactionalEntityManager(){
		EntityManagerFactory emf=getEntityManagerFactory();
		EntityManager em=EntityManagerFactoryUtils.getTransactionalEntityManager(emf, null);
		
		return em;
	}
}
