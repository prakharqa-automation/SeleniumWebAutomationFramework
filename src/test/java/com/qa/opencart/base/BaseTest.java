package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterationPage;
import com.qa.opencart.pages.SearchResultPage;

import io.qameta.allure.Step;

public class BaseTest {

	DriverFactory df;
	WebDriver driver;
	protected Properties prop;
	protected LoginPage loginPage;
	protected AccountsPage accPage;
	protected SearchResultPage searchResultPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterationPage regPage;

	protected SoftAssert softAssert;
	
	@Step("Setup for the test, initializing the browser: {0}")
	@Parameters({ "browser" })
	@BeforeTest
	public void setUp(@Optional("chrome") String browserName) {
		df = new DriverFactory();
		prop = df.initProp();
		
		if(browserName != null) {
			prop.setProperty("browser", browserName);
		}
		else {
			prop.getProperty(browserName);
		}
		
		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);
		softAssert = new SoftAssert();

	}
	
	@Step("Browser closing...")
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
