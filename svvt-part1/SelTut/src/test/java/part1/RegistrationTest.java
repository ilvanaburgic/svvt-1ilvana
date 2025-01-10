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

public class  RegistrationTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "/Users/ilvanaburgic/Downloads/chromedriver-mac-arm64/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.setBinary("/Users/ilvanaburgic/Downloads/chrome-mac-arm64/Google Chrome for Testing.app/Contents/MacOS/Google Chrome for Testing");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.olx.ba/register");

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterMethod
    public void pauseAfterTest() throws InterruptedException {
        Thread.sleep(10000);
    }

    @Test
    public void testRegistration_CorrectInput() throws InterruptedException {

        driver.get("https://olx.ba/register");

        Thread.sleep(2000);

        try {
            WebElement emailOrPhone = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='__layout']/div/div[2]/div[2]/div[1]/div/div/div[1]/div/input")));
            emailOrPhone.sendKeys("ilvanaburgic2@stu.ibu.edu.ba");

            WebElement nickname = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[3]/div/input")));
            nickname.sendKeys("sarajevoibuu");

            WebElement password = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[2]/div/input")));
            password.sendKeys("ilvaninasifra123");

            WebElement genderDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[4]/select")));
            genderDropdown.click();

            WebElement genderOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//option[normalize-space(text())='Ženski']")));
            genderOption.click();

            WebElement regionSelectElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[5]/div[1]/div[2]/select")));
            regionSelectElement.click();

            WebElement regionOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//option[normalize-space(text())='Kanton Sarajevo']")));
            regionOption.click();

            WebElement placeDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("select[label='Mjesto']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", placeDropdown);
            placeDropdown.click();

            WebElement placeOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("option[value='139']")));
            placeOption.click();

            WebElement termsCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkbox")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", termsCheckbox);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", termsCheckbox);

            Assert.assertTrue(termsCheckbox.isSelected(), "Checkbox nije označen!");

            WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.my-md")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", registerButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", registerButton);

            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#__layout > div > div:nth-child(2) > div")));
            Assert.assertTrue(successMessage.isDisplayed(), "Registration failed: Success message not displayed.");

        } catch (ElementNotInteractableException e) {
            System.out.println("Error: Element not interactable - " + e.getMessage());
            Assert.fail("Element not interactable during registration.");
        }
    }

    @Test
    public void testRegistration_EmailAlreadyExists() throws InterruptedException {

        driver.get("https://olx.ba/register");

        Thread.sleep(2000);

        try {
            WebElement emailOrPhone = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='__layout']/div/div[2]/div[2]/div[1]/div/div/div[1]/div/input")));
            emailOrPhone.sendKeys("ilvana.burgic@stu.ibu.edu.ba");

            WebElement password = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[2]/div/input")));
            password.sendKeys("strongpassword");

            WebElement nickname = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[3]/div/input")));
            nickname.sendKeys("sarajevoBurch");

            WebElement genderDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[4]/select")));
            genderDropdown.click();

            WebElement genderOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("option[value='female']")));
            genderOption.click();

            WebElement regionSelectElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[5]/div[1]/div[2]/select")));
            regionSelectElement.click();

            WebElement regionOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("option[value='9']")));
            regionOption.click();

            WebElement placeDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("select[label='Mjesto']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", placeDropdown);
            placeDropdown.click();

            WebElement placeOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("option[value='139']")));
            placeOption.click();

            WebElement termsCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkbox")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", termsCheckbox);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", termsCheckbox);

            Assert.assertTrue(termsCheckbox.isSelected(), "Checkbox nije označen!");

            WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.my-md")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", registerButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", registerButton);

            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.v-toast__text")));
            String expectedErrorMessage = "Email adresa je zauzeta";
            Assert.assertTrue(errorMessage.getText().contains(expectedErrorMessage), "Greška nije prikazana ili ne sadrži očekivani tekst: 'Već postoji korisnik sa ovom email adresom'");

        } catch (ElementNotInteractableException e) {
            System.out.println("Error: Element not interactable - " + e.getMessage());
            Assert.fail("Element not interactable during registration.");
        }
    }

    @Test
    public void testRegistration_UsernameAlreadyExists() throws InterruptedException {

        driver.get("https://olx.ba/register");

        Thread.sleep(2000);

        try {
            WebElement emailOrPhone = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='__layout']/div/div[2]/div[2]/div[1]/div/div/div[1]/div/input")));
            emailOrPhone.sendKeys("uniqueemail1@example.com");

            WebElement password = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[2]/div/input")));
            password.sendKeys("strongpassword");

            WebElement nickname = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[3]/div/input")));
            nickname.sendKeys("uni_testing");

            WebElement genderDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[4]/select")));
            genderDropdown.click();

            WebElement genderOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("option[value='female']")));
            genderOption.click();

            WebElement regionSelectElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[5]/div[1]/div[2]/select")));
            regionSelectElement.click();

            WebElement regionOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("option[value='9']")));
            regionOption.click();

            WebElement placeDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("select[label='Mjesto']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", placeDropdown);
            placeDropdown.click();

            WebElement placeOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("option[value='139']")));
            placeOption.click();

            WebElement termsCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkbox")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", termsCheckbox);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", termsCheckbox);

            Assert.assertTrue(termsCheckbox.isSelected(), "Checkbox nije označen!");

            WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.my-md")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", registerButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", registerButton);

        } catch (ElementNotInteractableException e) {
            System.out.println("Error: Element not interactable - " + e.getMessage());
            Assert.fail("Element not interactable during registration.");
        }
    }

    @Test
    public void testRegistration_InvalidEmailAndUsernameExist() {

        driver.get("https://olx.ba/register");

        try{
            WebElement emailOrPhone = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='__layout']/div/div[2]/div[2]/div[1]/div/div/div[1]/div/input")));
            emailOrPhone.sendKeys("invalidAdress.com");

            WebElement nickname = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[3]/div/input")));
            nickname.sendKeys("uni_testing");

            WebElement password = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[2]/div/input")));
            password.sendKeys("strongpassword");

            WebElement genderDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[4]/select")));
            genderDropdown.click();

            WebElement genderOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("option[value='female']")));
            genderOption.click();

            WebElement regionSelectElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[5]/div[1]/div[2]/select")));
            regionSelectElement.click();

            WebElement regionOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("option[value='9']")));
            regionOption.click();

            WebElement placeDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("select[label='Mjesto']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", placeDropdown);
            placeDropdown.click();

            WebElement placeOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("option[value='139']")));
            placeOption.click();

            WebElement termsCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkbox")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", termsCheckbox);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", termsCheckbox);

            Assert.assertTrue(termsCheckbox.isSelected(), "Polje uslovi korištenja mora biti prihvaćeno!");

            WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.my-md")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", registerButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", registerButton);

            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.v-toast__text")));
            String errorText = errorMessage.getText();

            Assert.assertTrue(errorText.contains("Polje email mora biti validna e-mail addressa."), "Greška za nevalidan email nije prikazana!");

            Assert.assertTrue(errorText.contains("Korisničko ime je zauzeto"), "Greška za zauzeto korisničko ime nije prikazana!");

        } catch (ElementNotInteractableException e) {
            System.out.println("Error: Element not interactable - " + e.getMessage());
            Assert.fail("Element not interactable during registration.");
        }
    }

    @Test
    public void testRegistration_TermsNotAccepted() {
        driver.get("https://olx.ba/register");

        try {
            WebElement emailOrPhone = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='__layout']/div/div[2]/div[2]/div[1]/div/div/div[1]/div/input")));
            emailOrPhone.sendKeys("ilvanaburgic@gmail.com");

            WebElement nickname = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[3]/div/input")));
            nickname.sendKeys("ilvanaburgic");

            WebElement password = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[2]/div/input")));
            password.sendKeys("strongpassword");

            WebElement genderDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[4]/select")));
            genderDropdown.click();
            WebElement genderOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("option[value='female']")));
            genderOption.click();

            WebElement regionSelectElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[5]/div[1]/div[2]/select")));
            regionSelectElement.click();
            WebElement regionOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("option[value='9']")));
            regionOption.click();

            WebElement placeDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("select[label='Mjesto']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", placeDropdown);
            placeDropdown.click();
            WebElement placeOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("option[value='139']")));
            placeOption.click();

            WebElement termsCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkbox")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", termsCheckbox);

            WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.my-md")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", registerButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", registerButton);

            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.v-toast__text")));
            String errorText = errorMessage.getText();

            Assert.assertTrue(errorText.contains("Polje uslovi korištenja mora biti prihvaćeno"),
                    "Greška za neprihvaćene uslove korištenja nije prikazana!");

        } catch (Exception e) {
            System.out.println("Greška: " + e.getMessage());
            Assert.fail("Test nije uspješno izvršen.");
        }
    }

    @Test
    public void testRegistration_AllFieldsEmpty() {
        driver.get("https://olx.ba/register");

        try {
            WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.my-md")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", registerButton);
            registerButton.click();

            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.v-toast__text")));
            String errorText = errorMessage.getText();

            Assert.assertTrue(errorText.contains("Polje korisničko ime je obavezno"),
                    "Greška za obavezno korisničko ime nije prikazana!");

            Assert.assertTrue(errorText.contains("Polje email je obavezno kada broj telefona nije prisutno"),
                    "Greška za obavezno email polje nije prikazana!");

            Assert.assertTrue(errorText.contains("Šifra je obavezna"),
                    "Greška za obavezno polje šifre nije prikazana!");

            Assert.assertTrue(errorText.contains("Vrijednost nije validna"),
                    "Greška za obavezno polje spola nije prikazana!");

            Assert.assertTrue(errorText.contains("Država je obavezno polje kada lokacija nije odabrana"),
                    "Greška za obavezno polje države nije prikazana!");

            Assert.assertTrue(errorText.contains("Polje uslovi korištenja mora biti prihvaćeno"),
                    "Greška za neprihvaćene uslove korištenja nije prikazana!");

            System.out.println("Test uspešno verifikovao greške za sva prazna polja.");

        } catch (Exception e) {
            System.out.println("Greška tokom testiranja: " + e.getMessage());
            Assert.fail("Test nije uspešno izvršen zbog greške.");
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}