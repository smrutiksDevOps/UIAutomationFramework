package com.hrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.hrm.actiondriver.ActionDriver;
import com.hrm.base.BaseClass;

public class HomePage {
	private ActionDriver actionDriver;

	// Define locators using By class
	private By adminTab = By.xpath("//span[text()='Admin']");
	private By userIDButton = By.className("oxd-userdropdown-name");
	private By logoutButton = By.xpath("//a[text()='Logout']");
	private By hrmLogo = By.xpath("//div[@class='oxd-brand-banner']");

	/*public HomePage(WebDriver driver) {
		actionDriver = new ActionDriver(driver);
	}*/
	
	public HomePage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}

	public boolean isAdminTabVisible() {
		return actionDriver.isDisplayed(adminTab);
	}

	public boolean verifyOrangeHRMLogo() {
		return actionDriver.isDisplayed(hrmLogo);
	}

	public void logOut() {
		actionDriver.click(userIDButton);
		actionDriver.click(logoutButton);
	}
}
