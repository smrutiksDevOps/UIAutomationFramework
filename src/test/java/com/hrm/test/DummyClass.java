package com.hrm.test;

import org.testng.annotations.Test;

import com.hrm.base.BaseClass;

public class DummyClass extends BaseClass{
	
	@Test
	public void dummyTest() {
		
		String title = driver.getTitle();
		assert title.equals("OrangeHRM") : "Test Failed due to title is not matching";
		
		System.out.println("Test Passed");
	}

}
