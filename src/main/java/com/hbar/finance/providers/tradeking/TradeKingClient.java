package com.hbar.finance.providers.tradeking;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.scribe.builder.*;
import org.scribe.model.*;
import org.scribe.oauth.*;
import org.xml.sax.Attributes;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.SAXException;

import com.hbar.finance.model.OptionQuote;
import com.hbar.finance.model.StockQuote;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class TradeKingClient
{
	private String consumerKey;
	private String consumerSecret;
	private String oauthToken;
	private String oauthTokenSecret;
	private static final Logger logger=Logger.getLogger(TradeKingClient.class);
	//private static final String PROTECTED_RESOURCE_URL = "https://api.tradeking.com/v1/member/profile.xml";
	private static final String PROTECTED_RESOURCE_URL = "https://api.tradeking.com/v1/market/options/search.xml?symbol=HPQ&query=xyear-gte:2013";
	
	public void setConsumerKey(String consumerKey){
		this.consumerKey=consumerKey;
	}
	
	public void setConsumerSecret(String consumerSecret){
		this.consumerSecret=consumerSecret;
	}

	public void setOauthToken(String oauthToken){
		this.oauthToken=oauthToken;
	}

	public void setOauthTokenSecret(String oauthTokenSecret){
		this.oauthTokenSecret=oauthTokenSecret;
	}
	
	private static void stocksTest(){

		List<StockQuote> quotes=new TradeKingClient().getStockQuotesForSymbol("AIG");	
		if (quotes != null) {
			for (int i = 0; i < quotes.size(); i++) {
				System.out.println("q" + i + ".week52lo="
						+ quotes.get(i).getWk52Lo());
				System.out.println("q" + i + ".week52lodate="
						+ quotes.get(i).getWk52LoDate());
				System.out.println("q" + i + ".yield="
						+ quotes.get(i).getYield());
			}

		}		

	}
	public List<StockQuote> getStockQuotesForSymbol(String symbol){
		OAuthService service = new ServiceBuilder()
				.provider(TradeKingApi.class).apiKey(consumerKey)
				.apiSecret(consumerSecret).build();
		Token accessToken = new Token(oauthToken, oauthTokenSecret);
		String OPTIONS_URL = "https://api.tradeking.com/v1/market/ext/quotes.xml?symbols="+symbol;
		// Now let's go and ask for a protected resource!
		OAuthRequest request = new OAuthRequest(Verb.GET, OPTIONS_URL);
		service.signRequest(accessToken, request);
		try{
			Response response = request.send();
			String strResponse = response.getBody();
			logger.info(strResponse);
			System.out.println(strResponse);

			XStream xStream = new XStream(new DomDriver());
			xStream.alias("quote", StockQuote.class);
			xStream.alias("response", StocksResponse.class);
			xStream.registerConverter(new TradeKingDateConverter());
			xStream.registerConverter(new TradeKingIntegerConverter());

			StocksResponse or1 = (StocksResponse) xStream.fromXML(strResponse);
			return or1.getQuotes();
		}catch(Exception exception){
			System.out.println("Error for OPTIONS_URL="+OPTIONS_URL);
			exception.printStackTrace();
		}
		return new ArrayList<StockQuote>();
	}

	public List<OptionQuote> getOptionQuotesForSymbol(String symbol) {
		OAuthService service = new ServiceBuilder()
				.provider(TradeKingApi.class).apiKey(consumerKey)
				.apiSecret(consumerSecret).build();
		Token accessToken = new Token(oauthToken, oauthTokenSecret);
		String OPTIONS_URL = "https://api.tradeking.com/v1/market/options/search.xml?symbol="+symbol+"&query=xyear-gte:2013";
		// Now let's go and ask for a protected resource!
		OAuthRequest request = new OAuthRequest(Verb.GET, OPTIONS_URL);
		service.signRequest(accessToken, request);
		try{
			Response response = request.send();
			String optionsResponse = response.getBody();
			logger.info(optionsResponse);
			System.out.println(optionsResponse);
			XStream xStream = new XStream(new DomDriver());
			xStream.alias("quote", OptionQuote.class);
			xStream.alias("response", OptionsResponse.class);
			xStream.registerConverter(new TradeKingDateConverter());
			xStream.registerConverter(new TradeKingIntegerConverter());
			OptionsResponse or1 = (OptionsResponse) xStream
					.fromXML(optionsResponse);
			return or1.getQuotes();
		}catch(Exception exception){
			System.out.println("Error for OPTIONS_URL="+OPTIONS_URL);
			exception.printStackTrace();
		}
		return new ArrayList<OptionQuote>();
		
	}

	public InputStream getStreamingQuotesForSymbols(List<String> urls) {

		OAuthService service = new ServiceBuilder()
		.provider(TradeKingApi.class).apiKey(consumerKey)
		.apiSecret(consumerSecret).build();
		Token accessToken = new Token(oauthToken, oauthTokenSecret);

		
		StringBuffer sbSymbols=new StringBuffer();
		for(int i=0;i<urls.size();i++){
			if(i==urls.size()-1){
				sbSymbols.append(urls.get(i));
			}else{
				sbSymbols.append(urls.get(i)+",");
			}
		}
		System.out.println("symbols="+sbSymbols.toString());
		String STREAM_URL = "https://stream.tradeking.com/v1/market/quotes.xml?symbols="
				+ sbSymbols.toString();
		
		// Now let's go and ask for a protected resource!
		OAuthRequest request = new OAuthRequest(Verb.GET, STREAM_URL);
		service.signRequest(accessToken, request);
		try {
			Response response = request.send();
			return response.getStream();
			/*logger.info(optionsResponse);
			System.out.println(optionsResponse);
			XStream xStream = new XStream(new DomDriver());
			xStream.alias("quote", OptionQuote.class);
			xStream.alias("response", OptionsResponse.class);
			xStream.registerConverter(new TradeKingDateConverter());
			xStream.registerConverter(new TradeKingIntegerConverter());
			xStream.createObjectInputStream(response.getStream());
			OptionsResponse or1 = (OptionsResponse) xStream
					.fromXML(optionsResponse);
			return or1.getQuotes();*/
		} catch (Exception exception) {
			System.out.println("Error for STREAM_URL=" + STREAM_URL);
			exception.printStackTrace();
		}
		return null;

	}
	
	
	private static void optionsTest() {
		List<OptionQuote> quotes=new TradeKingClient().getOptionQuotesForSymbol("AIG");	
	
		/*if (quotes != null) {
			
			for (int i = 0; i < quotes.size(); i++) {
				System.out.println("q" + i + ".xday="
						+ quotes.get(i).getXday());
				System.out.println("q" + i + ".xmonth="
						+ quotes.get(i).getXmonth());
				System.out.println("q" + i + ".xyear="
						+ quotes.get(i).getXyear());
			}

				
		}*/		
	}
	
	public void getWatchList() {
		OAuthService service = new ServiceBuilder()
				.provider(TradeKingApi.class).apiKey(consumerKey)
				.apiSecret(consumerSecret).build();
		Token accessToken = new Token(oauthToken, oauthTokenSecret);
		String OPTIONS_URL = "https://api.tradeking.com/v1/watchlists/DEFAULT.xml";
		// Now let's go and ask for a protected resource!
		OAuthRequest request = new OAuthRequest(Verb.GET, OPTIONS_URL);
		service.signRequest(accessToken, request);
		Response response = request.send();
		String strResponse = response.getBody();
		System.out.println(strResponse);
		
	}
	public static void main(String[] args)
	{
		File file = new File("E:/TRADE_KING_STREAM2.xml");
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();
			//xmlReader.setFeature("http://xml.org/sax/features/validation", false);
			xmlReader.setFeature("http://apache.org/xml/features/continue-after-fatal-error", true);
			StreamingQuotesHandler sqHandler=new StreamingQuotesHandler();
			
			xmlReader.setContentHandler(sqHandler);
			xmlReader.setErrorHandler(sqHandler);
			xmlReader.parse(new InputSource(fis));
			
			
			
		/*	XStream xStream = new XStream(new DomDriver());
			xStream.alias("status", String.class);
			xStream.alias("quote", OptionQuote.class);
			xStream.alias("response", OptionsResponse.class);
			xStream.registerConverter(new TradeKingDateConverter());
			xStream.registerConverter(new TradeKingIntegerConverter());
			ObjectInputStream ois=xStream.createObjectInputStream(fis);
			Object object=null;
			while((object=ois.readObject())!=null){
				System.out.println("class="+object.getClass());
				
			}*/
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
/*		TradeKingClient client=new TradeKingClient();
		List<String> urls=new ArrayList<String>();
		urls.add("KING");
		urls.add("COH");
		
		InputStream is=client.getStreamingQuotesForSymbols(urls);
		
		File file = new File("C:/tomcat-6.0.36/logs/TRADE_KING_STREAM.log");
		
		int n;
		
		FileWriter fw;
		BufferedWriter bw=null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);	
			
			while((n=is.read())!=-1){
				char ch=(char)n;
				bw.write(ch);
				bw.flush();
				System.out.print(ch);
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				if(bw!=null){
					bw.close();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	*/	
	
	}
	
}


class StreamingQuotesHandler extends DefaultHandler implements LexicalHandler{
	String currentEntity=null;
	public void startDocument(){
		System.out.println("startDocument");
		try {
			super.startDocument();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void fatalError(SAXParseException e){
		System.out.println("fatal error");
		try {
			super.fatalError(e);
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void endDocument() {
		System.out.println("endDocument");
		try {
			super.endDocument();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void characters(char[] ch, int start, int length){
		String content=new String (ch, start, length);
		System.out.println(content);
		if(currentEntity!=null){
		}
	}
	public void startElement(String uri, String name, String qName, Attributes attrs){
		System.out.println("startElement");
		try {
			super.startElement(uri, name, qName, attrs);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void endElement(String uri, String name, String qName){
		System.out.println("endElement");
		try {
			super.endElement(uri, name, qName);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void comment(char[] arg0, int arg1, int arg2) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endCDATA() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endDTD() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endEntity(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startCDATA() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startDTD(String arg0, String arg1, String arg2)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startEntity(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}
}