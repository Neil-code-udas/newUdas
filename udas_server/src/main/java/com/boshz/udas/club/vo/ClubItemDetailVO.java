package com.boshz.udas.club.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * 会费/经费单条收支明细
 */
@Data
public class ClubItemDetailVO {
    // 收支日期
    private LocalDate tradeTime;
    // 会计科目：其他应付款/文体活动费/场地费
    private String subject;
    // 金额
    private BigDecimal amount;
    // 备注（饮料、场地租赁、赛事物料等）
    private String remark;


}