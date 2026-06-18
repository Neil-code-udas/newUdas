package com.boshz.udas.club.vo;

import lombok.Data;

@Data
public class ClubSummaryVO {
    private String clubName;
    private java.math.BigDecimal budget;
    private java.math.BigDecimal income;
    private java.math.BigDecimal expend;
    private java.math.BigDecimal balance;
}