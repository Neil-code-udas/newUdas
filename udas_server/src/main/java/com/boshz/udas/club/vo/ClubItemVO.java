package com.boshz.udas.club.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClubItemVO {
    // 俱乐部编码
    private String clubCode;
    // 俱乐部中文名称
    private String clubName;

    // ========== 新增：接收SQL汇总数值的临时字段 ==========
    private BigDecimal incomeFeeSum;
    private BigDecimal incomeFundSum;
    private BigDecimal outFeeSum;
    private BigDecimal outFundSum;

    // ====================== 收入区域 ======================
    private ClubGroupVO incomeFeeGroup;
    private ClubGroupVO incomeFundGroup;
    private BigDecimal incomeTotal;

    // ====================== 支出区域 ======================
    private ClubGroupVO outFeeGroup;
    private ClubGroupVO outFundGroup;
    private BigDecimal siteFeeTotal;
    private BigDecimal outTotal;

    // ====================== 当年结余 ======================
    private BigDecimal yearSurplus;


}