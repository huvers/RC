package com.hbar.finance.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.hbar.finance.providers.tradeking.StocksResponse;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@AttributeOverrides({
	@AttributeOverride(name="id",column=@Column(name="id")),
	@AttributeOverride(name="dateTimeCreated",column=@Column(name="created")),
	@AttributeOverride(name="dateTimeUpdated",column=@Column(name="last_update")),
})
@NamedQueries({
	@NamedQuery( name="stockQuoteWithOrderedOptions", query="SELECT sq FROM StockQuote sq " +
			"INNER JOIN FETCH sq.optionQuotes oq " +
			"WHERE sq.id=:stockQuoteId ORDER BY oq.xyear asc, oq.xmonth asc, oq.xday asc, oq.strikeprice")
})
@Entity(name="StockQuote")
@Table(name="stock_quote")
public class StockQuote extends IdentityModel{
	private BigDecimal adp_100;
	private BigDecimal adp_200;
	private BigDecimal adp_50;
	private BigInteger adv_21;
	private BigInteger adv_30;
	private BigInteger adv_90;
	private BigDecimal ask;
	@Column(name="ask_time")
	private String ask_time;
	private BigInteger asksz;
	private BigInteger basis;
	private BigDecimal beta;
	private BigDecimal bid;
	private String bid_time;
	private BigInteger bidsz;
	private BigInteger bidtick;
	private BigDecimal chg;
	private char chg_sign;
	private BigDecimal chg_t;
	private BigDecimal cl;
	private String cusip;
	private Date date;
	private Date datetime;
	@Column( name="[div]", nullable=true)
	private BigDecimal div;
	
	private Date divexdate;
	private char divfreq;
	private Date divpaydt;
	private BigDecimal dollar_value;
	private BigDecimal eps;
	private String exch;
	private String exch_desc;
	private BigDecimal hi;
	private BigDecimal iad;
	private BigDecimal incr_vl; 
	private BigDecimal last;
	private BigDecimal lo;
	private String name;
	private Byte op_flag;
	private BigDecimal opn;
	private String pchg;
	private char pchg_sign;
	private BigDecimal pcls;
	private BigDecimal pe;
	private BigDecimal phi;
	private BigDecimal plo;
	private BigDecimal popn;
	private BigDecimal pr_adp_100;
	private BigDecimal pr_adp_200;
	private BigDecimal pr_adp_50;
	private Date pr_date;
	private BigDecimal prbook;
	private BigDecimal prchg;
	private String prime_exch;
	private BigInteger pvol;
	private BigInteger qcond;
	private BigInteger secclass;
	private String sesn;
	private BigInteger sesn_vl;
	private BigInteger sho;
	private String symbol;
	private char tcond;
	private Date timestamp;
	private BigInteger tr_num;
	private char tradetick;
	private BigInteger vl;
	private BigDecimal volatility12;
	private BigDecimal vwap;
	private BigDecimal wk52hi;
	private Date wk52hidate;
	private BigDecimal wk52lo;
	private Date wk52lodate;
	private BigDecimal yield;
	private String trend;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="stockQuote", fetch=FetchType.LAZY, orphanRemoval=true)
	private List<OptionQuote> optionQuotes=new ArrayList<OptionQuote>();
	
	public BigDecimal getAdp100() {
		return adp_100;
	}


	public void setAdp100(BigDecimal adp_100) {
		this.adp_100 = adp_100;
	}


	public BigDecimal getAdp200() {
		return adp_200;
	}


	public void setAdp200(BigDecimal adp_200) {
		this.adp_200 = adp_200;
	}


	public BigDecimal getAdp50() {
		return adp_50;
	}


	public void setAdp50(BigDecimal adp_50) {
		this.adp_50 = adp_50;
	}


	public BigInteger getAdv21() {
		return adv_21;
	}


	public void setAdv21(BigInteger adv_21) {
		this.adv_21 = adv_21;
	}


	public BigInteger getAdv30() {
		return adv_30;
	}


	public void setAdv30(BigInteger adv_30) {
		this.adv_30 = adv_30;
	}


	public BigInteger getAdv90() {
		return adv_90;
	}


	public void setAdv90(BigInteger adv_90) {
		this.adv_90 = adv_90;
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


	public BigInteger getBasis() {
		return basis;
	}


	public void setBasis(BigInteger basis) {
		this.basis = basis;
	}


	public BigDecimal getBeta() {
		return beta;
	}


	public void setBeta(BigDecimal beta) {
		this.beta = beta;
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


	public void setBidTime(String bid_time) {
		this.bid_time = bid_time;
	}


	public BigInteger getBidSz() {
		return bidsz;
	}


	public void setBidSz(BigInteger bidsz) {
		this.bidsz = bidsz;
	}


	public BigInteger getBidTick() {
		return bidtick;
	}


	public void setBidTick(BigInteger bidtick) {
		this.bidtick = bidtick;
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


	public void setChgT(BigDecimal chg_t) {
		this.chg_t = chg_t;
	}


	public BigDecimal getCl() {
		return cl;
	}


	public void setCl(BigDecimal cl) {
		this.cl = cl;
	}


	public String getCusip() {
		return cusip;
	}


	public void setCusip(String cusip) {
		this.cusip = cusip;
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


	public BigDecimal getDiv() {
		return div;
	}


	public void setDiv(BigDecimal div) {
		this.div = div;
	}


	public Date getDivexdate() {
		return divexdate;
	}


	public void setDivexdate(Date divexdate) {
		this.divexdate = divexdate;
	}


	public char getDivfreq() {
		return divfreq;
	}


	public void setDivfreq(char divfreq) {
		this.divfreq = divfreq;
	}


	public Date getDivpaydt() {
		return divpaydt;
	}


	public void setDivpaydt(Date divpaydt) {
		this.divpaydt = divpaydt;
	}


	public BigDecimal getDollarValue() {
		return dollar_value;
	}


	public void setDollarValue(BigDecimal dollar_value) {
		this.dollar_value = dollar_value;
	}


	public BigDecimal getEps() {
		return eps;
	}


	public void setEps(BigDecimal eps) {
		this.eps = eps;
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


	public void setExchDesc(String exch_desc) {
		this.exch_desc = exch_desc;
	}


	public BigDecimal getHi() {
		return hi;
	}


	public void setHi(BigDecimal hi) {
		this.hi = hi;
	}


	public BigDecimal getIad() {
		return iad;
	}


	public void setIad(BigDecimal iad) {
		this.iad = iad;
	}


	public BigDecimal getIncrVl() {
		return incr_vl;
	}


	public void setIncrVl(BigDecimal incr_vl) {
		this.incr_vl = incr_vl;
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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Byte getOpFlag() {
		return op_flag;
	}


	public void setOpFlag(Byte op_flag) {
		this.op_flag = op_flag;
	}


	public BigDecimal getOpn() {
		return opn;
	}


	public void setOpn(BigDecimal opn) {
		this.opn = opn;
	}


	public String getPchg() {
		return pchg;
	}


	public void setPchg(String pchg) {
		this.pchg = pchg;
	}


	public char getPchgSign() {
		return pchg_sign;
	}


	public void setPchgSign(char pchg_sign) {
		this.pchg_sign = pchg_sign;
	}


	public BigDecimal getPcls() {
		return pcls;
	}


	public void setPcls(BigDecimal pcls) {
		this.pcls = pcls;
	}


	public BigDecimal getPe() {
		return pe;
	}


	public void setPe(BigDecimal pe) {
		this.pe = pe;
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


	public BigDecimal getPrAdp100() {
		return pr_adp_100;
	}


	public void setPrAdp100(BigDecimal pr_adp_100) {
		this.pr_adp_100 = pr_adp_100;
	}


	public BigDecimal getPrAdp200() {
		return pr_adp_200;
	}


	public void setPrAdp200(BigDecimal pr_adp_200) {
		this.pr_adp_200 = pr_adp_200;
	}


	public BigDecimal getPrAdp50() {
		return pr_adp_50;
	}


	public void setPrAdp50(BigDecimal pr_adp_50) {
		this.pr_adp_50 = pr_adp_50;
	}


	public Date getPrDate() {
		return pr_date;
	}


	public void setPrDate(Date pr_date) {
		this.pr_date = pr_date;
	}


	public BigDecimal getPrBook() {
		return prbook;
	}


	public void setPrBook(BigDecimal prbook) {
		this.prbook = prbook;
	}


	public BigDecimal getPrChg() {
		return prchg;
	}


	public void setPrChg(BigDecimal prchg) {
		this.prchg = prchg;
	}


	public BigInteger getPvol() {
		return pvol;
	}


	public void setPvol(BigInteger pvol) {
		this.pvol = pvol;
	}

	public String getPrimeExch() {
		return prime_exch;
	}


	public void setPrimeExch(String prime_exch) {
		this.prime_exch = prime_exch;
	}


	public BigInteger getQcond() {
		return qcond;
	}


	public void setQcond(BigInteger qcond) {
		this.qcond = qcond;
	}


	public BigInteger getSecclass() {
		return secclass;
	}


	public void setSecclass(BigInteger secclass) {
		this.secclass = secclass;
	}


	public String getSesn() {
		return sesn;
	}


	public void setSesn(String sesn) {
		this.sesn = sesn;
	}


	public BigInteger getSesnVl() {
		return sesn_vl;
	}


	public void setSesnVl(BigInteger sesn_vl) {
		this.sesn_vl = sesn_vl;
	}


	public BigInteger getSho() {
		return sho;
	}


	public void setSho(BigInteger sho) {
		this.sho = sho;
	}


	public String getSymbol() {
		return symbol;
	}


	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}


	public char getTcond() {
		return tcond;
	}


	public void setTcond(char tcond) {
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


	public void setTrNum(BigInteger tr_num) {
		this.tr_num = tr_num;
	}


	public char getTradetick() {
		return tradetick;
	}


	public void setTradetick(char tradetick) {
		this.tradetick = tradetick;
	}


	public BigInteger getVl() {
		return vl;
	}


	public void setVl(BigInteger vl) {
		this.vl = vl;
	}


	public BigDecimal getVolatility12() {
		return volatility12;
	}


	public void setVolatility12(BigDecimal volatility12) {
		this.volatility12 = volatility12;
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


	public Date getWk52HiDate() {
		return wk52hidate;
	}


	public void setWk52HiDate(Date wk52hidate) {
		this.wk52hidate = wk52hidate;
	}


	public BigDecimal getWk52lo() {
		return wk52lo;
	}


	public void setWk52lo(BigDecimal wk52lo) {
		this.wk52lo = wk52lo;
	}



	public BigDecimal getWk52Lo() {
		return wk52lo;
	}


	public void setWk52Lo(BigDecimal wk52Lo) {
		this.wk52lo = wk52Lo;
	}


	public Date getWk52LoDate() {
		return wk52lodate;
	}


	public void setWk52LoDate(Date wk52LoDate) {
		this.wk52lodate = wk52LoDate;
	}


	public BigDecimal getYield() {
		return yield;
	}


	public void setYield(BigDecimal yield) {
		this.yield = yield;
	}

	public void setTrend(String trend){
		this.trend=trend;
	}

	public String getTrend(){
		return trend;
	}

	public List<OptionQuote> getOptionQuotes() {
		return optionQuotes;
	}


	public void setOptionQuotes(List<OptionQuote> optionQuotes) {
		this.optionQuotes = optionQuotes;
	}


	public static void main(String[] args){
		StockQuote q1=new StockQuote();
		q1.setWk52Lo(new BigDecimal(11.350));
		
		q1.setYield(new BigDecimal(3.11));

        XStream xStream = new XStream(new DomDriver());
        xStream.alias("quote", StockQuote.class);
        xStream.alias("response", StocksResponse.class);
        System.out.println(xStream.toXML(q1));
        StockQuote q2=(StockQuote)xStream.fromXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
        				"<quote>\n"+
        			      "<wk52lo>11.3500</wk52lo>\n"+
        			      "<wk52lodate>20121120</wk52lodate>\n"+
        			      "<yield>3.11</yield>\n"+
						"</quote>");
        System.out.println("week52lo="+q2.getWk52Lo());
        System.out.println("week52lodate="+q2.getWk52LoDate());
        System.out.println("yield="+q2.getYield());

		
		
	}
}
