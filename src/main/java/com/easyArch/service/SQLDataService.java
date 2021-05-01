package com.easyArch.service;

import com.easyArch.entity.DateAndNumber;
import java.util.List;
public interface SQLDataService {
    List<DateAndNumber> towHourByOneMac(String address, String date1, String date2);
    List<DateAndNumber> towHourByMac(List<String>addresses, String date1, String date2);

    List<DateAndNumber> dayByMac(List<String>macAddresses, String date1, String date2,String time1,String time2);
    List<DateAndNumber> dayByOneMacNoTime(String address, String date1, String date2);
    List<DateAndNumber> dayByOneMac(String address, String date1, String date2,String time1,String time2);
    List<DateAndNumber> dayByMacNoTime(List<String>macAddresses, String date1, String date2);

    List<DateAndNumber> monthByMac(List<String>macAddresses, String date1, String date2,String time1,String time2);
    List<DateAndNumber> monthByMacNoTime(List<String>macAddresses, String date1, String date2);
    List<DateAndNumber> monthByOneMac(String address, String date1, String date2,String time1,String time2);
    List<DateAndNumber> monthByOneMacNoTime(String address, String date1, String date2);

    List<DateAndNumber> yearByMac(List<String>macAddresses, String date1, String date2,String time1,String time2);
    List<DateAndNumber> yearByMacNoTime(List<String>macAddresses, String date1, String date2);
    List<DateAndNumber> yearByOneMac(String address, String date1, String date2,String time1,String time2);
    List<DateAndNumber> yearByOneMacNoTime(String address, String date1, String date2);

    Integer selectNumByOneMac(String mac_address, String date1, String date2);
    Integer selectNumByMacs(List<String>macAddresses, String date1, String date2);
   }
