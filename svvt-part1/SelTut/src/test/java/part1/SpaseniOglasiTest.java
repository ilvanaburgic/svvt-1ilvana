package part1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SpaseniOglasiTest {

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
    public void spasiOglasTest() {

        driver.get("https://www.olx.ba/login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        username.sendKeys("ilvana.burgic@stu.ibu.edu.ba");

        WebElement password = driver.findElement(By.cssSelector("input[name='password']"));
        password.sendKeys("ilvaninasifra123");

        WebElement loginButton = driver.findElement(By.cssSelector("button.my-lg"));
        loginButton.click();

        String expectedUrl = "https://olx.ba/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        driver.get("https://olx.ba/artikal/48947983");

        WebElement spasiOglasButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[1]/div/div[1]/div[2]/div/div[1]/div[1]/div[2]/div[2]/button[3]")));
        spasiOglasButton.click();

        WebElement successSaveMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/p")));
        Assert.assertTrue(successSaveMessage.isDisplayed(), "Poruka o spašenom oglasu nije prikazana.");

        driver.get("https://olx.ba/mojolx/spaseno/oglasi");

        WebElement savedAd = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[3]/div/div/div/div/a/div/div[2]/div[2]/div[1]/h1")));
        Assert.assertTrue(savedAd.isDisplayed(), "Oglas nije prikazan na listi spašenih oglasa.");
    }

    @Test
    public void ukloniOglasIzSpasenihOglasaTest() {

        driver.get("https://www.olx.ba/login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        username.sendKeys("ilvana.burgic@stu.ibu.edu.ba");

        WebElement password = driver.findElement(By.cssSelector("input[name='password']"));
        password.sendKeys("ilvaninasifra123");

        WebElement loginButton = driver.findElement(By.cssSelector("button.my-lg"));
        loginButton.click();

        String expectedUrl = "https://olx.ba/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        driver.get("https://olx.ba/mojolx/spaseno/oglasi");

        WebElement savedAdRemoveButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[3]/div/div/div/div/button")));
        savedAdRemoveButton.click();

        WebElement successRemoveMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Uspješno ste izbrisali spašeni oglas')]")));
        Assert.assertTrue(successRemoveMessage.isDisplayed(), "Poruka o uklonjenom oglasu nije prikazana.");

        driver.get("https://olx.ba/mojolx/spaseno/oglasi");
        boolean isAdPresent = driver.findElements(By.xpath("//h1[contains(text(), 'BMW X6 M PREFORMANCE/650ks/2016/FULL')]")).isEmpty();
        Assert.assertTrue(isAdPresent, "Oglas je i dalje prisutan na listi spašenih oglasa.");
    }

    @Test
    public void nemaSacuvanihOglasaPokusajUklanjanjaTest() {

        driver.get("https://www.olx.ba/login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        username.sendKeys("ilvana.burgic@stu.ibu.edu.ba");

        WebElement password = driver.findElement(By.cssSelector("input[name='password']"));
        password.sendKeys("ilvaninasifra123");

        WebElement loginButton = driver.findElement(By.cssSelector("button.my-lg"));
        loginButton.click();

        String expectedUrl = "https://olx.ba/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        driver.get("https://olx.ba/mojolx/spaseno/oglasi");

        WebElement noSavedAdsMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[3]/div/div/div/div/h1")));
        Assert.assertTrue(noSavedAdsMessage.isDisplayed(), "Poruka 'Nemate spašenih oglasa' nije prikazana.");

        Assert.assertEquals(noSavedAdsMessage.getText(), "Nemate spašenih oglasa", "Tekst poruke nije ispravan.");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
