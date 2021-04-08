package com.oppenheimer.testcase;

import com.oppenheimer.base.TestBase;
import com.oppenheimer.constants.TaxReliefDatas;
import com.oppenheimer.utils.ReadJsonData;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;

public class TC03InsertMultipleWorkingClassHero_POSTRequestTest extends TestBase {

    Logger logger = Logger.getLogger(String.valueOf(TC03InsertMultipleWorkingClassHero_POSTRequestTest.class));
    List<TaxReliefDatas> taxReliefDatas;
    JsonPath jsonPath;

    private TC03InsertMultipleWorkingClassHero_POSTRequestTest() {
    }

    @Test(description = "Verify POST Request for Inserting multiple working class hero details to '/calculator/insertMultiple'")
    void postPersonsDetails() {

        logger.info("---------Building the http request----------");

        httpRequest = new RequestSpecBuilder().setBaseUri(prop.getProperty("baseURL"))
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "*/*")
                .addHeader("connection", "keep-alive")
                .build();

        logger.info("---------Sending the http request and getting the response----------");
        //Passing the data as Json data
        response = given().spec(httpRequest).when().body(ReadJsonData.readJsonData())
                .post("/calculator/insertMultiple")
                .then().log().all().extract().response();
    }

    @Test(dependsOnMethods = "postPersonsDetails")
    void checkStatusCode() {
        logger.info("-----------Check response status code----------");
        logger.info("Response Status code is -> " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 202);
    }

    @Test(dependsOnMethods = "postPersonsDetails")
    void checkResponseHeaders() {
        logger.info("---Checking headers---");
        Headers headers = response.getHeaders();
        logger.info("Content-Type is -> " + headers.getValue("Content-Type"));
        Assert.assertEquals(headers.getValue("Content-Type"), "text/plain;charset=UTF-8");
        logger.info("Content-Length is -> " + headers.getValue("Content-Length"));
        Assert.assertEquals(headers.getValue("Content-Length"), "7");
    }

    @Test(dependsOnMethods = "postPersonsDetails")
    void checkResponseBody() {
        logger.info("---Checking Response body---");
        logger.info("Response body is -> " + response.getBody().asString());
        Assert.assertEquals(response.getBody().asString(), "Alright");
    }

    @Test(description = "Verify the data we Sent and Retrieved are same with all tax relief calculation and masking implemented", dependsOnMethods = "postPersonsDetails")
    void getTaxRelief_GETRequest() throws ParseException {
        httpRequest = new RequestSpecBuilder().setBaseUri(prop.getProperty("baseURL"))
                .addHeader("Accept", "*/*")
                .addHeader("connection", "keep-alive")
                .build();
        jsonPath = given().spec(httpRequest).when().get("calculator/taxRelief")
                .then().log().all().extract().body().jsonPath();
        taxReliefDatas = jsonPath.getList("", TaxReliefDatas.class);
        Assert.assertTrue(ReadJsonData.compareJsonData(taxReliefDatas));


    }

    @BeforeClass
    public void clearDB() throws InterruptedException {
        logger.info("-----------clearing the db to remove the 500 error--------");
        rakeDatabase();
        Thread.sleep(5000);
        logger.info("-----------DB cleared----------");
    }
}

