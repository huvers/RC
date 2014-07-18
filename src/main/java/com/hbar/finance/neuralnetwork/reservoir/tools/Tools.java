package com.hbar.finance.neuralnetwork.reservoir.tools;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import Jama.Matrix;

public abstract class Tools {

	/**
	 * Returns the maximum absolute value in an array
	 * 
	 * @param arr
	 * @return
	 */
	public static double absMax(double[] arr) {
		double d = Math.abs(arr[0]);
		for (int i = 1; i < arr.length; i++) {
			double curr = Math.abs(arr[i]);
			if (d < curr) {
				d = curr;
			}
		}
		return d;
	}

	/**
	 * Fills a two dimensional array with a random value between -1 and 1
	 * 
	 * @param arr
	 */
	public static void fillRandom(double[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				arr[i][j] = Math.random() * 2 - 1;
			}
		}
	}

	/**
	 * Fills a percentage of a two dimensional array with a random value between
	 * -1 and 1, the percentage it doesn't fill is filled with 0's
	 * 
	 * @param arr
	 * @param percent
	 */
	public static void fillPercentRandom(double[][] arr, double percent) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				arr[i][j] = (Math.random() < percent ? Math.random() * 2 - 1 : 0);
			}
		}
	}

	/**
	 * Fills a two dimensional array with a random bound Gaussian
	 * 
	 * @param arr
	 */
	public static void fillBoundGaussian(double[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				arr[i][j] = boundGaussian();
			}
		}
	}

	/**
	 * Fills a percentage of a two dimensional array with a random bound
	 * Gaussian, the percentage it doesn't fill is filled with 0's
	 * 
	 * @param arr
	 * @param percent
	 */
	public static void fillPercentBoundGaussian(double[][] arr, double percent) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				arr[i][j] = (Math.random() < percent ? boundGaussian() : 0);
			}
		}
	}

	/**
	 * Random generator
	 */
	private static Random rnd = new Random();

	/**
	 * Returns a bound Gaussian between -1 and 1
	 * 
	 * @return
	 */
	public static double boundGaussian() {
		double d = rnd.nextGaussian() / 3;
		if (d > 1) {
			return 1;
		} else if (d < -1) {
			return -1;
		} else {
			return d;
		}
	}

	/**
	 * Appends two array together
	 * 
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static double[] appendArray(double[] arr1, double[] arr2) {
		double[] arr = Arrays.copyOf(arr1, arr1.length + arr2.length);
		System.arraycopy(arr2, 0, arr, arr1.length, arr2.length);
		return arr;
	}

	/**
	 * Appends two array together
	 * 
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static <T> T[] appendArray(T[] arr1, T[] arr2) {
		T[] arr = Arrays.copyOf(arr1, arr1.length + arr2.length);
		System.arraycopy(arr2, 0, arr, arr1.length, arr2.length);
		return arr;
	}

	/**
	 * 
	 * @param lst
	 * @return
	 */
	public static double[][] toRowArr(List<double[]> lst) {
		double[][] arr = new double[lst.size()][lst.get(0).length];
		for (int i = 0; i < lst.size(); i++) {
			for (int r = 0; r < lst.size(); r++) {
				double[] curr = lst.get(r);
				for (int c = 0; c < curr.length; c++) {
					arr[r][c] = curr[c];
				}
			}
		}
		return arr;
	}

	/**
	 * Converts a List of double[] to a double[][] where each double[] becomes a
	 * Column in the new double[][]. Assumes that all double[] are the same
	 * length.
	 * 
	 * @param lst
	 *            - The List of double[] to be converted to double[][]
	 * @return A double[][] where each double[] is a Column.
	 */
	public static double[][] toColArr(List<double[]> lst) {
		double[][] arr = new double[lst.get(0).length][lst.size()];
		for (int r = 0; r < lst.size(); r++) {
			double[] curr = lst.get(r);
			for (int c = 0; c < curr.length; c++) {
				arr[c][r] = curr[c];
			}
		}
		return arr;
	}

	/**
	 * Makes a Matrix where each array in the list becomes a row. Dimensions
	 * (List Length x Array Length)
	 * 
	 * @param lst
	 * @return
	 */
	public static Matrix toRowMatrix(List<double[]> lst) {
		return new Matrix(toRowArr(lst));
	}

	/**
	 * Makes a Matrix where each array in the list becomes a column. Dimensions
	 * (Array Length x List Length)
	 * 
	 * @param lst
	 * @return
	 */
	public static Matrix toColMatrix(List<double[]> lst) {
		return new Matrix(toColArr(lst));
	}

	/**
	 * Makes array into Row Vector with dimensions 1xL
	 * 
	 * @param arr
	 * @return
	 */
	public static Matrix toRowMatrix(double[] arr) {
		return new Matrix(arr, 1);
	}

	/**
	 * Makes array into Column Vector with dimensions Lx1
	 * 
	 * @param arr
	 * @return
	 */
	public static Matrix toColMatrix(double[] arr) {
		return new Matrix(arr, arr.length);
	}

	public static double[] meanMerge(List<double[]> lst) {
		int arrLength = lst.get(0).length;
		double[] output = new double[arrLength];
		for (double[] d : lst) {
			for (int i = 0; i < arrLength; i++) {
				output[i] += d[i];
			}
		}
		for (int i = 0; i < arrLength; i++) {
			output[i] /= lst.size();
		}
		return output;
	}
}
