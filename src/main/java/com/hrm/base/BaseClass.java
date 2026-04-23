package com.hrm.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import com.hrm.actiondriver.ActionDriver;
import com.hrm.utilities.LoggerManager;

public class BaseClass {

	protected static Properties prop;
	protected static WebDriver driver;
	private static ActionDriver actionDriver;
	public static final Logger logger = LoggerManager.getLogger(BaseClass.class);

	// Getter and Setter
	public static WebDriver getDriver() {
		if (driver == null) {
			System.out.println("WebDriver is not initialized");
			throw new IllegalStateException("WebDriver is not initialized");
		}

		return driver;
	}

	public static ActionDriver getActionDriver() {
		if (actionDriver == null) {
			System.out.println("actionDriver is not initialized");
			throw new IllegalStateException("actionDriver is not initialized");
		}
		return actionDriver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public static Properties getProp() {
		return prop;
	}

	@BeforeSuite
	public void loadConfig() throws IOException {
		// Load config file
		prop = new Properties();
		FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
		prop.load(fis);
		logger.info("config.properties file loaded");
	}

	@BeforeMethod
	public void setup() throws IOException, IllegalAccessException {
		System.out.println("Setting up webdriver for :" + this.getClass().getSimpleName());
		launchBrowser();
		configureBrowser();
		staticWait(2);
		logger.info("WebDriver initialized and browser maximized");
		// initialize actionDriver once
		if (actionDriver == null) {
			actionDriver = new ActionDriver(driver);
		}
	}

	// initialize the webdriver based on browser defined on config.properties file
	private void launchBrowser() throws IllegalAccessException {
		String browser = prop.getProperty("browser");
		
		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
			logger.info("ChromeDriver instance is created");
		} else if (browser.equalsIgnoreCase("chrome")) {
			driver = new FirefoxDriver();
			logger.info("FirefoxDriver instance is created");
		} else if (browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
			logger.info("EdgeDriver instance is created");
		} else {
			throw new IllegalAccessException("Browser not supported");
		}

	}

	// browser settings such as wait, maximize browser, navigate to url
	private void configureBrowser() {
		// Implicit Wait
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

		// maximize the driver
		driver.manage().window().maximize();

		// Navigate to the url
		try {
			driver.get(prop.getProperty("url"));
		} catch (Exception e) {
			System.out.println("Failed to navigate to url:" + e.getMessage());
		}
	}

	// static wait for pause
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			try {
				driver.quit();
			} catch (Exception e) {
				System.out.println("Unable to quit the browser : " + e.getMessage());
			}
		}
		logger.info("Driver instance is closed");
		driver = null;
		actionDriver = null;
	}
}
