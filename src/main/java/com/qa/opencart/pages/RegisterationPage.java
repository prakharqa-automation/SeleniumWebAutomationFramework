package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

import io.qameta.allure.Step;

public class RegisterationPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1. Page Objects: By Locators:
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By emailId = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	
	private By subcribeYes = By.xpath("//input[@name='newsletter' and @value='1']");
	private By subcribeNo = By.xpath("//input[@name='newsletter' and @value='0']");
	
	private By agreeCheckBox = By.name("agree");
	private By continueButton = By.xpath("//input[@value='Continue']");
	
	private By successMessage = By.cssSelector("div#content h1");
	private By logoutLink = By.linkText("Logout");
	private By registertLink = By.linkText("Register");
	
	
	// 2. public constructor of the page
	public RegisterationPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// 3. Public page actions/methods:
	@Step("Registering the user")
	public boolean userRegister(String firstname, String lastname, String email, String telephone, String password, String subscribe) {
		eleUtil.doSendKeys(this.firstName, firstname, TimeUtil.DEFAULT_LONG_TIME);
		eleUtil.doSendKeys(this.lastName, lastname);
		eleUtil.doSendKeys(this.emailId, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, password);
		
		if(subscribe.equals("yes")) {
			eleUtil.doClick(subcribeYes);
		}else {
			eleUtil.doClick(subcribeNo);
		}
		
		
		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueButton, TimeUtil.DEFAULT_LONG_TIME);
		
		String successPromptMessage = eleUtil.doGetText(successMessage, TimeUtil.DEFAULT_LONG_TIME);
		System.out.println(successPromptMessage);
		
		if(successPromptMessage.contains(AppConstants.USE_REGISTER_SUCCESS_MESSAGE)) {
			eleUtil.doClick(logoutLink, TimeUtil.DEFAULT_LONG_TIME);
			eleUtil.doClick(registertLink, TimeUtil.DEFAULT_MEDIUM_TIME);
			return true;
		}else {
			return false;
		}
	}
}
