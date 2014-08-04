package com.hbar.finance.date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.TimeZone; 
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class TargetAndSignalDateMatcher {
	/**
	 * Matches a target date to signal date. A signal date (SignalDate1) matches (TargetDate1) if the following conditions are met.
	 * Given that TargetDate1 > TargetDate2, we find a SignalDate1 such that TargetDate1 >= SignalDate1 > TargetDate2. 
	 * This method assumes that the dates are in descending order.
	 *   
	 * @param jtTargetDateTimes
	 * @param jtSignalDateTimes
	 * @return List<TargetAndSignalDate>
	 */
	public static List<TargetAndSignalDateHolder> matchDates(List<? extends DateTimeHolder> jtTargetDateTimes, List<? extends DateTimeHolder> jtSignalDateTimes){
		
		List<TargetAndSignalDateHolder> targetAndSignalDates=new ArrayList<TargetAndSignalDateHolder>();
		
		int signalIndex=0;
		for(int i=0;i<jtTargetDateTimes.size();i++){
			if(i==jtTargetDateTimes.size()-1){
				DateTimeHolder targetDate=jtTargetDateTimes.get(i);			
				for (int j = signalIndex; j < jtSignalDateTimes.size(); j++) {

					DateTimeHolder signalDate = jtSignalDateTimes.get(j);

					if (targetDate.getDateTime().equals(signalDate.getDateTime())) {
						TargetAndSignalDateHolder targetAndSignalDate = new TargetAndSignalDateHolder();
						targetAndSignalDate.setSignalDate(signalDate);
						targetAndSignalDate.setTargetDate(targetDate);
						targetAndSignalDates.add(targetAndSignalDate);

						signalIndex = j + 1;
						break;

					}
				}
				//
				
			}else{
				DateTimeHolder priorTargetDate=jtTargetDateTimes.get(i + 1);
				DateTimeHolder targetDate=jtTargetDateTimes.get(i);			
				for (int j = signalIndex; j < jtSignalDateTimes.size(); j++) {

					DateTimeHolder signalDate=jtSignalDateTimes.get(j);

					if (priorTargetDate.getDateTime().isBefore(
							signalDate.getDateTime())
							&& (targetDate.getDateTime().isAfter(
									signalDate.getDateTime()) || targetDate
									.getDateTime().equals(signalDate.getDateTime())

							)) {
						TargetAndSignalDateHolder targetAndSignalDate=new TargetAndSignalDateHolder();
						targetAndSignalDate.setSignalDate(signalDate);
						targetAndSignalDate.setTargetDate(targetDate);
						targetAndSignalDates.add(targetAndSignalDate);
						
						signalIndex=j+1;
						break;
					}else if(priorTargetDate.getDateTime().isAfter(signalDate.getDateTime())){
						TargetAndSignalDateHolder targetAndSignalDate=new TargetAndSignalDateHolder();
						targetAndSignalDate.setSignalDate(null);
						targetAndSignalDate.setTargetDate(targetDate);
						targetAndSignalDates.add(targetAndSignalDate);
						
						break;
					}

				}				
			}
			

			if(signalIndex==jtSignalDateTimes.size()){
				break;
			}
			
		}
		
		for(int i=targetAndSignalDates.size();i<jtTargetDateTimes.size();i++){
			TargetAndSignalDateHolder tsDate=new TargetAndSignalDateHolder();
			tsDate.setTargetDate(jtTargetDateTimes.get(i));
			tsDate.setSignalDate(null);
			targetAndSignalDates.add(tsDate);
		}
		
		return targetAndSignalDates;
	}
	/*
	public List<DateTime> createDateListFromString(List<String> dates){

		List<DateTime> jtSignalDateTimes=new ArrayList<DateTime>();
		for(int i=0;i<dates.size();i++){
			String strDate=dates.get(i);
			String[] monthDayYear=strDate.split("-");
			String month=monthDayYear[1];
			if(month.charAt(0)=='0'){
				month=month.substring(1);
			}
			
			String day=monthDayYear[2];
			if(day.charAt(0)=='0'){
				day=day.substring(1);
			}
			
			String year=monthDayYear[0];
			
			DateTime jtDate=new DateTime(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 11, 45, timeZone); 
			jtSignalDateTimes.add(jtDate);
			
			//System.out.println("jt Signal Date: "+jtDate);
		}

		
	}
	*/
    public static void main(String[] args) {
        // Create a calendar object and set it time based on the local
        // time zone
        Calendar localTime = Calendar.getInstance();
        localTime.set(Calendar.HOUR, 3);
        localTime.set(Calendar.MINUTE, 0);
        localTime.set(Calendar.SECOND, 0);
        localTime.set(Calendar.AM_PM, 0);
        int hour = localTime.get(Calendar.HOUR);
        int minute = localTime.get(Calendar.MINUTE);
        int second = localTime.get(Calendar.SECOND);
        int localDayOfMonth = localTime.get(Calendar.DAY_OF_MONTH);
        int localMonth = localTime.get(Calendar.MONTH)+1;		
        int localYear = localTime.get(Calendar.YEAR);
        
        // Print the local time
        System.out.println("Local Time "+ (hour==0?"12":hour) +":"+(minute>9?minute:"0"+minute)+":"+ (second>9?second:"0"+second)+" "+(localTime.get(Calendar.AM_PM)==0?"AM":"PM"));
        System.out.println("Local Date "+ localMonth+"/"+localDayOfMonth+"/"+localYear);
        System.out.println("Local Date 2 "+localTime.getTime());
        
        // Create a calendar object for representing a Japan time zone. Then we
        // set the time of the calendar with the value of the local time
        String timezone="CET";
        Calendar otherTime = new GregorianCalendar(TimeZone.getTimeZone(timezone));
        otherTime.setTimeInMillis(localTime.getTimeInMillis());
        int otherHour = otherTime.get(Calendar.HOUR);
        int otherMinute = otherTime.get(Calendar.MINUTE);
        int otherSecond = otherTime.get(Calendar.SECOND);
        int otherDayOfMonth = otherTime.get(Calendar.DAY_OF_MONTH);
        int otherMonth = otherTime.get(Calendar.MONTH)+1;		
        int otherYear = otherTime.get(Calendar.YEAR);
        
        
        // Print the local time in Japan time zone
        System.out.println(timezone+" time "+ (otherHour==0?"12":otherHour) +":"+(otherMinute>9?otherMinute:"0"+otherMinute)+":"+ (otherSecond>9?otherSecond:"0"+otherSecond)+" " +(otherTime.get(Calendar.AM_PM)==0?"AM":"PM"));
        System.out.println(timezone+" Date "+ otherMonth+"/"+otherDayOfMonth+"/"+otherYear);
        System.out.println(timezone+" Date 2 "+otherTime.getTime());
/*        String[] timezones=TimeZone.getAvailableIDs();
        for(int i=0;i<timezones.length;i++){
        	System.out.println("TimeZone="+timezones[i]);
        	
        }
       
*/
		List<String> targetDates=new ArrayList<String>();
		targetDates.add("2000-04-28");
		targetDates.add("2000-04-27");
		targetDates.add("2000-04-26");
		targetDates.add("2000-04-25");
		targetDates.add("2000-04-24");
		targetDates.add("2000-04-20");
		targetDates.add("2000-04-19");
		targetDates.add("2000-04-18");
		targetDates.add("2000-04-17");
		targetDates.add("2000-04-14");
		targetDates.add("2000-04-13");
		targetDates.add("2000-04-12");
		targetDates.add("2000-04-11");
		targetDates.add("2000-04-10");
		targetDates.add("2000-04-07");
		targetDates.add("2000-04-06");
		targetDates.add("2000-04-05");
		targetDates.add("2000-04-04");
		targetDates.add("2000-04-03");


		List<String> signalDates=new ArrayList<String>();
		signalDates.add("2000-04-30");
		signalDates.add("2000-04-29");
		signalDates.add("2000-04-28");
		signalDates.add("2000-04-27");
		signalDates.add("2000-04-26");
		signalDates.add("2000-04-23");
		signalDates.add("2000-04-22");
		signalDates.add("2000-04-21");
		signalDates.add("2000-04-20");
		signalDates.add("2000-04-19");
		signalDates.add("2000-04-16");
		signalDates.add("2000-04-15");
		signalDates.add("2000-04-14");
		signalDates.add("2000-04-13");
		signalDates.add("2000-04-08");
		signalDates.add("2000-04-07");
		signalDates.add("2000-04-06");
		signalDates.add("2000-04-05");
		signalDates.add("2000-04-02");
		signalDates.add("2000-04-01");

		/*
		TargetDateMatcher matcher=new TargetDateMatcher();
		matcher.matchDates(targetDates, signalDates, null, null);*/
		/*Set<String> timeZones=DateTimeZone.getAvailableIDs();
		
		for(String curTimeZone:timeZones){
			System.out.println(curTimeZone);
		}*/
		
    }
}