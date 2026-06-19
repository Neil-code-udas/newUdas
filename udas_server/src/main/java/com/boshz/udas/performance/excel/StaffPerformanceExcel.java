package com.boshz.udas.performance.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 员工绩效汇总表 Excel导入导出实体
 *
 * @author makejava
 * @since 2026-06-18 21:11:47
 */
@Data
public class StaffPerformanceExcel {
    /**
     * 自增主键ID
     */
    @ExcelProperty(value = "自增主键ID")
    private Long id;
    /**
     * 统计周期，例：202606、2026上半年
     */
    @ExcelProperty(value = "统计周期，例：202606、2026上半年")
    private String period;
    /**
     * 员工工号
     */
    @ExcelProperty(value = "员工工号")
    private String staffNo;
    /**
     * 员工姓名
     */
    @ExcelProperty(value = "员工姓名")
    private String staffName;
    /**
     * 机构编码
     */
    @ExcelProperty(value = "机构编码")
    private String branchCode;
    /**
     * 机构名称
     */
    @ExcelProperty(value = "机构名称")
    private String branchName;
    /**
     * 重点工作绩效
     */
    @ExcelProperty(value = "重点工作绩效")
    private BigDecimal keyWorkPerf;
    /**
     * 网点评价绩效
     */
    @ExcelProperty(value = "网点评价绩效")
    private BigDecimal networkEvalPerf;
    /**
     * 专项奖励
     */
    @ExcelProperty(value = "专项奖励")
    private BigDecimal specialBonus;
    /**
     * 小计（元）
     */
    @ExcelProperty(value = "小计（元）")
    private BigDecimal totalAmount;
    /**
     * 对公促活综合指标
     */
    @ExcelProperty(value = "对公促活综合指标")
    private BigDecimal promotion;
    /**
     * 新开户回访综合指标
     */
    @ExcelProperty(value = "新开户回访综合指标")
    private BigDecimal account;
    /**
     * 人行账管系统核对综合指标
     */
    @ExcelProperty(value = "人行账管系统核对综合指标")
    private BigDecimal cheok;
    /**
     * 催收银企对账单综合指标
     */
    @ExcelProperty(value = "催收银企对账单综合指标")
    private BigDecimal bill;
    /**
     * 现金网格化服务综合指标
     */
    @ExcelProperty(value = "现金网格化服务综合指标")
    private BigDecimal cash;
    /**
     * 综合得分
     */
    @ExcelProperty(value = "综合得分")
    private BigDecimal score;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
