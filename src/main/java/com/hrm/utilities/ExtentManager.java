package com.hrm.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	
	private static ExtentReports extentReport;
	
	
	public static ExtentReports getReporte() {
		
		if(extentReport==null) {
			String reportPath = System.getProperty("user.dir") + "/src/test/resources/ExtentReport/ExtentReport.html";
			ExtentHtmlReporter spark = new ExtentHtmlReporter(reportPath);
			spark.config().setReportName("Automation Test Report");
			spark.config().setDocumentTitle("OrangeHRM Report");
			spark.config().setTheme(Theme.DARK);
			
			extentReport = new ExtentReports();
			//Adding system information
			extentReport.setSystemInfo("Operating System is : ", System.getProperty("os.name"));
			extentReport.setSystemInfo("JAVA version : ", System.getProperty("java.version"));
			extentReport.setSystemInfo("User details : ", System.getProperty("user.name"));
		}
		return extentReport;
	}
	
	//start the Test
	public static ExtentTest startTest(String testName) {
		ExtentTest extentTest =getReporte().createTest(testName);
		//test.set(extentTest);
		return extentTest;
	}

}
