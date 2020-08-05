package com.estate.sdzy.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 城市
 * </p>
 *
 * @author mq
 * @since 2020-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RCity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Code
     */
    private String cityCode;

    private String cityName;

    /**
     * ???
     */
    private Long provinceId;

    /**
     * 状态正常；作废
     */
    private String state;

    /**
     * 操作 状态位
     */
    private Integer operState;

    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;

    private Long createdBy;

    private String createdName;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifiedAt;

    private Long modifiedBy;

    private String modifiedName;


}
