package com.boshz.udas.performance.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 支行季度绩效表 Excel导入导出实体
 *
 * @author makejava
 * @since 2026-06-18 16:26:58
 */
@Data
public class BranchPerformanceExcel {
    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID", index = 0)
    private Long id;
    /**
     * 统计周期，用于区分不同上传表，例：2026.3月-2026.5月
     */
    @ExcelProperty(value = "统计周期，用于区分不同上传表，例：2026.3月-2026.5月", index = 1)
    private String period;
    /**
     * 经营单位编码（关联经营机构表）
     */
    @ExcelProperty(value = "经营单位编码（关联经营机构表）", index = 2)
    private String branchCode;
    /**
     * 经营单位名称
     */
    @ExcelProperty(value = "经营单位名称", index = 3)
    private String branchName;
    /**
     * 对公促活-网点完成情况（1=完成情况）
     */
    @ExcelProperty(value = "对公促活-网点完成情况（1=完成情况）", index = 4)
    private String promotion1;
    /**
     * 对公促活-网点可分配金额（2=可分配金额）
     */
    @ExcelProperty(value = "对公促活-网点可分配金额（2=可分配金额）", index = 5)
    private String promotion2;
    /**
     * 新开户回访-网点完成情况
     */
    @ExcelProperty(value = "新开户回访-网点完成情况", index = 6)
    private String account1;
    /**
     * 新开户回访-网点可分配金额
     */
    @ExcelProperty(value = "新开户回访-网点可分配金额", index = 7)
    private String account2;
    /**
     * 人行账管系统核对-网点完成情况
     */
    @ExcelProperty(value = "人行账管系统核对-网点完成情况", index = 8)
    private String check1;
    /**
     * 人行账管系统核对-网点可分配金额
     */
    @ExcelProperty(value = "人行账管系统核对-网点可分配金额", index = 9)
    private String check2;
    /**
     * 催收银企对账单-网点完成情况
     */
    @ExcelProperty(value = "催收银企对账单-网点完成情况", index = 10)
    private String bill1;
    /**
     * 催收银企对账单-网点可分配金额
     */
    @ExcelProperty(value = "催收银企对账单-网点可分配金额", index = 11)
    private String bill2;
    /**
     * 现金网格化服务-网点完成情况
     */
    @ExcelProperty(value = "现金网格化服务-网点完成情况", index = 12)
    private String cash1;
    /**
     * 现金网格化服务-网点可分配金额
     */
    @ExcelProperty(value = "现金网格化服务-网点可分配金额", index = 13)
    private String cash2;
    /**
     * 网点人数
     */
    @ExcelProperty(value = "网点人数", index = 14)
    private String staffCount;
    /**
     * 工作效率、团队协作、内控管理、培训考勤等综合评价
     */
    @ExcelProperty(value = "工作效率、团队协作、内控管理、培训考勤等综合评价", index = 15)
    private String score;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间", index = 16)
    private LocalDateTime createTime;
}
