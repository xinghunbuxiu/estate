package com.estate.sdzy.controller;


import com.estate.sdzy.service.RProvinceService;
import com.estate.util.RedisUtil;
import com.estate.util.Result;
import com.estate.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mq
 * @since 2020-08-04
 */
@RestController
@RequestMapping("/sdzy/rProvince")
public class RProvinceController {

    @Autowired
    private RProvinceService provinceService;
    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/{id}")
    public Result getProvinces(@PathVariable("id") Long code){
        return ResultUtil.success(provinceService.listProvinces(code));
    }

    @GetMapping("/get")
    public Result getProvince(){
        return ResultUtil.success(provinceService.listProvince());
    }

    @GetMapping("/getProvinceChild")
    public Result getProvinceChild(){
        return ResultUtil.success(provinceService.getProvinceChild());
    }

    @GetMapping("/getProvinceChildFromRedis")
    public Result getProvinceChildFromRedis(){
        return ResultUtil.success(redisUtil.get("District_Number"));
    }

    @GetMapping("/getCityList")
    public Result getCityList(Long provinceId){
        return ResultUtil.success(provinceService.getCityList(provinceId));
    }

    @GetMapping("/getDistList")
    public Result getDistList(Long cityId){
        return ResultUtil.success(provinceService.getDistList(cityId));
    }

}

