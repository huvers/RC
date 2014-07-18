package com.hbar.finance.service;

import java.util.List;

import com.hbar.finance.datasource.EquityDataSource;
import com.hbar.finance.model.BasicStockData;
import com.hbar.finance.model.EdsTickerTimeInfo;

public interface EdsTickerTimeInfoService {
	EdsTickerTimeInfo getEdsTickerTimeInfo(Long tickerId, EquityDataSource equityDataSource);
}
