package com.easyArch.service.impl;

import com.alibaba.fastjson.JSON;
import com.easyArch.entity.Mac_Loc;
import com.easyArch.entity.Mac_Num;
import com.easyArch.entity.P_User;
import com.easyArch.mapper.AddressDao;
import com.easyArch.mapper.P_UserDao;
import com.easyArch.mapper.SortDao;
import com.easyArch.service.P_TowHourSortService;
import com.easyArch.util.ControllerUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class P_TowHourSortServiceImpl implements P_TowHourSortService {
    @Autowired
    SortDao sortDao;
    @Autowired
    P_UserDao p_userDao;
    @Autowired
    AddressDao addressDao;

    @Override
    public String selectTowSortNum(P_User p_user) {
        List<Mac_Num> list=new ArrayList<>();
        String p_name=p_user.getUsername();
        String year=p_user.getYear();
        String month=p_user.getMonth();
        String day=p_user.getDay();
        String time=p_user.getTime();
        String userAddress=p_userDao
                .selectUserAddress(p_name);
        String []str= ControllerUtil.slipAddress(userAddress);
        String city=str[0];
        String county=str[1];
        String street=str[2];
        String specificAddress=str[3];

        List<Mac_Loc>Macs=addressDao
                .select_ma_lo(city,county,street,specificAddress);
        //设置日期格式
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取日期
//        String date=df.format(new Date());
        //设置当前日期格式 以及日期
        DateTime now=DateTime.now();
        String date=now.toString("yyyy-MM-dd HH:mm:ss");

        String [] str2=ControllerUtil.slipDate2(date);
        String[]strings=ControllerUtil.slipDate3(str2[1]);
        String date1=null;
        String date2=null;
        String day_=year+"-"+month+"-"+day;
        Integer time1=new Integer(time);//拿到的时间

        if (day_.equals(str2[0])){
            if ((time1+1)<new Integer(strings[0])){//与当前时间比较  TODO 待修改
                date1=/*"2020-08-11 00:00:00";*/day_+" "+time+":00:00";
                time1=time1+1;//比如3:00:00-4:59:59
                date2=/*"2020-08-11 23:59:59";*/day_+" "+time1+":59:59";

            }else if ((time1+1)==new Integer(strings[0])){
                date1=/*"2020-08-11 00:00:00";*/day_+" "+time+":00:00";
                date2=date;//就是指定时间-当前时间
            }else if (time1==new Integer(strings[0])){
                date1=/*"2020-08-11 00:00:00";*/day_+" "+time+":00:00";
                date2=date;//就是指定时间-当前时间
            }else {
                return JSON.toJSONString("f");
            }
        }else {
            date1=/*"2020-08-11 00:00:00";*/day_+" "+time+":00:00";
            time1=time1+1;
            date2=/*"2020-08-11 23:59:59";*/day_+" "+time1+":59:59";
        }

        for(int i=0;i<Macs.size();i++) {
            Mac_Num mac_num=new Mac_Num();
            //待改 TODO 待修改
            mac_num.setMac_address(Macs.get(i).getLocation());
            mac_num.setNum(sortDao.selectMacNumber(Macs.get(i).getMac_address(),date1,date2));
            list.add(mac_num);
        }
        List<Mac_Num>list1=ControllerUtil.listCustomSort(list);
        return JSON.toJSONString(list1);
    }
}
