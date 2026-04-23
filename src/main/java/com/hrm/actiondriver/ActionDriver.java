package com.hrm.actiondriver;

import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.hrm.base.BaseClass;
import com.hrm.utilities.LoggerManager;

public class ActionDriver {

	private WebDriver driver;
	private WebDriverWait wait;
	public static final Logger logger = BaseClass.logger;

	public ActionDriver(WebDriver driver) {
		this.driver = driver;
		int explicitWait = Integer.parseInt(BaseClass.getProp().getProperty("ExplicitWait"));
		new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
	}

	// Method to click
	public void click(By by) {
		try {
			waitForElementToBeClickable(by);
			driver.findElement(by).click();
			logger.info("clicked an element");
		} catch (Exception e) {
			logger.error("Unable to click element: " + e.getMessage());
		}
	}

	// Method to enter text
	public void enterText(By by, String text) {
		try {
			waitForElementToBeVisible(by);
			WebElement element = driver.findElement(by);
			element.clear();
			element.sendKeys(text);
			logger.info("Entered text on"+getElementDescription(by) +" "+ text);
		} catch (Exception e) {
			logger.error("Unable to enter the text: " + e.getMessage());
		}
	}

	// Method to get text
	public String getText(By by) {
		try {
			waitForElementToBeVisible(by);
			WebElement element = driver.findElement(by);
			return element.getText();
		} catch (Exception e) {
			logger.error("Unable to fetch the text: " + e.getMessage());
			return null;
		}
	}

	public boolean comapreText(By by, String expectedText) {
		waitForElementToBeVisible(by);
		WebElement element = driver.findElement(by);
		String actualText = element.getText();
		if (actualText.equals(expectedText))
			return true;
		else
			return false;
	}

	// Method to enter text
	public boolean isDisplayed(By by) {
		try {
			waitForElementToBeVisible(by);
			logger.info("Element is displayed"+ getElementDescription(by));
			return driver.findElement(by).isDisplayed();
		} catch (Exception e) {
			logger.error("Element is not displayed: " + e.getMessage());
			return false;
		}
	}

	// wait for elements to be clickable
	private void waitForElementToBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			logger.error("Element is not clickable: " + e.getMessage());
		}
	}

	// wait for elements to be visible
	private void waitForElementToBeVisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
		} catch (Exception e) {
			logger.error("Element is not visible: " + e.getMessage());
		}
	}

	//Method to get web element description
	public String getElementDescription(By locator) {
		//Check for null driver or locator to avoid null pointer exception
		if(driver==null)
			return "driver is null";
		if(locator==null)
			return "locator is null";
		
		try {
			WebElement webElement = driver.findElement(locator);
			String name = webElement.getDomAttribute("name");
			String id = webElement.getDomAttribute("id");
			String text = webElement.getText();
			String className = webElement.getDomAttribute("class");
			String placeHolder = webElement.getDomAttribute("placeholder");
			
			//return description based on element attributes
			if(isNotEmpty(name))
				return "Element with name:"+name;
			else if(isNotEmpty(id))
				return "Element with id:"+id;
			else if(isNotEmpty(text))
				return "Element with text:"+truncate(text, 50);
			else if(isNotEmpty(className))
				return "Element with class:"+className;
			else if(isNotEmpty(placeHolder))
				return "Element with placeHolder:"+placeHolder;
		} catch (Exception e) {
			logger.error("Unable to describe the element", e);
		}
		return "Unable to describe the element";			
	}
	
	//utility method to check a string is null or not
	private boolean isNotEmpty(String value) {
		return value!=null && !value.isEmpty();
	}
	
	//utility method to truncate long string
	private String truncate(String value, int maxLength) {
		if(value==null || value.length()<=maxLength) {
			return value;
		}
		return value.substring(0,maxLength)+"...";
	}
}
