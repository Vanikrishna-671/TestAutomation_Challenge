package com.oppenheimer.utils;

import com.oppenheimer.base.TestBase;
import com.oppenheimer.constants.TaxReliefDatas;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.DateValidator;
import org.openqa.selenium.support.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Logger;

public class Common extends TestBase {


    static Logger logger = Logger.getLogger(String.valueOf(Common.class));
    static String message = null;


    /*-----------To check the Date format-------------*/
    public static boolean verifyTheDateFormat(String date) {
        DateValidator dateValidator = new DateValidator();
        return dateValidator.isValid(date, "ddMMyyyy");
    }

    /*-----------To check the exception message-------------*/
    public static String verifyTheDay(String date) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            LocalDate fromLocalDate = LocalDate.parse(date, dateTimeFormatter);
            int day = fromLocalDate.getDayOfMonth();
            Month month = fromLocalDate.getMonth();
            int year = fromLocalDate.getYear();
        } catch (Throwable ex) {
            message = ex.getMessage();
        }
        return message;
    }

    /*-----------To get the age-------------*/
    public static int getAge(String birthday) throws ParseException {
        Date date1 = new SimpleDateFormat("ddMMyyyy").parse(birthday);
        Instant instant = date1.toInstant();
        ZonedDateTime doneDateTime = instant.atZone(ZoneId.systemDefault());
        LocalDate date = doneDateTime.toLocalDate();
        return Period.between(date, LocalDate.now()).getYears();
    }


    /*-----------To get the current date time-------------*/
    public static String getCurrentDateAndTime() {
        Date now = new Date();
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        return simpleDateFormat.format(now);
    }


    /*-----------To get the hex code format-------------*/
    public static String getHexCode(String colorCode) {
        return Color.fromString(colorCode).asHex();
    }

    /*-----------To check the Date format-------------*/
    public static String getColor(String hexCode) {
        String color;
        if ("#dc3545".equals(hexCode)) {
            color = "Red";
        } else {
            color = null;
        }
        return color;
    }

}
