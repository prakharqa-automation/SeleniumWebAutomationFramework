package com.qa.opencart.utils;

public class StringUtils {
	
	public static String getRandomEmailId() {
		String emailid = "userauto"+System.currentTimeMillis()+"@opencart.com";
		System.out.println("user email id: "+emailid);
		return emailid;
	}
}
