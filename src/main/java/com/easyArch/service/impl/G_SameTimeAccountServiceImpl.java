package com.easyArch.service.impl;

import com.alibaba.fastjson.JSON;
import com.easyArch.entity.DateAndAddress;
import com.easyArch.entity.Mac_Num;
import com.easyArch.mapper.AddressDao;
import com.easyArch.mapper.DateNumberDao;
import com.easyArch.service.G_SameTimeAccountService;
import com.easyArch.service.SQLDataService;
import com.easyArch.util.ControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class G_SameTimeAccountServiceImpl implements G_SameTimeAccountService {
    @Autowired
    AddressDao addressDao;
    @Autowired
    SQLDataService sqlDataService;
    @Override
    public String sameTimeStatistic(DateAndAddress address) {
        String addressStr = address.getAddress();
        String[] str = ControllerUtil.slipAddress(addressStr);
        String city = str[0];
        String county = str[1];
        String street = str[2];
        String specificAddress = str[3];
        String time1 = address.getTime1();
        String time2 = address.getTime2();
        String date1;
        String date2;
        if (time1 != null) {
            date1 = address.getYear1() + "-" + address.getMonth1() + "-" + address.getDay1() + " " + time1;
            date2 = address.getYear2() + "-" + address.getMonth2() + "-" + address.getDay2() + " " + time2;
        } else {
            date1 = address.getYear1() + "-" + address.getMonth1() + "-" + address.getDay1();
            date2 = address.getYear2() + "-" + address.getMonth2() + "-" + address.getDay2();
        }
        List<Mac_Num> list = new ArrayList<>();
        List<String> listMac = addressDao.selectMacBySpecific(specificAddress, city, county, street);
        for (int j = 0; j < listMac.size(); j++) {
            Integer num = sqlDataService.selectNumByOneMac(listMac.get(j), date1, date2);
            Mac_Num mac_num = new Mac_Num();
            mac_num.setNum(num);
            mac_num.setMac_address(listMac.get(j));
            list.add(mac_num);
        }
        return JSON.toJSONString(list);
    }
}
