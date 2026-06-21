package com.boshz.udas.club.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 接口顶层返回对象
 */
@Data
public class ClubYearTotalVO {
    // 查询年度
    private String year;

    // 所有俱乐部台账列表
    private List<ClubItemVO> clubList;
    // 全年度总收入合计
    private BigDecimal allIncomeTotal;
    // 全年度总支出合计（不含场地费）
    private BigDecimal allOutTotal;
    // 新增：全年度场地费汇总金额
    private BigDecimal allSiteFeeTotal;
    // 全年度总结余
    private BigDecimal allSurplusTotal;
}