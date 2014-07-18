package com.hbar.finance.neuralnetwork.reservoir.committee;

import java.util.List;

import com.hbar.finance.neuralnetwork.reservoir.Reservoir;


interface CommitteeOptimization {	
	List<Reservoir> optimize(List<Reservoir> resSet, int remain);
}
