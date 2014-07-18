package com.hbar.finance.date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtils {
	
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss Z");

	/**
	 * Returns basic standard date string given a DateTime. Note that time info is lost.
	 * @param dateTime
	 * @return String
	 */
	public static String createBasicStandardDateString(DateTime dateTime){
		StringBuffer sbDate=new StringBuffer();
		sbDate.append(dateTime.getYear());
		sbDate.append("-");
		sbDate.append(dateTime.getMonthOfYear()<10?"0"+dateTime.getMonthOfYear():dateTime.getMonthOfYear());
		sbDate.append("-");
		sbDate.append(dateTime.getDayOfMonth()<10?"0"+dateTime.getDayOfMonth():dateTime.getDayOfMonth());
		
		return sbDate.toString();
	}
	

	/**
	 * Returns basic standard date string given a DateTime. Note that time info is lost.
	 * @param dateTime
	 * @return String
	 */
	public static DateTime createDateTimeFromString(String dateTime){
		return dateTimeFormatter.parseDateTime(dateTime);
	}
	
}
