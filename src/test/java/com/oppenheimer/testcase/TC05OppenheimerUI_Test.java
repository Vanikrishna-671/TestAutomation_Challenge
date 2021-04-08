package com.oppenheimer.testcase;

import com.oppenheimer.base.TestBase;
import com.oppenheimer.bridge.BridgeOppenheimer;
import com.oppenheimer.constants.GeneralText;
import com.oppenheimer.pages.DispenseNowPage;
import com.oppenheimer.pages.HomePage;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;

public class TC05OppenheimerUI_Test {


    @BeforeClass
    void setUp() throws IOException {
        TestBase.createBrowser();
    }

    @Test(description = "Verify User is On Oppenheimer page")
    void verifyUserIsOnCorrectPage() throws IOException{
        TestBase.screenShot();
        Assert.assertTrue(StringUtils.equals(HomePage.getHomePageHeading(), GeneralText.HOME_PAGE_HEADING.toString()));
    }

    @Test(priority = 1,description = "Verify Red Colored 'Dispense Now' button available")
    void verifyTheDispenseNowButtonIsAvailableWithDispenseNowTextAndColorIsRed() throws IOException, InterruptedException {
        TestBase.screenShot();
        Assert.assertTrue(BridgeOppenheimer.verifyTheDispenseNowButtonAvailabilityAndColorIsRed(), "Dispense Now button is not visible");

    }

    @Test(priority = 3,description = "Verify User redirected to a page with 'Cash dispensed' upon clicking 'Dispense Now'")
    void verifyRedirectionToNewPageWithCashDispensedTitle() throws InterruptedException, IOException {
        HomePage.getNewPageTitleAfterClickOnDispenseNowButton();
        TestBase.screenShot();
        Assert.assertTrue(StringUtils.equals(DispenseNowPage.getDispenseNowButtonPageTitleLocatorElementText(), GeneralText.DISPENSE_PAGE_TEXT.toString()));

    }

    @Test(priority = 2,description = "Verify upload csv file")
    void uploadCsvFile() throws InterruptedException {

        HomePage.clickOnBrowseButton();
    }

    @Test(dependsOnMethods = "uploadCsvFile",description = "Verify datas are displayed with 'NatId' and 'Relief' as heading while clicking on 'Refresh relief summary'")
    void verifyTheReliefSummaryUI() throws IOException, InterruptedException {
        HomePage.clickOnRefreshTaxReliefTableButton();
        Thread.sleep(5000);
        TestBase.screenShot();
        Assert.assertTrue(HomePage.verifyTheTableHeadingText());
    }

    @AfterClass
    void tearDown() {
        TestBase.closeBrowser();
    }
}
