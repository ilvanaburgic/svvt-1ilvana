package part1;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;

public class UserInformationTest {

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
    public void EditKorisnickeInformacijeTest() {

        driver.get("https://www.olx.ba/login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        username.sendKeys("ilvana.burgic@stu.ibu.edu.ba");

        WebElement password = driver.findElement(By.cssSelector("input[name='password']"));
        password.sendKeys("ilvaninasifra123");

        WebElement loginButton = driver.findElement(By.cssSelector("button.my-lg"));
        loginButton.click();

        String expectedUrl = "https://olx.ba/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        driver.get("https://olx.ba/mojolx/postavke/korisnicke-informacije");

        WebElement imeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div/div[2]/div/div[2]/div[1]/div/div/form/div[1]/input")));
        imeInput.clear();
        imeInput.sendKeys("Sarajevo");

        WebElement prezimeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div/div[2]/div/div[2]/div[1]/div/div/form/div[2]/input")));
        prezimeInput.clear();
        prezimeInput.sendKeys("IBU");

        List<WebElement> dropdowns = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.grid.grid-cols-3.gap-x-3 select")));

        Assert.assertEquals(dropdowns.size(), 3);

        Select danSelect = new Select(dropdowns.get(0));
        danSelect.selectByVisibleText("15");

        Select mjesecSelect = new Select(dropdowns.get(1));
        mjesecSelect.selectByVisibleText("06");

        Select godinaSelect = new Select(dropdowns.get(2));
        godinaSelect.selectByVisibleText("1995");

        WebElement spasiIzmjeneButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.mt-lg")));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", spasiIzmjeneButton);

        try {
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/p")));
            Assert.assertTrue(successMessage.isDisplayed(), "Poruka o uspješnom čuvanju nije prikazana.");
        } catch (TimeoutException e) {
            System.out.println("Poruka o uspješnom spašavanju nije prikazana.");
            Assert.fail("Izmjene nisu spašene.");
        }
    }

    @Test
    public void EnterKorisnickeInformacijeForEmptyInputTest() {

        driver.get("https://www.olx.ba/login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        username.sendKeys("ilvana.burgic@stu.ibu.edu.ba");

        WebElement password = driver.findElement(By.cssSelector("input[name='password']"));
        password.sendKeys("ilvaninasifra123");

        WebElement loginButton = driver.findElement(By.cssSelector("button.my-lg"));
        loginButton.click();

        String expectedUrl = "https://olx.ba/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        driver.get("https://olx.ba/mojolx/postavke/korisnicke-informacije");

        WebElement imeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div/div[2]/div/div[2]/div[1]/div/div/form/div[1]/input")));
        if (imeInput.getAttribute("value").isEmpty()) {
            imeInput.sendKeys("sarajev");
            System.out.println("Upisano ime: sarajev");
        } else {
            System.out.println("Ime već postoji, preskačem.");
        }

        WebElement prezimeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div/div[2]/div/div[2]/div[1]/div/div/form/div[2]/input")));
        if (prezimeInput.getAttribute("value").isEmpty()) {
            prezimeInput.sendKeys("ibu");
            System.out.println("Upisano prezime: ibu");
        } else {
            System.out.println("Prezime već postoji, preskačem.");
        }

        List<WebElement> dropdowns = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.grid.grid-cols-3.gap-x-3 select")));
        Assert.assertEquals(dropdowns.size(), 3, "Nisu pronađena tri dropdown elementa!");

        Select danSelect = new Select(dropdowns.get(0));
        if (danSelect.getFirstSelectedOption().getText().isEmpty()) {
            danSelect.selectByVisibleText("15");
        } else {
            System.out.println("Dan već postoji, preskačem.");
        }

        Select mjesecSelect = new Select(dropdowns.get(1));
        if (mjesecSelect.getFirstSelectedOption().getText().isEmpty()) {
            mjesecSelect.selectByVisibleText("06");
        } else {
            System.out.println("Mjesec već postoji, preskačem.");
        }

        Select godinaSelect = new Select(dropdowns.get(2));
        if (godinaSelect.getFirstSelectedOption().getText().isEmpty()) {
            godinaSelect.selectByVisibleText("1995");
        } else {
            System.out.println("Godina već postoji, preskačem.");
        }

        WebElement spasiIzmjeneButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.mt-lg")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", spasiIzmjeneButton);

        try {
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/p")));
            Assert.assertTrue(successMessage.isDisplayed(), "Poruka o uspješnom čuvanju nije prikazana.");
            System.out.println("Promjene su uspješno sačuvane.");
        } catch (TimeoutException e) {
            System.out.println("Poruka o uspješnom spašavanju nije prikazana.");
            Assert.fail("Izmjene nisu spašene.");
        }
    }

    @Test
    public void InvalidPhoneNumberTest() {

        driver.get("https://www.olx.ba/login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        username.sendKeys("ilvana.burgic@stu.ibu.edu.ba");

        WebElement password = driver.findElement(By.cssSelector("input[name='password']"));
        password.sendKeys("ilvaninasifra123");

        WebElement loginButton = driver.findElement(By.cssSelector("button.my-lg"));
        loginButton.click();

        String expectedUrl = "https://olx.ba/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        driver.get("https://olx.ba/mojolx/postavke/korisnicke-informacije");

        WebElement telefonInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div/div[2]/div/div[2]/div[1]/div/div/form/div[3]/input")));

        telefonInput.clear();
        telefonInput.sendKeys("225883");

        WebElement spasiIzmjeneButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.mt-lg")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", spasiIzmjeneButton);

        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(@class, 'v-toast__text') and contains(text(), 'Unešeni broj nije u validnom formatu')]")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Validacijska poruka za neispravan broj telefona nije prikazana.");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}