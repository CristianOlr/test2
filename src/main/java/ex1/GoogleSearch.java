package ex1;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GoogleSearch {
    public static final String xmlFilePath = "C:\\Users\\Oloer Cristian\\IdeaProjects\\testare2";

    @Test
    public void googleSearch() throws InterruptedException {

        try {
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("javascript");
            doc.appendChild(rootElement);

            Element linkss = doc.createElement("links");
            rootElement.appendChild(linkss);



            String driverExecutablePath = "C:\\Drivers\\geckodriver.exe";
            System.setProperty("webdriver.gecko.driver", driverExecutablePath);


            WebDriver driver = new FirefoxDriver();
            FirefoxOptions options = new FirefoxOptions();

            String selectLinkOpenInNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
            options.addArguments("--start-maximized");
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); //wait before each element
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);


            driver.get("https://www.google.com");
            driver.getWindowHandle();


            WebElement element = driver.findElement(By.name("q"));
            element.sendKeys("javascript");
            element.submit();

            System.out.println("Page title " + driver.getTitle());

            new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver1) {
                    return driver1.getTitle().toLowerCase().startsWith("javascript");
                }
            });

            List<WebElement> links = driver.findElements(By.xpath("//div[@id='search']//a[contains(.,'avaScript')]"));


            int numberOfLinks = links.size();
            for (int iLinks = 0; iLinks < numberOfLinks; iLinks++) {
                links.get(iLinks).sendKeys(selectLinkOpenInNewTab);
            }

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            for (int iTabs = 0; iTabs < tabs.size(); iTabs++) {
                driver.switchTo().window(tabs.get(iTabs));
                driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

                System.out.println("Page url:  " + " " + driver.getCurrentUrl());
                System.out.println("Page title:  " + driver.getTitle());
                System.out.println("Number of occurences on the page:  " + driver.getTitle() + "   " + StringUtils.countMatches(driver.getPageSource().toLowerCase(), "javascript"));
                System.out.println();
            }

            Thread.sleep(2000);



            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
//            StreamResult result = new StreamResult(new File("search.xml"));
            StreamResult result = new StreamResult(new File(xmlFilePath));
//            final StreamResult sr = new StreamResult(file.toURI().getPath());

            transformer.transform(source, result);

            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

