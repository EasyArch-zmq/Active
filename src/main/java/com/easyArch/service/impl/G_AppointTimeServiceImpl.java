package com.easyArch.service.impl;

import com.alibaba.fastjson.JSON;
import com.easyArch.Application;
import com.easyArch.entity.DateAndAddress;
import com.easyArch.entity.DateAndNumber;
import com.easyArch.entity.DateNumberAll;
import com.easyArch.mapper.AddressDao;
import com.easyArch.mapper.DateNumberDao;
import com.easyArch.mapper.Date_Box_TimeDao;
import com.easyArch.mapper.Date_TimeDao;
import com.easyArch.service.G_AppointTimeService;
import com.easyArch.util.ControllerUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class G_AppointTimeServiceImpl implements G_AppointTimeService {
    private static final Logger logger=Logger.getLogger(G_AppointTimeServiceImpl.class);
    @Autowired
    DateNumberDao dateNumberDao;
    @Autowired
    AddressDao addressDao;
    @Autowired
    Date_TimeDao date_timeDao;
    @Autowired
    Date_Box_TimeDao dateBoxTimeDao;
    @Override
    public String selectDateNumber2(DateAndAddress dateAndAddress) {
        List<DateAndNumber> list;
        String year1 = dateAndAddress.getYear1();
        String month1 = dateAndAddress.getMonth1();
        String day1 = dateAndAddress.getDay1();
        String year2 = dateAndAddress.getYear2();
        String month2 = dateAndAddress.getMonth2();
        String day2 = dateAndAddress.getDay2();
        String myTime1=dateAndAddress.getTime1();
        String myTime2=dateAndAddress.getTime2();
        if(myTime1.equals("00:00:00")){
            myTime1=null;
            myTime2=null;
        }
        int state=0;
        DateNumberAll dateNumberAll=new DateNumberAll();

        /**
         *
         * 地址和mac地址
         */
        String[]str =null;
        String mac_address=null;
        String city=null;
        String county=null;
        String street=null;
        String specific_address=null;
        String address = dateAndAddress.getAddress();
        String mac_address1=dateAndAddress.getMac_address();
        if(address!=null&&mac_address1.equals("null")){
            mac_address=null;
            str = ControllerUtil.slipAddress(address);
            city=str[0];
            county=str[1];
            street=str[2];
            specific_address=str[3];
            if(county.equals("null")&&street.equals("null")&&specific_address.equals("null")){
                state=1;
                dateNumberAll.setAddress(city);
            }else if (street.equals("null")&&specific_address.equals("null")){
                state=2;
                dateNumberAll.setAddress(county);
            }else if(specific_address.equals("null")){
                state=3;
                dateNumberAll.setAddress(street);
            }else {
                state=4;
                dateNumberAll.setAddress(specific_address);
            }
        }else if(!mac_address1.equals("null")) {
            /**
             * mac地址
             */
//                String REGEX_CHINESE = "[\u4e00-\u9fa5]";
//                Pattern pat = Pattern.compile(REGEX_CHINESE);
//                Matcher mat = pat.matcher(mac_address1);
//                mac_address =mat.replaceAll("");
            logger.info("数组越界问题 原数据："+address);
            String []strings=ControllerUtil.slipDate2(mac_address1);
            logger.info("数组越界问题 分割后数据："+strings[1]);
            mac_address=strings[1];

        }
        if (myTime1!=null&&myTime2!=null){
            dateNumberAll.setTime1(myTime1);
            dateNumberAll.setTime2(myTime2);
        }
        System.out.println("address: "+address);
        if (year1!=null&& year2!=null) {
            if (month1 != null && month2 != null) {
                if (day1 != null && day2 != null) {
                    String str1 = year1 + "-" + month1 + "-" + day1;
                    String str2 = year2 + "-" + month2 + "-" + day2;
                    //查询一天里
                    if (str1.equals(str2)) {
                        System.out.println("str1=str2------------------");
                        if (myTime1 == null && myTime2 == null) {
                            System.out.println();
                            str1 = str1 + " 01:00:00";
                            Integer dayt=new Integer(day2)+1;
                            str2 = year2 + "-" + month2 + "-" + dayt+ " 00:59:59";
                        } else {
                            str1 = str1 + " " + myTime1;
                            str2 = str2 + " " + myTime2;
                        }

                        /**
                         * 盒子非盒子
                         */
                        if (mac_address == null && address != null) {
                            switch (state) {
                                case 1:
                                    list = ((myTime1 == null) ? dateNumberDao.selectTwoHour_Ci(city, str1, str2)
                                            : date_timeDao.selectDay_Ci(city, str1, str2, myTime1, myTime2));
                                    dateNumberAll.setList(list);
                                    return JSON.toJSONString(dateNumberAll);
                                case 2:
                                    list = ((myTime1 == null) ?
                                            dateNumberDao.selectTwoHour_Co(city, county, str1, str2)
                                            : date_timeDao.selectDay_Co(city, county, str1, str2, myTime1, myTime2));
                                    if (myTime1 == null) {
                                        list = ControllerUtil.filterTowHour(list, "23");
                                    }
                                    dateNumberAll.setList(list);
                                    return JSON.toJSONString(dateNumberAll);
                                case 3:
                                    list = ((myTime1 == null) ?
                                            dateNumberDao.selectTwoHour_St(city, county,street, str1, str2)
                                            : date_timeDao.selectDay_St(city, county,street, str1, str2, myTime1, myTime2));
                                    if (myTime1 == null) {
                                        list = ControllerUtil.filterTowHour(list, "23");
                                    }
                                    dateNumberAll.setList(list);
                                    return JSON.toJSONString(dateNumberAll);
                                case 4:
                                    list = ((myTime1 == null) ?
                                            dateNumberDao.selectTwoHour_Sp(city, county, street, specific_address, str1, str2)
                                            : date_timeDao.selectDay_Sp(city, county, street, specific_address, str1, str2, myTime1, myTime2));
                                    if (myTime1 == null) {
                                        list = ControllerUtil.filterTowHour(list, "23");
                                    }
                                    dateNumberAll.setList(list);
                                    return JSON.toJSONString(dateNumberAll);
                                default:
                            }
                        } else if (mac_address != null) {
                            list = ((myTime1 == null) ?
                                    dateNumberDao.selectTwoHour(mac_address, str1, str2)
                                    : dateBoxTimeDao.selectDay(mac_address, str1, str2, myTime1, myTime2));
                            if (myTime1 == null) {
                                list = ControllerUtil.filterTowHour(list, "23");
                            }

                            dateNumberAll.setList(list);
                            return JSON.toJSONString(dateNumberAll);
                        }
                    }
                    //按照天为单位查询多天
                    Integer dayt=new Integer(day2)+1;
                    str2 = year2 + "-" + month2 + "-" + dayt;
                    if (mac_address == null && address != null) {
                        switch (state) {
                            case 1:
                                list = ((myTime1 == null) ?
                                        dateNumberDao.selectDay_Ci(city, str1, str2)
                                        : date_timeDao.selectDay_Ci(city, str1, str2, myTime1, myTime2));
                                dateNumberAll.setList(list);
                                return JSON.toJSONString(dateNumberAll);
                            case 2:
                                list = ((myTime1 == null) ?
                                        dateNumberDao.selectDay_Co(city, county, str1, str2)
                                        : date_timeDao.selectDay_Co(city, county, str1, str2, myTime1, myTime2));
                                dateNumberAll.setList(list);
                                return JSON.toJSONString(dateNumberAll);
                            case 3:
                                list = ((myTime1 == null) ?
                                        dateNumberDao.selectDay_St(city, county,street, str1, str2)
                                        : date_timeDao.selectDay_St(city, county,street, str1, str2, myTime1, myTime2));
                                dateNumberAll.setList(list);
                                return JSON.toJSONString(dateNumberAll);
                            case 4:
                                list = ((myTime1 == null) ?
                                        dateNumberDao.selectDay_Sp(city, county,street, specific_address, str1, str2)
                                        : date_timeDao.selectDay_Sp(city, county,street, specific_address, str1, str2, myTime1, myTime2));
                                dateNumberAll.setList(list);
                                return JSON.toJSONString(dateNumberAll);
                            default:
                                return JSON.toJSONString("f");
                        }
                    }else if(mac_address!=null){
                        list = ((myTime1==null)?
                                dateNumberDao.selectDay(mac_address, str1, str2)
                                :dateBoxTimeDao.selectDay(mac_address, str1, str2,myTime1,myTime2));
                        dateNumberAll.setList(list);
                        return JSON.toJSONString(dateNumberAll);
                    }
                }
                String str1 = year1 + "-" + month1 + "-01";
                String str2 = year2 + "-" + month2 + "-01";
                //查询一个月里的31天
                if (str1.equals(str2)) {
                    str2 = year2 + "-" + month2 + "-31";
                    if (mac_address == null && address != null) {
                        switch (state) {
                            case 1:
                                list = ((myTime1 == null) ?
                                        dateNumberDao.selectDay_Ci(city, str1, str2)
                                        : date_timeDao.selectDay_Ci(city, str1, str2, myTime1, myTime2));
                                dateNumberAll.setList(list);
                                return JSON.toJSONString(dateNumberAll);
                            case 2:
                                list = ((myTime1 == null) ?
                                        dateNumberDao.selectDay_Co(city, county, str1, str2)
                                        : date_timeDao.selectDay_Co(city, county, str1, str2, myTime1, myTime2));
                                dateNumberAll.setList(list);
                                return JSON.toJSONString(dateNumberAll);
                            case 3:
                                list = ((myTime1 == null) ?
                                        dateNumberDao.selectDay_St(city, county,street, str1, str2)
                                        : date_timeDao.selectDay_St(city, county,street, str1, str2, myTime1, myTime2));
                                dateNumberAll.setList(list);
                                return JSON.toJSONString(dateNumberAll);
                            case 4:
                                list = ((myTime1 == null) ?
                                        dateNumberDao.selectDay_Sp(city, county,street, specific_address, str1, str2)
                                        : date_timeDao.selectDay_Sp(city, county,street, specific_address, str1, str2, myTime1, myTime2));
                                dateNumberAll.setList(list);
                                return JSON.toJSONString(dateNumberAll);
                            default:
                                return JSON.toJSONString("f");
                        }
                    }else if (mac_address!=null){
                        list = ((myTime1==null)?
                                dateNumberDao.selectDay(mac_address, str1, str2)
                                :dateBoxTimeDao.selectDay(mac_address, str1, str2,myTime1,myTime2));
                        dateNumberAll.setList(list);
                        return JSON.toJSONString(dateNumberAll);
                    }
                }
                //查询以月为单位的多个月
                str2 = year2 + "-" + month2 + "-31";
                if (mac_address == null && address != null) {
                    switch (state) {
                        case 1:
                            list = ((myTime1 == null) ?
                                    dateNumberDao.selectMonth_Ci(city, str1, str2)
                                    : date_timeDao.selectMonth_Ci(city, str1, str2, myTime1, myTime2));
                            dateNumberAll.setList(list);
                            return JSON.toJSONString(dateNumberAll);
                        case 2:
                            list = ((myTime1 == null) ?
                                    dateNumberDao.selectMonth_Co(city, county, str1, str2)
                                    : date_timeDao.selectMonth_Co(city, county, str1, str2, myTime1, myTime2));
                            dateNumberAll.setList(list);
                            return JSON.toJSONString(dateNumberAll);
                        case 3:
                            list = ((myTime1 == null) ?
                                    dateNumberDao.selectMonth_St(city, county,street, str1, str2)
                                    : date_timeDao.selectMonth_St(city, county,street, str1, str2, myTime1, myTime2));
                            dateNumberAll.setList(list);
                            return JSON.toJSONString(dateNumberAll);
                        case 4:
                            list = ((myTime1 == null) ?
                                    dateNumberDao.selectMonth_Sp(city, county,street, specific_address, str1, str2)
                                    : date_timeDao.selectMonth_Sp(city, county,street, specific_address, str1, str2, myTime1, myTime2));
                            dateNumberAll.setList(list);
                            return JSON.toJSONString(dateNumberAll);
                        default:
                            return JSON.toJSONString("f");
                    }
                }else if (mac_address!=null){
                    list = ((myTime1==null)?
                            dateNumberDao.selectMonth(mac_address, str1, str2)
                            :dateBoxTimeDao.selectMonth(mac_address, str1, str2,myTime1,myTime2));
                    dateNumberAll.setList(list);
                    return JSON.toJSONString(dateNumberAll);
                }
            }
            String str1 = year1;
            String str2 = year2;
            //查询一年内
            if (str1.equals(str2)) {
                str1 = year1 + "-01" + "-01";
                str2 = year2 + "-12" + "-31";
                if (mac_address == null && address != null) {
                    switch (state) {
                        case 1:
                            list = ((myTime1 == null) ?
                                    dateNumberDao.selectMonth_Ci(city, str1, str2)
                                    : date_timeDao.selectMonth_Ci(city, str1, str2, myTime1, myTime2));
                            dateNumberAll.setList(list);
                            return JSON.toJSONString(dateNumberAll);
                        case 2:
                            list = ((myTime1 == null) ?
                                    dateNumberDao.selectMonth_Co(city, county, str1, str2)
                                    : date_timeDao.selectMonth_Co(city, county, str1, str2, myTime1, myTime2));
                            dateNumberAll.setList(list);
                            return JSON.toJSONString(dateNumberAll);
                        case 3:
                            list = ((myTime1 == null) ?
                                    dateNumberDao.selectMonth_St(city, county,street, str1, str2)
                                    : date_timeDao.selectMonth_St(city, county,street, str1, str2, myTime1, myTime2));
                            dateNumberAll.setList(list);
                            return JSON.toJSONString(dateNumberAll);
                        case 4:
                            list = ((myTime1 == null) ?
                                    dateNumberDao.selectMonth_Sp(city, county,street, specific_address, str1, str2)
                                    : date_timeDao.selectMonth_Sp(city, county,street, specific_address, str1, str2, myTime1, myTime2));
                            dateNumberAll.setList(list);
                            return JSON.toJSONString(dateNumberAll);
                        default:
                            return JSON.toJSONString("f");
                    }
                }else if (mac_address!=null){
                    list = ((myTime1==null)?
                            dateNumberDao.selectMonth(mac_address, str1, str2)
                            :dateBoxTimeDao.selectMonth(mac_address, str1, str2,myTime1,myTime2));
                    dateNumberAll.setList(list);
                    return JSON.toJSONString(dateNumberAll);
                }
            }
            //查询多年
            str1 = year1 + "-01" + "-01";
            str2 = year2 + "-12" + "-31";
            if (mac_address==null) {
                if (mac_address == null && address != null) {
                    switch (state) {
                        case 1:
                            list = ((myTime1 == null) ?
                                    dateNumberDao.selectYear_Ci(city, str1, str2)
                                    : date_timeDao.selectYear_Ci(city, str1, str2, myTime1, myTime2));
                            dateNumberAll.setList(list);
                            return JSON.toJSONString(dateNumberAll);
                        case 2:
                            list = ((myTime1 == null) ?
                                    dateNumberDao.selectYear_Co(city, county, str1, str2)
                                    : date_timeDao.selectYear_Co(city, county, str1, str2, myTime1, myTime2));
                            dateNumberAll.setList(list);
                            return JSON.toJSONString(dateNumberAll);
                        case 3:
                            list = ((myTime1 == null) ?
                                    dateNumberDao.selectYear_St(city, county,street, str1, str2)
                                    : date_timeDao.selectYear_St(city, county,street, str1, str2, myTime1, myTime2));
                            dateNumberAll.setList(list);
                            return JSON.toJSONString(dateNumberAll);
                        case 4:
                            list = ((myTime1 == null) ?
                                    dateNumberDao.selectYear_Sp(city, county,street, specific_address, str1, str2)
                                    : date_timeDao.selectYear_Sp(city, county,street, specific_address, str1, str2, myTime1, myTime2));
                            dateNumberAll.setList(list);
                            return JSON.toJSONString(dateNumberAll);
                        default:
                            return JSON.toJSONString("f");
                    }
                }else if (mac_address!=null){
                    list = ((myTime1==null)?
                            dateNumberDao.selectYear(mac_address, str1, str2)
                            :dateBoxTimeDao.selectYear(mac_address, str1, str2,myTime1,myTime2));
                    dateNumberAll.setList(list);
                    return JSON.toJSONString(dateNumberAll);
                }
            }
        }
        return JSON.toJSONString("f");
    }
}
