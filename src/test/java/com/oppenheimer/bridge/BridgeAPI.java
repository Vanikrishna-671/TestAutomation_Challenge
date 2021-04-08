package com.oppenheimer.bridge;

import com.oppenheimer.base.TestBase;
import com.oppenheimer.constants.TaxReliefDatas;
import com.oppenheimer.pages.APIRelatedCalculations;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class BridgeAPI extends TestBase {

    public static boolean verifyNatID(List<TaxReliefDatas> datas) {
        int count = 0;
        for (TaxReliefDatas data : datas) {
            if (data.getNatid() != null && !StringUtils.equals(data.getNatid(), ""))
                count = count + 1;
        }
        return (count == APIRelatedCalculations.verifyNetIdMaskedOnlyFromFifthCharacter(datas));
    }

    public static boolean verifyTheTaxReliefListContainsNatIDAndNameAndRelief(TaxReliefDatas data1, List<TaxReliefDatas> data2) {

        int counter = 0;
        for (TaxReliefDatas data : data2) {
            if (StringUtils.equals(data1.getNatid(), data.getNatid())
                    && StringUtils.equals(data1.getName(), data.getName())
                    && StringUtils.equals(data1.getRelief(), data.getRelief()))
                counter = counter + 1;
        }
        return counter == data2.size();

    }
}
