package com.hbar.finance.dao;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hbar.finance.dao.BasicStockDataDao;
import com.hbar.finance.model.BasicStockData;
import com.hbar.finance.service.FinanceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/testApplicationContext.xml"})
@Transactional
@Ignore
public class BasicStockDataTest {
	@Autowired 
	BasicStockDataDao basicStockDataDao;

	public void setBasicStockDataDao(BasicStockDataDao basicStockDataDao){
		this.basicStockDataDao=basicStockDataDao;
	}
	@Ignore
	@Test
	public void testSaveAndRetrieveBasicStockData(){
		BasicStockData basicStockData=basicStockDataDao.findById(1l);
		System.out.println("id="+basicStockData.getId());
	}
	
	
}