package com.easyArch.service.impl;

import com.alibaba.fastjson.JSON;
import com.easyArch.entity.YanGan;
import com.easyArch.mapper.YanGanDao;
import com.easyArch.service.G_YanGanService;
import com.easyArch.util.ControllerUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class G_YanGanServiceImpl implements G_YanGanService {
    @Autowired
    YanGanDao yanGanDao;
    @Override
    public String getYanGan() {
        String userDdress= G_TodayAccountServiceImpl.user_Address;
        String[]str= ControllerUtil.slipAddress(userDdress);
        String city=str[0];
        String county=str[1];
        String street=str[3];
        String specificAddress=str[4];
        List<YanGan> list=null;
//        //设置日期格式
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        //获取日期
//        String date2=df.format(new Date());

        //设置当前日期格式 以及日期 TODO 待修改
        DateTime now=DateTime.now();
        String date2=now.toString("yyyy-MM-dd HH:mm:ss");

        String [] str2=ControllerUtil.slipDate2(date2);
        String date1=str2[0]+" 00:00:00";
        list= yanGanDao.yanGanList(city,county,street,specificAddress, date1, date2);
//        list= yanGanDao.yanGanList(city,county,town,street,specificAddress, "2020-08-16 00:00:00", "2020-08-18 23:59:59");
        String[] strings;
        strings = ControllerUtil.slipDate3(str2[1]);
        List<YanGan> listRes=ControllerUtil.filterOneHour(list,"23");
//        List<YanGan> listRes=util.filterOneHour(list,"23");
        return JSON.toJSONString(listRes);
    }
}
