package com.estate.sdzy.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.estate.sdzy.asstes.entity.RCommunity;
import com.estate.sdzy.system.entity.SUserComm;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户社区表 服务类
 * </p>
 *
 * @author mq
 * @since 2020-07-24
 */
public interface SUserCommService extends IService<SUserComm> {

    /**
     * 通过用户的登录token，查询用户分管的所有的社区id
     * @param token
     * @return 社区id集合
     */
    List<Long> getUserCommIdList(String token);

    /**
     * 设置用户数据权限
     * @param userId 用户id
     * @param commIds 用户权限集合
     * @param token 登录凭证
     * @param remark 备注
     * @return 返回一个boolean值
     */
    boolean setUserComm(Long userId,String commIds,String token,String remark);

    /**
     * 通过用户id，查询用户权限
     * @param id 用户id
     * @return 返回用户权限的集合
     */
    List<Map<String,String>> listUserComm(Long compId,Long id);

    /**
     * 通过用户id，查询出用户有权限的社区
     * @auth mzc
     * @param userId
     * @param compId
     * @return
     */
    List<RCommunity> getUserComm(Long userId, Long compId);
}
