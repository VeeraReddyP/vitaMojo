package com.vitaMojo.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

public class SeleniumUtils {

    public RemoteWebDriver driver;
    public WebDriverWait wait;

    public SeleniumUtils(RemoteWebDriver driver) {
        this.driver = driver;
    }


    public void enterData( By locator, String text) {
        try {
            System.out.println("enter text into the locator " + locator.toString());
            driver.findElement( locator).clear();
            driver.findElement( locator).sendKeys(text);
        } catch (Exception e) {
            throw new RuntimeException("Exception:" + e + " on ");
        }

    }
    public void click(By selector) {
        WebElement element = getElement(selector);
        waitForElementToBeClickable(selector);
        try {
            if (element != null)
                element.click();
            System.out.println("click on the locator " + selector.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public WebElement getElement(By selector) {
        try {
            return driver.findElement( selector);
        } catch (Exception e) {
            System.out.println(String.format("Element %s does not exist - proceeding", selector));
        }
        return null;
    }

    public String getText(By selector) {
        try {
            return driver.findElement( selector).getText();
        } catch (Exception e) {
            System.out.println(String.format("Element %s does not exist - proceeding", selector));
        }
        return null;
    }

    public void waitForElementToBeClickable( By selector) {
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(selector));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean waitForElementWithFluent(By selector, int timeOut, int pollTime) {
        try {
            FluentWait<WebDriver> wait = new FluentWait<>(driver);
            wait.withTimeout(Duration.ofSeconds(timeOut));
            wait.pollingEvery(Duration.ofMillis(pollTime));
            wait.ignoring(NoSuchElementException.class);

            Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver webDriver) {
                    WebElement element;
                    try {
                        element = webDriver.findElement( selector);
                        if (element.isDisplayed())
                            return true;
                    } catch (WebDriverException e) {
                        System.out.println(e);
                    }
                    return false;
                }
            };
            wait.until(function);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;

    }
}
