package com.easyArch.service.impl;

import com.alibaba.fastjson.JSON;
import com.easyArch.entity.*;
import com.easyArch.mapper.*;
import com.easyArch.service.P_mainService;
import com.easyArch.service.SQLDataService;
import com.easyArch.util.ControllerUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.SqlCall;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class P_mainServiceImpl implements P_mainService {
    @Autowired
    private SQLDataService sqlDataService;
    @Autowired
    private P_UserDao p_userDao;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private PictureDao pictureDao;
    @Autowired
    private ColorDao colorDao;

    @Override
    public String selectP_mainData(P_User p_user) {
        String username=p_user.getUsername();
        //返回给前端整个页面需要的数据总类
        AllNumber allNumber=new AllNumber();
        List<Data_inCons> list_inCons=new ArrayList<>();

        List<Construction_inAll>list_inAll=new ArrayList<>();
        //数据之一：用户地址
        String userAddress=p_userDao
                .selectUserAddress(username);
        String []str= ControllerUtil.slipAddress(userAddress);
        String city=str[0];
        String county=str[1];
        String street=str[2];
        String specificAddress=str[3];
        //设置日期格式
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        //获取日期
//        String date2=df.format(new Date());
        //设置当前日期格式 以及日期 TODO 待修改
        DateTime now=DateTime.now();
        String date2=now.toString("yyyy-MM-dd HH:mm:ss");

        String [] str2=ControllerUtil.slipDate2(date2);
        String date1=/*"2020-08-11 00:00:00";*/str2[0]+" 00:00:00";
//            获取日期
//            String date2="2020-08-11 23:59:00";//df.format(new Date());
//            String [] str2=ControllerUtil.slipDate2(date2);
//            String date1="2020-08-11 01:00:00";//str2[0]+" 01:00:00";
        String []str3=ControllerUtil.slipDate3(str2[1]);
        Integer time_inDate=new Integer(str3[0]);
        Integer time_inList=0;

        //返回Mac和具体地址
        List<String> cons_List = addressDao
                .select_construction(specificAddress,city,county,street);
        for(int i=0;i<cons_List.size();i++){

            Construction_inAll construction_inAll=new Construction_inAll();
            construction_inAll.setConstruction(cons_List.get(i));
            List<String> pics=pictureDao.selectPic(specificAddress,city,county,street,cons_List.get(i));
            construction_inAll.setPicture_url(pics.get(0));
            List<String>mac_list=addressDao
                    .selectMacByConstruction(specificAddress,city,county,street,cons_List.get(i));

            for (int j = 0; j <mac_list.size() ; j++) {
                //红绿警报范围值
                Color1 color1=colorDao
                        .selectColor(mac_list.get(j));

                Location_tier locationTier=addressDao
                        .selectLocation_tier(mac_list.get(j));

                //盒子的对应收集到的人数
                Integer num=0;
                List<DateAndNumber>numberList=new ArrayList<>();
                numberList=sqlDataService.towHourByOneMac(mac_list.get(j),date1,date2);
                if(numberList.size()!=0){
                    time_inList=new Integer(numberList.get(numberList.size()-1).getTime());
                    if (time_inDate.equals(time_inList)||time_inDate-1==time_inList){
                        num=numberList.get(numberList.size()-1).getNum();
                    }else {
                        num=0;
                    }
                }else {
                    num=0;
                }
//
                System.out.println("----------->num:"+num +"------------>mac:"+mac_list.get(j));
                Data_inCons data_inCons =new Data_inCons();
                data_inCons.setNumber(num);
                data_inCons.setLocation(locationTier.getLocation());
                data_inCons.setColor(color1);
                data_inCons.setMac_address(mac_list.get(j));
                list_inCons.add(data_inCons);
            }
            construction_inAll.setList_inCons(list_inCons);
            list_inAll.add(construction_inAll);
        }

        allNumber.setList_inAll(list_inAll);
        allNumber.setUserAddress(specificAddress);

        return JSON.toJSONString(allNumber);
    }
}
