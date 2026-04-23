package com.hrm.test;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hrm.actiondriver.ActionDriver;
import com.hrm.base.BaseClass;
import com.hrm.pages.HomePage;
import com.hrm.pages.LoginPage;

public class LoginPageTest extends BaseClass{
	
	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setUpPages() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}
	
	
	
	 @Test
	public void verifyValidateLoginTest() {
		loginPage.login("admin", "admin123");
		Assert.assertTrue(homePage.isAdminTabVisible(),"Admin tab should be visible");
		homePage.logOut();
		staticWait(3);
	}
	
	@Test
	public void invalidLoginTest() {
		loginPage.login("admin", "admin");
		loginPage.isErrorMessageDisplayed();
		staticWait(3);
	}

}
