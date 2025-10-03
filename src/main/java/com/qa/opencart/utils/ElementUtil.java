package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.ElementException;
import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.exceptions.NonWebElementException;
import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

/**
 *
 * @author Prakhar Patwa
 *
 */
public class ElementUtil {

	private WebDriver driver;
	private JavaScriptUtil jsUtil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}
	
	private void highlightTheElement(WebElement element) {
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
	}
	
	private void nullCheck(String value) {
		if (value == null) {
			throw new FrameworkException("VALUE IS NULL...");
		}
	}
	
	@Step("finding the element using locator: {0}")
	public WebElement getElement(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			
			highlightTheElement(element);
			
			return element;
		} catch (ElementException e) {
			System.out.println("ELEMENT IS NOT PRESENT ON THE PAGE..." + locator);
			throw new ElementException("ELEMENT IS NOT PRESENT ON THE PAGE..." + locator);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			throw new ElementException("ELEMENT IS NOT PRESENT ON THE PAGE..." + locator);
		}
//		catch(org.openqa.selenium.TimeoutException e) {
//			throw new ElementException("TIME OUT EXCEPTION..."+locator);
//		}
	}
	
	@Step("finding the element using locator: {0} and waiting for {1} seconds")
	public WebElement getElement(By locator, int timeOut) {
		try {
			WebElement element = waitForElementVisible(locator, timeOut);
			highlightTheElement(element);
			return element;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			throw new ElementException("ELEMENT IS NOT PRESENT ON THE PAGE..." + locator);
		} catch (org.openqa.selenium.TimeoutException e) {
			throw new ElementException("TIME OUT EXCEPTION..." + locator);
		}
	}
	
	@Step("Enetring value: {1} in text field using locator: {0}")
	public void doSendKeys(By locator, String value) {
		nullCheck(value);
		getElement(locator).clear();
		getElement(locator).sendKeys(value);
	}
	
	@Step("Enetring value: {1} in text field using locator: {0}")
	public void doSendKeys(By locator, CharSequence... value) {
		getElement(locator).clear();
		getElement(locator).sendKeys(value);
	}
	
	@Step("Enetring value: {1} in text field using locator: {0} and waiting for element with {2} seconds")
	public void doSendKeys(By locator, String value, int timeout) {
		nullCheck(value);
		try {
			getElement(locator).clear();
			waitForElementVisible(locator, timeout).sendKeys(value);
		} catch (TimeoutException e) {
			System.out.println("Certain element not found..." + locator);
			throw new ElementException(AppError.ELEMENT_TIMEOUT_EXCEPTION);
		}
	}
	
	
	@Step("Clicking on the element: {0}")
	public void doClick(By locator) {
		getElement(locator).click();
	}
	
	@Step("Clicking on the element: {0} and waiting for {1} seconds")
	public void doClick(By locator, int timeout) {
		waitForElementVisible(locator, timeout).click();
	}
	
	@Step("Getting the text for element: {0}")
	public String doGetText(By locator) {
		return getElement(locator).getText();
	}
	
	@Step("Getting the text for element: {0} and waiting for {1} seconds")
	public String doGetText(By locator, int timeOut) {
		try {
			return waitForElementVisible(locator, timeOut).getText();
		}catch (StaleElementReferenceException e) {
			throw new ElementException("STALE ELEMENT EXCEPTION..."+locator);
		}catch(org.openqa.selenium.NoSuchElementException e) {
			throw new ElementException("ELEMENT NOT FOUND EXCEPTION..."+locator);
		}
	}
	
	@Step("Getting the attribute vale: {1} for element: {0}")
	public String doGetAttribute(By locator, String attributeName) {
		return getElement(locator).getAttribute(attributeName);
	}

	/**
	 * An expectation for checking that the title contains a case-sensitive
	 * substring
	 * 
	 * @param timeout
	 * @param titleFraction
	 * @return String
	 */
	@Step("Getting the fractional title value: {1} and waiting for: {0} seconds")
	public String waitForTitleContains(int timeout, String titleFraction) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
				System.out.println("Title Matched");
				return driver.getTitle();
			}
		} catch (org.openqa.selenium.TimeoutException e) {
			throw new NonWebElementException("TITLE NOT FOUND UNDER CERTAIN TIME LIMIT..." + titleFraction);
		}
		return driver.getTitle();
	}

	/**
	 * The expected title, which must be an exact match
	 * 
	 * @param timeout
	 * @param title
	 * @return String
	 */
	@Step("Waiting for the title: {1} for {0} seconds to capturing it")
	public String waitForTitleToBe(int timeout, String title) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			if (wait.until(ExpectedConditions.titleIs(title))) {
				System.out.println("Title Matched");
				return driver.getTitle();
			}
		} catch (org.openqa.selenium.TimeoutException e) {
			throw new NonWebElementException("TITLE NOT FOUND UNDER CERTAIN TIME LIMIT..." + title);
		}
		return driver.getTitle();
	}

	/**
	 * An expectation for the URL of the current page to contain specific text.
	 * 
	 * @param timeout
	 * @param URLFraction
	 * @return String
	 */
	@Step("Waiting for the fractional URL: {1} for {0} seconds to capturing it")
	public String waitForURLContains(int timeout, String URLFraction) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			if (wait.until(ExpectedConditions.urlContains(URLFraction))) {
				System.out.println("Current URL Matched");
				return driver.getCurrentUrl();
			}
		} catch (org.openqa.selenium.TimeoutException e) {
			throw new NonWebElementException("URL NOT FOUND UNDER CERTAIN TIME LIMIT..." + URLFraction);
		}
		return driver.getCurrentUrl();
	}

	/**
	 * Expectation for the URL to match a specific regular expression
	 * 
	 * @param timeout
	 * @param URL
	 * @return String
	 */
	@Step("Waiting for the URL: {1} for {0} seconds to capturing it")
	public String waitForURLToBe(int timeout, String URL) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			if (wait.until(ExpectedConditions.urlMatches(URL))) {
				System.out.println("Current URL Matched");
				return driver.getCurrentUrl();
			}
		} catch (org.openqa.selenium.TimeoutException e) {
			throw new NonWebElementException("URL NOT FOUND UNDER CERTAIN TIME LIMIT..." + URL);
		}
		return driver.getCurrentUrl();
	}
	
	@Step("getting the list of all the webelements using locator: {0}")
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page..
	 * 
	 * @param locator
	 * @param timeout
	 * @return List<WebElement>
	 */
	@Step("getting the list of all the webelements using locator: {0} and waiting for {1} seconds")
	public List<WebElement> waitForPresenceOfElementsLocatedBy(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a height and width that is greater than 0.
	 * @param locator
	 * @param timeout
	 * @return List<WebElement>
	 */
	@Step("getting the list of all the webelements with visibility using locator: {0} and waiting for {1} seconds")
	public List<WebElement> waitForVisibilityOfElementsLocatedBy(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}catch(Exception e) {
			return List.of();//return the empty array list.
		}
	}
	
	@Step("getting the webelements count using locator: {0}")
	public int getElementsCount(By locator) {
		return driver.findElements(locator).size();
	}
	
	@Step("getting the webelements list using locator: {0}")
	public List<String> getElementsTextList(By locator) {
		List<WebElement> elementList = getElements(locator);
		List<String> elementTextList = new ArrayList<String>();
		for (WebElement e : elementList) {
			String text = e.getText();
			if (text.length() != 0 || !text.isBlank() || !text.isEmpty()) {
				elementTextList.add(text);
				System.out.println(text);
			}
		}
		return elementTextList;
	}

	public List<String> getElementAttributeListNotNull(By locator, String attributeName) {
		List<WebElement> attributeList = getElements(locator);
		List<String> attributeListValue = new ArrayList<String>();
		for (WebElement e : attributeList) {
			String attributeValue = e.getAttribute(attributeName);
			if (attributeValue != null && !attributeValue.isEmpty() && !attributeValue.isBlank()) {
				System.out.println(attributeValue);
				attributeListValue.add(attributeValue);
			}
		}
		return attributeListValue;
	}

	/*********************** Select drop down ***********************/
	public void doSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectByVisibleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}

	public void doSelectByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	/**
	 * This method select the option without using inbuilt select methods ex.
	 * ByIndex, ByVisibleText and ByValue
	 * 
	 * @param locator
	 * @param value
	 */
	public void selectValueFromDropDown(By locator, String value) {
		Select select = new Select(getElement(locator));
		List<WebElement> ls = select.getOptions();
		boolean flag = false;
		for (WebElement e : ls) {
			String text = e.getText();
			if (text.equals(value.trim())) {
				e.click();
				flag = true;
				break;
			}
		}
		if (!flag) {
			System.out.println("Couldn't find the value: " + value);
		}
	}

	/**
	 * This method select the drop down options without using select class.
	 * 
	 * @param locator
	 * @param value
	 */
	public void selectValueFromDropDownWithoutSelect(By locator, String value) {
		List<WebElement> ls = getElements(locator);
		boolean flag = false;
		for (WebElement e : ls) {
			String text = e.getText();
			if (text.equals(value.trim())) {
				e.click();
				flag = true;
				break;
			}
		}
		if (!flag) {
			System.out.println("Couldn't find the value: " + value);
		}
	}

	public List<String> getDropDownOptionsTextList(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsText = select.getOptions();
		List<String> textsList = new ArrayList<String>();
		for (WebElement e : optionsText) {
			String text = e.getText();
			textsList.add(text);
			// System.out.println(text);
		}
		return textsList;
	}

	public int getDropDownOptionsSize(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsCount = select.getOptions();
		return optionsCount.size();
	}

	/*************************** Display Method *************************/
	@Step("checking the state of locator: {0} is displayed or not")
	public boolean doIsDisplayed(By locator) {
		try {
			boolean flag = getElement(locator).isDisplayed();
			System.out.println("ELEMENT WITH LOCATOR " + locator + " IS DISPLAYED ON THE PAGE");
			return flag;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			throw new NoSuchElementException("ELEMENT WITH LOCATOR " + locator + " IS NOT DISPLAYED ON THE PAGE");
		}
	}

	public boolean doIsDisplayed(By locator, int timeOut) {
		try {
			boolean flag = getElement(locator, timeOut).isDisplayed();
			System.out.println("ELEMENT WITH LOCATOR " + locator + " IS DISPLAYED ON THE PAGE");
			return flag;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			throw new NoSuchElementException("ELEMENT WITH LOCATOR " + locator + " IS NOT DISPLAYED ON THE PAGE");
		}
	}

	/**
	 * This method check if the element is present only once or multiple times/no
	 * element
	 * 
	 * @param locator
	 * @return boolean
	 */
	public boolean isElementDisplayedCount(By locator) {
		int size = getElementsCount(locator);
		if (size == 1) {
			System.out.println("Single element is displayed: " + locator);
			return true;
		} else {
			throw new ElementException("Multiple or zero element is displayed for locator: " + locator);
//			System.out.println("Multiple or no element is displayed: "+locator);
//			return false;
		}
	}

	/**
	 * This method check the count/size of element is present or more than
	 * expected/no element
	 * 
	 * @param locator
	 * @return boolean
	 */
	public boolean isElementDisplayedCount(By locator, int expectedElementCount) {
		int size = getElementsCount(locator);
		if (size == expectedElementCount) {
			System.out.println(
					"Element is displayed: " + locator + " with the occurance of: " + expectedElementCount + " count");
			return true;
		} else {
			throw new ElementException("Multiple or no element is displayed for locator: " + locator);
//			System.out.println("Multiple or no element is displayed: "+locator);
//			return false;
		}
	}

	/**************** Actions Utils ******************/

	public void handleParentSubMenu(By parentLocator, By childLocator) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentLocator)).build().perform();
		Thread.sleep(3000);
		doClick(childLocator);
		Thread.sleep(3000);
	}

	public void doDragAndDrop(By sourceLocator, By targetLocator) {
		Actions act = new Actions(driver);
		act.dragAndDrop(getElement(sourceLocator), getElement(targetLocator)).perform();
	}

	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).perform();
	}

	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).perform();
	}

	/**
	 * This method is used to enter the value in the text field with a pause of 500
	 * mile seconds.
	 * 
	 * @param locator
	 * @param value
	 */
	public void doActionsSendKeysWithPause(By locator, String value) {
		Actions act = new Actions(driver);
		char[] ch = value.toCharArray();
		for (char c : ch) {
			act.sendKeys(getElement(locator), String.valueOf(c)).pause(500).perform();
		}
	}

	/**
	 * This method is used to enter the value in the text field with a pause
	 * 
	 * @param locator
	 * @param value
	 * @param pauseTimeInMS
	 */
	public void doActionsSendKeysWithPause(By locator, String value, long pauseTimeInMS) {
		Actions act = new Actions(driver);
		char[] ch = value.toCharArray();
		for (char c : ch) {
			act.sendKeys(getElement(locator), String.valueOf(c)).pause(pauseTimeInMS).perform();
		}
	}

	/********************* WebDriver Wait **************************/

	/**
	 * An expectation for checking if an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible. This method is
	 * slightly risky. There is slightly better approach. Use method -
	 * waitForElementVisible(By locator, int timeOut)
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			highlightTheElement(element);
			return element;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			// throw new ElementException("ELEMENT NOT VISIBLE..."+locator);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * An expectation for checking if an element is present on the DOM of a page and
	 * visible. Visibility means that the element is not only displayed but also has
	 * a height and width is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
//		try {
//			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
//		} catch (org.openqa.selenium.NoSuchElementException e) {
//			// throw new ElementException("ELEMENT NOT VISIBLE..."+locator);
//			e.printStackTrace();
//			return null;
//		}
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightTheElement(element);
		return element;
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeOut
	 */
	public void clickWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
			;
		} catch (TimeoutException e) {
			throw new ElementException("WAITING FOR ELEMENT TO BE CLICKABLE..." + locator);
		}
	}

	/*
	 * *********************************** JS Alert
	 * ***********************************
	 */

	/**
	 * It will not only wait for the alert, but also will switch to the alert.
	 * 
	 * @param timeout
	 * @return
	 */
	public Alert waitForJSAlert(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	/**
	 * Get the JS alert text.
	 * 
	 * @param timeout
	 * @return
	 */
	public String getJSAlertText(int timeout) {
		Alert alert = waitForJSAlert(timeout);
		String text = alert.getText();
		alert.accept();
		return text;
	}

	/**
	 * Accept the alert
	 * 
	 * @param timeout
	 */
	public void acceptJSAlert(int timeout) {
		waitForJSAlert(timeout).accept();
	}

	/**
	 * Dismiss the alert
	 * 
	 * @param timeout
	 */
	public void dismissJSAlert(int timeout) {
		waitForJSAlert(timeout).dismiss();
	}

	/**
	 * Sendkeys with accept the alert.
	 * 
	 * @param timeout
	 * @param value
	 */
	public void sendkeysJSAlert(int timeout, String value) {
		Alert alert = waitForJSAlert(timeout);
		alert.sendKeys(value);
		alert.accept();
	}

	/*
	 * *********************************** Frame Handling
	 * ***********************************
	 */

	/**
	 * An expectation for checking whether the given frame is available to switch
	 * to. If the frame is available it switches the given driver to the specified
	 * frame.
	 * 
	 * @param locator
	 * @param timeout
	 */
	public void waitForFrameByLocator(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}

	/**
	 * An expectation for checking whether the given frame is available to switch
	 * to. If the frame is available it switches the given driver to the specified
	 * frameIndex.
	 * 
	 * @param timeout
	 * @param index
	 */
	public void waitForFrameByIndex(int timeout, int index) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
	}

	/**
	 * An expectation for checking whether the given frame is available to switch
	 * to. If the frame is available it switches the given driver to the specified
	 * frame.
	 * 
	 * @param timeout
	 * @param frameIdorName
	 */
	public void waitForFrameByIdOrName(int timeout, String frameIdorName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIdorName));
	}

	/**
	 * An expectation for checking whether the given frame is available to switch
	 * to. If the frame is available it switches the given driver to the specified
	 * web element.
	 * 
	 * @param timeout
	 * @param frameElement
	 */
	public void waitForFrameByWebElement(int timeout, WebElement frameElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

	/*
	 * *********************************** Window Handling
	 * ***********************************
	 */

	/**
	 * It will return the total number of windows opened.
	 * 
	 * @param timeout
	 * @param numberOfWindows
	 * @return
	 */
	public boolean waitForWindowsToBe(int timeout, int numberOfWindows) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows));
	}

}
