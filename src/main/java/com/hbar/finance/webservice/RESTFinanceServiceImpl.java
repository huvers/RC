package com.hbar.finance.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.hbar.finance.date.DateUtils;
import com.hbar.finance.service.FinanceService;

@WebService(endpointInterface = "com.hbar.finance.webservice.RESTFinanceService")
@Path("/v1/financeService")
public class RESTFinanceServiceImpl implements RESTFinanceService {
	private static final String EQUITY_DATA_SOURCE_ID = "equityDataSourceId";
	private static final String SIGNAL_TICKER_IDS = "signalTickerIds";
	private static final String TARGET_TICKER_ID = "targetTickerId";
	private static final String START_DATE = "startDate";
	private static final String END_DATE = "endDate";
	private static final String DATE = "date";
	private static final String STRATEGY_ID = "strategyId";
	private static final String IS_PERCENTAGES_FORMAT = "isPercentagesFormat";
	private static final String IS_ASCENDING = "isAscending";
	
	private static final boolean DEFAULT_IS_PERCENTAGES_FORMAT=false;
	
	private static final boolean DEFAULT_IS_ASCENDING=true;

	private FinanceService financeService;

	public void setFinanceService(FinanceService financeService){
		this.financeService=financeService;
	}
    
	@GET
	@Path("")
	/*@Consumes(MediaType.APPLICATION_JSON)*/
	@Produces(MediaType.TEXT_PLAIN)
	public Response dateAlignedBasicEquityData(
			@QueryParam(SIGNAL_TICKER_IDS) String signalTickerIds,
			@QueryParam(TARGET_TICKER_ID) String targetTickerId,
			@QueryParam(START_DATE) String startDate,
			@QueryParam(END_DATE) String endDate,
			@QueryParam(EQUITY_DATA_SOURCE_ID) String equityDataSourceId,
			@QueryParam(IS_PERCENTAGES_FORMAT) Boolean isPercentagesFormat,
			@QueryParam(IS_ASCENDING) Boolean isAscending
			) throws Exception{
		DateTime startDateTime=DateUtils.createDateTimeFromString(startDate);
		DateTime endDateTime=DateUtils.createDateTimeFromString(endDate);
		String result = financeService.executeBasicEquityDataAlignment(
				targetTickerId, parseCommaDelimitedSymbols(signalTickerIds),
				startDateTime, endDateTime, equityDataSourceId,
				isPercentagesFormat == null ? DEFAULT_IS_PERCENTAGES_FORMAT
						: isPercentagesFormat,
				isAscending == null ? DEFAULT_IS_ASCENDING : isAscending);
		return Response.ok().header("Content-Disposition", "attachment; filename=\"test_text_file.csv\"").entity(result).build();
	}
	
	@GET
	@Path("/strategyEquityData")
	/*@Consumes(MediaType.APPLICATION_JSON)*/
	@Produces(MediaType.TEXT_PLAIN)
	public Response downloadAllBasicEquityDataForStrategy(
			@QueryParam(STRATEGY_ID) String strategyId,
			@QueryParam(EQUITY_DATA_SOURCE_ID) String equityDataSourceId) throws Exception {
		financeService.executeBasicEquityDataRetrievalForStrategyFromDataSource( strategyId, equityDataSourceId);
		return Response.ok().build();
	}
	
	

	@GET
	@Path("/bollingerClassifier")
	/*@Consumes(MediaType.APPLICATION_JSON)*/
	@Produces(MediaType.TEXT_PLAIN)
	public Response bollingerClassifier(
			@QueryParam(EQUITY_DATA_SOURCE_ID) String equityDataSourceId,
			@QueryParam(DATE) String date
			) throws Exception {
		String bollingerClassifier=financeService.executeBollingerBandClassifierStrategy(equityDataSourceId, date);
		return Response.ok().header("Content-Disposition", "attachment; filename=\"test_text_file.csv\"").entity(bollingerClassifier).build();
	}
	

	private List<String> parseCommaDelimitedSymbols(String commaDelimitedSymbols){
		String[] splitSymbols=commaDelimitedSymbols.split(",");
		List<String> symbols=new ArrayList<String>();
		for(String symbol:splitSymbols){
			symbols.add(symbol.trim());
		}
		return symbols;
	}
	
}
