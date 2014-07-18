package com.hbar.finance.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@AttributeOverrides({
	@AttributeOverride(name="id",column=@Column(name="id")),
	@AttributeOverride(name="dateTimeCreated",column=@Column(name="created")),
	@AttributeOverride(name="dateTimeUpdated",column=@Column(name="last_update")),
})
@NamedQueries({
	@NamedQuery( name="getCompanyFromSymbol", query="SELECT c FROM Company c "+
			"WHERE c.symbol=:symbol")
})
@Entity(name="Company")
@Table(name="company")
public class Company extends IdentityModel {
	private String name;
	private String symbol;
	/*private Set<BasicStockData> basicStockData = new HashSet<BasicStockData>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "basicStockData")
	public Set<BasicStockData> getBasicStockData() {
		return basicStockData;
	}
	public void setBasicStockData(Set<BasicStockData> basicStockData) {
		this.basicStockData = basicStockData;
	}
*/
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
}
