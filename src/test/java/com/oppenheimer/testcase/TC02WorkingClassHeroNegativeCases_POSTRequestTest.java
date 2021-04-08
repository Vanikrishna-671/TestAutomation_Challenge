package com.oppenheimer.testcase;

import com.oppenheimer.base.TestBase;
import com.oppenheimer.utils.Common;
import com.oppenheimer.utils.ExcelUtility;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Headers;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;

public class TC02WorkingClassHeroNegativeCases_POSTRequestTest extends TestBase {
    Logger logger = Logger.getLogger(String.valueOf(TC02WorkingClassHeroNegativeCases_POSTRequestTest.class));
    private String birthday, gender, name, natid, salary, tax;
    public TC02WorkingClassHeroNegativeCases_POSTRequestTest() {

    }

    //taking one set of data for all the test in this class
    public TC02WorkingClassHeroNegativeCases_POSTRequestTest(String birthday, String gender, String name, String natid, String salary, String tax) {
        this.birthday = birthday;
        this.gender = gender;
        this.name = name;
        this.natid = natid;
        this.salary = salary;
        this.tax = tax;
    }

    @DataProvider(name = "workingClassHeroDetailsWithNegativeCases")
    public static Object[][] getWorkingClassHeroDetailsWithNegativeCases() {
        return ExcelUtility.getCellData(ExcelUtility.filePath, "Negative cases");

    }

    @Test(description = "Verify POST Request for Inserting One working class hero detail to '/calculator/insert' with invalid datas")
    void postPersonDetailsWithoutProperValues() {
        //creating the request body
        HashMap<String, String> map = new HashMap<>();
        map.put("birthday", birthday);
        map.put("gender", gender);
        map.put("name", name);
        map.put("natid", natid);
        map.put("salary", salary);
        map.put("tax", tax);
        System.out.println(map);
        logger.info("---------Building the http request----------");
        httpRequest = new RequestSpecBuilder().setBaseUri(prop.getProperty("baseURL"))
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "*/*")
                .addHeader("connection", "keep-alive")
                .build();
        logger.info("---------Sending the http request and getting the response----------");
        response = given().spec(httpRequest).when().body(map).post("/calculator/insert").then().log().all().extract().response();
    }

    @Test(dependsOnMethods = "postPersonDetailsWithoutProperValues")
    void checkStatusCode() {
        logger.info("---Checking Status code---");
        logger.info("Response Status code is -> " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 500);
    }

    @Test(dependsOnMethods = "postPersonDetailsWithoutProperValues")
    void checkResponseHeaders() {
        logger.info("---Checking headers---");
        Headers headers = response.getHeaders();
        logger.info("Content-Type is -> " + headers.getValue("Content-Type"));
        Assert.assertEquals(headers.getValue("Content-Type"), "application/json;charset=UTF-8");
        logger.info("Transfer-Encoding is -> " + headers.getValue("Transfer-Encoding"));
        Assert.assertEquals(headers.getValue("Transfer-Encoding"), "chunked");
        logger.info("Connection is -> " + headers.getValue("Connection"));
        Assert.assertEquals(headers.getValue("Connection"), "close");
    }

    @Test(dependsOnMethods = "postPersonDetailsWithoutProperValues")
    void checkResponseBody() throws Exception {

        logger.info("---Checking Response body---");
        logger.info("Response body is -> " + response.getBody().asString());
        Assert.assertEquals(response.getBody().jsonPath().get("error"), "Internal Server Error");

        if (!Common.verifyTheDateFormat(birthday)) {
            Assert.assertEquals(response.getBody().jsonPath().get("message"), Common.verifyTheDay(birthday));
        } else if (gender.length() != 1) {
            Assert.assertEquals(response.getBody().jsonPath().get("message"), "could not execute statement; SQL [n/a]; nested exception is org.hibernate.exception.DataException: could not execute statement");
        } else {
            Assert.assertEquals(response.getBody().jsonPath().get("message"), "No message available");
        }
        logger.info("Path is  -> " + response.getBody().jsonPath().get("path"));
        Assert.assertEquals(response.getBody().jsonPath().get("path"),prop.getProperty("insertURI"));
        logger.info("Response body is -> " + response.getBody().jsonPath().get("message"));


    }

    @Factory(dataProvider = "workingClassHeroDetailsWithNegativeCases")
    public Object[] createInstances(String birthday, String gender, String name, String natid, String salary, String tax) {
        return new Object[]{new TC02WorkingClassHeroNegativeCases_POSTRequestTest(birthday, gender, name, natid, salary, tax)};
    }
}

