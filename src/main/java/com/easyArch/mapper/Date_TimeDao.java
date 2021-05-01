package com.easyArch.mapper;

import com.easyArch.entity.DateAndNumber;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface Date_TimeDao {
    /**
     * 每日查询
     * @param date1
     * @param date2
     * @return
     */


    List<DateAndNumber>selectDayByMac1(String mac_address,String date1,String date2,String time1,String time2);

    List<DateAndNumber>selectDayByMac2(String mac_address,String date1,String date2,String time1,String time2);

    /**
     * 每月查询
     * @param date1
     * @param date2
     * @return
     */

    List<DateAndNumber>selectMonthByMac1(String mac_address ,String date1,String date2,String time1,String time2);
    List<DateAndNumber>selectMonthByMac2(String mac_address ,String date1,String date2,String time1,String time2);

    /**
     *每年查询到省
     * @param date1
     * @param date2
     * @return
     */
    List<DateAndNumber> selectYearByMac1(String city, String date1, String date2,String time1,String time2);
    List<DateAndNumber> selectYearByMac2(String city, String date1, String date2,String time1,String time2);

}
