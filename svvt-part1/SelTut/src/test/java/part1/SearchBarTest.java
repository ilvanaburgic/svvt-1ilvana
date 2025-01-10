package part1;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;

public class SearchBarTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "/Users/ilvanaburgic/Downloads/chromedriver-mac-arm64/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.setBinary("/Users/ilvanaburgic/Downloads/chrome-mac-arm64/Google Chrome for Testing.app/Contents/MacOS/Google Chrome for Testing");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.olx.ba/");

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterMethod
    public void pauseAfterTest() throws InterruptedException {
        Thread.sleep(10000);
    }

    @Test
    public void testSearchBarFunctionality() {

        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Pretraga' and @name='notASearchField']")));
        String searchQuery = "Laptop";
        searchBar.sendKeys(searchQuery);

        WebElement firstSuggestion = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[contains(@class, 'dropdown-list')]/li[1]")));

        firstSuggestion.click();

        wait.until(ExpectedConditions.urlToBe("https://olx.ba/pretraga?q=" + searchQuery.toLowerCase()));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://olx.ba/pretraga?q=" + searchQuery.toLowerCase(), "URL redirection failed. Expected URL: https://olx.ba/pretraga?q=laptop but found: " + currentUrl);
    }

    @Test
    public void testSearchBarMistakeLetters() {

        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Pretraga']")));

        String searchQuery = "laptoo";
        searchBar.sendKeys(searchQuery);
        searchBar.sendKeys(Keys.RETURN);

        WebElement noResultsMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'Nema rezultata za traženi pojam')]")));

        Assert.assertTrue(noResultsMessage.isDisplayed(), "Expected 'No results' message is not displayed.");

        String actualMessage = noResultsMessage.getText();
        String expectedMessage = "Nema rezultata za traženi pojam";
        Assert.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void testSearchBarSpecialCharacters() {

        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Pretraga']")));

        String searchQuery = "@#$%^";
        searchBar.sendKeys(searchQuery);
        searchBar.sendKeys(Keys.RETURN);

        WebElement noResultsMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'Nema rezultata za traženi pojam')]")));

        Assert.assertTrue(noResultsMessage.isDisplayed(), "Expected 'No results' message is not displayed.");

        String actualMessage = noResultsMessage.getText();
        String expectedMessage = "Nema rezultata za traženi pojam";
        Assert.assertEquals(actualMessage, expectedMessage, "The 'no results' message text does not match.");
    }

    @Test
    public void testEmptySearchDisplaysResultsText() {

        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Pretraga']")));

        searchBar.sendKeys(Keys.RETURN);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(@class, 'search-title')]")));

        WebElement resultsTextElement = driver.findElement(By.xpath("//h1[contains(text(), 'rezultata')]"));

        Assert.assertTrue(resultsTextElement.isDisplayed(), "The 'rezultata' text is not displayed on the page.");

        String actualText = resultsTextElement.getText();
        System.out.println("Results Text: " + actualText);

        Assert.assertTrue(actualText.toLowerCase().contains("rezultata"), "The results text does not contain the word 'rezultata'.");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
