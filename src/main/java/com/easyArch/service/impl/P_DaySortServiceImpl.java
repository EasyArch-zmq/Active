package com.easyArch.service.impl;

import com.alibaba.fastjson.JSON;
import com.easyArch.entity.Mac_Loc;
import com.easyArch.entity.Mac_Num;
import com.easyArch.entity.P_User;
import com.easyArch.mapper.AddressDao;
import com.easyArch.mapper.P_UserDao;
import com.easyArch.service.P_DaySortService;
import com.easyArch.service.SQLDataService;
import com.easyArch.util.ControllerUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class P_DaySortServiceImpl implements P_DaySortService {
    @Autowired
    SQLDataService sqlDataService;
    @Autowired
    P_UserDao p_userDao;
    @Autowired
    AddressDao addressDao;
    @Override
    public String selectSortNum(P_User p_user) {
        List<Mac_Num> list=new ArrayList<>();
        String p_name=p_user.getUsername();
        String userAddress=p_userDao
                .selectUserAddress(p_name);
        String []str= ControllerUtil.slipAddress(userAddress);
        String city=str[0];
        String county=str[1];
        String street=str[2];
        String specificAddress=str[3];

        List<Mac_Loc>Macs=addressDao
                .select_ma_lo(city,county,street,specificAddress);
//        //设置日期格式
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        //获取日期
//        String date2=df.format(new Date());
        //设置当前日期格式 以及日期 TODO 待修改
        DateTime now=DateTime.now();
        String date2=now.toString("yyyy-MM-dd HH:mm:ss");

        String [] str2=ControllerUtil.slipDate2(date2);
        String date1=/*"2020-08-11 00:00:00";*/str2[0]+" 01:00:00";

//            String date2="2020-08-12 23:00:00";
//            String [] str2=ControllerUtil.slipDate2(date2);
//            String date1=str2[0]+" 01:00:00";

        for(int i=0;i<Macs.size();i++) {
            Mac_Num mac_num=new Mac_Num();
            //待改
            mac_num.setMac_address(Macs.get(i).getLocation());
            mac_num.setNum(sqlDataService.selectNumByOneMac(Macs.get(i).getMac_address(),date1,date2));
            list.add(mac_num);
        }
        List<Mac_Num>list1=ControllerUtil.listCustomSort(list);
        return JSON.toJSONString(list1);
    }
}
