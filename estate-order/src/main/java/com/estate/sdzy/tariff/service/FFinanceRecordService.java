package com.estate.sdzy.tariff.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.estate.sdzy.asstes.entity.ROwner;
import com.estate.sdzy.tariff.entity.FBill;
import com.estate.sdzy.tariff.entity.FFinanceRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.security.acl.Owner;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 财务流水 服务类
 * </p>
 *
 * @author mq
 * @since 2020-09-08
 */
public interface FFinanceRecordService extends IService<FFinanceRecord> {

    boolean insert(Map<String,String> map, String token);
    boolean payPrice(Map<String,String> map, String token);
    List<ROwner> getOwnerByName(String ownerName, String tel,String token);
    Page<FBill> getOwnerBill(Long ownerId, Long pageNo, Long size, String token);
}
