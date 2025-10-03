package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil2 {
	//private static final String TEST_DATA_SHEET_PATH = ".src\\test\\resources\\testdata\\OpenCartTestData.xlsx";
	private static final String TEST_DATA_SHEET_PATH = ".\\src\\test\\resources\\testdata\\OpenCartTestData.xlsx";
	private static Workbook book;
	private static Sheet sheet;
	
	public static Object[][] getTestData(String sheetName) {
		Object data[][] = null;
		try {
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH);
			book = WorkbookFactory.create(ip);
			sheet = book.getSheet(sheetName.trim());
			data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			System.out.println("###################Excel Data###################");
			for(int i = 0; i<sheet.getLastRowNum(); i++) {
				for(int j = 0; j<sheet.getRow(0).getLastCellNum(); j++) {
					data[i][j] = sheet.getRow(i+1).getCell(j).toString();
					System.out.print(data[i][j]+" | ");
				}
				System.out.println();
			}
			System.out.println("###################Excel Data###################");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data;
		
	}
	
}
