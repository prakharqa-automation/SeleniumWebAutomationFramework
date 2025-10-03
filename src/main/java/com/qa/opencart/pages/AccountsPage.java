package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.exceptions.ElementException;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

import io.qameta.allure.Step;

public class AccountsPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1. Page Objects: By Locators:
	private By logoutLink = By.linkText("Logout");
	private By headers = By.cssSelector("div#content h2");
	private By search = By.xpath("//input[@name='search']");
	private By searchIcon = By.cssSelector("div#search button");

	// 2. public constructor of the page
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// 3. Public page actions/methods:
	@Step("Getting account page title")
	public String getAccountPageTitle() {
		String title = eleUtil.waitForTitleToBe(TimeUtil.DEFAULT_MEDIUM_TIME, AppConstants.ACCOUNTS_PAGE_TITLE);
		System.out.println("Account page title:" + title);
		return title;
	}
	
	@Step("Getting account page URL")
	public String getAccountPageURL() {
		String url = eleUtil.waitForURLContains(TimeUtil.DEFAULT_TIME, AppConstants.Account_PAGE_FRACTION_URL);
		System.out.println("Account page url:" + url);
		return url;
	}
	
	@Step("Getting account page logout link status")
	public boolean isLogoutLinkExist() {
		return eleUtil.doIsDisplayed(logoutLink, TimeUtil.DEFAULT_TIME);
	}
	
	@Step("Getting account page headers")
	public List<String> getAccountsPageHeaders() {
		List<WebElement> headersList = eleUtil.waitForVisibilityOfElementsLocatedBy(headers, TimeUtil.DEFAULT_MEDIUM_TIME);
		List<String> headersValueList = new ArrayList<String>();
		for (WebElement e : headersList) {
			String text = e.getText();
			headersValueList.add(text);
		}
		return headersValueList;
	}
	
	@Step("Getting account page search field status")
	public boolean isSearchFieldExist() {
		return eleUtil.doIsDisplayed(search, TimeUtil.DEFAULT_TIME);
	}
	
	@Step("Getting account page search results")
	public SearchResultPage doSearch(String searchkey) {
		System.out.println("searching for product : " + searchkey);
		if (isSearchFieldExist()) {
			eleUtil.doSendKeys(search, searchkey);
			eleUtil.doClick(searchIcon);
			return new SearchResultPage(driver);
		} else {
			System.out.println("search field is not present on the page");
			throw new ElementException("SEARCH FIELD IS NOT VISIBLE/PRESENT..." + searchIcon);
		}
	}
}
