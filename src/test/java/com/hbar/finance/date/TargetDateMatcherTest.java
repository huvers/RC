package com.hbar.finance.date;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hbar.finance.date.DateTimeHolder;
import com.hbar.finance.date.TargetAndSignalDateHolder;
import com.hbar.finance.date.TargetAndSignalDateMatcher;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/testApplicationContext.xml"})
public class TargetDateMatcherTest {
	
	public List<DateTimeHolder> createTargetDates(List<String> targetDates){

		DateTimeZone timeZone=DateTimeZone.forID("EST5EDT");
		
		List<DateTimeHolder> jtTargetDateTimes=new ArrayList<DateTimeHolder>();
		for(int i=0;i<targetDates.size();i++){
			String strDate=targetDates.get(i);
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
			
			final DateTime jtTargetDate=new DateTime(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 6, 30, timeZone); 
			jtTargetDateTimes.add(new DateTimeHolder(){
				public DateTime getDateTime(){
					return jtTargetDate;
				}
				
			});
			
		}
		
		return jtTargetDateTimes;
		
	}
	public List<DateTimeHolder> createSignalDates(List<String> signalDates){

		DateTimeZone timeZone=DateTimeZone.forID("EST5EDT");
		List<DateTimeHolder> jtSignalDateTimes=new ArrayList<DateTimeHolder>();
		for(int i=0;i<signalDates.size();i++){
			String strDate=signalDates.get(i);
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
			
			final DateTime jtSignalDate=new DateTime(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 11, 45, timeZone); 
			jtSignalDateTimes.add(new DateTimeHolder(){
				public DateTime getDateTime(){
					return jtSignalDate;
				}
				
			});
			
		}
		
		return jtSignalDateTimes;
	}
	
	public List<DateTimeHolder> createDefaultTimeTargetDates(List<String> targetDates){
		
		List<DateTimeHolder> jtTargetDateTimes=new ArrayList<DateTimeHolder>();
		for(int i=0;i<targetDates.size();i++){
			String strDate=targetDates.get(i);
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
			
			final DateTime jtTargetDate=new DateTime(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 6, 30); 
			jtTargetDateTimes.add(new DateTimeHolder(){
				public DateTime getDateTime(){
					return jtTargetDate;
				}
				
			});
			
		}
		
		return jtTargetDateTimes;
		
	}
	public List<DateTimeHolder> createDefaultTimeSignalDates(List<String> signalDates){
		List<DateTimeHolder> jtSignalDateTimes=new ArrayList<DateTimeHolder>();
		for(int i=0;i<signalDates.size();i++){
			String strDate=signalDates.get(i);
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
			
			final DateTime jtSignalDate=new DateTime(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 6, 30); 
			jtSignalDateTimes.add(new DateTimeHolder(){
				public DateTime getDateTime(){
					return jtSignalDate;
				}
				
			});
			
		}
		
		return jtSignalDateTimes;
	}
	
	
	@Test
	public void testOneToOneDate(){
		List<String> targetDates=new ArrayList<String>();
		targetDates.add("2000-04-27");
		targetDates.add("2000-04-26");
		targetDates.add("2000-04-25");
		targetDates.add("2000-04-24");
		targetDates.add("2000-04-20");
		targetDates.add("2000-04-19");
		List<DateTimeHolder> jtTargetDates=createTargetDates(targetDates);
		
		List<String> signalDates=new ArrayList<String>();
		signalDates.add("2000-04-26");
		signalDates.add("2000-04-25");
		signalDates.add("2000-04-24");
		signalDates.add("2000-04-20");
		signalDates.add("2000-04-19");
		signalDates.add("2000-04-18");
		List<DateTimeHolder> jtSignalDates=createSignalDates(signalDates);
		
		List<TargetAndSignalDateHolder> targetAndSignalDates=TargetAndSignalDateMatcher.matchDates(jtTargetDates, jtSignalDates);
		//assert that we have a 
		for(int i=0;i<jtTargetDates.size();i++){
			assertEquals(targetAndSignalDates.get(i).getTargetDateHolder().getDateTime(),jtTargetDates.get(i).getDateTime());
		}
		for(int i=0;i<jtSignalDates.size();i++){
			if(i==jtSignalDates.size()-1){
				assertNull(targetAndSignalDates.get(i).getSignalDateHolder());
			}else{
				assertEquals(targetAndSignalDates.get(i).getSignalDateHolder().getDateTime(),jtSignalDates.get(i).getDateTime());
			}
		}
		
	}
	@Test
	public void testOneNullSignalDateAtEndOfList(){
		List<String> targetDates=new ArrayList<String>();
		targetDates.add("2000-04-27");
		targetDates.add("2000-04-26");
		targetDates.add("2000-04-25");
		targetDates.add("2000-04-24");
		targetDates.add("2000-04-20");
		targetDates.add("2000-04-19");
		List<DateTimeHolder> jtTargetDates=createTargetDates(targetDates);
		
		List<String> signalDates=new ArrayList<String>();
		signalDates.add("2000-04-26");
		signalDates.add("2000-04-25");
		signalDates.add("2000-04-24");
		signalDates.add("2000-04-20");
		signalDates.add("2000-04-19");
		signalDates.add("2000-04-18");
		List<DateTimeHolder> jtSignalDates=createSignalDates(signalDates);
		
		List<TargetAndSignalDateHolder> targetAndSignalDates=TargetAndSignalDateMatcher.matchDates(jtTargetDates, jtSignalDates);
		//assert that we have a 
		for(int i=0;i<jtTargetDates.size();i++){
			assertEquals(jtTargetDates.get(i).getDateTime(),targetAndSignalDates.get(i).getTargetDateHolder().getDateTime());
		}
		for(int i=0;i<jtSignalDates.size();i++){
			if(i==jtSignalDates.size()-1){
				assertNull(targetAndSignalDates.get(i).getSignalDateHolder());
			}else{
				assertEquals(jtSignalDates.get(i).getDateTime(),targetAndSignalDates.get(i).getSignalDateHolder().getDateTime());
			}
		}
		
	}
	

	@Test
	public void testNullSignalDateInBeginning(){
		List<String> targetDates=new ArrayList<String>();
		targetDates.add("2000-04-27");
		targetDates.add("2000-04-26");
		targetDates.add("2000-04-25");
		targetDates.add("2000-04-24");
		targetDates.add("2000-04-20");
		targetDates.add("2000-04-19");
		List<DateTimeHolder> jtTargetDates=createTargetDates(targetDates);
		
		List<String> signalDates=new ArrayList<String>();
		signalDates.add("2000-04-24");
		signalDates.add("2000-04-20");
		signalDates.add("2000-04-19");
		signalDates.add("2000-04-18");
		List<DateTimeHolder> jtSignalDates=createSignalDates(signalDates);
		
		List<TargetAndSignalDateHolder> targetAndSignalDates=TargetAndSignalDateMatcher.matchDates(jtTargetDates, jtSignalDates);
		//assert that we have a 
		for(int i=2;i<jtTargetDates.size();i++){
			assertEquals(jtTargetDates.get(i).getDateTime(),targetAndSignalDates.get(i).getTargetDateHolder().getDateTime());
		}
		assertNull(targetAndSignalDates.get(0).getSignalDateHolder());
		assertNull(targetAndSignalDates.get(1).getSignalDateHolder());
		
		for(int i=0;i<jtSignalDates.size();i++){
			if(i==jtSignalDates.size()-1){
				assertNull(targetAndSignalDates.get(i+2).getSignalDateHolder());
			}else{
				assertEquals(jtSignalDates.get(i).getDateTime(),targetAndSignalDates.get(i+2).getSignalDateHolder().getDateTime());
			}
		}
		
	}
	
	@Test
	public void testNullSignalDatesInEnd(){
		List<String> targetDates=new ArrayList<String>();
		targetDates.add("2000-04-27");
		targetDates.add("2000-04-26");
		targetDates.add("2000-04-25");
		targetDates.add("2000-04-24");
		targetDates.add("2000-04-23");
		targetDates.add("2000-04-22");
		List<DateTimeHolder> jtTargetDates=createTargetDates(targetDates);
		
		List<String> signalDates=new ArrayList<String>();
		signalDates.add("2000-04-26");
		signalDates.add("2000-04-25");
		signalDates.add("2000-04-24");
		List<DateTimeHolder> jtSignalDates=createSignalDates(signalDates);
		
		List<TargetAndSignalDateHolder> targetAndSignalDates=TargetAndSignalDateMatcher.matchDates(jtTargetDates, jtSignalDates);

		// assert that we have a
		for (int i = 0; i < jtTargetDates.size(); i++) {
			assertEquals(jtTargetDates.get(i).getDateTime(), targetAndSignalDates.get(i)
					.getTargetDateHolder().getDateTime());
			if(i>=3){
				assertNull(targetAndSignalDates.get(i).getSignalDateHolder());
				
			}else{
				assertEquals(jtSignalDates.get(i).getDateTime(), targetAndSignalDates.get(i)
						.getSignalDateHolder().getDateTime());
			}
			
		}
		
	}

	
	@Test
	public void testAllNullSignalDates(){
		List<String> targetDates=new ArrayList<String>();
		targetDates.add("2000-04-27");
		targetDates.add("2000-04-26");
		targetDates.add("2000-04-25");
		targetDates.add("2000-04-24");
		targetDates.add("2000-04-23");
		targetDates.add("2000-04-22");
		List<DateTimeHolder> jtTargetDates=createTargetDates(targetDates);
		
		List<String> signalDates=new ArrayList<String>();
		signalDates.add("2000-04-20");
		List<DateTimeHolder> jtSignalDates=createSignalDates(signalDates);
		
		List<TargetAndSignalDateHolder> targetAndSignalDates=TargetAndSignalDateMatcher.matchDates(jtTargetDates, jtSignalDates);

		// assert that we have a
		for (int i = 0; i < jtTargetDates.size(); i++) {
			assertEquals(jtTargetDates.get(i).getDateTime(), targetAndSignalDates.get(i)
					.getTargetDateHolder().getDateTime());
			assertNull(targetAndSignalDates.get(i).getSignalDateHolder());
			
		}
		
	}
	
	@Test
	public void testEmptySignalDatesList(){
		List<String> targetDates=new ArrayList<String>();
		targetDates.add("2000-04-27");
		targetDates.add("2000-04-26");
		targetDates.add("2000-04-25");
		targetDates.add("2000-04-24");
		targetDates.add("2000-04-23");
		targetDates.add("2000-04-22");
		List<DateTimeHolder> jtTargetDates=createTargetDates(targetDates);
		
		List<String> signalDates=new ArrayList<String>();
		List<DateTimeHolder> jtSignalDates=createSignalDates(signalDates);
		
		List<TargetAndSignalDateHolder> targetAndSignalDates=TargetAndSignalDateMatcher.matchDates(jtTargetDates, jtSignalDates);

		// assert that we have a
		for (int i = 0; i < jtTargetDates.size(); i++) {
			assertEquals(jtTargetDates.get(i).getDateTime(), targetAndSignalDates.get(i)
					.getTargetDateHolder().getDateTime());
			assertNull(targetAndSignalDates.get(i).getSignalDateHolder());
			
		}
	}
	
	@Test
	public void testMultipleSignalDatesForOneTargetDate(){
		List<String> targetDates=new ArrayList<String>();
		targetDates.add("2000-04-27");
		targetDates.add("2000-04-26");
		targetDates.add("2000-04-23");
		targetDates.add("2000-04-22");
		targetDates.add("2000-04-21");
		List<DateTimeHolder> jtTargetDates=createTargetDates(targetDates);
		
		List<String> signalDates=new ArrayList<String>();
		signalDates.add("2000-04-26");
		signalDates.add("2000-04-25");
		signalDates.add("2000-04-24");//no match 
		signalDates.add("2000-04-23");//no match
		signalDates.add("2000-04-22");
		signalDates.add("2000-04-21");
		
		List<DateTimeHolder> jtSignalDates=createSignalDates(signalDates);
		
		List<TargetAndSignalDateHolder> targetAndSignalDates=TargetAndSignalDateMatcher.matchDates(jtTargetDates, jtSignalDates);
		
		assertTrue(targetAndSignalDates.size()==5);
		assertEquals(jtTargetDates.get(0).getDateTime(),targetAndSignalDates.get(0).getTargetDateHolder().getDateTime());
		assertEquals(jtTargetDates.get(1).getDateTime(),targetAndSignalDates.get(1).getTargetDateHolder().getDateTime());
		assertEquals(jtTargetDates.get(2).getDateTime(),targetAndSignalDates.get(2).getTargetDateHolder().getDateTime());
		assertEquals(jtTargetDates.get(3).getDateTime(),targetAndSignalDates.get(3).getTargetDateHolder().getDateTime());
		assertEquals(jtTargetDates.get(4).getDateTime(),targetAndSignalDates.get(4).getTargetDateHolder().getDateTime());
		
		assertEquals(jtSignalDates.get(0).getDateTime(),targetAndSignalDates.get(0).getSignalDateHolder().getDateTime());
		assertEquals(jtSignalDates.get(1).getDateTime(),targetAndSignalDates.get(1).getSignalDateHolder().getDateTime());
		assertEquals(jtSignalDates.get(4).getDateTime(),targetAndSignalDates.get(2).getSignalDateHolder().getDateTime());
		assertEquals(jtSignalDates.get(5).getDateTime(),targetAndSignalDates.get(3).getSignalDateHolder().getDateTime());
		assertNull(targetAndSignalDates.get(4).getSignalDateHolder());
		
		
	
	}
	

	@Test
	public void testDefaultDateAlignment(){
		List<String> targetDates=new ArrayList<String>();
		targetDates.add("2010-01-08");
		targetDates.add("2010-01-07");
		targetDates.add("2010-01-06");
		targetDates.add("2010-01-05");
		targetDates.add("2010-01-04");
		List<DateTimeHolder> jtTargetDates=createDefaultTimeTargetDates(targetDates);
		
		List<String> signalDates=new ArrayList<String>();
		signalDates.add("2010-01-08");
		signalDates.add("2010-01-07");
		signalDates.add("2010-01-06"); 
		signalDates.add("2010-01-05");
		signalDates.add("2010-01-04");
		
		List<DateTimeHolder> jtSignalDates=createDefaultTimeSignalDates(signalDates);
		
		List<TargetAndSignalDateHolder> targetAndSignalDates=TargetAndSignalDateMatcher.matchDates(jtTargetDates, jtSignalDates);
		
		assertTrue(targetAndSignalDates.size()==5);
		assertEquals(jtTargetDates.get(0).getDateTime(),targetAndSignalDates.get(0).getTargetDateHolder().getDateTime());
		assertEquals(jtTargetDates.get(1).getDateTime(),targetAndSignalDates.get(1).getTargetDateHolder().getDateTime());
		assertEquals(jtTargetDates.get(2).getDateTime(),targetAndSignalDates.get(2).getTargetDateHolder().getDateTime());
		assertEquals(jtTargetDates.get(3).getDateTime(),targetAndSignalDates.get(3).getTargetDateHolder().getDateTime());
		assertEquals(jtTargetDates.get(4).getDateTime(),targetAndSignalDates.get(4).getTargetDateHolder().getDateTime());
		
		assertEquals(jtSignalDates.get(0).getDateTime(),targetAndSignalDates.get(0).getSignalDateHolder().getDateTime());
		assertEquals(jtSignalDates.get(1).getDateTime(),targetAndSignalDates.get(1).getSignalDateHolder().getDateTime());
		assertEquals(jtSignalDates.get(2).getDateTime(),targetAndSignalDates.get(2).getSignalDateHolder().getDateTime());
		assertEquals(jtSignalDates.get(3).getDateTime(),targetAndSignalDates.get(3).getSignalDateHolder().getDateTime());
		assertEquals(jtSignalDates.get(4).getDateTime(),targetAndSignalDates.get(4).getSignalDateHolder().getDateTime());
		
	}	
	public static void main(String[] args){
		System.out.println("test");
		
		DateTime jtSignalDate=new DateTime(2014, 3, 14, 3+12, 15, DateTimeZone.forID("Asia/Tokyo"));
		DateTime dtLocal = jtSignalDate.withZone(DateTimeZone.forID("PST8PDT"));
		
		System.out.println("japan="+jtSignalDate);
		System.out.println("dtLocal="+dtLocal);
		
		System.out.println("currentLocal="+new DateTime());
		
	}
}

