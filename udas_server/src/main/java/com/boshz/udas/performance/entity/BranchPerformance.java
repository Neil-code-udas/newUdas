package com.boshz.udas.performance.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 支行季度服务专员绩效统计表(BranchPerformance)实体类
 *
 * @author makejava
 * @since 2026-06-18 23:14:09
 */
@Data
public class BranchPerformance implements Serializable {
    private static final long serialVersionUID = -62502417822827148L;
/**
     * 主键ID
     */
    private Long id;
/**
     * 统计周期，区分不同上传表，例：2026.3月-2026.5月
     */
    private String period;
/**
     * 经营单位编码（关联经营机构表）
     */
    private String branchCode;
/**
     * 经营单位名称
     */
    private String branchName;
/**
     * 对公促活-网点完成情况
     */
    private BigDecimal promotion1;
/**
     * 对公促活-网点可分配金额（元）
     */
    private BigDecimal promotion2;
/**
     * 新开户回访-网点完成情况
     */
    private BigDecimal account1;
/**
     * 新开户回访-网点可分配金额（元）
     */
    private BigDecimal account2;
/**
     * 人行账管系统核对-网点完成情况
     */
    private BigDecimal check1;
/**
     * 人行账管系统核对-网点可分配金额（元）
     */
    private BigDecimal check2;
/**
     * 催收银企对账单-网点完成情况
     */
    private BigDecimal bill1;
/**
     * 催收银企对账单-网点可分配金额（元）
     */
    private BigDecimal bill2;
/**
     * 现金网格化服务-网点完成情况
     */
    private BigDecimal cash1;
/**
     * 现金网格化服务-网点可分配金额（元）
     */
    private BigDecimal cash2;
/**
     * 网点在岗人数
     */
    private BigDecimal staffCount;
/**
     * 网点评价综合打分（效率/协作/内控/考勤）
     */
    private BigDecimal score;
/**
     * 网点评价绩效-可分配金额
     */
    private BigDecimal evaluateAmount;
/**
     * 重点工作绩效-完成情况小计
     */
    private BigDecimal keyWorkCSub;
/**
     * 重点工作绩效-可分配金额小计
     */
    private BigDecimal keyWorkDSub;
/**
     * 网点评价绩效-可分配金额小计
     */
    private BigDecimal evaluateDSub;
/**
     * 网点绩效总可分配金额合计
     */
    private BigDecimal totalAllD;
/**
     * 记录更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
/**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

}
