package com.qa.opencart.tests;

import org.openqa.selenium.By;

public class DemoPageTest {
	
	By demo = By.id("demo");
	By cart = By.id("cart");
	
	public void getDemo() {
		System.out.println("demo");
		System.out.println("demo details");
	}
	
	public void addTocart() {
		System.out.println("cart");
		System.out.println("cart details");
	}
	
}
