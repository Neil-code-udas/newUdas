package com.boshz.udas.subject.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 单年度预算、实际、执行率
 */
@Data
public class YearDataDTO {
    private BigDecimal budget;
    private BigDecimal actual;
    private BigDecimal rate;
}