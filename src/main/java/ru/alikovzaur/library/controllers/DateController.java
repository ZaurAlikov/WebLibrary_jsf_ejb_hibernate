package ru.alikovzaur.library.controllers;

import ru.alikovzaur.library.enums.MonthEnum;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Calendar;

@Named
@SessionScoped
public class DateController implements Serializable {
    private String day = "01";
    private MonthEnum month = MonthEnum.Январь;
    private String year = "1980";
    private LinkedHashMap<String, Integer> dayList;
    private static LinkedHashMap<String, MonthEnum> monthList;
    private static LinkedHashMap<String, Integer> yearList;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public MonthEnum getMonth() {
        return month;
    }

    public void setMonth(MonthEnum month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public LinkedHashMap<String, Integer> getDayList() {
            dayList = daysOfMonth();
        return dayList;
    }

    public LinkedHashMap<String, MonthEnum> getMonthList() {
        if(monthList == null){
            monthList = new LinkedHashMap<String, MonthEnum>();
            monthList.put("Январь", MonthEnum.Январь);
            monthList.put("Февраль", MonthEnum.Февраль);
            monthList.put("Март", MonthEnum.Март);
            monthList.put("Апрель", MonthEnum.Апрель);
            monthList.put("Май", MonthEnum.Май);
            monthList.put("Июнь", MonthEnum.Июнь);
            monthList.put("Июль", MonthEnum.Июль);
            monthList.put("Август", MonthEnum.Август);
            monthList.put("Сентябрь", MonthEnum.Сентябрь);
            monthList.put("Октябрь", MonthEnum.Октябрь);
            monthList.put("Ноябрь", MonthEnum.Ноябрь);
            monthList.put("Декабрь", MonthEnum.Декабрь);
        }
        return monthList;
    }

    public LinkedHashMap<String, Integer> getYearList() {
        if (yearList == null){
            yearList = new LinkedHashMap<String, Integer>();
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR) - 5;
            for(int i = year; i>=1920; i--){
                yearList.put(String.valueOf(i), i);
            }
        }
        return yearList;
    }

    public Date getCurrentDate(){
        int numMonth = month.ordinal()+1;

        String strDate = year + "-" + String.format("%02d", numMonth) + "-" + String.format("%02d", Integer.valueOf(day));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = new Date(format.parse(strDate).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private LinkedHashMap<String, Integer> daysOfMonth(){
        dayList = new LinkedHashMap<String, Integer>();
        int daysCount = 31;
        if (month == MonthEnum.Январь || month == MonthEnum.Март || month == MonthEnum.Май ||
                month == MonthEnum.Июль || month == MonthEnum.Август || month == MonthEnum.Октябрь ||
                month == MonthEnum.Декабрь) {
            daysCount = 31;
        } else if (month == MonthEnum.Апрель || month == MonthEnum.Июнь || month == MonthEnum.Сентябрь ||
                month == MonthEnum.Ноябрь){
            daysCount = 30;
        } else if (month == MonthEnum.Февраль){
            daysCount = 29;
        }
        for(int i = 1; i<=daysCount; i++){
            String s = String.format("%02d", i);
            dayList.put(s, i);
        }
        return dayList;
    }
}
