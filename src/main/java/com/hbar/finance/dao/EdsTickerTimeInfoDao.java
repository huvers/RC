package com.hbar.finance.dao;

import com.hbar.finance.model.EdsTickerTimeInfo;
public interface EdsTickerTimeInfoDao extends GenericDao<EdsTickerTimeInfo,Long> {

	public EdsTickerTimeInfo findByTickerAndDataSource(Long tickerId, Integer equityDataSourceId);
	
}
