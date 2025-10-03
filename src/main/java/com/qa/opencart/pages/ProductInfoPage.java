package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

import io.qameta.allure.Step;

public class ProductInfoPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By productHeader = By.cssSelector("div#content h1");
	private By productImagesCount = By.cssSelector("div#content a.thumbnail");
	private By productMetadata = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPricingData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	
	private Map<String, String> productMap;
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	@Step("Getting product page header")
	public String getProductHeader() {
		String header = eleUtil.doGetText(productHeader);
		//System.out.println("product header === "+header);
		return header;
	}
	
	@Step("Getting product images count")
	public int getProductImagesCount() {
		int imagesCount = eleUtil.waitForVisibilityOfElementsLocatedBy(productImagesCount, TimeUtil.DEFAULT_MEDIUM_TIME).size();
		//System.out.println("Total number of images: "+imagesCount);
		return imagesCount;
	}
	
	@Step("Getting product information map")
	public Map<String, String> getProductInfoMap() {
		productMap = new HashMap<String, String>();
		//productMap = new LinkedHashMap<String, String>();
		//productMap = new TreeMap<String, String>();
		productMap.put("productname", getProductHeader());
		productMap.put("productimagescount", String.valueOf(getProductImagesCount()));
		getProductMetaData();
		getProductPriceData();
		return productMap;
	}
	
	private void getProductMetaData() {
		List<WebElement> metaList = eleUtil.waitForVisibilityOfElementsLocatedBy(productMetadata, TimeUtil.DEFAULT_MEDIUM_TIME);
		for(WebElement e: metaList) {
			String metaData = e.getText();
			String[] meta = metaData.split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productMap.put(metaKey, metaValue);
		}
	}
	
	private void getProductPriceData() {
		List<WebElement> priceList = eleUtil.waitForVisibilityOfElementsLocatedBy(productPricingData, TimeUtil.DEFAULT_MEDIUM_TIME);
		String productprice =  priceList.get(0).getText();
		String exPrice = priceList.get(1).getText().split(":")[1].trim();
		productMap.put("productprice", productprice);
		productMap.put("exTaxPrice", exPrice);
	}
	
}
