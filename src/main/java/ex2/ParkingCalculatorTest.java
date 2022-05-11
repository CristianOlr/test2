package ex2;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;


import java.util.concurrent.TimeUnit;

public class ParkingCalculatorTest {
    private static final Logger logger = Logger.getLogger(ParkingCalculatorTest.class);

    @DataProvider (name = "data-provider")
    public Object[][] dpMethod(){
        return new Object[][] {{"Short","2/17/2022", "9:00", "6/17/2022", "6:00"}, {"Economy","2/10/2022", "1:00", "9/10/2022", "2:00"},
                {"Long-Garage","1/11/2022", "5:00", "6/11/2022", "6:00"}, {"Long-Surface","1/1/2022", "1:00", "12/31/2022", "23:00"}};
}

    @Test (dataProvider = "data-provider")
    public void test(String var1, String var2, String var3, String var4, String var5) {
        String driverExecutablePath = "C:\\Drivers\\geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", driverExecutablePath);
        WebDriver driver = new FirefoxDriver();
        FirefoxOptions options = new FirefoxOptions();
        DOMConfigurator.configure("log4j.xml");
        options.addArguments("--start-maximized");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); //wait before each element
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.get("https://www.shino.de/parkcalc/");

        logger.info("loading page");


        WebElement parklot = driver.findElement(By.xpath("//select[@id='ParkingLot']"));
        parklot.sendKeys(var1);

        WebElement startdate = driver.findElement(By.xpath("//input[@name='StartingDate']"));
        startdate.clear();
        startdate.sendKeys(var2);

        WebElement starttime = driver.findElement(By.xpath("//input[@name='StartingTime']"));
        starttime.clear();
        starttime.sendKeys(var3);

        WebElement leavedate = driver.findElement(By.xpath("//input[@name='LeavingDate']"));
        leavedate.clear();
        leavedate.sendKeys(var4);

        WebElement leavetime = driver.findElement(By.xpath("//input[@name='LeavingTime']"));
        leavetime.clear();
        leavetime.sendKeys(var5);

        WebElement registerButton = driver.findElement(By.xpath("//input[@name='Submit']"));
        registerButton.click();

    }
}
