package com.hbar.finance.neuralnetwork.reservoir;

import java.util.ArrayList;
import java.util.List;

import com.hbar.finance.neuralnetwork.reservoir.tools.Debugger;
import com.hbar.finance.neuralnetwork.reservoir.tools.Tools;
import Jama.Matrix;

public class Reservoir {
	/**
	 * Wres Dimensions: # Nodes x # Nodes
	 * 
	 * Win Dimensions: # Nodes x # Inputs
	 * 
	 * Wout Dimensions: # Outputs x # Nodes (without using Inputs) Dimensions: #
	 * Outputs x (# Nodes + # Inputs) (with using Inputs)
	 * 
	 * Wback Dimensions: # Nodes x # Outputs
	 */
	private Matrix wres;
	private Matrix win;
	private Matrix wout;
	private Matrix wback;

	private ReservoirProperties rp;
	private ReservoirData data;

	// Reservoir name for debugging purposes
	private String name;
	private static int idNext = 0;

	// supports 2 dimensional input --- add dimensionality if want 3d input

	/**
	 * Constructor
	 * 
	 * @param rp
	 *            - ResevoirProperites for this Reservoir
	 */
	public Reservoir(ReservoirProperties rp) {
		name = "RES-" + idNext++;
		this.rp = rp;
		data = new ReservoirData();

		wres = wres();
		win = win();
		wout = wout();
		wback = wback();
	}

	/**
	 * Internally generates the Wres matrix
	 * 
	 * @return
	 */
	private Matrix wres() {
		double[][] arr = new double[rp.properties.nodes][rp.properties.nodes];
		Tools.fillPercentRandom(arr, rp.properties.connectivity);
		Matrix m = new Matrix(arr);
		double sradiusScaler = rp.properties.spectralRadius / Tools.absMax(m.eig().getRealEigenvalues());
		m.timesEquals(sradiusScaler);
		return m;
	}

	/**
	 * Internally generates the Win matrix
	 * 
	 * @return
	 */
	private Matrix win() {
		double[][] arr = new double[rp.properties.nodes][rp.dimensions.input];
		Tools.fillRandom(arr);
		return new Matrix(arr);
	}

	/**
	 * Internally generates the Wout matrix. If the reservoir's data is
	 * currently empty, generates the default Wout. Otherwise it calculates the
	 * Wout matrix.
	 * 
	 * @return
	 */
	private Matrix wout() {
		return (data.isEmpty() ? woutDefault() : woutCalc());
	}

	/**
	 * Calculates the Wout matrix. TODO Comment this method
	 * 
	 * @return
	 */
	private Matrix woutCalc() {
		// A list of all the states at all times
		List<double[]> trainingStates = new ArrayList<double[]>();
		for (int i = 0; i < data.inputs.size(); i++) {
			// Includes inputs as nodes if useInputStates is on
			double[] curr = (rp.using.inputStates ? Tools.appendArray(data.states.get(i), data.inputs.get(i)) : data.states.get(i));
			trainingStates.add(curr);
		}

		Matrix mTrainingStates = Tools.toRowMatrix(trainingStates);
		Matrix mOutputs = Tools.toRowMatrix(data.outputs);

		Matrix s = mTrainingStates;
		Matrix d = mOutputs;

		Matrix t;
		t = s.transpose().times(s);
		t = t.inverse();
		t = t.times(s.transpose());

		return t.times(d).transpose();
	}

	/**
	 * Generates the default Wout
	 * 
	 * @return
	 */
	private Matrix woutDefault() {
		return (rp.using.inputStates ? new Matrix(rp.dimensions.output, rp.properties.nodes + rp.dimensions.input) : new Matrix(rp.dimensions.output, rp.properties.nodes));
	}

	/**
	 * Internally generates the Wback matrix
	 * 
	 * @return
	 */
	private Matrix wback() {
		double[][] arr = new double[rp.properties.nodes][rp.dimensions.output];
		Tools.fillRandom(arr);
		return new Matrix(arr);
	}

	/**
	 * Executes the current input
	 * 
	 * @param input
	 *            - Input for execution
	 * @return - WARNING: Is not a clone of the last output
	 */
	public double[] exe(double[] input) {
		Debugger.log(name + ": Beginning exe");
		
		data.inputs.add(input);
		data.states.add(calcState());
		data.outputs.add(calcOutput());
		
		Debugger.log(name + ": Ending exe");
		
		return data.getLastOutput();
	}

	/**
	 * Internally calculates the substates
	 * 
	 * @return
	 */
	private double[] calcSubstate() {
		double[] input = data.getLastInput();
		Matrix mInput = new Matrix(input, input.length); // I x 1

		Matrix substate = new Matrix(rp.properties.nodes, 1); // N x 1

		Matrix winTerm = win.times(mInput); // N x 1
		substate.plusEquals(winTerm);

		if (rp.using.previousState && data.states.size() > 0) {
			Matrix wresTerm = wres.times(Tools.toColMatrix(data.getLastState()));
			substate.plusEquals(wresTerm);
		}

		if (rp.using.backPropagation && data.outputs.size() > 0) {
			Matrix wbackTerm = wback.times(Tools.toColMatrix(data.getLastOutput()));
			substate.plusEquals(wbackTerm);
		}

		return substate.getColumnPackedCopy();
	}

	/**
	 * Internally calculates the current states
	 * 
	 * @return
	 */
	private double[] calcState() {
		double[] substate = calcSubstate();
		double[] state = new double[substate.length];
		for (int i = 0; i < substate.length; i++) {
			state[i] = fres(substate[i]);
		}

		// leak decay
		if (rp.properties.leakDecay != 1 && data.states.size() > 0) {
			double[] lastState = data.getLastState();
			for (int i = 0; i < state.length; i++) {
				state[i] = lastState[i] * (1 - rp.properties.leakDecay) + state[i] * rp.properties.leakDecay;
			}
		}

		return state;
	}

	/**
	 * Internally calculates the outputs
	 * 
	 * @return
	 */
	private double[] calcOutput() {
		double[] stateInput = (rp.using.inputStates ? Tools.appendArray(data.getLastState(), data.getLastInput()) : data.getLastState());
		Matrix mStateInput = Tools.toColMatrix(stateInput);
		Matrix mOutput = wout.times(mStateInput);
		double[] output = mOutput.getColumnPackedCopy();
		for (int i = 0; i < output.length; i++) {
			output[i] = fout(output[i]);
		}
		return output;
	}

	private double fres(double d) {
		return Math.tanh(d);
	}

	private double fout(double d) {
		return d;
	}

	// TODO Check Sizes - expects same list sizes and arr lengths
	/**
	 * Trains the Reservoir with the given inputs and expected outputs. Clears
	 * the current ResevoirData
	 * 
	 * @param inputLst
	 *            - Training inputs
	 * @param expectedLst
	 *            - Training expected outputs
	 */
	public void train(List<double[]> inputLst, List<double[]> expectedLst) {
		Debugger.log(name + ": Beginning training");
		
		data = new ReservoirData();

		int listSize = inputLst.size();

		for (int i = 0; i < listSize; i++) {
			data.inputs.add(inputLst.get(i));
			data.states.add(calcState());
			data.outputs.add(expectedLst.get(i)); // does this so back
													// propagation uses the
													// correct output
		}

		wout = wout();
		data = new ReservoirData();
		
		Debugger.log(name + ": Ending training");
	}

	/**
	 * Returns the ReservoirData
	 * 
	 * @return - WARNING: Is not a clone of the ReservoirData
	 */
	public ReservoirData getData() {
		return data;
	}

	/**
	 * Returns the Reservoir name
	 */
	public String toString() {
		return name;
	}
}
