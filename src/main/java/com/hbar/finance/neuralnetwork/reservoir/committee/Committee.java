package com.hbar.finance.neuralnetwork.reservoir.committee;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.hbar.finance.neuralnetwork.reservoir.Reservoir;
import com.hbar.finance.neuralnetwork.reservoir.ReservoirProperties;
import com.hbar.finance.neuralnetwork.reservoir.tools.Debugger;
import com.hbar.finance.neuralnetwork.reservoir.tools.ThreadControl;
import com.hbar.finance.neuralnetwork.reservoir.tools.Tools;


/**
 * A set of Reservoirs all with the same properties working towards the same
 * task for better accuracy. Contains a multithreading component.
 * 
 * @author Andrew Fishberg
 * 
 */
public class Committee {
	// TODO make sure cp and rp have the same input/output dimension
	CommitteeProperties cp;
	ReservoirProperties rp;
	CommitteeData data;

	List<Reservoir> committee;

	// Committee name for debugging purposes
	private String name;
	private static int idNext = 0;

	/**
	 * Constructor
	 * 
	 * @param cp
	 *            - Committee Properties
	 * @param rp
	 *            - Reservoir Properties for each reservoir in the committee
	 */
	public Committee(CommitteeProperties cp, ReservoirProperties rp) {
		name = "COM-" + idNext++;
		data = new CommitteeData();

		this.cp = cp;
		this.rp = rp;

		committee = new ArrayList<Reservoir>();
		for (int i = 0; i < cp.properties.members; i++) {
			committee.add(new Reservoir(rp));
		}

		Debugger.log(name + ": Created");
	}

	/**
	 * Get method for CommitteeData
	 * 
	 * @return
	 */
	public CommitteeData getData() {
		return data;
	}

	/**
	 * Applies optimization to the committee
	 * 
	 * @param co
	 *            - Committee Optimization
	 * @param remain
	 *            - Number of members to keep
	 */
	public void applyOptimization(CommitteeOptimization co, int remain) {
		committee = co.optimize(committee, remain);
		cp.properties.members = remain;

		Debugger.log(name + ": Applied optimization " + co);
	}

	/**
	 * Trains the Committee's Reservoirs in a single thread
	 * 
	 * @param inputs
	 *            - Inputs to train Reservoirs
	 * @param expected
	 *            - Expected outputs to train Reservoirs
	 */
	public void train(List<double[]> inputs, List<double[]> expected) {
		Debugger.log(name + ": Beginning single thread training");
		
		for (int i = 0; i < committee.size(); i++) {
			committee.get(i).train(inputs, expected);
		}
		
		Debugger.log(name + ": Completed single thread training");
	}

	/**
	 * Trains the Committee's Reservoirs in multiple threads
	 * 
	 * @param inputs
	 *            - Inputs to train Reservoirs
	 * @param expected
	 *            - Expected outputs to train Reservoirs
	 */
	public void trainMT(List<double[]> inputs, List<double[]> expected) {
		Debugger.log(name + ": Beginning multithread training");
		
		ConcurrentLinkedQueue<ReservoirTrainTask> queue = new ConcurrentLinkedQueue<ReservoirTrainTask>();

		for (int i = 0; i < committee.size(); i++) {
			queue.add(new ReservoirTrainTask(committee.get(i), inputs, expected)); // multithreading
		}

		while (!queue.isEmpty() || ThreadControl.activeTasks > 0) {
			Debugger.log("There are " + ThreadControl.activeTasks + " acitve threads out of " + cp.properties.maxThreads);
			
			if (!queue.isEmpty() && ThreadControl.activeTasks < cp.properties.maxThreads) {
				ThreadControl.activeTasks++;
				Debugger.log(name + ": Beginning train thread for " + queue.peek().res);
				(new Thread(queue.remove())).start();
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
		
		Debugger.log(name + ": Completed multithread training");
	}

	/**
	 * Executes an input on the Committee's Reservoirs in a single thread
	 * 
	 * @param inputs
	 *            - Inputs to execute on the Reservoirs
	 */
	public double[] exe(double[] inputs) {
		Debugger.log(name + ": Beginning single thread execution");
		
		double[] output = new double[cp.dimensions.output]; // for returning
		List<double[]> outputs = new ArrayList<double[]>(); // for storing

		for (int i = 0; i < committee.size(); i++) {
			outputs.add(committee.get(i).exe(inputs));
		}

		output = Tools.meanMerge(outputs);
		data.outputs.add(output);
		
		Debugger.log(name + ": Completed single thread execution");
		
		return output;
	}

	/**
	 * Executes an input on the Committee's Reservoirs in multiple threads
	 * 
	 * @param inputs
	 *            - Inputs to execute on the Reservoirs
	 */
	public double[] exeMT(double[] input) {
		
		Debugger.log(name + ": Beginning multithread execution");
		
		double[] output = new double[cp.dimensions.output]; // for returning
		List<double[]> outputs = new ArrayList<double[]>(); // for storing

		ConcurrentLinkedQueue<ReservoirExeTask> queue = new ConcurrentLinkedQueue<ReservoirExeTask>();

		for (int i = 0; i < committee.size(); i++) {
			queue.add(new ReservoirExeTask(committee.get(i), input)); // multithreading
		}

		while (!queue.isEmpty() || ThreadControl.activeTasks > 0) {
			Debugger.log("There are " + ThreadControl.activeTasks + " acitve threads out of " + cp.properties.maxThreads);
			
			if (!queue.isEmpty() && ThreadControl.activeTasks < cp.properties.maxThreads) {
				ThreadControl.activeTasks++;
				Debugger.log(name + ": Beginning exe thread for " + queue.peek().res);
				(new Thread(queue.remove())).start();
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}

		for (Reservoir res : committee) {
			outputs.add(res.getData().getLastOutput());
		}

		output = Tools.meanMerge(outputs);
		data.outputs.add(output);
		
		Debugger.log(name + ": Completed multithread execution");
		
		return output;
	}

	/**
	 * Returns the Committee name
	 */
	public String toString() {
		return name;
	}

	/**
	 * A container for multithreading the train task
	 * 
	 * @author Andrew Fishberg
	 * 
	 */
	public class ReservoirTrainTask implements Runnable {
		Reservoir res;
		List<double[]> inputs;
		List<double[]> expected;

		ReservoirTrainTask(Reservoir res, List<double[]> inputs, List<double[]> expected) {
			this.res = res;
			this.inputs = inputs;
			this.expected = expected;
		}

		public void run() {
			// activeTask and Beginning Thread message moved to outside
			// in case Thread start is delayed
			res.train(inputs, expected);
			ThreadControl.activeTasks--;
			
			Debugger.log(name + ": Ending train thread for " + res);
		}
	}

	/**
	 * A container for multithreading the execute task
	 * 
	 * @author Andrew Fishberg
	 * 
	 */
	public class ReservoirExeTask implements Runnable {
		Reservoir res;
		double[] input;

		ReservoirExeTask(Reservoir res, double[] input) {
			this.res = res;
			this.input = input;
		}

		public void run() {
			// activeTask and Beginning Thread message move to outside
			// in case Thread start is delayed
			res.exe(input);
			ThreadControl.activeTasks--;
			
			Debugger.log(name + ": Ending exe thread for " + res);
		}
	}
}
