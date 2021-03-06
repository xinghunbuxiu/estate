package com.estate.sdzy.asstes.service;

import com.estate.sdzy.asstes.entity.RCity;
import com.estate.sdzy.asstes.entity.RDistrict;
import com.estate.sdzy.asstes.entity.RProvince;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mq
 * @since 2020-08-04
 */
public interface RProvinceService extends IService<RProvince> {

    /**
     * 获取全国全部的省份 和下属单位
     * @return
     */
    List<RCity> listProvinces(Long code);

    /**
     * 获取全国全部的省份
     * @return
     */
    List<RProvince> listProvince();

    List<RProvince> getProvinceChild();
    List<RProvince> listProvincesAndCity();

    List<RCity> getCityList(Long id);
    List<RDistrict> getDistList(Long id);
}
