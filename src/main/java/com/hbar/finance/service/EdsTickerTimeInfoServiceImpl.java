package com.hbar.finance.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hbar.finance.dao.EdsTickerTimeInfoDao;
import com.hbar.finance.datasource.EquityDataSource;
import com.hbar.finance.model.BasicStockData;
import com.hbar.finance.model.EdsTickerTimeInfo;

public class EdsTickerTimeInfoServiceImpl implements EdsTickerTimeInfoService{
	private EdsTickerTimeInfoDao edsTickerTimeInfoDao;
	/**
	 * 
	 *@param tickerId
	 *@param List<BasicStockData>
	 *@param EquityDataSource 
	 * @return 
	 */
	@Transactional
	public EdsTickerTimeInfo getEdsTickerTimeInfo(Long tickerId, EquityDataSource equityDataSource){
		return edsTickerTimeInfoDao.findByTickerAndDataSource(tickerId, equityDataSource.getEquityDataSourceId());
	}
	
	public void setEdsTickerTimeInfoDao(EdsTickerTimeInfoDao edsTickerTimeInfoDao){
		this.edsTickerTimeInfoDao=edsTickerTimeInfoDao;
	}
}
