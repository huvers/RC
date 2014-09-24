package com.hbar.finance.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hbar.finance.model.BasicStockData;
import com.hbar.finance.model.Company;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
@Transactional
@Ignore
public class CompanyUploader {

	@Autowired
	CompanyDao companyDao;

	@Test
	@Rollback(false)
	public void uploadDataFromFile() {
		try {
			File file = new File(
					"C:\\Users\\geemein80\\Desktop\\company_list.csv");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
			List<BasicStockData> basicStockData = new ArrayList<BasicStockData>();
			BigDecimal bdDivisor = new BigDecimal(10);
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				String[] columns = line.split(",");
				Company curCompany = null;
				try{
					curCompany = companyDao.findBySymbol(columns[0]);
					continue;
				}catch(NoResultException nre){
					
				}
				Company company = new Company();
				company.setName(columns[1]);
				company.setSymbol(columns[0]);
				companyDao.persist(company);

			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}
