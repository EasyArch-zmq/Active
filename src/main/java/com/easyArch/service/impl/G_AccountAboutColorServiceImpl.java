package com.easyArch.service.impl;

import com.alibaba.fastjson.JSON;
import com.easyArch.entity.*;
import com.easyArch.mapper.AddressDao;
import com.easyArch.mapper.ColorDao;
import com.easyArch.mapper.DateNumberDao;
import com.easyArch.service.G_AccountAboutColorService;
import com.easyArch.service.SQLDataService;
import com.easyArch.util.ControllerUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class G_AccountAboutColorServiceImpl implements G_AccountAboutColorService {
    @Autowired
    AddressDao addressDao;
    @Autowired
    SQLDataService sqlDataService;
    @Autowired
    ColorDao colorDao;

    @Override
    public String getNum_color(DateAndAddress address) {
        String addressStr=address.getAddress();
        String []str= ControllerUtil.slipAddress(addressStr);
        String city=str[0];
        String county=str[1];
        String street=str[2];
        String specificAddress=str[3];

        //设置当前日期格式 以及日期
        DateTime now=DateTime.now();
        String date2=now.toString("yyyy-MM-dd HH:mm:ss");

        String [] str2=ControllerUtil.slipDate2(date2);
        String date1=str2[0]+" 01:00:00";
        String[]strings=ControllerUtil.slipDate3(str2[1]);
        String index=strings[0];
        List<YellowInfo> listYellowInfo=new ArrayList<>();//
        List<GreenInfo>listGreenInfo=new ArrayList<>();//
        List<RedInfo>listRedInfo=new ArrayList<>();//
        List<ColorAndData>listColorAndData=new ArrayList<>();
        List<DateAndNumber>list;

        List<String>listMac=addressDao.selectMacBySpecific(specificAddress, city, county, street);
        for (int j = 0; j < listMac.size(); j++) {
            list = sqlDataService.towHourByOneMac(listMac.get(j),date1,date2);
            List<DateAndNumber>resList=ControllerUtil.filterTowHour(list,index);

            Color1 color1=colorDao.selectColor(listMac.get(j));
            Integer red=new Integer(color1.getRed());
            Integer green=new Integer(color1.getGreen());
            for (int i = 0; i < resList.size(); i++) {
                if ((resList.get(i).getNum()>=green)&&(resList.get(i).getNum()<=red)){
                    YellowInfo yellowInfo=new YellowInfo();
                    yellowInfo.setNumber(resList.get(i).getNum());
                    yellowInfo.setMac_address(listMac.get(j));
                    Integer time=new Integer(resList.get(i).getTime());
                    if(i==resList.size()-1){
                        yellowInfo.setTime(time+":00-"+str2[1]);

                    }else {
                        yellowInfo.setTime(time+":00-"+(time+2)+":00");

                    }
                    listYellowInfo.add(yellowInfo);
                }else if(resList.get(i).getNum()<green&&resList.get(i).getNum()>0){
                    GreenInfo greenInfo=new GreenInfo();
                    greenInfo.setNumber(resList.get(i).getNum());
                    greenInfo.setMac_address(listMac.get(j));
                    Integer time=new Integer(resList.get(i).getTime());
                    if(i==resList.size()-1){
                        greenInfo.setTime(time+":00-"+str2[1]);

                    }else {
                        greenInfo.setTime(time+":00-"+(time+2)+":00");

                    }
                    listGreenInfo.add(greenInfo);
                }else if(resList.get(i).getNum()>=red) {
                    RedInfo redInfo=new RedInfo();
                    redInfo.setNumber(resList.get(i).getNum());
                    redInfo.setMac_address(listMac.get(j));
                    Integer time=new Integer(resList.get(i).getTime());
                    if(i==resList.size()-1){
                        redInfo.setTime(time+":00-"+str2[1]);
                    }else {
                        redInfo.setTime(time+":00-"+(time+2)+":00");
                    }
                    listRedInfo.add(redInfo);
                }
            }
        }
        ColorAndData data1=new ColorAndData("yellow");
        data1.setYellowInfoList(listYellowInfo);
        listColorAndData.add(data1);
        ColorAndData data2=new ColorAndData("red");
        data2.setRedInfoList(listRedInfo);
        listColorAndData.add(data2);
        ColorAndData data3=new ColorAndData("green");
        data3.setGreenInfoList(listGreenInfo);
        listColorAndData.add(data3);
        return JSON.toJSONString(listColorAndData);
    }
}
