package part1;

import org.openqa.selenium.*;
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

public class ChangePasswordTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/ilvanaburgic/Downloads/chromedriver-mac-arm64/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.setBinary("/Users/ilvanaburgic/Downloads/chrome-mac-arm64/Google Chrome for Testing.app/Contents/MacOS/Google Chrome for Testing");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://olx.ba/login");

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterMethod
    public void pauseAfterTest() throws InterruptedException {
        Thread.sleep(10000);
    }

    @Test
    public void ValidChangePasswordTest() {

        driver.get("https://www.olx.ba/login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        username.sendKeys("Ilvana.burgic1@gmail.com");

        WebElement password = driver.findElement(By.cssSelector("input[name='password']"));
        password.sendKeys("ilvana1234");

        WebElement loginButton = driver.findElement(By.cssSelector("button.my-lg"));
        loginButton.click();

        String expectedUrl = "https://olx.ba/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        driver.get("https://olx.ba/mojolx/postavke/promjena-sifre");

        WebElement staraSifraInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password' and contains(@class, 'newPassword__user-input')][1]")));
        staraSifraInput.sendKeys("ilvana1234");

        WebElement novaSifraInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div/div[2]/div/div[2]/div/div/div[2]/div[1]/input")));
        novaSifraInput.sendKeys("ilvana123");

        WebElement ponoviSifruInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div/div[2]/div/div[2]/div/div/div[2]/div[2]/input")));
        ponoviSifruInput.sendKeys("ilvana123");

        WebElement odjavaCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='logout']")));
        if (odjavaCheckbox.isSelected()) {
            odjavaCheckbox.click();
        }

        WebElement spasiIzmjeneButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.mt-lg")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", spasiIzmjeneButton);

        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".v-toast__text")));
        Assert.assertTrue(successMessage.isDisplayed(), "Poruka o uspješnoj promjeni šifre nije prikazana.");
        Assert.assertEquals(successMessage.getText(), "Uspješno ste izmijenili šifru.", "Tekst poruke nije ispravan.");

        String currentUrl = driver.getCurrentUrl();
        assert currentUrl != null;
        Assert.assertTrue(currentUrl.contains("promjena-sifre"), "Korisnik je odjavljen umjesto da ostane na stranici.");
    }

    @Test
    public void SameOldAndNewPasswordTest() {

        driver.get("https://www.olx.ba/login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        username.sendKeys("Ilvana.burgic1@gmail.com");

        WebElement password = driver.findElement(By.cssSelector("input[name='password']"));
        password.sendKeys("ilvana1234");

        WebElement loginButton = driver.findElement(By.cssSelector("button.my-lg"));
        loginButton.click();

        String expectedUrl = "https://olx.ba/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        driver.get("https://olx.ba/mojolx/postavke/promjena-sifre");

        WebElement staraSifraInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password' and contains(@class, 'newPassword__user-input')][1]")));
        staraSifraInput.sendKeys("ilvana1234");

        WebElement novaSifraInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div/div[2]/div/div[2]/div/div/div[2]/div[1]/input")));
        novaSifraInput.sendKeys("ilvana1234");

        WebElement ponoviSifruInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div/div[2]/div/div[2]/div/div/div[2]/div[2]/input")));
        ponoviSifruInput.sendKeys("ilvana1234");

        WebElement spasiIzmjeneButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.mt-lg")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", spasiIzmjeneButton);

        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".v-toast__text")));
        Assert.assertTrue(successMessage.isDisplayed(), "Poruka o uspješnoj promjeni šifre nije prikazana.");
        Assert.assertEquals(successMessage.getText(), "Uspješno ste izmijenili šifru.", "Tekst poruke nije ispravan.");

        String currentUrl = driver.getCurrentUrl();
        assert currentUrl != null;
        Assert.assertTrue(currentUrl.contains("promjena-sifre"), "Korisnik je odjavljen umjesto da ostane na stranici.");
    }

    @Test
    public void IncorrectShortPassword() {

        driver.get("https://www.olx.ba/login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        username.sendKeys("ilvana.burgic@stu.ibu.edu.ba");

        WebElement password = driver.findElement(By.cssSelector("input[name='password']"));
        password.sendKeys("ilvaninasifra123");

        WebElement loginButton = driver.findElement(By.cssSelector("button.my-lg"));
        loginButton.click();

        String expectedUrl = "https://olx.ba/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        driver.get("https://olx.ba/mojolx/postavke/promjena-sifre");

        WebElement staraSifraInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password' and contains(@class, 'newPassword__user-input')][1]")));
        staraSifraInput.sendKeys("ilvaninasifra123");

        WebElement novaSifraInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div/div[2]/div/div[2]/div/div/div[2]/div[1]/input")));
        novaSifraInput.sendKeys("short");

        WebElement ponoviSifruInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div/div[2]/div/div[2]/div/div/div[2]/div[2]/input")));
        ponoviSifruInput.sendKeys("short");

        WebElement spasiIzmjeneButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.mt-lg")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", spasiIzmjeneButton);

        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/p")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Poruka o grešci nije prikazana.");
    }

    @Test
    public void DifferentValuesPasswordTest() {
        // Login na platformu
        driver.get("https://www.olx.ba/login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        username.sendKeys("ilvana.burgic@stu.ibu.edu.ba");

        WebElement password = driver.findElement(By.cssSelector("input[name='password']"));
        password.sendKeys("ilvaninasifra123");

        WebElement loginButton = driver.findElement(By.cssSelector("button.my-lg"));
        loginButton.click();

        String expectedUrl = "https://olx.ba/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        driver.get("https://olx.ba/mojolx/postavke/promjena-sifre");

        WebElement staraSifraInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password' and contains(@class, 'newPassword__user-input')][1]")));
        staraSifraInput.sendKeys("ilvaninasifra123");

        WebElement novaSifraInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div/div[2]/div/div[2]/div/div/div[2]/div[1]/input")));
        novaSifraInput.sendKeys("novasifra123");

        WebElement ponoviSifruInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div/div[2]/div/div[2]/div/div/div[2]/div[2]/input")));
        ponoviSifruInput.sendKeys("razlicitasifra");

        WebElement spasiIzmjeneButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.mt-lg")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", spasiIzmjeneButton);

        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/p")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Poruka o grešci nije prikazana.");
        Assert.assertEquals(errorMessage.getText(), "Polje šifra ima različite vrijednosti.", "Tekst greške nije ispravan.");
    }

    @Test
    public void EmptyPonoviPoljeTest() {

        driver.get("https://www.olx.ba/login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        username.sendKeys("ilvana.burgic@stu.ibu.edu.ba");

        WebElement password = driver.findElement(By.cssSelector("input[name='password']"));
        password.sendKeys("ilvaninasifra123");

        WebElement loginButton = driver.findElement(By.cssSelector("button.my-lg"));
        loginButton.click();

        String expectedUrl = "https://olx.ba/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        driver.get("https://olx.ba/mojolx/postavke/promjena-sifre");

        WebElement staraSifraInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password' and contains(@class, 'newPassword__user-input')][1]")));
        staraSifraInput.sendKeys("ilvaninasifra123");

        WebElement novaSifraInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div/div[2]/div/div[2]/div/div/div[2]/div[1]/input")));
        novaSifraInput.sendKeys("novasifra123");

        WebElement ponoviSifruInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div/div[2]/div/div[2]/div/div/div[2]/div[2]/input")));
        ponoviSifruInput.clear();

        WebElement spasiIzmjeneButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.mt-lg")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", spasiIzmjeneButton);

        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/p")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Poruka o grešci nije prikazana.");
        Assert.assertEquals(errorMessage.getText(), "Polje šifra ima različite vrijednosti.", "Tekst greške nije ispravan.");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}