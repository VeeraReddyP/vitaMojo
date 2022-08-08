package com.vitaMojo.page;

import com.vitaMojo.cucumber.DriverInit;
import com.vitaMojo.utils.SeleniumUtils;
import io.cucumber.datatable.DataTable;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SignUpPage {

    public RemoteWebDriver driver = DriverInit.getDriver();
    public SeleniumUtils seleniumUtils;
    public SignUpPage(){
        PageFactory.initElements(driver, this);
        this.driver = driver;
        seleniumUtils = new SeleniumUtils(driver);
    }



    int timeOut= 30;
    int pollTime=5;
    By loginBtn =By.xpath("//a[text()='Login']");
    By createAccountTab = By.xpath("//li[@data-test='auth-tab-register']");
    By name = By.id ( "firstName");
    By emailAddress = By.id ( "email");
    By password=By.id ( "password");
    By createAccountButton=By.xpath ( "//button[@data-test='auth-button']");

    public By getLoginUserName(String text){
        return By.xpath("//span[text()='"+text+"']");
    }
    By signOut=By.xpath ( "//button[@data-test='header-profile-sign-out']");


    public void navigateToAccountCreation(){
        seleniumUtils.waitForElementWithFluent(loginBtn, timeOut, pollTime);
        seleniumUtils.click(loginBtn);
    }
    public void createAccount(DataTable dataTable) {
        seleniumUtils.waitForElementToBeClickable(createAccountTab);
        seleniumUtils.click(createAccountTab);
        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
        Map<String, String> registration = new HashMap<>(list.get(0));
        Set<String> values = registration.keySet();
        for (String value : values) {
            if (value.equalsIgnoreCase("Name")) {
                seleniumUtils.enterData(name,registration.get("Name"));
            } else if (value.equalsIgnoreCase("EmailAddress")) {
                seleniumUtils.enterData(emailAddress,registration.get("EmailAddress")+ RandomStringUtils.randomAlphabetic(3));
            } else if (value.equalsIgnoreCase("Password")) {
                seleniumUtils.enterData(password,registration.get("Password"));
            }
        }
        seleniumUtils.click(createAccountButton);
    }

    public boolean verifyAccountCreated(String userName){
        seleniumUtils.waitForElementWithFluent(getLoginUserName(userName), timeOut, pollTime);
        return seleniumUtils.getText(getLoginUserName(userName)).equalsIgnoreCase(userName);
    }

    public void signOut(String userName){
        seleniumUtils.waitForElementWithFluent(getLoginUserName(userName), timeOut, pollTime);
        seleniumUtils.click(getLoginUserName(userName));
        seleniumUtils.click(signOut);
    }

}
