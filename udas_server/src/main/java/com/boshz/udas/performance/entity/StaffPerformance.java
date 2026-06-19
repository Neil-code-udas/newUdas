package com.boshz.udas.performance.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 员工绩效汇总表(StaffPerformance)实体类
 *
 * @author makejava
 * @since 2026-06-18 21:11:46
 */
@Data
public class StaffPerformance implements Serializable {
    private static final long serialVersionUID = -93556276323143475L;
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 统计周期，例：202606、2026上半年
     */
    private String period;
    /**
     * 员工工号
     */
    private String staffNo;
    /**
     * 员工姓名
     */
    private String staffName;
    /**
     * 机构编码
     */
    private String branchCode;
    /**
     * 机构名称
     */
    private String branchName;
    /**
     * 重点工作绩效
     */
    private BigDecimal keyWorkPerf;
    /**
     * 网点评价绩效
     */
    private BigDecimal networkEvalPerf;
    /**
     * 专项奖励
     */
    private BigDecimal specialBonus;
    /**
     * 小计（元）
     */
    private BigDecimal totalAmount;
    private BigDecimal total;
    /**
     * 对公促活综合指标
     */
    private BigDecimal promotion;
    /**
     * 新开户回访综合指标
     */
    private BigDecimal account;
    /**
     * 人行账管系统核对综合指标
     */
    private BigDecimal cheok;
    /**
     * 催收银企对账单综合指标
     */
    private BigDecimal bill;
    /**
     * 现金网格化服务综合指标
     */
    private BigDecimal cash;
    /**
     * 综合得分
     */
    private BigDecimal score;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

}
