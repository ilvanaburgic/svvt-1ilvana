package part1;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class LoginTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "/Users/ilvanaburgic/Downloads/chromedriver-mac-arm64/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.setBinary("/Users/ilvanaburgic/Downloads/chrome-mac-arm64/Google Chrome for Testing.app/Contents/MacOS/Google Chrome for Testing");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.olx.ba/login");

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterMethod
    public void pauseAfterTest() throws InterruptedException {
        Thread.sleep(10000);
    }

    @Test
    public void testLogin_ValidInput() {

        driver.get("https://www.olx.ba/login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        username.sendKeys("Ilvana.burgic1@gmail.com");

        WebElement password = driver.findElement(By.cssSelector("input[name='password']"));
        password.sendKeys("ilvana1234");

        WebElement loginButton = driver.findElement(By.cssSelector("button.my-lg"));
        loginButton.click();

        String expectedUrl = "https://olx.ba/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, expectedUrl, "Login failed: URL did not match the expected page.");

        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Pretraga']")));
        Assert.assertTrue(searchBar.isDisplayed(), "Login failed: Search bar not visible.");
    }

    @Test
    public void testLogin_IncorrectEmailAndPassword() {

        driver.get("https://www.olx.ba/login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", username);
        username.sendKeys("emailgmail.com");

        WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='password']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", password);
        password.sendKeys("password");

        WebElement loginButton = driver.findElement(By.cssSelector("button.my-lg"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);

        String expectedErrorMessage = "Podaci nisu tačni.";
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.v-toast__text")));
        String actualErrorMessage = errorMessage.getText();
        Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage), "Error message text does not match.");

        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl, "Current URL is null, driver may not be on the login page.");
        Assert.assertTrue(currentUrl.contains("/login"), "URL changed unexpectedly, user is not on login page.");
    }

    @Test
    public void testLogin_InvalidEmailInput() {

        driver.get("https://www.olx.ba/login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        username.sendKeys("wrong_username");

        WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='password']")));
        password.sendKeys("wrong_password");

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.my-lg")));
        loginButton.click();

        String expectedMessage = "Podaci nisu tačni.";
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.v-toast__text")));

        String actualMessage = errorMessage.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage), "Error message text did not match.");
    }

    @Test
    public void testLogin_BothEmptyFields() {

        driver.get("https://www.olx.ba/login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        username.clear();

        WebElement password = driver.findElement(By.cssSelector("input[name='password']"));
        password.clear();

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.my-lg")));
        loginButton.click();

        WebElement errorBorder = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".error-border")));
        Assert.assertTrue(errorBorder.isDisplayed(), "Error border is not visible.");
    }

    @Test
    public void testLogin_EmailEmptyField() {

        driver.get("https://www.olx.ba/login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        username.clear();

        WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='password']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", password);
        password.sendKeys("ilvaninasifra123");


        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.my-lg")));
        loginButton.click();

        WebElement errorBorder = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username'].error-border")));
        Assert.assertTrue(errorBorder.isDisplayed());
    }

    @Test
    public void testLogin_PasswordEmptyField() {

        driver.get("https://www.olx.ba/login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", username);
        username.sendKeys("ilvana.burgic@stu.ibu.edu.ba");

        WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='password']")));
        password.clear();

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.my-lg")));
        loginButton.click();

        WebElement errorBorder = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='password'].error-border")));
        Assert.assertTrue(errorBorder.isDisplayed());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}