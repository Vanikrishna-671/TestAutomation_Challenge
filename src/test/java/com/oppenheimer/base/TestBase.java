package com.oppenheimer.base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class TestBase {

    public static RequestSpecification httpRequest;
    public static Response response;
    public static String path = "src/test/resources/screenshots/";
    public static File file = new File("C:\\Automation_Challenge\\TestAutomation\\src\\test\\resources\\config\\config.properties");
    public static FileInputStream fis;
    public static Properties prop = new Properties();
    public static WebDriver driver;

    public TestBase() {
        try {
            fis = new FileInputStream(file);
            prop.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createBrowser(){
        new TestBase();
        if (prop.getProperty("browser").equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/chromedriver.exe");
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        driver.get(prop.getProperty("baseURL"));

    }

    public static void rakeDatabase() {

        //For Clearing database
        httpRequest = new RequestSpecBuilder().setBaseUri(prop.getProperty("baseURL"))
                .addHeader("Accept", "*/*")
                .build();
        response = given().spec(httpRequest).when().body("").post("/calculator/rakeDatabase").then().log().all().extract().response();

    }

    public static void screenShot() throws IOException {
        File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String filename = new SimpleDateFormat("yyyyMMddhhmmss'.jpeg'").format(new Date());
        File dest = new File(path + filename);
        FileUtils.copyFile(scr, dest);
    }



    public static void closeBrowser() {
        driver.close();
    }

}
