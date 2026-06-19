package com.boshz.udas.performance.vo;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/** 重点工作绩效整体模块 */
@Data
public class KeyWorkPerformanceVO {
    // 5项指标明细集合
    private List<PerformanceItemVO> itemList;
//    // 重点小计 C列总分
//    private BigDecimal subCompleteTotal;
//    // 重点小计 D列总金额
//    private BigDecimal subAmountTotal;
}