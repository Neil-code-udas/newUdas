package com.boshz.udas.performance.vo;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Data;

/** 每一行指标（对公促活/新开户回访等） */
@Data
public class PerformanceItemVO {
    // 指标名称
    private String itemName;
    private String filed;
    // C列 网点完成情况
    private BigDecimal completeScore;
    // D列 网点可分配金额
    private BigDecimal allocateAmount;

    private Map<String, BigDecimal>  userList;
}