package com.vitaMojo.cucumber;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverInit {

    public static ThreadLocal<WebDriver> driver = new ThreadLocal();

    public static synchronized RemoteWebDriver getDriver() {
        return (RemoteWebDriver) driver.get();
    }

    public static void initiateDriver(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.setPageLoadStrategy(PageLoadStrategy.EAGER);
            driver.set(new ChromeDriver(options));
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver.set(new FirefoxDriver());
        } else if (browser.equalsIgnoreCase("ie")) {
            WebDriverManager.iedriver().setup();
            driver.set(new InternetExplorerDriver());
        }
        driver.get().get("https://fego.vmos-demo.com/");
        driver.get().manage().window().maximize();
    }

    public static void killDriver() {
        driver.get().quit();
    }
}
