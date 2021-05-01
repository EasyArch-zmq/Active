package com.easyArch.mapper;

import com.easyArch.entity.DateAndNumber;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DateNumberDao {
    /**
     * 根据boxid查询总人数
     * @param mac_address
     * @return
     */
//    Integer selectAllNumber(String mac_address);

    /**
     * 每两小时查询
     * @param date1
     * @return
     */
    List<DateAndNumber>selectTwoHourByMac1(String mac_address,String date1,String date2);
    List<DateAndNumber>selectTwoHourByMac2(String mac_address,String date1,String date2);

    /**
     * 查数量
     * */
    Integer selectNumByMacAndTime1(String mac_address, String date1, String date2);
    Integer selectNumByMacAndTime2(String mac_address, String date1, String date2);
    /**
     * 每日查询
     * @param date1
     * @param date2
     * @return
     */
    List<DateAndNumber>selectDayByMac1(String mac_address,String date1,String date2);

    List<DateAndNumber>selectDayByMac2(String mac_address,String date1,String date2);
    /**
     * 每月查询
     * @param date1
     * @param date2
     * @return
     */

    List<DateAndNumber>selectMonthByMac1(String mac_address ,String date1,String date2);
    List<DateAndNumber>selectMonthByMac2(String mac_address ,String date1,String date2);
    /**
     *每年查询
     * @param date1
     * @param date2
     * @return
     */
    List<DateAndNumber>selectYearByMac1(String mac_address,String date1,String date2);
    List<DateAndNumber>selectYearByMac2(String mac_address,String date1,String date2);

}
