package com.hbar.finance.strategy;

public enum StrategyEnum {
	
	BOLLINGER_CLASSIFIER(1l);
	
	Long strategyId;
	
	StrategyEnum(Long strategyId){
		this.strategyId=strategyId;
	}
	
	public Long getStrategyId(){
		return strategyId;
	}
		
}
