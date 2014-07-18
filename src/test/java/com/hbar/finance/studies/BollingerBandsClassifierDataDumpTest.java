package com.hbar.finance.studies;

import java.io.FileNotFoundException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hbar.finance.studies.BollingerBandsStudies;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/applicationContext.xml"})
@Transactional
@Ignore
public class BollingerBandsClassifierDataDumpTest {
	@Autowired BollingerBandsStudies bollingerBandsStudies;
	
	public void setBollingerBands(BollingerBandsStudies bollingerBandsStudies){
		this.bollingerBandsStudies=bollingerBandsStudies;
	}
	
	@Test
	public void testBollingerBands(){
		try {
			bollingerBandsStudies.dumpDataForAllCompanies();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
