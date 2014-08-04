package com.hbar.finance.date;
/**
 * Convenience class for associating a target date with a signal date. This is typically stored in
 * a list of such relationships.
 * 
 * 
 * @author Gerardo Alvarado
 *
 */

public class TargetAndSignalDateHolder {
	private DateTimeHolder targetDateHolder;
	private DateTimeHolder signalDateHolder;
	
	public DateTimeHolder getTargetDateHolder() {
		return targetDateHolder;
	}
	public void setTargetDate(DateTimeHolder targetDateHolder) {
		this.targetDateHolder = targetDateHolder;
	}
	public DateTimeHolder getSignalDateHolder() {
		return signalDateHolder;
	}
	public void setSignalDate(DateTimeHolder signalDateHolder) {
		this.signalDateHolder = signalDateHolder;
	}
	
}
