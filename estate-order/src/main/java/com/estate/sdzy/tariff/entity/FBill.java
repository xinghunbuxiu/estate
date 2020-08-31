package com.estate.sdzy.tariff.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author mq
 * @since 2020-08-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FBill implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 账单号
     */
    private String billNo;

    /**
     * 物业id：停车位、厂房、住宅的id
     */
    private Long propertyId;

    /**
     * 物业类型停车位、厂房、住宅
     */
    private String propertyType;

    /**
     * 账单生成时间
     */
    private Date billTime;

    /**
     * 是否逾期
     */
    private String isOverdue;

    /**
     * 是否付款
     */
    private String isPayment;

    /**
     * 逾期产生的费用
     */
    private BigDecimal overdueCost;

    /**
     * 逾期费用计费规则
     */
    private String overdueRule;

    /**
     * 账单总价格
     */
    private BigDecimal price;

    /**
     * 已经付的钱
     */
    private BigDecimal payPrice;

    /**
     * 备注
     */
    private String remark;

    /**
     * 费用减免
     */
    private BigDecimal salePrice;

    /**
     * 是否打印，打印过就不能打印了
     */
    private String isPrint;

    /**
     * 是否打印发票
     */
    private String isInvoice;

    /**
     * 收费结束日期
     */
    private Date payEndTime;


}