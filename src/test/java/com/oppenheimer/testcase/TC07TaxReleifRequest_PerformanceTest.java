package com.oppenheimer.testcase;

import com.oppenheimer.base.TestBase;
import io.restassured.builder.RequestSpecBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class TC07TaxReleifRequest_PerformanceTest extends TestBase {

    Logger logger = Logger.getLogger(String.valueOf(TC01WorkingClassHero_POSTRequestTest.class));

    @BeforeClass
    public void clearDB() throws InterruptedException {
        logger.info("-----------clearing the db to remove the 500 error--------");
        rakeDatabase();
        Thread.sleep(5000);
        logger.info("-----------DB cleared----------");
    }

    @Test(description = "Verify GET Request retuning empty array", dependsOnMethods = "insertRandomToDatabaseForNoReason")
    void getTaxRelief_GETRequest_ForMultipleData() {

        logger.info("Getting the tax releif data from DB");
        httpRequest = new RequestSpecBuilder().setBaseUri(prop.getProperty("baseURL"))
                .addHeader("Accept", "*/*")
                .addHeader("connection", "keep-alive")
                .build();
        response = given().spec(httpRequest).when().get("calculator/taxRelief");
        Assert.assertTrue(response.getTime()<2000);
        logger.info("Response time is => "+response.getTime());


    }

    @Test(dependsOnMethods = "getTaxRelief_GETRequest_ForMultipleData")
    void checkStatusCode() {
        logger.info("-----------Check response status code----------");
        logger.info("Response Status code is -> " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(description = "Inserting many records to DB using /calculator/insertRandomToDatabaseForNoReason?count={value} POST Request")
    public void insertRandomToDatabaseForNoReason() throws InterruptedException {
        logger.info("---------Inserting more records to DB------------ ");
        httpRequest = new RequestSpecBuilder().setBaseUri(prop.getProperty("baseURL")).addParam("count", 1000)
                .addHeader("Accept", "*/*")
                .addHeader("connection", "keep-alive")
                .build();
        response = given().spec(httpRequest).when().post("/calculator/insertRandomToDatabaseForNoReason").then().log().all().extract().response();
        logger.info(response.getBody().asString());
        Thread.sleep(5000);

    }

}


