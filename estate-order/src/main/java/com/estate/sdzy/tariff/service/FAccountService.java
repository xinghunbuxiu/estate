package com.estate.sdzy.tariff.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.estate.sdzy.tariff.entity.FAccount;
import com.estate.sdzy.tariff.entity.FAccountCostItem;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 账户 服务类
 * </p>
 *
 * @author mzc
 * @since 2020-09-10
 */
public interface FAccountService extends IService<FAccount> {

    Page<FAccount> listAccount(Map<String,String> map,Integer pageNo, Integer size, String token);

    boolean saveAccount(FAccount item,String token);

    boolean saveOrUpdate(FAccount item,String token);

    FAccount getAccount(Long ownerId,String ruleId,String propertyType,String propertyId);

    List<FAccount> getAccountByOwnerId(Long ownerId, String token);

    List<Map<String,String>> listTypes(Map<String,String> map,String token);

    String doUpdate(FAccount account, String token);

    List<FAccountCostItem> getAccountItemByAccountId(String accountId);
}
