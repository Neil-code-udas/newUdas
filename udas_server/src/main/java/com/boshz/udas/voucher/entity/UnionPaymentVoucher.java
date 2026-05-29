package com.boshz.udas.voucher.entity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UnionPaymentVoucher {
    private Long id;
    private Date voucherDate;
    private String payType;
    private String receiptUnit;
    private String receiptAccount;
    private String costCenter;
    private String handlePerson;
    private String reimburseItem;
    private String payAmountUpper;
    private BigDecimal payAmountLower;
    private String payDesc;
    private Date createTime;

    // getter/setter 省略，自行生成
}