package com.hbar.finance.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@AttributeOverrides({
	@AttributeOverride(name="id",column=@Column(name="id")),
	@AttributeOverride(name="dateTimeCreated",column=@Column(name="created")),
	@AttributeOverride(name="dateTimeUpdated",column=@Column(name="last_update")),
})
@Entity(name="OptionQuote")
@Table(name="option_quote")
public class OptionQuote extends IdentityModel{
	
	
	@Column(name="xday")
	private BigInteger xday;
	private BigInteger xmonth;
	private BigInteger xyear;
	private BigDecimal ask;
	private String ask_time;
	private BigInteger asksz;
	private BigDecimal basis;
	private BigDecimal bid;
	private String bid_time;
	private BigInteger bidsz;
	private BigDecimal chg;
	private char chg_sign;
	private BigDecimal chg_t;
	private BigDecimal cl;
	private BigInteger contract_size;
	private Date date;
	private Date datetime;
	private BigInteger days_to_expiration;
	private String exch;
	private String exch_desc;
	private BigDecimal hi;
	private BigDecimal idelta;
	private BigDecimal igamma;
	private BigDecimal imp_volatility;
	private BigInteger incr_vl;
	private BigDecimal irho;
	private String issue_desc;
	private BigDecimal itheta;
	private BigDecimal ivega;
	private BigDecimal last;
	private BigDecimal lo;
	private String op_delivery;
	private BigInteger op_flag;
	private String op_style;
	private BigInteger op_subclass;
	private BigInteger openinterest;
	private BigDecimal opn;
	private BigDecimal opt_val;
	private String pchg;
	private String pchg_sign;
	private BigDecimal pcls;
	private BigDecimal phi;
	private BigDecimal plo;
	private BigDecimal popn;
	private Date pr_date;
	private BigInteger pr_openinterest;
	private BigDecimal prchg;
	private BigInteger prem_mult;
	private String put_call;
	private BigInteger pvol;
	private BigInteger qcond;
	private String rootsymbol;
	private String secclass;
	private String sesn;
	private BigDecimal strikeprice;
	private String symbol;
	private String tcond;
	private Date timestamp;
	private BigInteger tr_num;
	private String tradetick;
	private String trend;
	private String under_cusip;
	private String undersymbol;
	private BigInteger vl;
	private BigDecimal vwap;
	private BigDecimal wk52hi;
	private Date wk52hidate;
	private BigDecimal wk52lo;
	private Date wk52lodate;
	private Date xdate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="stock_quote_id", nullable=true)
	private StockQuote stockQuote;

	public BigInteger getXday() {
		return xday;
	}
	public void setXday(BigInteger xday) {
		this.xday = xday;
	}
	public BigInteger getXmonth() {
		return xmonth;
	}
	public void setXmonth(BigInteger xmonth) {
		this.xmonth = xmonth;
	}
	public BigInteger getXyear() {
		return xyear;
	}
	public void setXyear(BigInteger xyear) {
		this.xyear = xyear;
	}
	
	public BigDecimal getAsk() {
		return ask;
	}
	public void setAsk(BigDecimal ask) {
		this.ask = ask;
	}
	public String getAskTime() {
		return ask_time;
	}
	public void setAskTime(String ask_time) {
		this.ask_time = ask_time;
	}
	public BigInteger getAsksz() {
		return asksz;
	}
	public void setAsksz(BigInteger asksz) {
		this.asksz = asksz;
	}
	public BigDecimal getBasis() {
		return basis;
	}
	public void setBasis(BigDecimal basis) {
		this.basis = basis;
	}
	public BigDecimal getBid() {
		return bid;
	}
	public void setBid(BigDecimal bid) {
		this.bid = bid;
	}
	public String getBidTime() {
		return bid_time;
	}
	public void setBid_time(String bid_time) {
		this.bid_time = bid_time;
	}
	public BigInteger getBidsz() {
		return bidsz;
	}
	public void setBidsz(BigInteger bidsz) {
		this.bidsz = bidsz;
	}
	public BigDecimal getChg() {
		return chg;
	}
	public void setChg(BigDecimal chg) {
		this.chg = chg;
	}
	public char getChgSign() {
		return chg_sign;
	}
	public void setChgSign(char chg_sign) {
		this.chg_sign = chg_sign;
	}
	public BigDecimal getChgT() {
		return chg_t;
	}
	public void setChg_t(BigDecimal chg_t) {
		this.chg_t = chg_t;
	}
	public BigDecimal getCl() {
		return cl;
	}
	public void setCl(BigDecimal cl) {
		this.cl = cl;
	}
	public BigInteger getContractSize() {
		return contract_size;
	}
	public void setContract_size(BigInteger contract_size) {
		this.contract_size = contract_size;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public BigInteger getDaysToExpiration() {
		return days_to_expiration;
	}
	public void setDays_to_expiration(BigInteger days_to_expiration) {
		this.days_to_expiration = days_to_expiration;
	}
	public String getExch() {
		return exch;
	}
	public void setExch(String exch) {
		this.exch = exch;
	}
	public String getExchDesc() {
		return exch_desc;
	}
	public void setExch_desc(String exch_desc) {
		this.exch_desc = exch_desc;
	}
	public BigDecimal getHi() {
		return hi;
	}
	public void setHi(BigDecimal hi) {
		this.hi = hi;
	}
	public BigDecimal getIdelta() {
		return idelta;
	}
	public void setIdelta(BigDecimal idelta) {
		this.idelta = idelta;
	}
	public BigDecimal getIgamma() {
		return igamma;
	}
	public void setIgamma(BigDecimal igamma) {
		this.igamma = igamma;
	}

	public BigDecimal getImpVolatility() {
		return imp_volatility;
	}
	public void setImp_volatility(BigDecimal imp_volatility) {
		this.imp_volatility = imp_volatility;
	}
	public BigInteger getIncrVl() {
		return incr_vl;
	}
	public void setIncr_vl(BigInteger incr_vl) {
		this.incr_vl = incr_vl;
	}
	public BigDecimal getIrho() {
		return irho;
	}
	public void setIrho(BigDecimal irho) {
		this.irho = irho;
	}
	public String getIssueDesc() {
		return issue_desc;
	}
	public void setIssue_desc(String issue_desc) {
		this.issue_desc = issue_desc;
	}
	public BigDecimal getItheta() {
		return itheta;
	}
	public void setItheta(BigDecimal itheta) {
		this.itheta = itheta;
	}
	public BigDecimal getIvega() {
		return ivega;
	}
	public void setIvega(BigDecimal ivega) {
		this.ivega = ivega;
	}
	public BigDecimal getLast() {
		return last;
	}
	public void setLast(BigDecimal last) {
		this.last = last;
	}
	public BigDecimal getLo() {
		return lo;
	}
	public void setLo(BigDecimal lo) {
		this.lo = lo;
	}
	public String getOpDelivery() {
		return op_delivery;
	}
	public void setOp_delivery(String op_delivery) {
		this.op_delivery = op_delivery;
	}
	public BigInteger getOpFlag() {
		return op_flag;
	}
	public void setOp_flag(BigInteger op_flag) {
		this.op_flag = op_flag;
	}
	public String getOpStyle() {
		return op_style;
	}
	public void setOp_style(String op_style) {
		this.op_style = op_style;
	}
	public BigInteger getOpSubclass() {
		return op_subclass;
	}
	public void setOp_subclass(BigInteger op_subclass) {
		this.op_subclass = op_subclass;
	}
	public BigInteger getOpeninterest() {
		return openinterest;
	}
	public void setOpeninterest(BigInteger openinterest) {
		this.openinterest = openinterest;
	}
	public BigDecimal getOpn() {
		return opn;
	}
	public void setOpn(BigDecimal opn) {
		this.opn = opn;
	}
	public BigDecimal getOpt_val() {
		return opt_val;
	}
	public void setOpt_val(BigDecimal opt_val) {
		this.opt_val = opt_val;
	}
	public String getPchg() {
		return pchg;
	}
	public void setPchg(String pchg) {
		this.pchg = pchg;
	}
	public String getPchg_sign() {
		return pchg_sign;
	}
	public void setPchg_sign(String pchg_sign) {
		this.pchg_sign = pchg_sign;
	}
	public BigDecimal getPcls() {
		return pcls;
	}
	public void setPcls(BigDecimal pcls) {
		this.pcls = pcls;
	}
	public BigDecimal getPhi() {
		return phi;
	}
	public void setPhi(BigDecimal phi) {
		this.phi = phi;
	}
	public BigDecimal getPlo() {
		return plo;
	}
	public void setPlo(BigDecimal plo) {
		this.plo = plo;
	}
	public BigDecimal getPopn() {
		return popn;
	}
	public void setPopn(BigDecimal popn) {
		this.popn = popn;
	}
	public Date getPrDate() {
		return pr_date;
	}
	public void setPrDate(Date pr_date) {
		this.pr_date = pr_date;
	}
	public BigInteger getPrOpenInterest() {
		return pr_openinterest;
	}
	public void setPrOpenInterest(BigInteger pr_openinterest) {
		this.pr_openinterest = pr_openinterest;
	}
	public BigDecimal getPrChg() {
		return prchg;
	}
	public void setPrChg(BigDecimal prchg) {
		this.prchg = prchg;
	}
	public BigInteger getPremMult() {
		return prem_mult;
	}
	public void setPremMult(BigInteger prem_mult) {
		this.prem_mult = prem_mult;
	}
	public String getPutCall() {
		return put_call;
	}
	public void setPutCall(String put_call) {
		this.put_call = put_call;
	}
	public BigInteger getPvol() {
		return pvol;
	}
	public void setPvol(BigInteger pvol) {
		this.pvol = pvol;
	}
	public BigInteger getQcond() {
		return qcond;
	}
	public void setQcond(BigInteger qcond) {
		this.qcond = qcond;
	}
	public String getRootsymbol() {
		return rootsymbol;
	}
	public void setRootsymbol(String rootsymbol) {
		this.rootsymbol = rootsymbol;
	}
	public String getSecclass() {
		return secclass;
	}
	public void setSecclass(String secclass) {
		this.secclass = secclass;
	}
	public String getSesn() {
		return sesn;
	}
	public void setSesn(String sesn) {
		this.sesn = sesn;
	}
	public BigDecimal getStrikeprice() {
		return strikeprice;
	}
	public void setStrikeprice(BigDecimal strikeprice) {
		this.strikeprice = strikeprice;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getTcond() {
		return tcond;
	}
	public void setTcond(String tcond) {
		this.tcond = tcond;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public BigInteger getTrNum() {
		return tr_num;
	}
	public void setTr_num(BigInteger tr_num) {
		this.tr_num = tr_num;
	}
	public String getTradetick() {
		return tradetick;
	}
	public void setTradetick(String tradetick) {
		this.tradetick = tradetick;
	}
	public String getTrend() {
		return trend;
	}
	public void setTrend(String trend) {
		this.trend = trend;
	}
	public String getUnderCusip() {
		return under_cusip;
	}
	public void setUnder_cusip(String under_cusip) {
		this.under_cusip = under_cusip;
	}
	public String getUnderSymbol() {
		return undersymbol;
	}
	public void setUndersymbol(String undersymbol) {
		this.undersymbol = undersymbol;
	}
	public BigInteger getVl() {
		return vl;
	}
	public void setVl(BigInteger vl) {
		this.vl = vl;
	}
	public BigDecimal getVwap() {
		return vwap;
	}
	public void setVwap(BigDecimal vwap) {
		this.vwap = vwap;
	}
	public BigDecimal getWk52hi() {
		return wk52hi;
	}
	public void setWk52hi(BigDecimal wk52hi) {
		this.wk52hi = wk52hi;
	}
	public Date getWk52hidate() {
		return wk52hidate;
	}
	public void setWk52hidate(Date wk52hidate) {
		this.wk52hidate = wk52hidate;
	}
	public BigDecimal getWk52lo() {
		return wk52lo;
	}
	public void setWk52lo(BigDecimal wk52lo) {
		this.wk52lo = wk52lo;
	}
	public Date getWk52lodate() {
		return wk52lodate;
	}
	public void setWk52lodate(Date wk52lodate) {
		this.wk52lodate = wk52lodate;
	}
	public Date getXdate() {
		return xdate;
	}
	public void setXdate(Date xdate) {
		this.xdate = xdate;
	}
	public StockQuote getStockQuote() {
		return stockQuote;
	}
	public void setStockQuote(StockQuote stockQuote) {
		this.stockQuote = stockQuote;
	}
	
}
