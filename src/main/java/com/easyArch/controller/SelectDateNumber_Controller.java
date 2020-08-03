package com.easyArch.controller;

import com.alibaba.fastjson.JSON;
import com.easyArch.Application;
import com.easyArch.entity.*;
import com.easyArch.mapper.AddressDao;
import com.easyArch.mapper.DateNumberDao;
import com.easyArch.util.ControllerUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SelectDateNumber_Controller {
    private static final Logger logger=Logger.getLogger(Application.class);
    @Autowired
    DateNumberDao dateNumberDao;
    @Autowired
    AddressDao addressDao;

    @ResponseBody
    @RequestMapping(value = "selectDateNumber"
            , produces = "application/json;charset=utf-8"
            , method = RequestMethod.POST)
    public String selectDateNumber(@RequestBody DateAndAddress dateAndAddress) {
        logger.info("selectDateNumber!!!!!");
        System.out.println("selectDateNumber!!!!!");
        ControllerUtil util = new ControllerUtil();
        if (dateAndAddress != null) {
            List<DateAndNumber> list;
            String year1 = dateAndAddress.getYear1();
            String month1 = dateAndAddress.getMonth1();
            String day1 = dateAndAddress.getDay1();
            String year2 = dateAndAddress.getYear2();
            String month2 = dateAndAddress.getMonth2();
            String day2 = dateAndAddress.getDay2();
            System.out.println("year1:"+year1);
            System.out.println("month1:"+month1);
            System.out.println("day1:"+day1);
            System.out.println("year2:"+year2);
            System.out.println("month2:"+month2);
            System.out.println("day2:"+day2);

            String address = dateAndAddress.getAddress();
            String[] str = util.slipAddress(address);

            System.out.println("address: "+address);

            if (year1 != null && year2 != null) {
                if (month1 != null && month2 != null) {
                    if (day1 != null && day2 != null) {
                        String str1 = year1 + "-" + month1 + "-" + day1;
                        String str2 = year2 + "-" + month2 + "-" + day2;
                        //查询一天里
                        if (str1.equals(str2)) {
                            str1=str1+" 00:00:00";
                            str2=str2+" 23:59:29";
                            if (str.length == 1) {
                                System.out.println(str[0]);
                                list = dateNumberDao.selectTwoHour1(str[0], str1, str2);
                                List<DateAndNumber>resList=util.filterTowHour(list,"23");
                                return JSON.toJSONString(resList);
                            }
                            if (str.length == 2) {
                                list = dateNumberDao.selectTwoHour2(str[0],str[1], str1, str2);
                                List<DateAndNumber>resList=util.filterTowHour(list,"23");
                                return JSON.toJSONString(resList);
                            }
                            if (str.length == 3) {
                                System.out.println("xxxxxxxxxxxxxxx");
                                list = dateNumberDao.selectTwoHour3(str[0], str[1],str[2],str1, str2);
                                List<DateAndNumber>resList=util.filterTowHour(list,"23");
                                return JSON.toJSONString(resList);
                            }

                        }
                        //按照天为单位查询多天
                        if (str.length == 1) {
                            list = dateNumberDao.selectDay1(str[0], str1, str2);
                            return JSON.toJSONString(list);
                        }
                        if (str.length == 2) {
                            list = dateNumberDao.selectDay2(str[0],str[1], str1, str2);
                            return JSON.toJSONString(list);
                        }
                        if (str.length == 3) {
                            list = dateNumberDao.selectDay3(str[0],str[1],str[2], str1, str2);
                            return JSON.toJSONString(list);
                        }


                    }
                    String str1 = year1 + "-" + month1 + "-01";
                    String str2 = year2 + "-" + month2 + "-01";
                    //查询一个月里的31天
                    if (str1.equals(str2)) {
                        str2 = year2 + "-" + month2 + "-31";
                        if (str.length == 1) {
                            list = dateNumberDao.selectDay1(str[0], str1, str2);
                            return JSON.toJSONString(list);
                        }
                        if (str.length == 2) {
                            list = dateNumberDao.selectDay2(str[0],str[1], str1, str2);
                            return JSON.toJSONString(list);
                        }
                        if (str.length == 3) {
                            list = dateNumberDao.selectDay3(str[0],str[1],str[2], str1, str2);
                            return JSON.toJSONString(list);
                        }

                    }
                    //查询以月为单位的多个月
                    str2 = year2 + "-" + month2 + "-31";
                    if (str.length==1){
                        list = dateNumberDao.selectMonth1(str[0], str1, str2);
                        return JSON.toJSONString(list);
                    }
                    if (str.length==2){
                        list = dateNumberDao.selectMonth2(str[0],str[1], str1, str2);
                        return JSON.toJSONString(list);
                    }
                    if (str.length==3){
                        list = dateNumberDao.selectMonth3(str[0],str[1],str[2], str1, str2);
                        return JSON.toJSONString(list);
                    }

                }
                String str1 = year1;
                String str2 = year2;
                //查询一年内
                if (str1.equals(str2)) {
                    str1 = year1 + "-01" + "-01";
                    str2 = year2 + "-12" + "-31";
                    if (str.length==1){
                        list = dateNumberDao.selectMonth1(str[0], str1, str2);
                        return JSON.toJSONString(list);
                    }
                    if (str.length==2){
                        list = dateNumberDao.selectMonth2(str[0],str[1], str1, str2);
                        return JSON.toJSONString(list);
                    }
                    if (str.length==3){
                        list = dateNumberDao.selectMonth3(str[0],str[1],str[2], str1, str2);
                        return JSON.toJSONString(list);
                    }
                }
                //查询多年
                str1 = year1 + "-01" + "-01";
                str2 = year2 + "-12" + "-31";
                if (str.length==1){
                    list = dateNumberDao.selectYear1(str[0], str1, str2);
                    return JSON.toJSONString(list);
                }
                if (str.length==2){
                    list = dateNumberDao.selectYear2(str[0],str[1], str1, str2);
                    return JSON.toJSONString(list);
                }
                if (str.length==3){
                    list = dateNumberDao.selectYear3(str[0],str[1],str[2], str1, str2);
                    return JSON.toJSONString(list);
                }
            }
        }

        return JSON.toJSONString("f");
        }
    }

