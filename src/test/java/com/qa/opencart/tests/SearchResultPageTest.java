package com.qa.opencart.tests;

import org.testng.annotations.BeforeClass;

import com.qa.opencart.base.BaseTest;

public class SearchResultPageTest extends BaseTest{
	@BeforeClass
	public void searchPageSetUp() {
		//accPage = loginPage.doLogin(prop.getProperty("username"), System.getProperty("password"));
		String pwd = System.getProperty("password");
		if (pwd != null) {
			accPage = loginPage.doLogin(prop.getProperty("username"), System.getProperty("password"));
		} else {
			accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		}
	}
}
