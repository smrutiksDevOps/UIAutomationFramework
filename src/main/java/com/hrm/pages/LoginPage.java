package com.hrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.hrm.actiondriver.ActionDriver;
import com.hrm.base.BaseClass;

public class LoginPage {
	private ActionDriver actionDriver;
	
	/*public LoginPage(WebDriver driver) {
		actionDriver = new ActionDriver(driver);
	}*/
	
	public LoginPage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}
	
	//Define locators using By class
	
	private By userNameField= By.name("username");
	private By passwordField= By.cssSelector("input[type='password']");
	private By loginButton= By.xpath("//button[text()=' Login ']");
	private By errorMessage= By.xpath("//p[text()='Invalid credentials']");
	
	//Method to perform login
	public void login(String username, String password)
	{
		actionDriver.enterText(userNameField, username);
		actionDriver.enterText(passwordField, password);
		actionDriver.click(loginButton);
	}
	
	//Method to check error message
	public boolean isErrorMessageDisplayed()
	{
		return actionDriver.isDisplayed(errorMessage);
	}
	
	public String getErrorMessage() {
		return actionDriver.getText(errorMessage);
	}
	
	public void verifyErrorMessage(String expectedError) {
		actionDriver.comapreText(errorMessage, expectedError);
	}
}
