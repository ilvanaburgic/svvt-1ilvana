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


public class SpaseniKorisniciTest {

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
    public void testSpasiKorisnika() {

        driver.get("https://www.olx.ba/login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        username.sendKeys("ilvana.burgic@stu.ibu.edu.ba");

        WebElement password = driver.findElement(By.cssSelector("input[name='password']"));
        password.sendKeys("ilvaninasifra123");

        WebElement loginButton = driver.findElement(By.cssSelector("button.my-lg"));
        loginButton.click();

        String expectedUrl = "https://olx.ba/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        driver.get("https://olx.ba/shops/AudiDiijelovi/aktivni");

        WebElement spasiKorisnikaButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[1]/div[1]/div[2]/div[1]/div/div[2]/div[2]/button[2]")));
        spasiKorisnikaButton.click();

        WebElement successSaveMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/p")));
        Assert.assertTrue(successSaveMessage.isDisplayed(), "Poruka o spašenom korisniku nije prikazana.");
        Assert.assertEquals(successSaveMessage.getText(), "Uspješno ste spasili korisnika", "Tekst poruke nije ispravan.");

        driver.get("https://olx.ba/mojolx/spaseno/korisnici");

        WebElement savedUser = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'AudiDiijelovi')]")));
        Assert.assertTrue(savedUser.isDisplayed(), "Korisnik 'AudiDiijelovi' nije prikazan na listi spašenih korisnika.");
    }

    @Test
    public void testUkloniKorisnikaIzSpasenih() {

        driver.get("https://www.olx.ba/login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        username.sendKeys("ilvana.burgic@stu.ibu.edu.ba");

        WebElement password = driver.findElement(By.cssSelector("input[name='password']"));
        password.sendKeys("ilvaninasifra123");

        WebElement loginButton = driver.findElement(By.cssSelector("button.my-lg"));
        loginButton.click();

        String expectedUrl = "https://olx.ba/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        driver.get("https://olx.ba/shops/AudiDiijelovi/aktivni");

        WebElement ukloniKorisnikaButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[1]/div[1]/div[2]/div[1]/div/div[2]/div[2]/button[2]")));
        ukloniKorisnikaButton.click();

        WebElement successRemoveMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/p")));
        Assert.assertTrue(successRemoveMessage.isDisplayed(), "Poruka o uklonjenom korisniku nije prikazana.");
        Assert.assertEquals(successRemoveMessage.getText(), "Uspješno ste izbrisali korisnika iz spašenih", "Tekst poruke nije ispravan.");

        driver.get("https://olx.ba/mojolx/spaseno/korisnici");

        boolean isUserPresent = driver.findElements(By.xpath("//p[contains(text(), 'AudiDiijelovi')]")).isEmpty();
        Assert.assertTrue(isUserPresent, "Korisnik 'AudiDiijelovi' je i dalje prisutan na listi spašenih korisnika.");
    }

    @Test
    public void testUkloniKorisnikaIzSpasenihAkoNePostojiNistaTamo() {

        driver.get("https://www.olx.ba/login");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        username.sendKeys("ilvana.burgic@stu.ibu.edu.ba");

        WebElement password = driver.findElement(By.cssSelector("input[name='password']"));
        password.sendKeys("ilvaninasifra123");

        WebElement loginButton = driver.findElement(By.cssSelector("button.my-lg"));
        loginButton.click();

        String expectedUrl = "https://olx.ba/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        driver.get("https://olx.ba/mojolx/spaseno/korisnici");

        boolean isSavedUserListEmpty = driver.findElements(By.xpath("//p[contains(text(), 'AudiDiijelovi')]")).isEmpty();

        if (isSavedUserListEmpty) {
            WebElement noSavedUsersMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[3]/div/div/div/div/h1")));
            Assert.assertTrue(noSavedUsersMessage.isDisplayed(), "Poruka 'Nemate spašenih korisnika' nije prikazana.");
            Assert.assertEquals(noSavedUsersMessage.getText(), "Nemate spašenih korisnika", "Tekst poruke nije ispravan.");
        } else {
            WebElement savedUserRemoveButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__layout\"]/div/div[1]/div[1]/div[2]/div[1]/div/div[2]/div[2]/button[2]")));
            savedUserRemoveButton.click();

            WebElement successRemoveMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/p")));
            Assert.assertTrue(successRemoveMessage.isDisplayed(), "Poruka o uklonjenom korisniku nije prikazana.");
            Assert.assertEquals(successRemoveMessage.getText(), "Uspješno ste izbrisali korisnika iz spašenih", "Tekst poruke nije ispravan.");

            driver.get("https://olx.ba/mojolx/spaseno/korisnici");
            boolean isUserStillPresent = driver.findElements(By.xpath("//p[contains(text(), 'AudiDiijelovi')]")).isEmpty();
            Assert.assertTrue(isUserStillPresent, "Korisnik 'AudiDiijelovi' je i dalje prisutan na listi spašenih korisnika.");
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
