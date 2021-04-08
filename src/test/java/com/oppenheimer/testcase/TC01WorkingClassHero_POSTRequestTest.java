package com.oppenheimer.testcase;


import com.oppenheimer.base.TestBase;
import com.oppenheimer.constants.WorkingClassHeroDetail;
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

public class TC01WorkingClassHero_POSTRequestTest extends TestBase {

    Logger logger = Logger.getLogger(String.valueOf(TC01WorkingClassHero_POSTRequestTest.class));

    private String birthday, gender, name, natid, salary, tax;

    //taking one set of data for all the test in this class
    public TC01WorkingClassHero_POSTRequestTest(String birthday, String gender, String name, String natid, String salary, String tax) {
        this.birthday = birthday;
        this.gender = gender;
        this.name = name;
        this.natid = natid;
        this.salary = salary;
        this.tax = tax;
    }


    @Test(description = "Verify POST Request for Inserting One working class hero detail to '/calculator/insert'")
    public void postPersonDetails() {
      // creating request body data
        HashMap<String, String> map = new HashMap<>();
        map.put("birthday", birthday);
        map.put("gender", gender);
        map.put("name", name);
        map.put("natid", natid);
        map.put("salary", salary);
        map.put("tax", tax);
        System.out.println(map);
        logger.info("---------Building the http request----------");
        httpRequest =new RequestSpecBuilder().setBaseUri(prop.getProperty("baseURL"))
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "*/*")
                .addHeader("connection", "keep-alive")
                .build();
        logger.info("---------Sending the http request and getting the response----------");
        response = given().spec(httpRequest).when().body(map).post("/calculator/insert").then().log().all().extract().response();
    }

    @Test(dependsOnMethods = "postPersonDetails")
    void checkStatusCode() {
        logger.info("-----------Check response status code----------");
        logger.info("Response Status code is -> " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 202);
    }

    @Test(dependsOnMethods = "postPersonDetails")
    void checkResponseHeaders() {
        logger.info("---Checking headers---");
        Headers headers = response.getHeaders();
        logger.info("Content-Type is -> " + headers.getValue("Content-Type"));
        Assert.assertEquals(headers.getValue("Content-Type"), "text/plain;charset=UTF-8");
        logger.info("Content-Length is -> " + headers.getValue("Content-Length"));
        Assert.assertEquals(headers.getValue("Content-Length"), "7");
    }

    @Test(dependsOnMethods = "postPersonDetails")
    void checkResponseBody() {
        logger.info("---Checking Response body---");
        logger.info("Response body is -> " + response.getBody().asString());
        Assert.assertEquals(response.getBody().asString(), "Alright");
    }

    @Factory(dataProvider = "workingClassHeroDetails")
    public static Object[] createInstances(String birthday, String gender, String name, String natid, String salary, String tax) {
        return new Object[]{new TC01WorkingClassHero_POSTRequestTest(birthday, gender, name, natid, salary, tax)};
    }

    @DataProvider(name = "workingClassHeroDetails")
    public static Object[][] getWorkingClassHeroDetails(){

        return ExcelUtility.getCellData(ExcelUtility.filePath, "ProperData_HappyFlow");

    }

}
