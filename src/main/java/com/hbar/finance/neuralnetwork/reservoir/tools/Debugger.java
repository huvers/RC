package com.hbar.finance.neuralnetwork.reservoir.tools;

import java.util.ArrayList;
import java.util.List;

public abstract class Debugger {
	static boolean enabled = false;
	static boolean activeLog = false;
	static List<String> log = new ArrayList<String>();
	
	public static void log(String str) {
		if (enabled) {
			log.add(str);
			
			if (activeLog) {
				System.out.println(str);
			}
		}
	}
	
	public static void printLog() {
		for (String str : log) {
			System.out.println(str);
		}
	}
}
