package com.estate.sdzy.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author mq
 * @since 2020-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RProvince implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String provinceCode;

    private String provinceName;

    /**
     * 省下的市的集合
     */
    @TableField(exist = false)
    private List<RCity> cityList;


}