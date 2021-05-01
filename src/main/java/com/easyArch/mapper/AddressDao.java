package com.easyArch.mapper;


import com.easyArch.entity.Address;
import com.easyArch.entity.Location_tier;

import com.easyArch.entity.Mac_Loc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AddressDao {


    /**
     *  返回市
     * @return
     */
    List<String>city( );

    /**
     *  返回县
     * @return
     */
    List<String>county(String city);
    /**
     *  返回街
     * @return
     */
    List<String>street(String city,String county);

    /**
     *返回具体地址
     * @param city
     * @param county
     * @param street
     * @return
     */
    List<String>specificAddress(String city,String county,String street);

    /**
     *  返回具体建筑
     * @return
     */
    List<String> select_construction(@Param("specific_address") String specific_address, String city, String county,String street);



    /**
     *SelectMac
     * @return
     * -----------------------------------------------------------------------------------------------
     */
    /*找出按照市为条件的所有Mac*/
    List<String>selectMacByCity(String city);
    /*根据县区查Mac*/
    List<String>selectMacByCounty(String city,String county);
    /*根据街查询Mac*/
    List<String>selectMacByStreet(String city,String county,String street);
    /*根据建筑查找Mac*/
    List<String>selectMacByConstruction(@Param("specific_address") String specific_address, String city, String county,String street,String construction);
    /*根据具体地址查询Mac*/
    List<String>selectMacBySpecific(@Param("specific_address") String specific_address, String city, String county,String street);

    /**
     * other
     * -----------------------------------------------------------------------------------------------
     * */
    List<Mac_Loc>select_ma_lo(String city, String county,String street, @Param("specific_address") String specific_address);
    Location_tier selectLocation_tier(String mac_address);
    List<Address>selectAddress();


}
