package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.qa.opencart.exceptions.FrameworkException;

public class ExcelUtil {
	
	private static final String TEST_DATA_SHEET_PATH = "./src/test/resources/testdata/OpenCartTestData.xlsx";
	private static Workbook book;
	private static Sheet sheet;
	
	public static Object[][] getTestData(String sheetName) {
		Object data[][] = null;
		try {
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH);
			book = WorkbookFactory.create(ip);
			sheet = book.getSheet(sheetName.trim());
			data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			
			for(int i=0; i<sheet.getLastRowNum(); i++) {
				for(int j=0; j<sheet.getRow(0).getLastCellNum(); j++) {
					data[i][j] = sheet.getRow(i+1).getCell(j).toString();
				}
			}
			
		} catch (FileNotFoundException e) {
			throw new FrameworkException("EXCEL FILE NOT FOUND...SHEET_NAME: "+ sheetName);
		} catch (InvalidFormatException e) {
			throw new FrameworkException("INVALID FORMAT OF EXCEL FILE...SHEET_NAME: "+sheetName);
		} catch (IOException e) {
			throw new FrameworkException("INPUT/OUTPUT EXCEPTION FOR THE EXCEL FILE...SHEET_NAME: "+sheetName);
		}
		
		return data;
	}
	
}
