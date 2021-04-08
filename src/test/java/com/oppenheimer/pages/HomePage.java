package com.oppenheimer.pages;

import com.oppenheimer.base.TestBase;
import com.oppenheimer.constants.GeneralText;
import com.oppenheimer.utils.Common;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomePage extends TestBase {

    private static final By dispenseNowButtonLocator = By.xpath("//a[text()=\"Dispense Now\"]");
    private static final By browseButton = By.xpath("//*[@class=\"custom-file-input\"]");
    private static final By refreshTaxReliefSummaryButtonLocator = By.xpath("//button[text()=\"Refresh Tax Relief Table\"]");
    private static final By reliefTableElementLocator = By.xpath("//*[contains(@class,\"table-hover\")]");
    private static final By reliefTableHeadingElementLocator = By.xpath("./thead/tr/th");
    private static final By homePageLocator = By.xpath("//*[@class='container-fluid']/h1");

    public static String getHomePageHeading() {
        return driver.findElement(homePageLocator).getText();
    }

    public static WebElement getDispenseNowButtonElement() {
        WebElement element = driver.findElement(dispenseNowButtonLocator);
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }

    public static String getDispenseNowButtonText() {
        return getDispenseNowButtonElement().getText();
    }

    public static String getDispenseNowButtonColorIsRed() {
        String colorCode = getDispenseNowButtonElement().getCssValue("background-color");
        return Common.getColor(Common.getHexCode(colorCode));
    }

    public static void clickOnDispenseNowButton() {
        getDispenseNowButtonElement().click();
    }

    public static void getNewPageTitleAfterClickOnDispenseNowButton() throws InterruptedException {
        clickOnDispenseNowButton();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs(GeneralText.DISPENSE_PAGE_TITLE.toString()));
        Thread.sleep(2000);
    }

    public static WebElement getBrowseButtonElement() throws InterruptedException {
//        JavascriptExecutor js = ((JavascriptExecutor) driver);
//        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
//        Thread.sleep(5000);
        return driver.findElement(browseButton);
    }

    public static void clickOnBrowseButton() throws InterruptedException {
        getBrowseButtonElement().sendKeys("C:/Automation_Challenge/TestAutomation/src/test/resources/testData/Book2.csv");
        Thread.sleep(5000);

    }

    public static WebElement getReliefTableElement() {
        return driver.findElement(reliefTableElementLocator);
    }

    public static List<WebElement> getTableHeadingsElement() {
        return getReliefTableElement().findElements(reliefTableHeadingElementLocator);
    }

    public static boolean verifyTheTableHeadingText() {
        List<WebElement> elements = getTableHeadingsElement();
        return StringUtils.equals(elements.get(0).getText(), GeneralText.NATID_TABLE_HEADING.toString())
                && StringUtils.equals(elements.get(1).getText(), GeneralText.RELIEF_TABLE_HEADING.toString());
    }

    public static void clickOnRefreshTaxReliefTableButton() {
        //driver.findElement(refreshTaxReliefSummaryButtonLocator).click();
        WebElement element = driver.findElement(refreshTaxReliefSummaryButtonLocator);
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
        driver.manage().timeouts().pageLoadTimeout(7, TimeUnit.SECONDS);
    }

}
