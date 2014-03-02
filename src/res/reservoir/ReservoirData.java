package res.reservoir;

import java.util.ArrayList;
import java.util.List;

public class ReservoirData {
	List<double[]> inputs = new ArrayList<double[]>();
	List<double[]> states = new ArrayList<double[]>();
	List<double[]> outputs = new ArrayList<double[]>();
	
	public double[] getLastInput() {
		return inputs.get(inputs.size() - 1);
	}
	
	public double[] getLastState() {
		return states.get(states.size() - 1);
	}
	
	public double[] getLastOutput() {
		return outputs.get(outputs.size() - 1);
	}
	
	public boolean isEmpty() {
		return inputs.isEmpty() || states.isEmpty() || outputs.isEmpty();
	}
}
