package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.listeners.TestAllureListener;
import com.qa.opencart.utils.CSVUtil;
import com.qa.opencart.utils.ExcelUtil2;
import com.qa.opencart.utils.StringUtils;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

@Listeners(TestAllureListener.class)
public class RegisterationPageTest extends BaseTest {
	
	@BeforeClass
	public void registrationSetUp() {
		regPage = loginPage.navigateToRegisterPage();
	}
	
	/************ Normal Data Providing *************/
	@DataProvider
	public Object[][] userRegTestData() {
		return new Object[][] {
			{"Peter", "Parker", "9876543219", "selenium12345", "yes"},
			{"Peter", "Parker", "9876543219", "selenium12345", "no"},
			{"Prakhar", "Parker", "9876543219", "selenium12345", "yes"},
			{"Madhu", "automation", "9876543219", "selenium12345", "no"}
		};
	}
	
	@Description("checking User Registration Test using normal data provider")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Prakhar Patwa")
	@Link(url="https://www.opencart.com")
	@Feature("Registration Page For New User Feature")
	@Test(dataProvider = "userRegTestData", enabled = false)
	public void userRegistrationTest(String firstname, String lastname, String telephone, String password, String subscribe) {
		boolean flag = regPage.userRegister(firstname, lastname, StringUtils.getRandomEmailId(), telephone, password, subscribe);
		Assert.assertTrue(flag, AppError.REGISTRATION_NOT_DONE);
	}
	
	
	/************ Excel Data Providing *************/
	@DataProvider
	public Object[][] userRegTestDataFromSheet() {
		return ExcelUtil2.getTestData(AppConstants.REGISTER_SHEET_NAME);
	}
	
	@Description("checking User Registration Test using excel data provider")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Prakhar Patwa")
	@Link(url="https://www.opencart.com")
	@Feature("Registration Page For New User using Excel Data Set Feature")
	@Test(dataProvider="userRegTestDataFromSheet", enabled=false)
	public void userRegistrationExcelTest(String firstname, String lastname, String telephone, String password, String subscribe) {
		boolean flag = regPage.userRegister(firstname, lastname, StringUtils.getRandomEmailId(), telephone, password, subscribe);
		Assert.assertTrue(flag, AppError.REGISTRATION_NOT_DONE);
	}
	
	/************ CSV Data Providing *************/
	@DataProvider
	public Object[][] userRegTestDataFromCSV() {
		return CSVUtil.csvData(AppConstants.REGISTER_SHEET_NAME);
	}
	
	@Description("checking User Registration Test using CSV data provider")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Prakhar Patwa")
	@Link(url="https://www.opencart.com")
	@Feature("Registration Page For New User using CSV Data Set Feature")
	@Test(dataProvider="userRegTestDataFromCSV", enabled=true)
	public void userRegistrationCSVTest(String firstname, String lastname, String telephone, String password, String subscribe) {
		boolean flag = regPage.userRegister(firstname, lastname, StringUtils.getRandomEmailId(), telephone, password, subscribe);
		Assert.assertTrue(flag, AppError.REGISTRATION_NOT_DONE);
	}
	
}
