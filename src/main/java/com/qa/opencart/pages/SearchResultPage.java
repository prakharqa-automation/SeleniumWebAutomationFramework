package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class SearchResultPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1. Page Objects: By Locators:
	private By searchResult = By.xpath("//div[@class='product-thumb']");

	// 2. public constructor of the page
	public SearchResultPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public int getSearchResultCount() {
		 List<WebElement> resultList = eleUtil.waitForVisibilityOfElementsLocatedBy(searchResult, TimeUtil.DEFAULT_MEDIUM_TIME);
		 System.out.println("product search result count = "+resultList.size());
		 return resultList.size();
	}
	
	public ProductInfoPage selectProduct(String productName) {
		eleUtil.doClick(By.linkText(productName), TimeUtil.DEFAULT_TIME);
		return new ProductInfoPage(driver);
	}
}
