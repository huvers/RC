package com.hbar.finance.structure;

import java.util.List;

import com.hbar.finance.date.TargetAndSignalDateHolder;
import com.hbar.finance.model.Company;

/**
 * Class that associates a target and signal company along with actual date aligned data.
 * 
 * @author Gerardo Alvarado
 *
 */
public class AssociatedTargetAndSignalCompanyDateData {
	private Company targetCompany;
	private Company signalCompany;
	private List<TargetAndSignalDateHolder> targetAndSignalDateHolders;
	public Company getTargetCompany() {
		return targetCompany;
	}
	public void setTargetCompany(Company targetCompany) {
		this.targetCompany = targetCompany;
	}
	public Company getSignalCompany() {
		return signalCompany;
	}
	public void setSignalCompany(Company signalCompany) {
		this.signalCompany = signalCompany;
	}
	public List<TargetAndSignalDateHolder> getTargetAndSignalDateHolder() {
		return targetAndSignalDateHolders;
	}
	public void setTargetAndSignalDateHolder(List<TargetAndSignalDateHolder> targetAndSignalDateHolders) {
		this.targetAndSignalDateHolders = targetAndSignalDateHolders;
	}
	
	
}
