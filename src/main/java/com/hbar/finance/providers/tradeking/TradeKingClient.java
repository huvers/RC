package com.hbar.finance.providers.tradeking;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.scribe.builder.*;
import org.scribe.model.*;
import org.scribe.oauth.*;

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
		//new TradeKingClient().getWatchList();
		stocksTest();
		//optionsTest();
/*
		OAuthService service = new ServiceBuilder()
				.provider(TradeKingApi.class).apiKey(CONSUMER_KEY)
				.apiSecret(CONSUMER_SECRET).build();
		Token accessToken = new Token(OAUTH_TOKEN, OAUTH_TOKEN_SECRET);
		String OPTIONS_URL = "https://api.tradeking.com/v1/market/ext/quotes.xml?symbols=HPQ";
		// Now let's go and ask for a protected resource!
		OAuthRequest request = new OAuthRequest(Verb.GET, OPTIONS_URL);
		service.signRequest(accessToken, request);
		Response response = request.send();
		String optionsResponse = response.getBody();
		System.out.println(optionsResponse);
*/
	}
	
	
}