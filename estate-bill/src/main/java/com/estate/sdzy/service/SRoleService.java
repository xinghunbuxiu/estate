package com.estate.sdzy.service;

import com.estate.exception.BillException;
import com.estate.sdzy.entity.SRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表  服务类
 * </p>
 *
 * @author mq
 * @since 2020-07-23
 */
public interface SRoleService extends IService<SRole> {

    boolean saveOrUpdate(SRole role,String token) throws BillException;
    boolean save(SRole role,String token) throws BillException;
    boolean remove(Long id,String token) throws BillException;
}