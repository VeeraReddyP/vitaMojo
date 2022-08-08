package com.vitaMojo.step;

import com.vitaMojo.page.LoginPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LoginSteps {

     LoginPage loginPage = new LoginPage();
     public Response rsp;
     private String baseUrl = "https://vmos2.vmos-demo.com";

    @When("User is on Login page")
    public void user_is_on_login_page() {
        loginPage.navigateToLoginPage();
    }

    @Given("user enter the data to login")
    public void user_enter_the_data_to_login(DataTable dataTable) {
        loginPage.login(dataTable);
    }
    @Then("User should able to see the account {string}")
    public void user_should_able_to_see_the_account(String user) {
        Assert.assertTrue(loginPage.verifyLoginUserName(user));
        loginPage.signOut(user);
    }

    @Given("user enter the data for login API")
    public void user_enter_the_data_for_loginAPI(DataTable dataTable) {

        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
        Map<String, String> registration = new HashMap<>(list.get(0));
        Set<String> values = registration.keySet();
        JSONObject js = new JSONObject();
        for (String value : values) {
            if (value.equalsIgnoreCase("EmailAddress")) {
                js.put("email",registration.get("EmailAddress"));
            } else if (value.equalsIgnoreCase("Password")) {
                js.put("password",registration.get("Password"));
            }
        }
        rsp = (Response) RestAssured.given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(js.toString())
                .post("/user/v1/auth")
                .body();
        System.out.println(rsp.getStatusCode());
        System.out.println(rsp.body().asString());
    }

    @Then("User should able to see the {int} success")
    public void verifySuccessRsp(int status) {
        Assert.assertTrue(rsp.getStatusCode()==status);
    }


}
