package com.hbar.finance.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hbar.finance.dao.CompanyDao;
import com.hbar.finance.datasource.BasicStockDataDownloader;
import com.hbar.finance.model.BasicStockData;
import com.hbar.finance.model.Company;
import com.hbar.finance.service.BasicStockDataService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/applicationContext.xml"})
@Transactional
@Ignore
public class BasicStockDataDownload {
	@Autowired
	public BasicStockDataDownloader basicStockDataDownloader;
	
	@Autowired
	BasicStockDataService basicStockDataService;
	
	@Autowired
	CompanyDao companyDao;
	
	@Test
	@Rollback(false)
	public void testDownloadData(){
		try {
			//List<Company> allCompanies=companyDao.findAll();
			List<Company> allCompanies=new ArrayList<Company>();
			allCompanies.add(companyDao.findById(2l));
			//allCompanies.add(companyDao.findById(40l));
			//allCompanies.add(companyDao.findById(41l));
			for(Company curCompany:allCompanies){
				//get latest persisted date for company
				List<BasicStockData> latestBasicStockData=basicStockDataService.getLatestBasicStockDataForCompany(curCompany.getId(), 1);
				DateTime startDate=null;
				Integer curVersionIndex=1;
				if(latestBasicStockData.size()>0){
					//startDate=latestBasicStockData.get(0).getDateTime();
					curVersionIndex=latestBasicStockData.get(0).getVersionIndex()+1;
				}
				
				List<BasicStockData> basicStockData=basicStockDataDownloader.getLatestDataFromSource( curCompany, startDate, null);
				if(basicStockData==null)
					continue;
					
				for(int i=0;i<basicStockData.size();i++){
					try{
						BasicStockData bsd=basicStockData.get(i);
						bsd.setVersionIndex(curVersionIndex);
						basicStockDataService.saveBasicStockData(bsd);
						
					}catch(Exception exc){
						//exc.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
