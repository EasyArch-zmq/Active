package com.easyArch.controller;

import com.alibaba.fastjson.JSON;
import com.easyArch.entity.P_User;
import com.easyArch.service.P_DaySortService;
import com.easyArch.service.P_TodayAccountService;
import com.easyArch.service.P_TowHourSortService;
import com.easyArch.service.P_mainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class P_DataController {

    @Autowired
    private P_mainService p_mainService;
    @Autowired
    private P_TodayAccountService p_todayAccountService;
    @Autowired
    private P_DaySortService p_daySortService;
    @Autowired
    private P_TowHourSortService pTowHourSortService;

    /**
     *返回用户地址，具体地址，总人数，green,red 在普通用户首页
     */
    @ResponseBody
    @RequestMapping(value = "selectAllNumber"
            ,produces = "application/json;charset=utf-8"
            ,method = RequestMethod.POST)
    public String selectAllNumber(@RequestBody P_User p_user){
        if(p_user!=null){
            System.out.println("selectAllNumber!");
            return p_mainService.selectP_mainData(p_user);
        }
        return JSON.toJSONString("f");
    }


    /**
     * 普通用户P_TodayAccount页面默认数据
     * @param p_user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "selectDefaultNumber_p"
            , produces = "application/json;charset=utf-8"
            , method = RequestMethod.POST)
    public String selectDefaultNumber (@RequestBody P_User p_user) {
        if(p_user!=null){
            return p_todayAccountService.selectTodayNumber(p_user);

        }
        return JSON.toJSONString("f");
    }

    /**
     * 普通用户排行榜页面 查询今天数据排行
     * @param p_user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "SelectDaySort"
            , produces = "application/json;charset=utf-8"
            , method = RequestMethod.POST)
    public String selectSortNum(@RequestBody P_User p_user){
        if(p_user!=null){
            return p_daySortService.selectSortNum(p_user);
        }
        return JSON.toJSONString("f");
    }

    /**
     * 普通用户排行榜页面 查询具体某天某个两小时数据排行
     * @param p_user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "SelectTowSort"
            , produces = "application/json;charset=utf-8"
            , method = RequestMethod.POST)
    public String selectTowSortNum(@RequestBody P_User p_user){
        if(p_user!=null){
            return pTowHourSortService.selectTowSortNum(p_user);
        }
        return JSON.toJSONString("f");
    }


}
