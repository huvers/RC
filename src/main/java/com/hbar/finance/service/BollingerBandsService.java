package com.hbar.finance.service;

import java.util.List;

import com.hbar.finance.model.Company;

public interface BollingerBandsService {
	public String getEquitiesClassifiedByBandSection(List<Company> companies, String date);

}
