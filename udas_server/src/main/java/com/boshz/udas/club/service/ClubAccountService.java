package com.boshz.udas.club.service;


import com.boshz.udas.club.vo.ClubYearTotalVO;

public interface ClubAccountService {
    /**
     * 查询年度各俱乐部完整台账（含会费、经费全部明细流水，场地费单独统计不计入总支出）
     * @param year 年度，如2025
     * @return 年度汇总台账
     */
    ClubYearTotalVO getYearClubAccountDetail(String year);
}