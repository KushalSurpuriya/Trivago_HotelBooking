package hotel_Booking;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
 
import java.time.Duration;
import java.util.Scanner;
 
public class driverSetup {
    public static WebDriver driver;
 
    public static void initializeDriver() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 1 for Chrome or any other key for Edge:");
        String input = scanner.nextLine();
        if (input.equals("1")) {
            driver = new ChromeDriver();
        } else {
            driver = new EdgeDriver();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }
}
