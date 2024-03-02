package hotel_Booking;
 
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
 
public class Excel {
 
	
	public String excelInput () throws FileNotFoundException
    {
    	try 
    	{
    		FileInputStream file = new FileInputStream("C:\\Users\\2304035\\Downloads\\Testing_input.xlsx");
    		XSSFWorkbook workbook=new XSSFWorkbook(file);
        	XSSFSheet sheet=workbook.getSheet("Sheet1");
            XSSFRow row = sheet.getRow(1);
            XSSFCell destiLoc = row.getCell(0);
            String destiValue = destiLoc.toString();
            workbook.close();
            file.close();
            return destiValue;
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    		return null;
    	}

    }
}	
