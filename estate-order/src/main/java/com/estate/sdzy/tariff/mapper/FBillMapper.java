package com.estate.sdzy.tariff.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.estate.sdzy.tariff.entity.FBill;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mq
 * @since 2020-08-27
 */
public interface FBillMapper extends BaseMapper<FBill> {
    /**
     *
     * @param compId
     * @return
     */
    List<Map<String,String>> listOwner(@Param("compId") Long compId);
}
