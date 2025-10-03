package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1. Page Objects: By Locators:
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");

	// 2. public constructor of the page
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//3. Public page actions/methods:
	@Step("Getting login page title")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleToBe(TimeUtil.DEFAULT_TIME,AppConstants.LOGIN_PAGE_TITLE);
		System.out.println("Login page title:"+title);
		return title;
	}
	
	@Step("Getting login page URL")
	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContains(TimeUtil.DEFAULT_TIME, AppConstants.LOGIN_PAGE_FRACTION_URL);
		System.out.println("Login page url:"+url);
		return url;
	}
	
	@Step("Getting forgot link status")
	public boolean checkForgowPwdLinkExist() {
		return eleUtil.doIsDisplayed(forgotPwdLink);
	}
	
	@Step("Sign-in to the login page with username: {0}")
	public AccountsPage doLogin(String username, String pwd){
		eleUtil.doSendKeys(emailId, username, TimeUtil.DEFAULT_MEDIUM_TIME);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn, TimeUtil.DEFAULT_TIME);
		return new AccountsPage(driver);
	}
	
	@Step("Getting registration for the user")
	public RegisterationPage navigateToRegisterPage() {
		eleUtil.doClick(registerLink, TimeUtil.DEFAULT_MEDIUM_TIME);
		return new RegisterationPage(driver);
	}

}
