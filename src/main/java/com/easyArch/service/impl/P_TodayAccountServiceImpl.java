package com.easyArch.service.impl;

import com.alibaba.fastjson.JSON;
import com.easyArch.entity.Construction_inDefa;
import com.easyArch.entity.DateAndNumber;
import com.easyArch.entity.Info_inCons;
import com.easyArch.entity.P_User;
import com.easyArch.mapper.AddressDao;
import com.easyArch.mapper.DateNumberDao;
import com.easyArch.mapper.P_UserDao;
import com.easyArch.mapper.PictureDao;
import com.easyArch.service.P_TodayAccountService;
import com.easyArch.util.ControllerUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class P_TodayAccountServiceImpl implements P_TodayAccountService {
    @Autowired
    private DateNumberDao dateNumberDao;
    @Autowired
    private P_UserDao p_userDao;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private PictureDao pictureDao;

    @Override
    public String selectTodayNumber(P_User p_user) {
        List<DateAndNumber> list;
        String p_name=p_user.getUsername();
        String userAddress=p_userDao
                .selectUserAddress(p_name);
        System.out.println("p_userDdress:"+userAddress);
        List<Construction_inDefa>list_data=new ArrayList<>();
        List<Info_inCons>info_list=new ArrayList<>();
        String []str= ControllerUtil.slipAddress(userAddress);
        String city=str[0];
        String county=str[1];
        String street=str[2];
        String specificAddress=str[3];

        //返回Mac和具体地址
        List<String> cons_List = addressDao
                .select_construction(specificAddress,city,county,street);
        for(int i=0;i<cons_List.size();i++) {
            Construction_inDefa construction_inDefa = new Construction_inDefa();
            construction_inDefa.setConstruction(cons_List.get(i));
            List<String> pics=pictureDao.selectPic(specificAddress,city,county,street,cons_List.get(i));
            construction_inDefa.setPicture_url(pics.get(0));
            List<String> mac_list = addressDao
                    .select_mac(specificAddress, city, county, street, cons_List.get(i));
            for (int j = 0; j < mac_list.size(); j++) {
                Info_inCons info_inCons=new Info_inCons();
                info_inCons.setMac_address(mac_list.get(j));
                String tier=addressDao.selectLocation_tier(mac_list.get(j)).getTier();
                String location=addressDao.selectLocation_tier(mac_list.get(j)).getLocation();
                info_inCons.setTier(tier);
                info_inCons.setLocation(location);
                //设置日期格式
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //设置当前日期格式 以及日期 TODO 待修改
                DateTime now=DateTime.now();
                String date2=now.toString("yyyy-MM-dd HH:mm:ss");
                //获取日期
//                String date2="2020-08-11 23:59:59";//df.format(new Date());
                String [] str2=ControllerUtil.slipDate2(date2);
                String date1=/*"2020-08-11 01:00:00";*/str2[0]+" 01:00:00";
//                list = dateNumberDao.selectTwoHour(mac_list.get(j),"2020-07-28 00:00:00", "2020-07-28 23:59:00");
                list = dateNumberDao.selectTwoHour(mac_list.get(j),date1,date2);
//                String[] strings;
//                strings = ControllerUtil.slipDate3(str2[1]);
                List<DateAndNumber>resList=ControllerUtil.filterTowHour(list,"23");
                info_inCons.setList_inCons(resList);
                info_list.add(info_inCons);
            }
            construction_inDefa.setInfo_inCons_List(info_list);
            list_data.add(construction_inDefa);


        }
        return JSON.toJSONString(list_data);
    }
}
