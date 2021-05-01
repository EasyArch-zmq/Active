package com.easyArch.service.impl;

import com.alibaba.fastjson.JSON;
import com.easyArch.entity.DateAndAddress;
import com.easyArch.entity.DateAndNumber;
import com.easyArch.entity.DateNumberAll;
import com.easyArch.mapper.AddressDao;
import com.easyArch.mapper.DateNumberDao;
import com.easyArch.service.G_SuccessiveTimeService;
import com.easyArch.service.SQLDataService;
import com.easyArch.util.ControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class G_SuccessiveTimeServiceImpl implements G_SuccessiveTimeService {
    @Autowired
    SQLDataService sqlDataService;
    @Autowired
    AddressDao addressDao;
    @Override
    public String selectDateNumber1(DateAndAddress dateAndAddress) {
        int state=0;
        List<DateAndNumber> list=new ArrayList<>();
        String city=null;
        String county=null;
        String street=null;
        String specific_address=null;
        String mac_address=null;
        String[] str=null;
        List<String> macList=null;
        String year1 = dateAndAddress.getYear1();
        String month1 = dateAndAddress.getMonth1();
        String day1 = dateAndAddress.getDay1();
        String year2 = dateAndAddress.getYear2();
        String month2 = dateAndAddress.getMonth2();
        String day2 = dateAndAddress.getDay2();
        String myTime1=dateAndAddress.getTime1();
        String myTime2=dateAndAddress.getTime2();

        DateNumberAll dateNumberAll=new DateNumberAll();

        String mac_address1=dateAndAddress.getMac_address();
        String address = dateAndAddress.getAddress();

        if(address!=null&&mac_address1.equals("null")){
            mac_address=null;
            str = ControllerUtil.slipAddress(address);
            city=str[0];;
            county=str[1];;
            street=str[2];
            specific_address=str[3];
            if(county.equals("null")&&street.equals("null")&&specific_address.equals("null")){
                macList=addressDao.selectMacByCity(city);/*查看天津市的总的*/
                dateNumberAll.setAddress(city);
            }else if (street.equals("null")&&specific_address.equals("null")){
                macList=addressDao.selectMacByCounty(city,county);/*查看到区的*/
                dateNumberAll.setAddress(county);
            }else if(specific_address.equals("null")){
                macList=addressDao.selectMacByStreet(city, county,street);/*查看到街的*/
                dateNumberAll.setAddress(street);
            }else {
                macList=addressDao.selectMacBySpecific(city, county, street, specific_address);/*查看到具体位置的*/
                dateNumberAll.setAddress(specific_address);
            }
        }else if(!mac_address1.equals("null")) {
//                String REGEX_CHINESE = "[\u4e00-\u9fa5]";
//                Pattern pat = Pattern.compile(REGEX_CHINESE);
//                Matcher mat = pat.matcher(mac_address1);
//                mac_address=mat.replaceAll("");
            String []strings=ControllerUtil.slipDate2(mac_address1);
            mac_address=strings[1];
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
                            Integer dayt = new Integer(day2) + 1;
                            str2 = year2 + "-" + month2 + "-" + dayt + " 00:59:59";
                        } else {
                            str1 = str1 + " " + myTime1;
                            str2 = str2 + " " + myTime2;
                        }
                        if (mac_address == null && address != null) {
                            if (myTime1 == null && myTime2 == null) {
                                list = sqlDataService.towHourByMac(macList, str1, str2);
                                list = ControllerUtil.filterTowHour(list, "23");

                            } else {
                                DateAndNumber andNumber = new DateAndNumber();
                                andNumber.setNum(sqlDataService.selectNumByMacs(macList, str1, str2));
                                andNumber.setTime(str1 + "至" + str2);
                                list.add(andNumber);
                            }
                            dateNumberAll.setList(list);
                            return JSON.toJSONString(dateNumberAll);
                        } else if (mac_address != null) {
                            if (myTime1 == null && myTime2 == null) {
                                list = sqlDataService.towHourByOneMac(mac_address, str1, str2);
                                list = ControllerUtil.filterTowHour(list, "23");
                            } else {
                                DateAndNumber andNumber = new DateAndNumber();
                                andNumber.setNum(sqlDataService.selectNumByOneMac(mac_address, str1, str2));
                                andNumber.setTime(str1 + "至" + str2);
                                list.add(andNumber);
                            }
                            dateNumberAll.setList(list);
                            return JSON.toJSONString(dateNumberAll);
                        }
                    }
                    //按照天为单位查询多天
                    if (mac_address == null && address != null) {
                        if (myTime1 == null) {
                            Integer dayt = new Integer(day2) + 1;
                            str2 = year2 + "-" + month2 + "-" + dayt;
                            list = sqlDataService.dayByMacNoTime(macList, str1, str2);
                        } else {
                            str1 = str1 + " " + myTime1;
                            str2 = str2 + " " + myTime2;
                            DateAndNumber andNumber = new DateAndNumber();
                            andNumber.setNum(sqlDataService.selectNumByMacs(macList, str1, str2));
                            andNumber.setTime(str1 + "至" + str2);
                            list.add(andNumber);
                        }
                        dateNumberAll.setList(list);
                        return JSON.toJSONString(dateNumberAll);
                    } else if (mac_address != null) {
                        if (myTime1 == null) {
                            list = sqlDataService.dayByOneMacNoTime(mac_address, str1, str2);
                        } else {
                            str1 = str1 + " " + myTime1;
                            str2 = str2 + " " + myTime2;
                            DateAndNumber andNumber = new DateAndNumber();
                            andNumber.setNum(sqlDataService.selectNumByOneMac(mac_address, str1, str2));
                            andNumber.setTime(str1 + "至" + str2);
                            list.add(andNumber);
                        }
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
                        list = sqlDataService.dayByMacNoTime(macList, str1, str2);
                        dateNumberAll.setList(list);
                        return JSON.toJSONString(dateNumberAll);
                    } else if (mac_address != null) {
                        if (str1.equals(str2)) {
                            str2 = year2 + "-" + month2 + "-31";
                            list = sqlDataService.dayByOneMacNoTime(mac_address, str1, str2);
                            dateNumberAll.setList(list);
                            return JSON.toJSONString(dateNumberAll);

                        }
                    }
                }
                //查询以月为单位的多个月
                str2 = year2 + "-" + month2 + "-31";
                if (mac_address == null && address != null) {
                    list = sqlDataService.monthByMacNoTime(macList, str1, str2);
                    dateNumberAll.setList(list);
                    return JSON.toJSONString(dateNumberAll);
                } else if (mac_address != null) {
                    list = sqlDataService.monthByOneMacNoTime(mac_address, str1, str2);
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
                    list = sqlDataService.monthByMacNoTime(macList, str1, str2);
                    dateNumberAll.setList(list);
                    return JSON.toJSONString(dateNumberAll);
                } else if (mac_address != null) {
                    list = sqlDataService.monthByOneMacNoTime(mac_address, str1, str2);
                    dateNumberAll.setList(list);
                    return JSON.toJSONString(dateNumberAll);
                }

            }
            //查询多年
            str1 = year1 + "-01" + "-01";
            str2 = year2 + "-12" + "-31";
            if (mac_address == null && address != null) {
                list = sqlDataService.yearByMacNoTime(macList, str1, str2);
                dateNumberAll.setList(list);
                return JSON.toJSONString(dateNumberAll);
            } else if (mac_address != null) {
                list = sqlDataService.yearByOneMacNoTime(mac_address, str1, str2);
                dateNumberAll.setList(list);
                return JSON.toJSONString(dateNumberAll);
            }
        }
        return JSON.toJSONString("f");
    }
}
