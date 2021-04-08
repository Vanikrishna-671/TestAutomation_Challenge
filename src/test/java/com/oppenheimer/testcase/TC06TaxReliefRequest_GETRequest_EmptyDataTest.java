package com.oppenheimer.testcase;

import com.oppenheimer.base.TestBase;
import io.restassured.builder.RequestSpecBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.logging.Logger;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TC06TaxReliefRequest_GETRequest_EmptyDataTest extends TestBase {

    Logger logger = Logger.getLogger(String.valueOf(TC01WorkingClassHero_POSTRequestTest.class));
    @Test(description = "Verify GET Request retuning empty array")
    void getTaxRelief_GETRequest_ReturnsEmptyArray() {

        logger.info("Getting the tax releif data from DB");
        httpRequest = new RequestSpecBuilder().setBaseUri(prop.getProperty("baseURL"))
                .addHeader("Accept", "*/*")
                .addHeader("connection", "keep-alive")
                .build();
                response=given().spec(httpRequest).when().get("calculator/taxRelief");
                logger.info("Message body is => "+response.getBody().asString());
                response.then().assertThat().body(notNullValue());

    }
    @Test(dependsOnMethods = "getTaxRelief_GETRequest_ReturnsEmptyArray")
    void checkStatusCode() {
        logger.info("-----------Check response status code----------");
        logger.info("Response Status code is -> " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @BeforeClass
    public void clearDB() throws InterruptedException {
        logger.info("-----------clearing the db to remove the 500 error--------");
        rakeDatabase();
        Thread.sleep(5000);
        logger.info("-----------DB cleared----------");
    }

}
