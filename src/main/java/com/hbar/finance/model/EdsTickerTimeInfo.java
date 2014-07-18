package com.hbar.finance.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
@AttributeOverrides({
	@AttributeOverride(name="id",column=@Column(name="id")),
	@AttributeOverride(name="dateTimeCreated",column=@Column(name="created")),
	@AttributeOverride(name="dateTimeUpdated",column=@Column(name="last_update")),
})
@NamedQueries({
	@NamedQuery( name="getEdsTickerTimeInfo", query="SELECT etti FROM EdsTickerTimeInfo etti "+
			"WHERE etti.tickerId=:tickerId AND etti.equityDataSourceId=:equityDataSourceId")
})
@Entity(name="EdsTickerTimeInfo")
@Table(name="eds_ticker_timeinfo")
public class EdsTickerTimeInfo extends IdentityModel{
	@Column(name="ticker_id")	
	private Long tickerId;
	@Column(name="equity_data_source_id")	
	private Integer equityDataSourceId;
	
	@Column(name="close_time_utc")
	private String closeTimeUtc; 
	
	public Long getTickerId() {
		return tickerId;
	}
	public void setTickerId(Long tickerId) {
		this.tickerId = tickerId;
	}
	public Integer getEquityDataSourceId() {
		return equityDataSourceId;
	}
	public void setEquityDataSourceId(Integer equityDataSourceId) {
		this.equityDataSourceId = equityDataSourceId;
	}
	public String getCloseTimeUtc() {
		return closeTimeUtc;
	}
	public void setCloseTimeUtc(String closeTimeUtc) {
		this.closeTimeUtc = closeTimeUtc;
	}	
}
