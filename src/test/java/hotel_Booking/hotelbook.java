package hotel_Booking;
 
import java.io.IOException;

public class hotelbook extends Page_automation {
 
    public static void main(String[] args) throws InterruptedException, IOException {
        hotelbook hb = new hotelbook();
        Excel pT = new Excel();
        String destiLoc = pT.excelInput();
        
        hb.initializeDriver();
        hb.navigateToLandingPage();        
        hb.enterDestinationLocation(destiLoc);
        hb.handleCookies();
        hb.selectCheckInCheckOutDates();
        hb.adjustNumberOfAdults();
        hb.FullScreenShot();
        hb.applyFilters();
        hb.performSearch();
        hb.sortByRating();
        hb.listHotelPrices();
        hb.check_desti();
        driver.quit();
    }
}