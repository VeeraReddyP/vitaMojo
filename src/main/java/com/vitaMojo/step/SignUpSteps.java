package com.vitaMojo.step;

import com.vitaMojo.page.SignUpPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SignUpSteps {

     SignUpPage signUpPage = new SignUpPage();
    public Response rsp;
    private String baseUrl = "https://vmos2.vmos-demo.com";

    @When("User is on createAccount page")
    public void user_is_on_create_account_page() {
        signUpPage.navigateToAccountCreation();
    }

    @Given("user enter the data for creating account")
    public void user_enter_the_data_for_creating_account(DataTable dataTable) {
        signUpPage.createAccount(dataTable);
    }
    @Then("User should able to create the account {string}")
    public void user_should_able_to_create_the_account(String user) {
        Assert.assertTrue(signUpPage.verifyAccountCreated(user));
        signUpPage.signOut(user);
    }

    @Given("user enter the data for registration API")
    public void user_enter_the_data_for_signUpAPI(DataTable dataTable) {

        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
        Map<String, String> registration = new HashMap<>(list.get(0));
        Set<String> values = registration.keySet();
        JSONObject js = new JSONObject();
        for (String value : values) {
            if (value.equalsIgnoreCase("EmailAddress")) {
                js.put("email",registration.get("EmailAddress")+ RandomStringUtils.randomAlphabetic(3));
            } else if (value.equalsIgnoreCase("Password")) {
                js.put("password",registration.get("Password"));
            }
            js.put("locale","en-GB");
            js.put("storeUUID","ced48917-54c2-40ad-a646-5315f5dcb28f");
            JSONObject profile = new JSONObject();
            profile.put("firstName","hello");
            js.put("profile",profile);
        }
        rsp = (Response) RestAssured.given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(js.toString())
                .header("tenant","695a1486-80e7-4ee6-bc55-f4911944ef2a")
                .post("/user/v1/user")
                .body();
        System.out.println(rsp.getStatusCode());
        System.out.println(rsp.body().asString());
    }

    @Then("User should able to see the {int} success registration")
    public void verifySuccessRsp(int status) {

        Assert.assertTrue(rsp.getStatusCode()==status);
    }

}
