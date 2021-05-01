package com.easyArch.service.impl;

import com.easyArch.entity.DateAndNumber;
import com.easyArch.mapper.DateNumberDao;
import com.easyArch.mapper.Date_TimeDao;
import com.easyArch.service.SQLDataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class SQLDataServiceImpl implements SQLDataService{
    private static final Logger logger=Logger.getLogger(SQLDataService.class);
    @Autowired
    private DateNumberDao dateNumberDao;
    @Autowired
    private Date_TimeDao dateTimeDao;

    /**
     * 根据MacList 日期 时间 查询按照天为单位返回 dateTimeDao
     * */
    @Override
    public List<DateAndNumber> dayByMac(List<String> macAddresses, String date1, String date2, String time1, String time2) {
        List<DateAndNumber> dateAndNumberList=new ArrayList<>();
        for (String macAddress:macAddresses){
            if (macAddress.length()<2){//TODO 查询2表的
                List<DateAndNumber> list1=dateTimeDao.selectDayByMac2(macAddress,date1,date2,time1,time2);
                dateAndNumberList.addAll(list1);
            }else {//TODO 1表
                List<DateAndNumber> list2=dateTimeDao.selectDayByMac1(macAddress,date1,date2,time1,time2);
                dateAndNumberList.addAll(list2);
            }
        }
        return dateAndNumberList;
    }

    /**
     * 根据确定的一个 Mac 日期 查询按照天为单位返回
     * */
    @Override
    public List<DateAndNumber> dayByOneMacNoTime(String address, String date1, String date2) {
        List<DateAndNumber> dateAndNumberList=new ArrayList<>();
        if (address.length()<2){//TODO 查询2表的
            List<DateAndNumber> list1=dateNumberDao.selectDayByMac2(address,date1,date2);
            dateAndNumberList.addAll(list1);
        }else {//TODO 1表
            List<DateAndNumber> list2=dateNumberDao.selectDayByMac1(address,date1,date2);
            dateAndNumberList.addAll(list2);
        }
        return dateAndNumberList;
    }
    /**
     * 根据确定的一个 Mac 日期 时间 查询按照天为单位返回 dateTimeDao
     * */
    @Override
    public List<DateAndNumber> dayByOneMac(String address, String date1, String date2, String time1, String time2) {
        List<DateAndNumber> dateAndNumberList=new ArrayList<>();
        if (address.length()<2){
            List<DateAndNumber> list1=dateTimeDao.selectDayByMac2(address,date1,date2,time1,time2);
            dateAndNumberList.addAll(list1);
        }else {//TODO 1表
            List<DateAndNumber> list2=dateTimeDao.selectDayByMac1(address,date1,date2,time1,time2);
            dateAndNumberList.addAll(list2);
        }
        return dateAndNumberList;
    }
    /**
     * 根据MacList 日期 查询按照天为单位返回
     * */
    @Override
    public List<DateAndNumber> dayByMacNoTime(List<String> macAddresses, String date1, String date2) {
        List<DateAndNumber> dateAndNumberList=new ArrayList<>();
        for (String macAddress:macAddresses){
            if (macAddress.length()<2){//TODO 查询2表的
                List<DateAndNumber> list1=dateNumberDao.selectDayByMac2(macAddress,date1,date2);
                dateAndNumberList.addAll(list1);
            }else {//TODO 1表
                List<DateAndNumber> list2=dateNumberDao.selectDayByMac1(macAddress,date1,date2);
                dateAndNumberList.addAll(list2);
            }
        }
        return dateAndNumberList;
    }

    /**
     * 根据确定的一个 Mac 日期时间 查询按照当天每两小时为单位返回 dateNumberDao
     * */
    @Override
    public List<DateAndNumber> towHourByOneMac(String address, String date1, String date2) {
        List<DateAndNumber> dateAndNumberList=new ArrayList<>();
        if (address.length()<2){//TODO 查询2表的
            List<DateAndNumber> list1=dateNumberDao.selectTwoHourByMac2(address,date1,date2);
            dateAndNumberList.addAll(list1);
        }else {//TODO 1表
            List<DateAndNumber> list2=dateNumberDao.selectTwoHourByMac1(address,date1,date2);
            dateAndNumberList.addAll(list2);
        }
        return dateAndNumberList;
    }
    /**
     * 根据MacList 日期 查询按照当天每两小时为单位返回 dateNumberDao
     * */
    @Override
    public List<DateAndNumber> towHourByMac(List<String> macAddresses, String date1, String date2){
        List<DateAndNumber> dateAndNumberList=new ArrayList<>();
        for (String macAddress:macAddresses){
            if (macAddress.length()<2){//TODO 查询2表的
                List<DateAndNumber> list1=dateNumberDao.selectTwoHourByMac2(macAddress,date1,date2);
                dateAndNumberList.addAll(list1);
            }else {//TODO 1表
                List<DateAndNumber> list2=dateNumberDao.selectTwoHourByMac1(macAddress,date1,date2);
                dateAndNumberList.addAll(list2);
            }
        }
        return dateAndNumberList;
    }
    /**
     * 根据MacList 日期 时间 查询按照月为单位返回 dateTimeDao
     * */
    @Override
    public List<DateAndNumber> monthByMac(List<String> macAddresses, String date1, String date2, String time1, String time2) {
        List<DateAndNumber> dateAndNumberList=new ArrayList<>();
        for (String macAddress:macAddresses){
            if (macAddress.length()<2){//TODO 查询2表的
                List<DateAndNumber> list1=dateTimeDao.selectMonthByMac2(macAddress,date1,date2,time1,time2);
                dateAndNumberList.addAll(list1);
            }else {//TODO 1表
                List<DateAndNumber> list2=dateTimeDao.selectMonthByMac1(macAddress,date1,date2,time1,time2);
                dateAndNumberList.addAll(list2);
            }
        }
        return dateAndNumberList;
    }
    /**
     * 根据MacList 日期 查询按照月为单位返回
     * */
    @Override
    public List<DateAndNumber> monthByMacNoTime(List<String> macAddresses, String date1, String date2) {
        List<DateAndNumber> dateAndNumberList=new ArrayList<>();
        for (String macAddress:macAddresses){
            if (macAddress.length()<2){//TODO 查询2表的
                List<DateAndNumber> list1=dateNumberDao.selectMonthByMac2(macAddress,date1,date2);
                dateAndNumberList.addAll(list1);
            }else {//TODO 1表
                List<DateAndNumber> list2=dateNumberDao.selectMonthByMac1(macAddress,date1,date2);
                dateAndNumberList.addAll(list2);
            }
        }
        return dateAndNumberList;
    }
    /**
     * 根据确定的一个 Mac 日期 时间 查询按照月为单位返回 dateTimeDao
     * */
    @Override
    public List<DateAndNumber> monthByOneMac(String address, String date1, String date2, String time1, String time2) {
        List<DateAndNumber> dateAndNumberList=new ArrayList<>();
        if (address.length()<2){
            List<DateAndNumber> list1=dateTimeDao.selectMonthByMac2(address,date1,date2,time1,time2);
            dateAndNumberList.addAll(list1);
        }else {//TODO 1表
            List<DateAndNumber> list2=dateTimeDao.selectMonthByMac1(address,date1,date2,time1,time2);
            dateAndNumberList.addAll(list2);
        }
        return dateAndNumberList;
    }
    /**
     * 根据确定的一个 Mac 日期 查询按照月为单位返回
     * */
    @Override
    public List<DateAndNumber> monthByOneMacNoTime(String address, String date1, String date2) {
        List<DateAndNumber> dateAndNumberList=new ArrayList<>();
        if (address.length()<2){//TODO 查询2表的
            List<DateAndNumber> list1=dateNumberDao.selectMonthByMac2(address,date1,date2);
            dateAndNumberList.addAll(list1);
        }else {//TODO 1表
            List<DateAndNumber> list2=dateNumberDao.selectMonthByMac1(address,date1,date2);
            dateAndNumberList.addAll(list2);
        }
        return dateAndNumberList;
    }

    /**
     * 根据MacList 日期 查询按照年为单位返回 dateTimeDao
     * */
    @Override
    public List<DateAndNumber> yearByMac(List<String> macAddresses, String date1, String date2, String time1, String time2) {
        List<DateAndNumber> dateAndNumberList=new ArrayList<>();
        for (String macAddress:macAddresses){
            if (macAddress.length()<2){//TODO 查询2表的
                List<DateAndNumber> list1=dateTimeDao.selectYearByMac2(macAddress,date1,date2,time1,time2);
                dateAndNumberList.addAll(list1);
            }else {//TODO 1表
                List<DateAndNumber> list2=dateTimeDao.selectYearByMac1(macAddress,date1,date2,time1,time2);
                dateAndNumberList.addAll(list2);
            }
        }
        return dateAndNumberList;
    }

    /**
     * 根据MacList 日期 查询按照当天每两小时为单位返回
     * */
    @Override
    public List<DateAndNumber> yearByMacNoTime(List<String> macAddresses, String date1, String date2) {
        List<DateAndNumber> dateAndNumberList=new ArrayList<>();
        for (String macAddress:macAddresses){
            if (macAddress.length()<2){//TODO 查询2表的
                List<DateAndNumber> list1=dateNumberDao.selectYearByMac2(macAddress,date1,date2);
                dateAndNumberList.addAll(list1);
            }else {//TODO 1表
                List<DateAndNumber> list2=dateNumberDao.selectYearByMac1(macAddress,date1,date2);
                dateAndNumberList.addAll(list2);
            }
        }
        return dateAndNumberList;
    }

    /**
     * 根据确定的一个 Mac 日期 时间 查询按照月为单位返回 dateTimeDao
     * */
    @Override
    public List<DateAndNumber> yearByOneMac(String address, String date1, String date2, String time1, String time2) {
        List<DateAndNumber> dateAndNumberList=new ArrayList<>();
        if (address.length()<2){
            List<DateAndNumber> list1=dateTimeDao.selectYearByMac2(address,date1,date2,time1,time2);
            dateAndNumberList.addAll(list1);
        }else {//TODO 1表
            List<DateAndNumber> list2=dateTimeDao.selectYearByMac1(address,date1,date2,time1,time2);
            dateAndNumberList.addAll(list2);
        }
        return dateAndNumberList;
    }
    /**
     * 根据确定的一个 Mac 日期 时间 查询按照月为单位返回
     * */
    @Override
    public List<DateAndNumber> yearByOneMacNoTime(String address, String date1, String date2) {
        List<DateAndNumber> dateAndNumberList=new ArrayList<>();
        if (address.length()<2){//TODO 查询2表的
            List<DateAndNumber> list1=dateNumberDao.selectYearByMac2(address,date1,date2);
            dateAndNumberList.addAll(list1);
        }else {//TODO 1表
            List<DateAndNumber> list2=dateNumberDao.selectYearByMac1(address,date1,date2);
            dateAndNumberList.addAll(list2);
        }
        return dateAndNumberList;
    }

    /**
     * 根据确定的一个 Mac 日期时间 查询按照数量为单位返回
     * */
    @Override
    public Integer selectNumByOneMac(String mac_address, String date1, String date2) {
        Integer num=0;
        if (mac_address.length()<2){//TODO 查询2表的
            num+=dateNumberDao.selectNumByMacAndTime2(mac_address,date1,date2);
        }else {//TODO 1表
            num+=dateNumberDao.selectNumByMacAndTime1(mac_address,date1,date2);
        }
        return num;
    }

    @Override
    public Integer selectNumByMacs(List<String> macAddresses, String date1, String date2) {
        Integer num=0;
        for (String macAddress:macAddresses){
            if (macAddress.length()<2){//TODO 查询2表的
                num+=dateNumberDao.selectNumByMacAndTime2(macAddress,date1,date2);
            }else {//TODO 1表
                num+=dateNumberDao.selectNumByMacAndTime1(macAddress,date1,date2);
            }
        }
        return num;
    }

}
