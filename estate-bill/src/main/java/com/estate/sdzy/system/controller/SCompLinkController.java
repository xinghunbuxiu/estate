package com.estate.sdzy.system.controller;


import com.estate.common.controller.BaseController;
import com.estate.common.util.Result;
import com.estate.common.util.ResultUtil;
import com.estate.sdzy.system.entity.SCompLink;
import com.estate.sdzy.system.service.SCompLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 公司联系人 前端控制器
 * </p>
 *
 * @author mq
 * @since 2020-07-23
 */
@RestController
@RequestMapping("/sdzy/sCompLink")
public class SCompLinkController extends BaseController {

    @Autowired
    private SCompLinkService compLinkService;

    @GetMapping("/{id}")
    public Result listCompLink(@PathVariable("id") Long id, HttpServletRequest request){
        return ResultUtil.success(compLinkService.listCompLink(id,super.getParameterMap(request)));
    }

    @PostMapping("/insertCompLink")
    public Result insertCompLink(@RequestBody SCompLink compLink,@RequestHeader("Authentication-Token") String token){
        return ResultUtil.success(compLinkService.save(compLink,token));
    }

    @PutMapping("/updateCompLink")
    public Result updateCompLink(@RequestBody SCompLink compLink,@RequestHeader("Authentication-Token") String token){
        return ResultUtil.success(compLinkService.saveOrUpdate(compLink,token));
    }

    @DeleteMapping("/{id}")
    public Result deleteCompLink(@PathVariable("id") Long id,@RequestHeader("Authentication-Token") String token){
        return ResultUtil.success(compLinkService.removeById(id,token));
    }

}

