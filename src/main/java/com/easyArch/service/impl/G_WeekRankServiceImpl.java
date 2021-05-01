package com.easyArch.service.impl;

import com.alibaba.fastjson.JSON;
import com.easyArch.entity.*;
import com.easyArch.mapper.AddressDao;
import com.easyArch.mapper.DateNumberDao;
import com.easyArch.service.G_WeekRankService;
import com.easyArch.service.SQLDataService;
import com.easyArch.util.ControllerUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
@Service
public class G_WeekRankServiceImpl implements G_WeekRankService {
    @Autowired
    SQLDataService sqlDataService;
    @Autowired
    AddressDao addressDao;
    @Override
    public String week_Rank(Address_Month address_month) {
        String addressStr = address_month.getAddress();
        String month = address_month.getMonth();
        String year = address_month.getYear();
        String[] str = ControllerUtil.slipAddress(addressStr);
        String city = str[0];
        String county = str[1];
        String street = str[2];
        String specificAddress = str[3];
//        //设置日期格式
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        //获取日期
//        String date2 = df.format(new Date());
        //设置当前日期格式 以及日期 TODO 待修改
        DateTime now=DateTime.now();
        String date2=now.toString("yyyy-MM-dd HH:mm:ss");

        //分割“-”
        String[] str2 = ControllerUtil.slipDate(date2);
        String date1 = null;
        //分割“ ”
        String[] strings = ControllerUtil.slipDate2(str2[2]);
        Integer day_ = new Integer(strings[0]);
        //拿到当前月
        String month_Temp = str2[1];

        String[] weeks = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        List<Week_num> weekNumList = new ArrayList<>();
        List<Week_withList> lists = new ArrayList<>();
        Integer dayNum = 0;
        String index;
        date1 = year + "-" + month + "-01";
        if (month.equals(month_Temp)) {
            index = date2;
        } else {
            //拿到该月有几天
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, new Integer(year));
            cal.set(Calendar.MONTH, new Integer(month));
            dayNum = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            index = year + "-" + month + "-" + dayNum;
        }
        List<String> macList=addressDao.selectMacBySpecific(city, county, street, specificAddress);
        List<DateAndNumber> list = sqlDataService.dayByMacNoTime(macList, date1, index);
        if (list.size()==0){
            return JSON.toJSONString("f");
        }
        Map<String,List<IntWeekNum>> map=new HashMap<>();
        for (int i = 0; i < 7; i++) {
            map.put(weeks[i],new ArrayList<IntWeekNum>());
        }
        //第几周
        Integer week = null;

        for (int j = 0; j < list.size(); j++) {
            //星期日
            String temp1 = ControllerUtil.getDayOfWeekByDate(list.get(j).getTime());
            String strs[]=ControllerUtil.slipDate2(list.get(j).getTime());
            String date[]=ControllerUtil.slipDate(strs[0]);
            Integer dayT=new Integer(date[2]);
            if (dayT<=7){
                week=1;
            }else if (dayT>7&&dayT<=14){
                week=2;
            }else if (dayT>14&&dayT<=21){
                week=3;
            }else if (dayT>21&&dayT<=28){
                week=4;
            }else {
                week=5;
            }
            switch (temp1) {
                case "Sun":
                    IntWeekNum intWeekNum=new IntWeekNum();
                    intWeekNum.setWeek(week);
                    intWeekNum.setNum(list.get(j).getNum());
                    map.get(temp1).add(intWeekNum);
                    break;
                case "Mon":
                    IntWeekNum intWeekNum1=new IntWeekNum();
                    intWeekNum1.setWeek(week);
                    intWeekNum1.setNum(list.get(j).getNum());
                    map.get(temp1).add(intWeekNum1);
                    break;
                case "Tue":
                    IntWeekNum intWeekNum2=new IntWeekNum();
                    intWeekNum2.setWeek(week);
                    intWeekNum2.setNum(list.get(j).getNum());
                    map.get(temp1).add(intWeekNum2);
                    break;
                case "Wed":
                    IntWeekNum intWeekNum3=new IntWeekNum();
                    intWeekNum3.setWeek(week);
                    intWeekNum3.setNum(list.get(j).getNum());
                    map.get(temp1).add(intWeekNum3);
                    break;
                case "Thu":
                    IntWeekNum intWeekNum4=new IntWeekNum();
                    intWeekNum4.setWeek(week);
                    intWeekNum4.setNum(list.get(j).getNum());
                    map.get(temp1).add(intWeekNum4);
                    break;
                case "Fri":
                    IntWeekNum intWeekNum5=new IntWeekNum();
                    intWeekNum5.setWeek(week);
                    intWeekNum5.setNum(list.get(j).getNum());
                    map.get(temp1).add(intWeekNum5);
                    break;
                case "Sat":
                    IntWeekNum intWeekNum6=new IntWeekNum();
                    intWeekNum6.setWeek(week);
                    intWeekNum6.setNum(list.get(j).getNum());
                    map.get(temp1).add(intWeekNum6);
                    break;
            }

        }
//            for (int i = 0; i < weeks.length; i++) {
//                System.out.println(map.get(weeks[i]));
//            }

        List<WebWeek>weekList=new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            WebWeek webWeek=new WebWeek();
            webWeek.setWeek_str(weeks[i]);
            try {
                webWeek.setList(ControllerUtil.listWeekSort(map.get(weeks[i])));
                weekList.add(webWeek);
            }catch (NullPointerException nullPointerException){
                continue;
            }
        }
        return JSON.toJSONString(weekList);
    }
}
