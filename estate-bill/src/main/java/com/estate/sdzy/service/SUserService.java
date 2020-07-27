package com.estate.sdzy.service;

import com.estate.sdzy.entity.SUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author mq
 * @since 2020-07-23
 */
public interface SUserService extends IService<SUser> {

    List<SUser> findOne(Integer id);
    SUser findByUserName(String username);
}
