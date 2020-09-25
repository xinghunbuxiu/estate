package com.estate.sdzy.wechat.controller;

import com.estate.common.util.ConnectUtil;
import com.estate.common.util.Result;
import com.estate.common.util.ResultUtil;
import com.estate.common.util.TransactionConnUtil;
import com.estate.sdzy.wechat.resource.WeChatResources;
import com.estate.sdzy.wechat.util.ResultSetToMap;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author mq
 * @description: TODO
 * @title: EstateWeChatController
 * @projectName estate-parent
 * @date 2020/9/2111:05
 */
@RestController
@RequestMapping("/estate/")
public class EstateWeChatController {

    @Autowired
    private RestTemplate restTemplate;


    @PostMapping("setWeChatUser")
    public boolean setWeChatUser(HttpServletRequest request) {
        String compId = request.getParameter("compId");
        String commId = request.getParameter("commId");
        String areaId = request.getParameter("areaId");
        String buildId = request.getParameter("buildId");
        String roomId = request.getParameter("roomId");
        String openid = request.getParameter("openid");
        String nickname = request.getParameter("nickname");
        String sex = request.getParameter("sex");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String country = request.getParameter("country");
        String sql = "select id from  r_owner where wx_openid = '" + openid + "' and comp_id = "+ WeChatResources.COMP_ID;
        String ownerProper = "select id from  r_owner_property where property_id = "+roomId +" and property_type = '房产' and owner_id= ?";


        Date date = new Date();
        String insertSql = "insert into r_owner (wx_openid,wx_sex,wx_province,wx_city,wx_country,created_at,modified_at,comp_id) values(?,?,?,?,?,?,?,?)";
        String ownerProperty = "insert into r_owner_property (owner_id,comp_id,comm_id,comm_area_id,property_type,property_id,building_id,type,created_at,modified_at) values(?,?,?,?,?,?,?,?,?,?)";
        Connection connection = null;
        Integer integer =0;
        try {
            ResultSet resultSet = ConnectUtil.executeQuery(sql);
            if (resultSet.next()){
                // 添加用户信息
                integer = resultSet.getInt("id");
            }else {
                integer = TransactionConnUtil.executeUpdate(insertSql, new Object[]{openid, sex, province, city, country, date, date,compId}, true);
            }


            connection = TransactionConnUtil.getConnection();
            ResultSet resultSet1 = TransactionConnUtil.executeQuery(ownerProper,new Object[]{integer});
            if(!resultSet1.next()){
            Object[] objects = {integer, compId, commId, areaId, "房产", roomId, buildId, "业主", date, date};
                TransactionConnUtil.executeUpdate(ownerProperty, objects);
            }
            connection.commit();
        } catch (ClassNotFoundException classNotFoundException) {
            try {
                connection.rollback();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            classNotFoundException.printStackTrace();
            return false;
        } catch (SQLException sqlException) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            sqlException.printStackTrace();
            return false;
        }

        return true;
    }

    @GetMapping("getCity")
    public Result getCity(HttpServletRequest request){
        String provinceId = request.getParameter("provinceId");
        System.out.println(provinceId+"<--->");
        String forObject = restTemplate.getForObject("http://estate-bill/sdzy/rProvince/getCityList?provinceId="+provinceId, String.class);
        System.out.println(forObject);
        return ResultUtil.success(forObject);
    }

    @GetMapping("getDist")
    public Result getDist(HttpServletRequest request){
        String provinceId = request.getParameter("provinceId");
        String forObject = restTemplate.getForObject("http://estate-bill/sdzy/rProvince/getDistList?cityId="+provinceId, String.class);
        System.out.println(forObject);
        return ResultUtil.success(forObject);
    }

    @GetMapping("getCompList")
    public Result getCompList() {
        String sql = "select * from s_company where is_delete = 0";
        List<Map<String, Object>> stringObjectMap = new ArrayList<>();
        try {
            ResultSet resultSet = ConnectUtil.executeQuery(sql);

            stringObjectMap = ResultSetToMap.resultSetToMap(resultSet);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        return ResultUtil.success(stringObjectMap);
    }

    @GetMapping("getCommList")
    public Result getCommList(HttpServletRequest request) {
        String id = request.getParameter("id");
        List<Map<String, Object>> stringObjectMap = new ArrayList<>();
        String sql = "select id ,name from r_community where district_id = " + id + " and is_delete=0 ";
        try {
            ResultSet resultSet = ConnectUtil.executeQuery(sql);
            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<>(16);
                map.put("id", resultSet.getInt("id"));
                map.put("name", resultSet.getString("name"));
                stringObjectMap.add(map);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        return ResultUtil.success(stringObjectMap);
    }

    @GetMapping("getCommAreaList")
    public Result getCommAreaList(HttpServletRequest request) {
        String id = request.getParameter("id");
        List<Map<String, Object>> stringObjectMap = new ArrayList<>();
        //select * from r_comm_area where comm_id =1 and is_delete=0
        String sql = "select id ,name from r_comm_area where comm_id = " + id + " and is_delete=0 ";
        try {
            ResultSet resultSet = ConnectUtil.executeQuery(sql);
            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<>(16);
                map.put("id", resultSet.getInt("id"));
                map.put("name", resultSet.getString("name"));
                stringObjectMap.add(map);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        return ResultUtil.success(stringObjectMap);
    }

    @GetMapping("getBuildList")
    public Result getBuildList(HttpServletRequest request) {
        String id = request.getParameter("id");
        List<Map<String, Object>> stringObjectMap = new ArrayList<>();
        //select * from r_comm_area where comm_id =1 and is_delete=0
        String sql = "select id ,name from r_building where comm_area_id = " + id + " and is_delete=0 ";
        try {
            ResultSet resultSet = ConnectUtil.executeQuery(sql);
            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<>(16);
                map.put("id", resultSet.getInt("id"));
                map.put("name", resultSet.getString("name"));
                stringObjectMap.add(map);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        return ResultUtil.success(stringObjectMap);
    }

    @GetMapping("getUnitList")
    public Result getUnitList(HttpServletRequest request) {
        String id = request.getParameter("id");
        List<Map<String, Object>> stringObjectMap = new ArrayList<>();
        //select * from r_comm_area where comm_id =1 and is_delete=0
        String sql = "select id ,name from r_unit where building_id = " + id + " and is_delete=0 ";
        try {
            ResultSet resultSet = ConnectUtil.executeQuery(sql);
            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<>(16);
                map.put("id", resultSet.getInt("id"));
                map.put("name", resultSet.getString("name"));
                stringObjectMap.add(map);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        return ResultUtil.success(stringObjectMap);
    }

    @GetMapping("getRoomList")
    public Result getRoomList(HttpServletRequest request) {
        String id = request.getParameter("id");
        List<Map<String, Object>> stringObjectMap = new ArrayList<>();
        //select * from r_comm_area where comm_id =1 and is_delete=0
        String sql = "select id ,name from r_room where unit_id = " + id + " and is_delete=0 ";
        try {
            ResultSet resultSet = ConnectUtil.executeQuery(sql);
            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<>(16);
                map.put("id", resultSet.getInt("id"));
                map.put("name", resultSet.getString("name"));
                stringObjectMap.add(map);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        return ResultUtil.success(stringObjectMap);
    }
}