package com.hbar.finance.neuralnetwork.reservoir.committee;

import java.util.ArrayList;
import java.util.List;

public class CommitteeData {
	List<double[]> inputs = new ArrayList<double[]>();
	List<double[]> outputs = new ArrayList<double[]>();
	
	public double[] getLastInput() {
		return inputs.get(inputs.size() - 1);
	}
	
	public double[] getLastOutput() {
		return outputs.get(outputs.size() - 1);
	}
	
	public boolean isEmpty() {
		return inputs.isEmpty() || outputs.isEmpty();
	}
}
