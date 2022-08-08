package com.vitaMojo.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",  glue = { "com.vitaMojo.step" },
        plugin = { "pretty", "html:target/cucumber/report.html, json:target/cucumber/report.json",
                },monochrome = true)

public class RunCucumberTests  {

    @BeforeClass
    public static void setup(){
        DriverInit.initiateDriver("chrome");
    }

    @AfterClass
    public static void tearDown(){
        DriverInit.killDriver();
    }
}
