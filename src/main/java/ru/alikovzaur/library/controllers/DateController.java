package ru.alikovzaur.library.controllers;

import ru.alikovzaur.library.enums.MonthEnum;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Calendar;
import java.util.ResourceBundle;

@Named
@RequestScoped
public class DateController implements Serializable {
    private String day = "01";
    private MonthEnum month = MonthEnum.Январь;
    private String year = "1980";
    private LinkedHashMap<String, Integer> dayList;
    private static LinkedHashMap<String, MonthEnum> monthList;
    private static LinkedHashMap<String, Integer> yearList;

    @PostConstruct
    public void postConstruct() {
        ResourceBundle res = ResourceBundle.getBundle("nls/message", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        monthList = new LinkedHashMap<>();
        monthList.put(res.getString("january"), MonthEnum.Январь);
        monthList.put(res.getString("february"), MonthEnum.Февраль);
        monthList.put(res.getString("march"), MonthEnum.Март);
        monthList.put(res.getString("april"), MonthEnum.Апрель);
        monthList.put(res.getString("may"), MonthEnum.Май);
        monthList.put(res.getString("june"), MonthEnum.Июнь);
        monthList.put(res.getString("july"), MonthEnum.Июль);
        monthList.put(res.getString("august"), MonthEnum.Август);
        monthList.put(res.getString("september"), MonthEnum.Сентябрь);
        monthList.put(res.getString("october"), MonthEnum.Октябрь);
        monthList.put(res.getString("november"), MonthEnum.Ноябрь);
        monthList.put(res.getString("december"), MonthEnum.Декабрь);

        yearList = new LinkedHashMap<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR) - 5;
        for(int i = year; i>=1920; i--){
            yearList.put(String.valueOf(i), i);
        }
    }

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
        return monthList;
    }

    public LinkedHashMap<String, Integer> getYearList() {
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
        dayList = new LinkedHashMap<>();
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
