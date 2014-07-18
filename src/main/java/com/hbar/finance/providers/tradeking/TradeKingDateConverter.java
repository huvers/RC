package com.hbar.finance.providers.tradeking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class TradeKingDateConverter implements Converter {

    private SimpleDateFormat inputFormatter1 = new SimpleDateFormat(
            "yyyy-MM-dd'T'HHmmssZ");
    
    private SimpleDateFormat inputFormatter2 = new SimpleDateFormat(
            "yyyy-MM-dd");
    private SimpleDateFormat inputFormatter3 = new SimpleDateFormat(
            "yyyyMMdd");
    
    private SimpleDateFormat outputFormatter = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ssZ");
    
    public boolean canConvert(Class clazz) {
        // This converter is only for Calendar fields.
        return Date.class.isAssignableFrom(clazz);
    }

    public void marshal(Object value, HierarchicalStreamWriter writer,
            MarshallingContext context) {
        Date date = (Date) value;
        writer.setValue(outputFormatter.format(date));
    }

    public Object unmarshal(HierarchicalStreamReader reader,
            UnmarshallingContext context) {
    	if(reader.getValue().matches("[0-9]{10}")){
    		return new Date(Long.parseLong(reader.getValue())*1000);
    	}
        GregorianCalendar calendar = new GregorianCalendar();
        try {
            calendar.setTime(inputFormatter1.parse(reader.getValue().replaceAll(":", "")));
            return calendar.getTime();
        } catch (ParseException e) {
            //throw new ConversionException(e.getMessage(), e);
        }
        try {
            calendar.setTime(inputFormatter2.parse(reader.getValue()));
            return calendar.getTime();
        } catch (ParseException e) {
            //throw new ConversionException(e.getMessage(), e);
        }
        try {
            calendar.setTime(inputFormatter3.parse(reader.getValue()));
            return calendar.getTime();
        } catch (ParseException e) {
            //throw new ConversionException(e.getMessage(), e);
        } 
        return new Date();
    }
    public static void main(String[] args){
    	SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd'T'HHmmssZ");
    	//"yyyy-MM-dd'T'HH:mm:ssz");
    	try {
			//Date date=formatter.parse("2013-04-17T00:00:00-04:00");
			Date date=formatter.parse("2001-07-04T12:08:56-06:00".replaceAll(":", ""));
			
			System.out.println("date="+date.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}