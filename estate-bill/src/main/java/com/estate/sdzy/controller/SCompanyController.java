package com.estate.sdzy.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.estate.exception.BillException;
import com.estate.sdzy.entity.SCompany;
import com.estate.sdzy.service.SCompanyService;
import com.estate.sdzy.service.SUserService;
import com.estate.util.BillExceptionEnum;
import com.estate.util.Pinyin;
import com.estate.util.Result;
import com.estate.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 物业公司表 前端控制器
 * </p>
 *
 * @author mq
 * @since 2020-07-23
 */
@RestController
@RequestMapping("/sdzy/sCompany")
@Slf4j
public class SCompanyController {

    @Autowired
    private SCompanyService companyService;

    @Autowired
    private SUserService userService;

    @PostMapping("/insertCompany")
    public Result insertCompany(SCompany sCompany, @RequestHeader("Authentication-Token") String token) {
        boolean save = companyService.save(sCompany, token);
        if (save) {
            log.info("添加公司信息成功，公司id={}", sCompany.getId());
            return ResultUtil.success(userService.autoSave(sCompany));
        }

        return ResultUtil.error("保存公司信息失败！", 1);
    }

    @PutMapping("/updateCompany")
    public Result updateCompany(SCompany sCompany, @RequestHeader("Authentication-Token") String token) {
        return ResultUtil.success(companyService.saveOrUpdate(sCompany));
    }

    @GetMapping("/listCompany")
    public Result listCompany(Integer pageNo, Integer size) {
        if (StringUtils.isEmpty(pageNo)) {
            throw new BillException(BillExceptionEnum.PARAMS_MISS_ERROR);
        }
        if (StringUtils.isEmpty(size)) {
            size = 10;
        }
        Page<SCompany> p = new Page<>(pageNo, size);
        return ResultUtil.success(companyService.page(p));

    }

    @GetMapping("/{id}")
    public Result getCompany(@PathVariable("id") Long id) {
        return ResultUtil.success(companyService.getById(id));
    }

    @DeleteMapping("/{id}")
    public Result deleteCompany(@PathVariable("id") Long id, @RequestHeader("Authentication-Token") String token) {
        return ResultUtil.success(companyService.removeById(id, token));
    }


}

