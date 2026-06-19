package com.boshz.udas.club.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 接口顶层返回对象
 */
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

    // getter、setter
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<ClubItemVO> getClubList() {
        return clubList;
    }

    public void setClubList(List<ClubItemVO> clubList) {
        this.clubList = clubList;
    }

    public BigDecimal getAllIncomeTotal() {
        return allIncomeTotal;
    }

    public void setAllIncomeTotal(BigDecimal allIncomeTotal) {
        this.allIncomeTotal = allIncomeTotal;
    }

    public BigDecimal getAllOutTotal() {
        return allOutTotal;
    }

    public void setAllOutTotal(BigDecimal allOutTotal) {
        this.allOutTotal = allOutTotal;
    }

    public BigDecimal getAllSiteFeeTotal() {
        return allSiteFeeTotal;
    }

    public void setAllSiteFeeTotal(BigDecimal allSiteFeeTotal) {
        this.allSiteFeeTotal = allSiteFeeTotal;
    }

    public BigDecimal getAllSurplusTotal() {
        return allSurplusTotal;
    }

    public void setAllSurplusTotal(BigDecimal allSurplusTotal) {
        this.allSurplusTotal = allSurplusTotal;
    }
}