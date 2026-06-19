package com.boshz.udas.performance.dto;

import java.math.BigDecimal;
import lombok.Data;

/** 单条更新明细：工号、要更新的指标字段、金额 */
@Data
public class BatchStaffPerfUpdateDTO {
    /** 员工工号 */
    private String staffNo;
    /** 需要更新的字段：promotion / account / cheok / bill / cash */
    private String targetField;
    /** 指标新金额 */
    private BigDecimal amount;
}