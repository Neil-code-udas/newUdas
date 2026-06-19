package com.boshz.udas.performance.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/** 单个网点完整绩效顶层VO */
@Data
public class BranchPerformanceVO {
    // 基础信息
    private String period;
    private String branchCode;
    private String branchName;
    //网点人数
    private BigDecimal staffCount;
    private KeyWorkPerformanceVO keyWork;
//
//    // 整张表格合计行
//    private BigDecimal totalComplete;
//    private BigDecimal totalAllocate;

}