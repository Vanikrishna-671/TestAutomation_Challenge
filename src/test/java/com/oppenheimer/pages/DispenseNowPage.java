package com.oppenheimer.pages;

import com.oppenheimer.base.TestBase;
import org.openqa.selenium.By;

public class DispenseNowPage extends TestBase {

    public static final By dispenseNowButtonPageTitleLocator = By.xpath("//*[contains(@class,'v-main__wrap')]/div/div");

    public static String getDispenseNowButtonPageTitleLocatorElementText() {

        return driver.findElement(dispenseNowButtonPageTitleLocator).getText();
    }
}
