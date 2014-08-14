package com.hbar.finance.dao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.runner.RunWith;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.transaction.annotation.Transactional;

import com.hbar.finance.dao.CompanyAndStrategyAnalysisDao;
import com.hbar.finance.dao.CompanyDao;
import com.hbar.finance.dao.OptionQuoteDao;
import com.hbar.finance.dao.StockQuoteDao;
import com.hbar.finance.model.Company;
import com.hbar.finance.model.CompanyAndStrategyAnalysis;
import com.hbar.finance.model.OptionQuote;
import com.hbar.finance.model.StockQuote;
import com.hbar.finance.service.FinanceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/applicationContext.xml"})
@Transactional
@Ignore
public class FinanceServiceTest {
	@Autowired
	OptionQuoteDao optionQuoteDao;

	@Autowired
	StockQuoteDao stockQuoteDao;

	@Autowired
	CompanyDao companyDao;

	@Autowired
	CompanyAndStrategyAnalysisDao companyAndStrategyAnalysisDao;
	
	@Autowired 
	FinanceService financeService;
	
	@Ignore
	@Test
	public void testSaveAndRetrieveOptionQuotes(){
		assertNotNull(optionQuoteDao);
		OptionQuote optionQuoteTest=new OptionQuote();

		BigInteger xday=new BigInteger("13");
		optionQuoteTest.setXday(xday);

		BigInteger xmonth=new BigInteger("12");
		optionQuoteTest.setXmonth(xmonth);
		
		BigInteger xyear=new BigInteger("2012");
		optionQuoteTest.setXyear(xyear);
		
		BigDecimal ask=new BigDecimal(10);
		optionQuoteTest.setAsk(ask);
		
		String ask_time="ast_time_test";
		optionQuoteTest.setAskTime(ask_time);
		
		BigInteger asksz=new BigInteger("12");
		optionQuoteTest.setAsksz(asksz);
		
		BigDecimal basis=new BigDecimal(2);
		optionQuoteTest.setBasis(basis);
		
		BigDecimal bid=new BigDecimal(3);
		optionQuoteTest.setBid(bid);
		
		String bid_time="bid_time";
		optionQuoteTest.setBid_time(bid_time);
		
		
		BigInteger bidsz=new BigInteger("123");
		optionQuoteTest.setBidsz(bidsz);
		
		BigDecimal chg=new BigDecimal(235);
		optionQuoteTest.setChg(chg);
		
		char chg_sign='A';
		optionQuoteTest.setChgSign(chg_sign);
		
		BigDecimal chg_t=new BigDecimal(2);
		optionQuoteTest.setChg_t(chg_t);
		
		BigDecimal cl=new BigDecimal(67);
		optionQuoteTest.setCl(cl);
		
		BigInteger contract_size=new BigInteger("9");
		optionQuoteTest.setContract_size(contract_size);
		
		Date date=new Date();
		optionQuoteTest.setDate(date);
		
		Date datetime=new Date();
		optionQuoteTest.setDatetime(datetime);
		
		BigInteger days_to_expiration=new BigInteger("3");
		optionQuoteTest.setDays_to_expiration(days_to_expiration);
		
		String exch="exch";
		optionQuoteTest.setExch(exch);
		
		String exch_desc="exch_desc";
		optionQuoteTest.setExch_desc(exch_desc);
		
		BigDecimal hi=new BigDecimal(-2);
		optionQuoteTest.setHi(hi);
		
		BigDecimal idelta=new BigDecimal(-3);
		optionQuoteTest.setIdelta(idelta);
		
		BigDecimal igamma=new BigDecimal(4);
		optionQuoteTest.setIgamma(igamma);
		
		BigDecimal imp_volatility=new BigDecimal(-5);
		optionQuoteTest.setImp_volatility(imp_volatility);
		
		BigInteger incr_vl=new BigInteger("-6");
		optionQuoteTest.setIncr_vl(incr_vl);
		
		BigDecimal irho=new BigDecimal(-8);
		optionQuoteTest.setIrho(irho);
		
		String issue_desc="test1";
		optionQuoteTest.setIssue_desc(issue_desc);
		
		BigDecimal itheta=new BigDecimal(-9);
		optionQuoteTest.setItheta(itheta);
		
		BigDecimal ivega=new BigDecimal(-10);
		optionQuoteTest.setIvega(ivega);
		
		BigDecimal last=new BigDecimal(-11);
		optionQuoteTest.setLast(last);
		
		BigDecimal lo=new BigDecimal(-12);
		optionQuoteTest.setLo(lo);
		
		String op_delivery="test2";
		optionQuoteTest.setOp_delivery(op_delivery);
		
		BigInteger op_flag=new BigInteger("3");
		optionQuoteTest.setOp_flag(op_flag);
		
		String op_style="test4";
		optionQuoteTest.setOp_style(op_style);
		
		BigInteger op_subclass=new BigInteger("5");
		optionQuoteTest.setOp_subclass(op_subclass);
		
		BigInteger openinterest=new BigInteger("6");
		optionQuoteTest.setOpeninterest(openinterest);
		
		BigDecimal opn=new BigDecimal(-22);
		optionQuoteTest.setOpn(opn);
		
		BigDecimal opt_val=new BigDecimal(-23);
		optionQuoteTest.setOpt_val(opt_val);
		
		String pchg="test7";
		optionQuoteTest.setPchg(pchg);
		
		String pchg_sign="test8";
		optionQuoteTest.setPchg_sign(pchg_sign);
		
		BigDecimal pcls=new BigDecimal(9);
		optionQuoteTest.setPcls(pcls);
		
		BigDecimal phi=new BigDecimal(10);
		optionQuoteTest.setPhi(phi);
		
		BigDecimal plo=new BigDecimal(11);
		optionQuoteTest.setPlo(plo);
		
		BigDecimal popn=new BigDecimal(12);
		optionQuoteTest.setPopn(popn);
		
		Date pr_date=new Date();
		optionQuoteTest.setPrDate(pr_date);
		
		BigInteger pr_openinterest=new BigInteger("14");
		optionQuoteTest.setPrOpenInterest(pr_openinterest);
		
		BigDecimal prchg=new BigDecimal(15);
		optionQuoteTest.setPrChg(prchg);
		
		BigInteger prem_mult=new BigInteger("23");
		optionQuoteTest.setPremMult(prem_mult);
		
		String put_call="test16";
		optionQuoteTest.setPutCall(put_call);
		
		BigInteger pvol=new BigInteger("1");
		optionQuoteTest.setPvol(pvol);
		
		BigInteger qcond=new BigInteger("2");
		optionQuoteTest.setQcond(qcond);
		
		String rootsymbol="test17";
		optionQuoteTest.setRootsymbol(rootsymbol);
		
		String secclass="test18";
		optionQuoteTest.setSecclass(secclass);
		
		String sesn="test19";
		optionQuoteTest.setSesn(sesn);
		
		BigDecimal strikeprice=new BigDecimal(24);
		optionQuoteTest.setStrikeprice(strikeprice);
		
		String symbol="test21";
		optionQuoteTest.setSymbol(symbol);
		
		String tcond="test221";
		optionQuoteTest.setTcond(tcond);
		
		Date timestamp=new Date();
		optionQuoteTest.setTimestamp(timestamp);
		
		BigInteger tr_num=new BigInteger("4");
		optionQuoteTest.setTr_num(tr_num);
		
		String tradetick="test23";
		optionQuoteTest.setTradetick(tradetick);
		
		String trend="test24";
		optionQuoteTest.setTrend(trend);
		
		String under_cusip="test25";
		optionQuoteTest.setUnder_cusip(under_cusip);
		
		String undersymbol="test26";
		optionQuoteTest.setUndersymbol(undersymbol);
		
		BigInteger vl=new BigInteger("5");
		optionQuoteTest.setVl(vl);
		
		BigDecimal vwap=new BigDecimal(25);
		optionQuoteTest.setVwap(vwap);
		
		BigDecimal wk52hi=new BigDecimal(27);
		optionQuoteTest.setWk52hi(wk52hi);
		
		Date wk52hidate=new Date();
		optionQuoteTest.setWk52hidate(wk52hidate);
		
		BigDecimal wk52lo=new BigDecimal(28);
		optionQuoteTest.setWk52lo(wk52lo);
		
		Date wk52lodate=new Date();
		optionQuoteTest.setWk52lodate(wk52lodate);
		
		Date xdate=new Date();
		optionQuoteTest.setXdate(xdate);
		
		
		financeService.saveOptionQuoteInfo(optionQuoteTest);
		
		OptionQuote persistedOptionQuote=financeService.getOptionQuote(optionQuoteTest.getId());
		optionQuoteTest=null;
		
		assertEquals(xday,persistedOptionQuote.getXday());
		assertEquals(xmonth,persistedOptionQuote.getXmonth());
		assertEquals(xyear,persistedOptionQuote.getXyear());
		assertEquals(ask,persistedOptionQuote.getAsk());
		assertEquals(ask_time,persistedOptionQuote.getAskTime());
		assertEquals(asksz,persistedOptionQuote.getAsksz());
		assertEquals(basis,persistedOptionQuote.getBasis());
		assertEquals(bid,persistedOptionQuote.getBid());
		assertEquals(bid_time,persistedOptionQuote.getBidTime());
		assertEquals(bidsz,persistedOptionQuote.getBidsz());
		assertEquals(chg,persistedOptionQuote.getChg());
		assertEquals(chg_sign,persistedOptionQuote.getChgSign());
		assertEquals(chg_t,persistedOptionQuote.getChgT());
		assertEquals(cl,persistedOptionQuote.getCl());
		assertEquals(contract_size,persistedOptionQuote.getContractSize());
		assertEquals(date,persistedOptionQuote.getDate());
		assertEquals(datetime,persistedOptionQuote.getDatetime());
		assertEquals(days_to_expiration,persistedOptionQuote.getDaysToExpiration());
		assertEquals(exch,persistedOptionQuote.getExch());
		assertEquals(exch_desc,persistedOptionQuote.getExchDesc());
		assertEquals(hi,persistedOptionQuote.getHi());
		assertEquals(idelta,persistedOptionQuote.getIdelta());
		assertEquals(igamma,persistedOptionQuote.getIgamma());
		assertEquals(imp_volatility,persistedOptionQuote.getImpVolatility());
		assertEquals(incr_vl,persistedOptionQuote.getIncrVl());
		assertEquals(irho,persistedOptionQuote.getIrho());
		assertEquals(issue_desc,persistedOptionQuote.getIssueDesc());
		assertEquals(itheta,persistedOptionQuote.getItheta());
		assertEquals(ivega,persistedOptionQuote.getIvega());
		assertEquals(last,persistedOptionQuote.getLast());
		assertEquals(lo,persistedOptionQuote.getLo());
		assertEquals(op_delivery,persistedOptionQuote.getOpDelivery());
		assertEquals(op_flag,persistedOptionQuote.getOpFlag());
		assertEquals(op_style,persistedOptionQuote.getOpStyle());
		assertEquals(op_subclass,persistedOptionQuote.getOpSubclass());
		assertEquals(openinterest,persistedOptionQuote.getOpeninterest());
		assertEquals(opn,persistedOptionQuote.getOpn());
		assertEquals(opt_val,persistedOptionQuote.getOpt_val());
		assertEquals(pchg,persistedOptionQuote.getPchg());
		assertEquals(pchg_sign,persistedOptionQuote.getPchg_sign());
		assertEquals(pcls,persistedOptionQuote.getPcls());
		assertEquals(phi,persistedOptionQuote.getPhi());
		assertEquals(plo,persistedOptionQuote.getPlo());
		assertEquals(popn,persistedOptionQuote.getPopn());
		assertEquals(pr_date,persistedOptionQuote.getPrDate());
		assertEquals(pr_openinterest,persistedOptionQuote.getPrOpenInterest());
		assertEquals(prchg,persistedOptionQuote.getPrChg());
		assertEquals(prem_mult,persistedOptionQuote.getPremMult());
		assertEquals(put_call,persistedOptionQuote.getPutCall());
		assertEquals(pvol,persistedOptionQuote.getPvol());
		assertEquals(qcond,persistedOptionQuote.getQcond());
		assertEquals(rootsymbol,persistedOptionQuote.getRootsymbol());
		assertEquals(secclass,persistedOptionQuote.getSecclass());
		assertEquals(sesn,persistedOptionQuote.getSesn());
		assertEquals(strikeprice,persistedOptionQuote.getStrikeprice());
		assertEquals(symbol,persistedOptionQuote.getSymbol());
		assertEquals(tcond,persistedOptionQuote.getTcond());
		assertEquals(timestamp,persistedOptionQuote.getTimestamp());
		assertEquals(tr_num,persistedOptionQuote.getTrNum());
		assertEquals(tradetick,persistedOptionQuote.getTradetick());
		assertEquals(trend,persistedOptionQuote.getTrend());
		assertEquals(under_cusip,persistedOptionQuote.getUnderCusip());
		assertEquals(undersymbol,persistedOptionQuote.getUnderSymbol());
		assertEquals(vl,persistedOptionQuote.getVl());
		assertEquals(vwap,persistedOptionQuote.getVwap());
		assertEquals(wk52hi,persistedOptionQuote.getWk52hi());
		assertEquals(wk52hidate,persistedOptionQuote.getWk52hidate());
		assertEquals(wk52lo,persistedOptionQuote.getWk52lo());
		assertEquals(wk52lodate,persistedOptionQuote.getWk52lodate());
		assertEquals(xdate,persistedOptionQuote.getXdate());
		
	}
	@Ignore
	@Test
	public void testSaveAndRetrieveStockQuotes(){
		assertNotNull(stockQuoteDao);
		
		
		
		StockQuote stockQuoteTest=new StockQuote();
		
		BigDecimal adp_100=new BigDecimal(123);
		stockQuoteTest.setAdp100(adp_100);
		
		BigDecimal adp_200=new BigDecimal(245);
		stockQuoteTest.setAdp200(adp_200);
		
		BigDecimal adp_50=new BigDecimal(2456);
		stockQuoteTest.setAdp50(adp_50);
		
		BigInteger adv_21=new BigInteger("1");
		stockQuoteTest.setAdv21(adv_21);
		
		BigInteger adv_30=new BigInteger("12");
		stockQuoteTest.setAdv30(adv_30);
		
		BigInteger adv_90=new BigInteger("13");
		stockQuoteTest.setAdv90(adv_90);
		
		BigDecimal ask=new BigDecimal(123);
		stockQuoteTest.setAsk(ask);
		
		String ask_time="ASK_TIME";
		stockQuoteTest.setAskTime(ask_time);
		
		BigInteger asksz=new BigInteger("14");
		stockQuoteTest.setAsksz(asksz);
		
		BigInteger basis=new BigInteger("16");
		stockQuoteTest.setBasis(basis);
		
		BigDecimal beta=new BigDecimal(24432);
		stockQuoteTest.setBeta(beta);
		
		BigDecimal bid=new BigDecimal(2367);
		stockQuoteTest.setBid(bid);
		
		String bid_time="BID_TIME";
		stockQuoteTest.setBidTime(bid_time);
		
		BigInteger bidsz=new BigInteger("7");
		stockQuoteTest.setBidSz(bidsz);
		
		BigInteger bidtick=new BigInteger("87");
		stockQuoteTest.setBidTick(bidtick);
		
		BigDecimal chg=new BigDecimal(12456);
		stockQuoteTest.setChg(chg);
		
		char chg_sign='c';
		stockQuoteTest.setChgSign(chg_sign);
		
		BigDecimal chg_t=new BigDecimal(267);
		stockQuoteTest.setChgT(chg_t);
		
		BigDecimal cl=new BigDecimal(6252);
		stockQuoteTest.setCl(cl);
		
		String cusip=new String("9");
		stockQuoteTest.setCusip(cusip);
		
		Date date=new Date();
		stockQuoteTest.setDate(date);
		
		Date datetime=new Date();
		stockQuoteTest.setDatetime(datetime);
		
		BigDecimal div=new BigDecimal(3);
		stockQuoteTest.setDiv(div);
		
		Date divexdate=new Date();
		stockQuoteTest.setDivexdate(divexdate);
		
		char divfreq='1';
		stockQuoteTest.setDivfreq(divfreq);
		
		Date divpaydt=new Date();
		stockQuoteTest.setDivpaydt(divpaydt);
		
		BigDecimal dollar_value=new BigDecimal(4);
		stockQuoteTest.setDollarValue(dollar_value);
		
		BigDecimal eps=new BigDecimal(5);
		stockQuoteTest.setEps(eps);
		
		String exch="test1";
		stockQuoteTest.setExch(exch);
		
		String exch_desc="test12";
		stockQuoteTest.setExchDesc(exch_desc);
		
		BigDecimal hi=new BigDecimal(6);
		stockQuoteTest.setHi(hi);
		
		BigDecimal iad=new BigDecimal(7);
		stockQuoteTest.setIad(iad);
		
		BigDecimal incr_vl=new BigDecimal(8);
		stockQuoteTest.setIncrVl(incr_vl);
		
		BigDecimal last=new BigDecimal(9);
		stockQuoteTest.setLast(last);
		
		BigDecimal lo=new BigDecimal(10);
		stockQuoteTest.setLo(lo);
		
		String name="test123";
		stockQuoteTest.setName(name);
		
		Byte op_flag='2';
		stockQuoteTest.setOpFlag(op_flag);
		
		BigDecimal opn=new BigDecimal(11);
		stockQuoteTest.setOpn(opn);
		
		String pchg="test123";
		stockQuoteTest.setPchg(pchg);
		
		char pchg_sign='3';
		stockQuoteTest.setPchgSign(pchg_sign);
		
		BigDecimal pcls=new BigDecimal(12);
		stockQuoteTest.setPcls(pcls);

		BigDecimal pe=new BigDecimal(12);
		stockQuoteTest.setPe(pe);
		
		BigDecimal phi=new BigDecimal(13);
		stockQuoteTest.setPhi(phi);
		
		BigDecimal plo=new BigDecimal(14);
		stockQuoteTest.setPlo(plo);
		
		BigDecimal popn=new BigDecimal(15);
		stockQuoteTest.setPopn(popn);
		
		BigDecimal pr_adp_100=new BigDecimal(16);
		stockQuoteTest.setPrAdp100(pr_adp_100);
		
		BigDecimal pr_adp_200=new BigDecimal(17);
		stockQuoteTest.setPrAdp200(pr_adp_200);
		
		BigDecimal pr_adp_50=new BigDecimal(18);
		stockQuoteTest.setPrAdp50(pr_adp_50);
		
		String prime_exch=new String("prime_exc");
		stockQuoteTest.setPrimeExch(prime_exch);
		
		Date pr_date=new Date();
		stockQuoteTest.setPrDate(pr_date);
		
		BigDecimal prbook=new BigDecimal("78");
		stockQuoteTest.setPrBook(prbook);
		
		BigDecimal prchg=new BigDecimal(19);
		stockQuoteTest.setPrChg(prchg);
		
		BigInteger pvol=new BigInteger("81");
		stockQuoteTest.setPvol(pvol);
		
		BigInteger qcond=new BigInteger("91");
		stockQuoteTest.setQcond(qcond);
		
		BigInteger secclass=new BigInteger("82");
		stockQuoteTest.setSecclass(secclass);
		
		String sesn="test5";
		stockQuoteTest.setSesn(sesn);
		
		BigInteger sesn_vl=new BigInteger("85");
		stockQuoteTest.setSesnVl(sesn_vl);
		
		BigInteger sho=new BigInteger("6");
		stockQuoteTest.setSho(sho);
		
		String symbol="test7";
		stockQuoteTest.setSymbol(symbol);
		
		char tcond='A';
		stockQuoteTest.setTcond(tcond);
		
		Date timestamp=new Date();
		stockQuoteTest.setTimestamp(timestamp);
		
		BigInteger tr_num=new BigInteger("14");
		stockQuoteTest.setTrNum(tr_num);
		
		char tradetick='Z';
		stockQuoteTest.setTradetick(tradetick);
		
		BigInteger vl=new BigInteger("15");
		stockQuoteTest.setVl(vl);
		
		BigDecimal volatility12=new BigDecimal(20);
		stockQuoteTest.setVolatility12(volatility12);
		
		BigDecimal vwap=new BigDecimal(21);
		stockQuoteTest.setVwap(vwap);
		
		BigDecimal wk52hi=new BigDecimal(22);
		stockQuoteTest.setWk52hi(wk52hi);
		
		Date wk52hidate=new Date();
		stockQuoteTest.setWk52HiDate(wk52hidate);
		
		BigDecimal wk52lo=new BigDecimal(24);
		stockQuoteTest.setWk52Lo(wk52lo);
		
		Date wk52lodate=new Date();
		stockQuoteTest.setWk52LoDate(wk52lodate);
		
		BigDecimal yield=new BigDecimal(25);
		stockQuoteTest.setYield(yield);
		
		String trend="test9";
		stockQuoteTest.setTrend(trend);
		
		financeService.saveStockQuoteInfo(stockQuoteTest);
		
		StockQuote stockQuotePersisted=financeService.getStockQuote(stockQuoteTest.getId());
		
		assertEquals(adp_100,stockQuotePersisted.getAdp100());
		assertEquals(adp_200, stockQuoteTest.getAdp200());
		assertEquals(adp_50,stockQuoteTest.getAdp50());
		assertEquals(adv_21,stockQuoteTest.getAdv21());
		assertEquals(adv_30,stockQuoteTest.getAdv30());
		assertEquals(adv_90,stockQuoteTest.getAdv90());
		assertEquals(ask,stockQuoteTest.getAsk());
		assertEquals(ask_time,stockQuoteTest.getAskTime());
		assertEquals(asksz,stockQuoteTest.getAsksz());
		assertEquals(basis,stockQuoteTest.getBasis());
		assertEquals(beta,stockQuoteTest.getBeta());
		assertEquals(bid,stockQuoteTest.getBid());
		assertEquals(bid_time,stockQuoteTest.getBidTime());
		assertEquals(bidsz,stockQuoteTest.getBidSz());
		assertEquals(bidtick,stockQuoteTest.getBidTick());
		assertEquals(chg,stockQuoteTest.getChg());
		assertEquals(chg_sign,stockQuoteTest.getChgSign());
		assertEquals(chg_t,stockQuoteTest.getChgT());
		assertEquals(cl,stockQuoteTest.getCl());
		assertEquals(cusip,stockQuoteTest.getCusip());
		assertEquals(date,stockQuoteTest.getDate());
		assertEquals(datetime,stockQuoteTest.getDatetime());
		assertEquals(div,stockQuoteTest.getDiv());
		assertEquals(divexdate,stockQuoteTest.getDivexdate());
		assertEquals(divfreq,stockQuoteTest.getDivfreq());
		assertEquals(divpaydt,stockQuoteTest.getDivpaydt());
		assertEquals(dollar_value,stockQuoteTest.getDollarValue());
		assertEquals(eps,stockQuoteTest.getEps());
		assertEquals(exch,stockQuoteTest.getExch());
		assertEquals(exch_desc,stockQuoteTest.getExchDesc());
		assertEquals(hi,stockQuoteTest.getHi());
		assertEquals(iad,stockQuoteTest.getIad());
		assertEquals(incr_vl,stockQuoteTest.getIncrVl());
		assertEquals(last,stockQuoteTest.getLast());
		assertEquals(lo,stockQuoteTest.getLo());
		assertEquals(name,stockQuoteTest.getName());
		assertEquals(op_flag,stockQuoteTest.getOpFlag());
		assertEquals(opn,stockQuoteTest.getOpn());
		assertEquals(pchg,stockQuoteTest.getPchg());
		assertEquals(pchg_sign,stockQuoteTest.getPchgSign());
		assertEquals(pcls,stockQuoteTest.getPcls());
		assertEquals(pe,stockQuoteTest.getPe());
		assertEquals(prime_exch,stockQuoteTest.getPrimeExch());
		assertEquals(phi,stockQuoteTest.getPhi());
		assertEquals(plo,stockQuoteTest.getPlo());
		assertEquals(popn,stockQuoteTest.getPopn());
		assertEquals(pr_adp_100,stockQuoteTest.getPrAdp100());
		assertEquals(pr_adp_200,stockQuoteTest.getPrAdp200());
		assertEquals(pr_adp_50,stockQuoteTest.getPrAdp50());
		assertEquals(pr_date,stockQuoteTest.getPrDate());
		assertEquals(prbook,stockQuoteTest.getPrBook());
		assertEquals(prchg,stockQuoteTest.getPrChg());
		assertEquals(pvol,stockQuoteTest.getPvol());
		assertEquals(qcond,stockQuoteTest.getQcond());
		assertEquals(secclass,stockQuoteTest.getSecclass());
		assertEquals(sesn,stockQuoteTest.getSesn());
		assertEquals(sesn_vl,stockQuoteTest.getSesnVl());
		assertEquals(sho,stockQuoteTest.getSho());
		assertEquals(symbol,stockQuoteTest.getSymbol());
		assertEquals(tcond,stockQuoteTest.getTcond());
		assertEquals(timestamp,stockQuoteTest.getTimestamp());
		assertEquals(tr_num,stockQuoteTest.getTrNum());
		assertEquals(tradetick,stockQuoteTest.getTradetick());
		assertEquals(vl,stockQuoteTest.getVl());
		assertEquals(volatility12,stockQuoteTest.getVolatility12());
		assertEquals(vwap,stockQuoteTest.getVwap());
		assertEquals(wk52hi,stockQuoteTest.getWk52hi());
		assertEquals(wk52hidate,stockQuoteTest.getWk52HiDate());
		assertEquals(wk52lo,stockQuoteTest.getWk52Lo());
		assertEquals(wk52lodate,stockQuoteTest.getWk52LoDate());
		assertEquals(yield,stockQuoteTest.getYield());
		assertEquals(trend,stockQuoteTest.getTrend());
		
		
	}
	@Ignore
	@Test
	public void testSaveAndRetrieveCompany() {
		List<Company> allCompanies=companyDao.findAll();		
		List<CompanyAndStrategyAnalysis> companyAndStrategyAnalysis=companyAndStrategyAnalysisDao.findAll();
		Company theCompany = companyDao.findById(1l);
		assertEquals("American International Group", theCompany.getName());
		assertEquals("AIG", theCompany.getSymbol());
		
		
	}
	@Test
	@Rollback(false)
	public void testSaveAndRetrieveCompanyFromFile() throws Exception {
		
		Company company=companyDao.findById(138l);
		financeService.executeBasicEquityRetrievalForCompanyFromDataSource(company,"YAHOO");
	}
	@Test
	@Rollback(false)
	public void testBasicEquityDataAlignment() throws Exception {
		
		Company company=companyDao.findById(4l);
		List<String> symbols=new ArrayList<String>();
		symbols.add("^N225");
		DateTime startDateTime=new DateTime(2006, 1, 1, 0,0);
		DateTime endDateTime=new DateTime(2014, 1, 1, 0,0);
		financeService.executeBasicEquityDataAlignment("^GSPC", symbols, startDateTime, endDateTime, "YAHOO", true,true);
	}
}

