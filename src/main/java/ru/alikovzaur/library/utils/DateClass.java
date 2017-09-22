package ru.alikovzaur.library.utils;

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
public class DateClass implements Serializable {
    private String day = "01";
    private String month = String.valueOf(MonthEnum.Январь);
    private String year = "1980";
    private LinkedHashMap<String, Integer> dayList;
    private LinkedHashMap<String, String> monthList;
    private LinkedHashMap<String, Integer> yearList;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    private LinkedHashMap<String, Integer> daysOfMonth(){
        dayList = new LinkedHashMap<String, Integer>();
        int daysCount = 31;
        if (month.equals("Январь") || month.equals("Март") || month.equals("Май") ||
                month.equals("Июль") || month.equals("Август") || month.equals("Октябрь") ||
                month.equals("Декабрь")) {
            daysCount = 31;
        } else if (month.equals("Апрель") || month.equals("Июнь") || month.equals("Сентябрь") ||
                month.equals("Ноябрь")){
            daysCount = 30;
        } else if (month.equals("Февраль")){
            daysCount = 29;
        }
        for(int i = 1; i<=daysCount; i++){
            String s = String.format("%02d", i);
            dayList.put(s, i);
        }
        return dayList;
    }

    public LinkedHashMap<String, Integer> getDayList() {
            dayList = daysOfMonth();
        return dayList;
    }

    public void setDayList(LinkedHashMap<String, Integer> dayList) {
        this.dayList = dayList;
    }

    public LinkedHashMap<String, String> getMonthList() {
        if(monthList == null){
            monthList = new LinkedHashMap<String, String>();
            monthList.put("Январь", String.valueOf(MonthEnum.Январь));
            monthList.put("Февраль", String.valueOf(MonthEnum.Февраль));
            monthList.put("Март", String.valueOf(MonthEnum.Март));
            monthList.put("Апрель", String.valueOf(MonthEnum.Апрель));
            monthList.put("Май", String.valueOf(MonthEnum.Май));
            monthList.put("Июнь", String.valueOf(MonthEnum.Июнь));
            monthList.put("Июль", String.valueOf(MonthEnum.Июль));
            monthList.put("Август", String.valueOf(MonthEnum.Август));
            monthList.put("Сентябрь", String.valueOf(MonthEnum.Сентябрь));
            monthList.put("Октябрь", String.valueOf(MonthEnum.Октябрь));
            monthList.put("Ноябрь", String.valueOf(MonthEnum.Ноябрь));
            monthList.put("Декабрь", String.valueOf(MonthEnum.Декабрь));
        }
        return monthList;
    }

    public void setMonthList(LinkedHashMap<String, String> monthList) {
        this.monthList = monthList;
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

    public void setYearList(LinkedHashMap<String, Integer> yearList) {
        this.yearList = yearList;
    }

    public Date getCurrentDate(){
        int numMonth = 0;
        MonthEnum mothEnum[] = MonthEnum.values();
        for (MonthEnum me : mothEnum){
            if(month.equals(me.toString())){
                numMonth = me.ordinal()+1;
            }
        }
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

}
