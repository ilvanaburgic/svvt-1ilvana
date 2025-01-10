package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestChrome {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/Users/ilvanaburgic/Downloads/chromedriver-mac-arm64/chromedriver");
        WebDriver driver = new ChromeDriver();

        driver.get("https://olx.ba/");
        System.out.println("Page title is: " + driver.getTitle());

        driver.quit();
    }
}