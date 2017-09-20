package ru.alikovzaur.library;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.text.Format;
import java.util.LinkedHashMap;

@Named
@SessionScoped
public class DateClass implements Serializable {
    private LinkedHashMap<String, Integer> dayList;
    private LinkedHashMap<String, Integer> monthList;
    private LinkedHashMap<String, Integer> yearList;

    public LinkedHashMap<String, Integer> getDayList() {
        dayList = new LinkedHashMap<String, Integer>();
        for(int i = 1; i<=31; i++){
            String s = String.format("%02d", i);
            dayList.put(s, i);
        }
        return dayList;
    }

    public void setDayList(LinkedHashMap<String, Integer> dayList) {
        this.dayList = dayList;
    }

    public LinkedHashMap<String, Integer> getMonthList() {
        monthList = new LinkedHashMap<String, Integer>();
        monthList.put("Январь", 1);
        monthList.put("Февраль", 2);
        monthList.put("Март", 3);
        monthList.put("Апрель", 4);
        monthList.put("Май", 5);
        monthList.put("Июнь", 6);
        monthList.put("Июль", 7);
        monthList.put("Август", 8);
        monthList.put("Сентябрь", 9);
        monthList.put("Октябрь", 10);
        monthList.put("Ноябрь", 11);
        monthList.put("Декабрь", 12);
        return monthList;
    }

    public void setMonthList(LinkedHashMap<String, Integer> monthList) {
        this.monthList = monthList;
    }

    public LinkedHashMap<String, Integer> getYearList() {
        yearList = new LinkedHashMap<String, Integer>();
        for(int i = 1900; i<=2015; i++){
            yearList.put(String.valueOf(i), i);
        }
        return yearList;
    }

    public void setYearList(LinkedHashMap<String, Integer> yearList) {
        this.yearList = yearList;
    }
}
