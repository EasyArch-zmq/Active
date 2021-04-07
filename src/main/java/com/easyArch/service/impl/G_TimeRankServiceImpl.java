package com.easyArch.service.impl;

import com.alibaba.fastjson.JSON;
import com.easyArch.entity.DateAndAddress;
import com.easyArch.entity.DateAndNumber;
import com.easyArch.entity.Location_tier;
import com.easyArch.entity.Mac_ListTimeSort;
import com.easyArch.mapper.AddressDao;
import com.easyArch.mapper.DateNumberDao;
import com.easyArch.service.G_TimeRankService;
import com.easyArch.util.ControllerUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class G_TimeRankServiceImpl implements G_TimeRankService {
    @Autowired
    AddressDao addressDao;
    @Autowired
    DateNumberDao dateNumberDao;
    @Override
    public String time_Rank(DateAndAddress address) {
        String addressStr = address.getAddress();
        String[] str = ControllerUtil.slipAddress(addressStr);
        String city = str[0];
        String county = str[1];
        String street = str[2];
        String specificAddress = str[3];

        //设置日期格式
        //设置当前日期格式 以及日期
        DateTime now=DateTime.now();
        String date2=now.toString("yyyy-MM-dd HH:mm:ss");
        String[] str2 = ControllerUtil.slipDate2(date2);

        String date1 = str2[0] + " 01:00:00";
        String index=str2[1];
        List<DateAndNumber> list;
        List<Mac_ListTimeSort>listTimeSortList=new ArrayList<>();

        List<String> listMac=addressDao.select_mac2(specificAddress, city, county, street);
        for (int j = 0; j < listMac.size(); j++) {
            Location_tier locationTier=addressDao.selectLocation_tier(listMac.get(j));
            list = dateNumberDao.selectTwoHour(listMac.get(j),date1,date2);
            list=ControllerUtil.TowHourSortUtil(list,index);
            List<DateAndNumber>resList=ControllerUtil.listTimeSort(list);
            Mac_ListTimeSort mac_listTimeSort=new Mac_ListTimeSort();
            mac_listTimeSort.setList(resList);
            mac_listTimeSort.setMac_address(locationTier.getLocation());
            listTimeSortList.add(mac_listTimeSort);
        }
        return JSON.toJSONString(listTimeSortList);
    }
}
