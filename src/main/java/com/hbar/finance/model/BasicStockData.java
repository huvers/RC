package com.hbar.finance.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.hbar.finance.date.DateTimeHolder;
@AttributeOverrides({
	@AttributeOverride(name="id",column=@Column(name="id")),
	@AttributeOverride(name="dateTimeCreated",column=@Column(name="created")),
	@AttributeOverride(name="dateTimeUpdated",column=@Column(name="last_update")),
})
@NamedQueries({
	@NamedQuery( name="getLatestBasicStockDateForCompany", query="SELECT bsd FROM BasicStockData bsd "+
			"WHERE bsd.companyId=:companyId  AND bsd.versionIndex=(" +
				"SELECT max(bsd2.versionIndex) FROM BasicStockData bsd2 " +
				"WHERE bsd2.companyId=bsd.companyId AND bsd2.equityDataSourceId=bsd.equityDataSourceId" +			
			")"+
			"ORDER BY bsd.dateTime DESC"),
	@NamedQuery( name="getBasicStockDataForCompanyBeforeDate", query="SELECT bsd FROM BasicStockData bsd "+
			"WHERE bsd.companyId=:companyId AND bsd.dateTime<=:date AND bsd.versionIndex=:versionIndex "+
			"AND bsd.equityDataSourceId=:equityDataSourceId "+
			"ORDER BY bsd.dateTime DESC"),
	@NamedQuery( name="getBasicStockDataForCompanyBeforeDateAscending", query="SELECT bsd FROM BasicStockData bsd "+
			"WHERE bsd.companyId=:companyId AND bsd.dateTime<=:date  AND bsd.versionIndex=:versionIndex "+
			"AND bsd.equityDataSourceId=:equityDataSourceId "+
			"ORDER BY bsd.dateTime ASC"),
			
	@NamedQuery(name = "getBasicStockDataForCompanyBetweenDates", query = "SELECT bsd FROM BasicStockData bsd "
			+ "WHERE bsd.companyId=:companyId AND bsd.dateTime>=:startDate AND bsd.dateTime<=:endDate " +
			"AND bsd.equityDataSourceId=:equityDataSourceId AND bsd.versionIndex=:versionIndex "+
			"ORDER BY bsd.dateTime DESC"),
			
	@NamedQuery(name = "getBasicStockDataForCompanyBetweenDatesAscending", query = "SELECT bsd FROM BasicStockData bsd "
			+ "WHERE bsd.companyId=:companyId AND bsd.dateTime>=:startDate AND bsd.dateTime<=:endDate " +
			"AND bsd.equityDataSourceId=:equityDataSourceId AND bsd.versionIndex=(" +
				"SELECT max(bsd2.versionIndex) FROM BasicStockData bsd2 " +
				"WHERE bsd2.companyId=bsd.companyId AND bsd2.equityDataSourceId=bsd.equityDataSourceId" +			
			" )"+
			" ORDER BY bsd.dateTime ASC"),
	@NamedQuery(name = "getMaxVersionIndexForCompanyId", query = "SELECT max(bsd.versionIndex) FROM BasicStockData bsd "
					+ "WHERE bsd.companyId=:companyId and bsd.equityDataSourceId=:equityDataSourceId")
})
@Entity(name="BasicStockData")
@Table(name="basic_stock_data")
public class BasicStockData extends IdentityModel implements DateTimeHolder{

	@Column(name="company_id")
	private Long companyId;
	@Column(name="date")
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	private DateTime dateTime;
	private BigDecimal close;
	private BigDecimal open;
	private BigDecimal high;
	private BigDecimal low;
	private BigDecimal volume;
	@Column(name="equity_data_source_id")	
	private Integer equityDataSourceId;
	@Column(name="version_index")
	private Integer versionIndex;
	
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	/*
	private Company company;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", nullable = false)
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}*/
	public DateTime getDateTime() {
		return dateTime;
	}
	public void setDate(DateTime dateTime) {
		this.dateTime = dateTime;
	}
	public BigDecimal getClose() {
		return close;
	}
	public void setClose(BigDecimal close) {
		this.close = close;
	}
	public BigDecimal getOpen() {
		return open;
	}
	public void setOpen(BigDecimal open) {
		this.open = open;
	}
	public BigDecimal getHigh() {
		return high;
	}
	public void setHigh(BigDecimal high) {
		this.high = high;
	}
	public BigDecimal getLow() {
		return low;
	}
	public void setLow(BigDecimal low) {
		this.low = low;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	public Integer getEquityDataSourceId() {
		return equityDataSourceId;
	}
	public void setEquityDataSourceId(Integer equityDataSourceId) {
		this.equityDataSourceId = equityDataSourceId;
	}
	public Integer getVersionIndex() {
		return versionIndex;
	}
	public void setVersionIndex(Integer versionIndex) {
		this.versionIndex = versionIndex;
	}
	

}
