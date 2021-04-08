package com.oppenheimer.utils;

import com.oppenheimer.constants.TaxReliefDatas;
import com.oppenheimer.constants.WorkingClassHeroDetail;
import com.oppenheimer.pages.APIRelatedCalculations;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ReadJsonData {

    public static List<WorkingClassHeroDetail> readJsonData() {
        JsonPath jsonPath = JsonPath.from(new File("src/test/resources/testdata/testdatajson.json"));
        return jsonPath.getList("", WorkingClassHeroDetail.class);
    }

    public static boolean compareJsonData(List<TaxReliefDatas> taxReleifReceivedInTheResponse) throws ParseException {
        List<TaxReliefDatas> taxReliefCalculatedFromPassedData = getTaxReliefDataMadeOutOfPassedWorkingClassHero();
        int counter = 0;
        if (taxReliefCalculatedFromPassedData.size() == taxReleifReceivedInTheResponse.size()) {
            for (TaxReliefDatas taxReliefData1 : taxReliefCalculatedFromPassedData) {
                for (TaxReliefDatas taxReliefData2 : taxReleifReceivedInTheResponse) {

                    if (StringUtils.equals(taxReliefData1.getNatid(), taxReliefData2.getNatid()) && StringUtils.equals(taxReliefData2.getName(), taxReliefData2.getName())) {
                        StringUtils.equals(taxReliefData2.getRelief(), taxReliefData2.getRelief());
                        counter = counter + 1;

                    }

                }
            }
        }
        return counter == taxReleifReceivedInTheResponse.size();

    }

    public static List<TaxReliefDatas> getTaxReliefDataMadeOutOfPassedWorkingClassHero() throws ParseException {

        List<WorkingClassHeroDetail> workingClassHeroDetails = readJsonData();

        List<TaxReliefDatas> datas = new ArrayList<>();
        for (int i = 0; i < workingClassHeroDetails.size(); i++) {
            TaxReliefDatas data = new TaxReliefDatas();
            data.setNatid(APIRelatedCalculations.getMaskedNatId(workingClassHeroDetails.get(i).getNatid()));
            data.setName(workingClassHeroDetails.get(i).getName());
            data.setRelief(APIRelatedCalculations.calculateTaxReliefFromPassedWorkingClassHeroData(workingClassHeroDetails.get(i).getBirthday(), workingClassHeroDetails.get(i).getSalary()
                    , workingClassHeroDetails.get(i).getTax(), workingClassHeroDetails.get(i).getGender()));
            datas.add(data);
        }
        return datas;
    }

}
