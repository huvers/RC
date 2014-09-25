package com.hbar.finance.webservice;
import javax.ws.rs.core.Response;

public interface RESTFinanceService {
	public Response dateAlignedBasicEquityData(String targetTickerId, String signalTickerIds, String startDate, String endDate, String equityDataSourceId, Boolean percentagesFormat, Boolean isAscending, String excludedFields ) throws Exception;

	public Response downloadAllBasicEquityDataForStrategy(String strategy,
			String equityDataSourceId) throws Exception;

	public Response bollingerClassifier(
			String equityDataSourceId,
			String date
			) throws Exception;
}
