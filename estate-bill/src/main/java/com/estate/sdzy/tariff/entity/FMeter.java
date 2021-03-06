package com.estate.sdzy.tariff.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.estate.sdzy.common.annotation.ExcelAnnotation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 仪表表
 * </p>
 *
 * @author mq
 * @since 2020-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FMeter implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long compId;
    @TableField(exist = false)
    @ExcelAnnotation(value = "物业公司",master = true)
    private String compName;

    private Long commId;
    @TableField(exist = false)
    @ExcelAnnotation(value = "社区",master = true)
    private String commName;

    /**
     * 分区
     */
    private Long commAreaId;
    @TableField(exist = false)
    @ExcelAnnotation(value = "分区",master = true)
    private String commAreaName;

    /**
     * 物业类型：房产、停车位
     */
    @ExcelAnnotation(value = "物业类型",master = true,dist = "45")
    private String propertyType;

    /**
     * 物业id：房产id、停车位id
     */
    private Long propertyId;
    @TableField(exist = false)
    @ExcelAnnotation(value = "物业编号",master = true)
    private String propertyName;

    @TableField(exist = false)
    private String ownerName;
    /**
     * 仪表类型（水表、电表、煤气表）
     */
    @ExcelAnnotation(value = "仪表类型",master = true,dist = "48")
    private String type;
    /**
     * 仪表编号
     */
    @ExcelAnnotation(value = "仪表编号",master = true)
    private String no;
    /**
     * 仪表名称
     */
    @ExcelAnnotation(value = "仪表名称",master = true)
    private String name;

    /**
     * 抄表刻度
     */
    @ExcelAnnotation(value = "抄表刻度",master = true)
    private BigDecimal newNum;
    /**
     * 抄表时间
     */
    @ExcelAnnotation(value = "抄表时间",master = true,fmt="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date meterReadTime;

    /**
     * 前次数量（生成账单）
     */
    @ExcelAnnotation(value = "账单刻度",master = true)
    private BigDecimal billNum;
    /**
     * 账单日期
     */
    @ExcelAnnotation(value = "账单日期",master = true,fmt="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date billDate;

    /**
     * 状态
     */
    @ExcelAnnotation(value = "状态",master = true)
    private String state;

    /**
     * 备注
     */
    @ExcelAnnotation(value = "备注")
    private String remark;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ExcelAnnotation(value = "录入时间",fmt="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    private Long createdBy;
    @ExcelAnnotation(value = "录入人")
    private String createdName;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ExcelAnnotation(value = "修改时间",fmt="yyyy-MM-dd HH:mm:ss")
    private Date modifiedAt;

    private Long modifiedBy;
    @ExcelAnnotation(value = "修改人")
    private String modifiedName;

    private Integer isDelete;
}
