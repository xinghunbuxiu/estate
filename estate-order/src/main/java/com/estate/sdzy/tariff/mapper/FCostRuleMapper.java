package com.estate.sdzy.tariff.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.estate.sdzy.tariff.entity.FCostRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 收费标准 Mapper 接口
 * </p>
 *
 * @author mq
 * @since 2020-08-04
 */
public interface FCostRuleMapper extends BaseMapper<FCostRule> {

    Page<FCostRule> listCostRule(Page<FCostRule> page, @Param("ew")QueryWrapper<FCostRule> queryWrapper);
}
