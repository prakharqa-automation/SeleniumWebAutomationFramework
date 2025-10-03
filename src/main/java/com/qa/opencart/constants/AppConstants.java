package com.qa.opencart.constants;

import java.util.Arrays;
import java.util.List;

public class AppConstants {
	
	public static final String CONFIG_FILE_PATH = "./src/test/resources/config/config.properties";
	public static final String CONFIG_QA_FILE_PATH = "./src/test/resources/config/qa.properties";
	public static final String CONFIG_DEV_FILE_PATH = "./src/test/resources/config/dev.properties";
	public static final String CONFIG_STAGE_FILE_PATH = "./src/test/resources/config/stage.properties";
	public static final String CONFIG_UAT_FILE_PATH = "./src/test/resources/config/uat.properties";
	
	public static final String LOGIN_PAGE_TITLE ="Account Login";
	public static final String ACCOUNTS_PAGE_TITLE ="My Account";
	
	public static final String LOGIN_PAGE_FRACTION_URL ="route=account/login";
	public static final String Account_PAGE_FRACTION_URL ="route=account/account";
	
	public static final List<String> ACCOUNTS_PAGE_HEADERS_LIST = Arrays.asList("My Account","My Orders","My Affiliate Account","Newsletter");
	
	public static final String USE_REGISTER_SUCCESS_MESSAGE = "Your Account Has Been Created";
	
	public static final String REGISTER_SHEET_NAME = "register";
}
