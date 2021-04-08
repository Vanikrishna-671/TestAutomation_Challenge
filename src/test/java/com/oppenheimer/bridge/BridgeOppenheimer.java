package com.oppenheimer.bridge;


import com.oppenheimer.base.TestBase;
import com.oppenheimer.constants.GeneralText;
import com.oppenheimer.pages.HomePage;
import org.apache.commons.lang3.StringUtils;

public class BridgeOppenheimer extends TestBase {
    public static boolean verifyTheDispenseNowButtonAvailabilityAndColorIsRed() {
        return HomePage.getDispenseNowButtonElement().isDisplayed()
                && StringUtils.equals(HomePage.getDispenseNowButtonText(), GeneralText.DISPENSE_NOW_BUTTON.toString())
                && StringUtils.equals(HomePage.getDispenseNowButtonColorIsRed(), GeneralText.DISPENSE_BUTTON_COLOR.toString());
    }
}
