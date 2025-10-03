package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
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

@Epic("Epic 10006: Design Opencart Application - Account Page Workflow")
@Story("User Story: Design Account's Page Test")
@Listeners(TestAllureListener.class)
public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void accSetUp() {
		//accPage = loginPage.doLogin(prop.getProperty("username"), System.getProperty("password"));
		String pwd = System.getProperty("password");
		if (pwd != null) {
			accPage = loginPage.doLogin(prop.getProperty("username"), System.getProperty("password"));
		} else {
			accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		}
	}
	
	@Description("Checking account page title test")
	@Severity(SeverityLevel.MINOR)
	@Owner("Prakhar Patwa")
	@Link(url="https://www.opencart.com")
	@Feature("Account Page Title Feature")
	@Test
	public void accPageTitleTest() {
		Assert.assertEquals(accPage.getAccountPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
	}
	
	@Description("Checking account page URL test")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("Prakhar Patwa")
	@Link(url="https://www.opencart.com")
	@Feature("Account Page URL Feature")
	@Test
	public void accPageURLTest() {
		Assert.assertTrue(accPage.getAccountPageURL().contains(AppConstants.Account_PAGE_FRACTION_URL),
				AppError.URL_NOT_FOUND);
	}
	
	@Description("Checking account page header test")
	@Severity(SeverityLevel.MINOR)
	@Owner("Peter Parker")
	@Link(url="https://www.opencart.com")
	@Feature("Account Page Header Feature")
	@Test
	public void accountPageHeaderTest() {
		List<String> accPageHeadersList = accPage.getAccountsPageHeaders();
		Assert.assertEquals(accPageHeadersList, AppConstants.ACCOUNTS_PAGE_HEADERS_LIST, AppError.LIST_IS_NOT_MATCHED);
	}

	@DataProvider
	public Object[][] getSearchData() {
		return new Object[][] { { "macbook", 3 }, { "imac", 1 }, { "samsung", 2 }, { "dyson", 0 } };
	}
	
	@Description("Checking account page product count search test")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Prakhar Patwa")
	@Link(url="https://www.opencart.com")
	@Feature("Account Page Search Feature")
	@Test(dataProvider = "getSearchData")
	public void searchTest(String searchKey, int expectedCount) {
		searchResultPage = accPage.doSearch(searchKey);
		Assert.assertEquals(searchResultPage.getSearchResultCount(), expectedCount, AppError.RESULT_COUNT_MISMATCHED);
	}

}
