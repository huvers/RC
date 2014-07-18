package com.hbar.finance.providers.tradeking;

import java.math.BigInteger;
import java.util.Date;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.basic.BigIntegerConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class TradeKingIntegerConverter implements Converter {
	BigIntegerConverter bigIntegerConverter=new BigIntegerConverter();
	public boolean canConvert(Class type) {
		// TODO Auto-generated method stub
		return BigInteger.class.isAssignableFrom(type);
	}

	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		// TODO Auto-generated method stub
        BigInteger theInteger = (BigInteger) source;
        writer.setValue(theInteger.toString());

	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return new BigInteger(reader.getValue().replaceAll(",", ""));
	}

}
