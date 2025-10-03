package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
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

@Epic("Epic 10007: Design Opencart Application - Product Page Workflow")
@Story("User Story: Design Product page Test")
@Listeners(TestAllureListener.class)
public class ProductInfoPageTest extends BaseTest {

	@BeforeClass
	public void productInfoPageSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), System.getProperty("password"));
//		String pwd = System.getProperty("password");
//		if (pwd != null) {
//			accPage = loginPage.doLogin(prop.getProperty("username"), System.getProperty("password"));
//		} else {
//			accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
//		}
	}

	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] { { "macbook", "MacBook Pro" }, { "imac", "iMac" },
				{ "samsung", "Samsung SyncMaster 941BW" }, { "samsung", "Samsung Galaxy Tab 10.1" },
				{ "canon", "Canon EOS 5D" } };
	}
	
	@Description("checking product header Test")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("Prakhar Patwa")
	@Link(url="https://www.opencart.com")
	@Feature("Product Page Header Feature")
	@Test(dataProvider = "getProductData")
	public void productHeaderTest(String searchKey, String productName) {
		searchResultPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductHeader(), productName, AppError.HEADER_NOT_FOUND);
	}

	@DataProvider
	public Object[][] getProductImageData() {
		return new Object[][] { { "macbook", "MacBook Pro", 4 }, { "imac", "iMac", 3 },
				{ "samsung", "Samsung SyncMaster 941BW", 1 }, { "samsung", "Samsung Galaxy Tab 10.1", 7 },
				{ "canon", "Canon EOS 5D", 3 } };
	}
	
	@Description("checking product images count Test")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Ethen Hunt")
	@Link(url="https://www.opencart.com")
	@Feature("Product Page Image Count Feature")
	@Test(dataProvider = "getProductImageData")
	public void productImageCountTest(String searchKey, String productName, int imagesCount) {
		searchResultPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultPage.selectProduct(productName);
		int totalImages = productInfoPage.getProductImagesCount();
		Assert.assertEquals(totalImages, imagesCount, AppError.PRODUCT_IMAGE_COUNT_NOT_MATCHED);
	}
	
	@Description("checking product information Test")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("Martin Fowler")
	@Feature("Product Page product Info Feature")
	@Link(url="https://www.opencart.com")
	@Test
	public void productInfoTest() {
		searchResultPage = accPage.doSearch("macbook");
		productInfoPage = searchResultPage.selectProduct("MacBook Pro");
		Map<String, String> productInfoMap = productInfoPage.getProductInfoMap();
		System.out.println("======product information======");
		System.out.println(productInfoMap);
		System.out.println("======product information======");

		softAssert.assertEquals(productInfoMap.get("productname"), "MacBook Pro");
		softAssert.assertEquals(productInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(productInfoMap.get("Availability"), "Out Of Stock");
		softAssert.assertEquals(productInfoMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(productInfoMap.get("Reward Points"), "800");
		softAssert.assertEquals(productInfoMap.get("productprice"), "$2,000.00");
		softAssert.assertEquals(productInfoMap.get("exTaxPrice"), "$2,000.00");
		softAssert.assertEquals(productInfoMap.get("productimagescount"), "4");
		softAssert.assertAll();
		System.out.println("======Test is done======");
	}
}
