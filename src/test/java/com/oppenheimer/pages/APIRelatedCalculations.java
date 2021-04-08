package com.oppenheimer.pages;

import com.oppenheimer.constants.TaxReliefDatas;
import com.oppenheimer.utils.Common;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

public class APIRelatedCalculations {

    public static String calculateTaxReliefFromPassedWorkingClassHeroData(String birthday, String salary, String tax, String gender) throws ParseException {
        double variable = 0;
        double taxRelief;
        int bonus = 0;
        int age = Common.getAge(birthday);
        if (age <= 18) {
            variable = 1;
        } else if (age <= 35) {
            variable = 0.8;
        } else if (age <= 50) {
            variable = 0.5;
        } else if (age <= 75) {
            variable = 0.367;
        } else {
            variable = 0.05;
        }
        double gap = Double.parseDouble(salary) - Double.parseDouble(tax);
        if (gender.equals("F")) {
            bonus = 500;
        }
        taxRelief = (gap * variable) + bonus;
        if ((BigDecimal.valueOf(taxRelief).scale() > 2)) {
            taxRelief = Double.parseDouble(String.format("%.2f", taxRelief));
        } else {

            taxRelief = Math.round(taxRelief);
        }
        if (taxRelief > 0.00 && taxRelief < 50.00) {
            taxRelief = 50.00;
        }
        return String.format("%.2f", taxRelief);
    }

    /*-----------To get the masked ID-------------*/
    public static String getMaskedNatId(String natid) {
        String newString = natid;
        StringBuilder sb = new StringBuilder();
        sb.append(natid, 0, 4);
        if (natid.length() > 4) {
            String substr = StringUtils.substring(natid, 4, natid.length());
            for (char c : substr.toCharArray()) {
                sb.append("$");
            }
            return sb.toString();
        }
        return newString;
    }

    /*-----------To verify the masking is from 5th character-------------*/
    public static int verifyNetIdMaskedOnlyFromFifthCharacter(List<TaxReliefDatas> taxReliefDatas) {
        int counter = 0;
        for (TaxReliefDatas taxReliefData : taxReliefDatas) {
            String natId = taxReliefData.getNatid();
            int stringLength = natId.length();
            String substringFrom5thCharacter = natId.substring(4, stringLength);
            String substringBefore5thCharacter = natId.substring(0, 4);

            if (stringLength > 4) {
                if (StringUtils.containsOnly("$", substringFrom5thCharacter) && StringUtils.containsNone("$", substringBefore5thCharacter)) {
                    counter = counter + 1;
                }
            } else {
                if (StringUtils.equals(natId, substringBefore5thCharacter)) {
                    counter = counter + 1;
                }

            }
        }
        return counter;
    }

}
