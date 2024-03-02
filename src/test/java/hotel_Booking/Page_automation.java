package hotel_Booking;
 
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
 
public class Page_automation extends driverSetup {
	String dateString;
	public WebDriverWait wait;
	
	// Creating Explicit wait 
    public  void initializeWait() {
       wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    // Navigating to the web page
    public void navigateToLandingPage() throws IOException {
        driver.get("https://www.trivago.in/");
    }
    
    // Entering the destination location
    public void enterDestinationLocation(String location) throws IOException {
    	// Locating the search box
        WebElement searchBox = driver.findElement(By.xpath("//*[@id='input-auto-complete']"));
        // Clicking and sending the search value in the in the search box 
        searchBox.click();
        searchBox.sendKeys(location);        
    }
    
    // Handling the cookies
    public void handleCookies() {
    	try 
    	{
    		initializeWait();
    		SearchContext shadow = driver.findElement(By.cssSelector("#usercentrics-root")).getShadowRoot();
    		shadow.findElement(By.cssSelector(".sc-dcJsrY.NmLox")).click();
    	}catch (Exception e) {}    	
    }
    
    // Entering check in and check out dates
    public void selectCheckInCheckOutDates() throws InterruptedException {
    	// Getting today's date and storing it in a variable
    	LocalDate today = LocalDate.now();
    	System.out.println("Todays date is"+"	"+today);
    	// Adding 7 days to my current date
    	LocalDate futureDate = today.plusDays(7);
    	System.out.println("Next Weeks date is"+"	"+futureDate);
    	// Formating the future date to get only date & storing it in a string 
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
    	dateString = futureDate.format(formatter);
    	// Locating the required date in calendar and clicking the date
    	String path = "//button[@data-testid='valid-calendar-day-2024-02-"+dateString+"']";
    	Thread.sleep(3000);
    	driver.findElement(By.xpath(path)).click();
    }
    
    // Adjust the number of Adults
    public void adjustNumberOfAdults() {
        driver.findElement(By.xpath("//button[@class='group w-full text-left truncate h-15 px-1 bg-white active:bg-grey-200']")).click();
		WebElement adultsButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid='adults-amount-minus-button']")));
		adultsButton.click();		
    }
    
    // Taking SS of whole page
    public void FullScreenShot() throws IOException {

		TakesScreenshot ss = (TakesScreenshot) driver;
		// Get the current time-stamp
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//		String timestamp = dateFormat.format(new Date());

		// Create a unique filename using the time-stamp
		String target = System.getProperty("user.dir") + "/ScreenShot/";
		String filename = target + "screenshot_" + dateString + ".png";
		File src = ss.getScreenshotAs(OutputType.FILE);
		File trg = new File(filename);
		FileUtils.copyFile(src, trg);
    }
    
    // Click apply filters button
    public void applyFilters() throws IOException {
        driver.findElement(By.xpath("//button[normalize-space()='Apply']")).click();
        
    }
    
    // Click on search button
    public void performSearch() throws IOException {
        driver.findElement(By.xpath("//button[contains(@class,'SearchButton')]")).click();
    }
 
    // Sorting the visible options on the basis of rating only.
    public void sortByRating() {
        Select sortBy = new Select(driver.findElement(By.xpath("//select[@id='sorting-selector']")));
        sortBy.selectByVisibleText("Rating only");   
    }
    
    // Printing the hotel name, price,rating and location
    public void listHotelPrices() throws InterruptedException, IOException 
    {
    	Thread.sleep(5000);
    	// Storing all the hotel prices in a list
        List<WebElement> prices = driver.findElements(By.xpath("//p[@itemprop='price']"));
        // Storing all the hotel ratings in a list
        List<WebElement> ratings = driver.findElements(By.xpath("//strong[@class='leading-none']//span"));
        // Storing all the hotel names in a list
        List<WebElement> hotelName = driver.findElements(By.xpath("//button[@class='ItemNameSection_itemNameButton__sWjFo']//span"));
        // Storing all the hotel destinations locations in a list
        List<WebElement> destiName = driver.findElements(By.xpath("//span[@class='block text-left w-11/12 text-m']"));
        System.out.println("Number of hotels present: " + prices.size());
        System.out.println(prices.size());
        System.out.println(ratings.size());
        System.out.println(hotelName.size());
        System.out.println(destiName.size());
        // Creating the file path to store the excel file
        String filePath = "C:\\Users\\2304035\\Downloads\\Testing_output.xlsx";
       
        try 
    	{
    		FileOutputStream file = new FileOutputStream(filePath);
        	XSSFWorkbook workbook=new XSSFWorkbook();
    		XSSFSheet sheet=workbook.createSheet("Hotel Prints");
    		// Headers
            XSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Hotel Name");
            headerRow.createCell(1).setCellValue("Hotel Rating");
            headerRow.createCell(2).setCellValue("Hotel Price");
            headerRow.createCell(3).setCellValue("Hotel Destination");
            
            
            // Data
            int rowCount = 1;
            for (int i = 0; i<35; i++) {
            	
                System.out.println(hotelName.get(i).getText()+"	"+ratings.get(i).getText()+"	"+prices.get(i).getText()+"	"+destiName.get(i).getText());
                
                XSSFRow row = sheet.createRow(rowCount++);
                XSSFCell nameCell = row.createCell(0);
                XSSFCell ratingCell = row.createCell(1);
                XSSFCell priceCell = row.createCell(2);
                XSSFCell desti_Cell = row.createCell(3);
 
                nameCell.setCellValue(hotelName.get(i).getText());
                ratingCell.setCellValue(ratings.get(i).getText());
                priceCell.setCellValue(prices.get(i).getText());
                desti_Cell.setCellValue(destiName.get(i).getText());
            }
 
            workbook.write(file);
            System.out.println("Excel file created successfully at: " + filePath);
    	}
    	catch (IOException e) 
    	{
    		e.printStackTrace();
    	}
    }
    // Verifying the weather the destination location is same is searched location or not
    public void check_desti () throws InterruptedException 
    {
    	// clicking the info tab in hotel info
    	List <WebElement> clicks = driver.findElements(By.xpath("//button[@class='w-full flex items-center justify-between cursor-pointer hover:bg-grey-200 py-1 mt-1']"));
    	for (int i=0;i<5;i++) {
    	clicks.get(i).click();
    	Thread.sleep(10000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@itemprop='addressLocaspanty']")));
		// Scrolling the page to get the next hotel's info
		JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("window.scrollBy(0,620)");
    	// Taking the city name and printing it on console
    	String city_Nmae = driver.findElement(By.xpath("//span[@itemprop='addressLocaspanty']")).getText();
    	System.out.println("The city is validated "+"	"+city_Nmae);
    	}
    }
}