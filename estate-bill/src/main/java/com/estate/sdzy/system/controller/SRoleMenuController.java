package com.estate.sdzy.system.controller;


import com.estate.common.util.Result;
import com.estate.common.util.ResultUtil;
import com.estate.sdzy.system.service.SRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色菜单 前端控制器
 * </p>
 *
 * @author mq
 * @since 2020-07-23
 */
@RestController
@RequestMapping("/sdzy/sRoleMenu")
public class SRoleMenuController {

    @Autowired
    private SRoleMenuService roleMenuService;

    /**
     *
     * @param roleId 角色id
     * @param menuIds 菜单ids
     * @param flag 添加还是减少
     * @return
     */
    @PostMapping("/setRoleMenu")
    public Result setRoleMenu(Long roleId, String menuIds, String flag) {
        roleMenuService.setRoleMenu(roleId,menuIds);
        return ResultUtil.error("添加角色菜单信息失败", 1);
    }
}

