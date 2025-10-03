package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.listeners.TestAllureListener;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 10004: Design Opencart Application - Login Page Workflow")
@Story("User Story: Design Login Page Test")
@Listeners(TestAllureListener.class)
public class LoginPageTest extends BaseTest {
	
	@Description("checking login Page Title Test")
	@Severity(SeverityLevel.MINOR)
	@Owner("Peter Pan")
	@Link(url="https://www.opencart.com")
	@Feature("Login Page Title Feature")
	@Test(priority = 1)
	public void loginPageTitleTest() {
		String actualTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
	}
	
	@Description("checking login Page URL Test")
	@Severity(SeverityLevel.MINOR)
	@Owner("Prakhar Patwa")
	@Link(url="https://www.opencart.com")
	@Feature("Login Page URL Feature")
	@Test(priority = 2)
	public void urlPageTitleTest() {
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL), AppError.URL_NOT_FOUND);
	}
	
	@Description("checking login Page forgot password link exist Test")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("John Doe")
	@Link(url="https://www.opencart.com")
	@Feature("Login Page Forgot Link Feature")
	@Test(priority = 3)
	public void forgotPasswordLinkExistTest() {
		Assert.assertTrue(loginPage.checkForgowPwdLinkExist(), AppError.ELEMENT_NOT_FOUND);
	}
	
	@Description("checking login in to the application with username and paasword Test")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("Prakhar Patwa")
	@Link(url="https://www.opencart.com")
	@Feature("Login Page Sign-in Feature")
	@Test(priority = 4)
	public void loginPageTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), System.getProperty("password"));
//		String pwd = System.getProperty("password");
//		if (pwd != null) {
//			accPage = loginPage.doLogin(prop.getProperty("username"), System.getProperty("password"));
//		} else {
//			accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
//		}
		Assert.assertEquals(accPage.getAccountPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
	}

}
