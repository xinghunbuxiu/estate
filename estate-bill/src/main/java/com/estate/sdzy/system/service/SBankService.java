package com.estate.sdzy.system.service;

import com.estate.sdzy.system.entity.SBank;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 银行 服务类
 * </p>
 *
 * @author mq
 * @since 2020-08-04
 */
public interface SBankService extends IService<SBank> {
    List<SBank> getAllBank(String token);
}
