package com.hbar.finance.dao;

import java.io.Serializable;
import java.util.List;
public interface GenericDao<T, ID extends Serializable> {
	
	T findById(ID id);
	List<T> findAll();
	List<T> findAll(String orderBy);
	List<T> findAll(int offset, int limit, String orderBy);
	void persist(T entity);
	void delete(T entity);
	void flush();
	void clear();	
	
}