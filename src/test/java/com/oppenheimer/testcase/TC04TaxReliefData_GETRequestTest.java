package com.oppenheimer.testcase;

import com.oppenheimer.base.TestBase;
import com.oppenheimer.bridge.BridgeAPI;
import com.oppenheimer.constants.TaxReliefDatas;
import com.oppenheimer.pages.APIRelatedCalculations;
import com.oppenheimer.utils.ExcelUtility;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;

public class TC04TaxReliefData_GETRequestTest extends TestBase {

    Logger logger = Logger.getLogger(String.valueOf(TC01WorkingClassHero_POSTRequestTest.class));
    String taxReliefCalculatedFromPassingData;
    JsonPath jsonPath;
    List<TaxReliefDatas> datas;
    TaxReliefDatas taxReliefDetailsFromPassedWorkingClassHero;

    private String birthday, gender, name, natid, salary, tax;

    //taking one set of data for all the test in this class
    public TC04TaxReliefData_GETRequestTest(String birthday, String gender, String name, String natid, String salary, String tax) {
        this.birthday = birthday;
        this.gender = gender;
        this.name = name;
        this.natid = natid;
        this.salary = salary;
        this.tax = tax;
    }

    @Factory(dataProvider = "workingClassHeroDetailsForTaxReliefCalculation")
    public static Object[] createInstances(String birthday, String gender, String name, String natid, String salary, String tax) {
        return new Object[]{new TC04TaxReliefData_GETRequestTest(birthday, gender, name, natid, salary, tax)};
    }

    @DataProvider(name = "workingClassHeroDetailsForTaxReliefCalculation")
    public static Object[][] getWorkingClassHeroDetailsForTaxReliefCalculation() {

        return ExcelUtility.getCellData(ExcelUtility.filePath, "DataForTaxReliefCalculation");

    }

    @BeforeClass
    void setup() throws Exception {
        logger.info("-----------clearing the db to remove the 500 error--------");
        rakeDatabase();
        logger.info("-----------DB cleared----------");
        Thread.sleep(5000);
    }

    @Test(description = "Verify POST Request for Inserting One working class hero detail to '/calculator/insert'")
    public void postPersonDetails() throws ParseException {
        //specify the base URL to the restful webservice
        HashMap<String, String> map = new HashMap<>();
        map.put("birthday", birthday);
        map.put("gender", gender);
        map.put("name", name);
        map.put("natid", natid);
        map.put("salary", salary);
        map.put("tax", tax);
        System.out.println(map);

        taxReliefCalculatedFromPassingData = APIRelatedCalculations.calculateTaxReliefFromPassedWorkingClassHeroData(birthday, salary, tax, gender);
        taxReliefDetailsFromPassedWorkingClassHero = new TaxReliefDatas();
        taxReliefDetailsFromPassedWorkingClassHero.setNatid(APIRelatedCalculations.getMaskedNatId(natid));
        taxReliefDetailsFromPassedWorkingClassHero.setName(name);
        taxReliefDetailsFromPassedWorkingClassHero.setRelief(taxReliefCalculatedFromPassingData);
        logger.info("Tax releif calculated for this hero is => " + taxReliefCalculatedFromPassingData);

        httpRequest = new RequestSpecBuilder().setBaseUri(prop.getProperty("baseURL"))
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "*/*")
                .addHeader("connection", "keep-alive")
                .build();
        response = given().spec(httpRequest).when().body(map).post("/calculator/insert").then().log().all().extract().response();
    }

    @Test(dependsOnMethods = "postPersonDetails")
    void checkResponseBody() {
        logger.info("---Checking Response body---");
        logger.info("Response body is -> " + response.getBody().asString());
        Assert.assertEquals(response.getBody().asString(), "Alright");
    }

    @Test(description = "Verify GET Request for getting Tax relief calculated for One working class hero from 'calculator/taxRelief'", dependsOnMethods = "postPersonDetails")
    void getTaxRelief_GETRequest() {

        logger.info("Getting the tax releif data from DB");
        httpRequest = new RequestSpecBuilder().setBaseUri(prop.getProperty("baseURL"))
                .addHeader("Accept", "*/*")
                .addHeader("connection", "keep-alive")
                .build();
        jsonPath = given().spec(httpRequest).when().get("calculator/taxRelief")
                .then().log().all().extract().body().jsonPath();
        datas = jsonPath.getList("", TaxReliefDatas.class);


    }
    @Test(dependsOnMethods = "getTaxRelief_GETRequest")
    void checkStatusCode() {
        logger.info("-----------Check response status code----------");
        logger.info("Response Status code is -> " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 202);
    }

    @Test(description = "Verify List contains 'natid','name' and 'relief' details of working class hero", dependsOnMethods = "getTaxRelief_GETRequest")
    void verifyResponseBody() {
        logger.info("----------Checking Response body-----------");
        Assert.assertTrue(BridgeAPI.verifyTheTaxReliefListContainsNatIDAndNameAndRelief(taxReliefDetailsFromPassedWorkingClassHero, datas));
        logger.info("The list contains natid,name and relief of working class hero");


    }

    @Test(description = "Verify natid in the GET request is masked from its 5th character onwards", dependsOnMethods = "getTaxRelief_GETRequest")
    void verifyNatIdMaskedOrNot() {
        logger.info("----------Verifying natid masked or not-----------");
        Assert.assertTrue(BridgeAPI.verifyNatID(datas));
        logger.info("Masked id is => " + APIRelatedCalculations.getMaskedNatId(natid));
    }

    @Test(dependsOnMethods = "getTaxRelief_GETRequest")
    void checkResponseHeaders() {
        logger.info("---Checking headers---");
        Headers headers = response.getHeaders();
        logger.info("Content-Type is -> " + headers.getValue("Content-Type"));
        Assert.assertEquals(headers.getValue("Content-Type"), "text/plain;charset=UTF-8");

    }

}

