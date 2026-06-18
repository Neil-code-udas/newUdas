package com.boshz.udas.club.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 俱乐部收支明细表 Excel导入导出实体
 *
 * @author makejava
 * @since 2026-06-11 15:33:44
 */
@Data
public class ClubBillDetailExcel {
    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID", index = 0)
    private Long id;
    /**
     * 俱乐部类别
     */
    @ExcelProperty(value = "俱乐部类别", index = 1)
    private String clubType;
    /**
     * 收支类型：1-收入，2-支出
     */
    @ExcelProperty(value = "收支类型：1-收入，2-支出", index = 2)
    private Integer incomeExpend;
    /**
     * 项目ID
     */
    @ExcelProperty(value = "项目ID", index = 3)
    private Long projectId;
    /**
     * 项目名称
     */
    @ExcelProperty(value = "项目名称", index = 4)
    private String projectName;
    /**
     * 科目编码
     */
    @ExcelProperty(value = "科目编码", index = 5)
    private String subjectCode;
    /**
     * 科目名称
     */
    @ExcelProperty(value = "科目名称", index = 6)
    private String subjectName;
    /**
     * 预算金额
     */
    @ExcelProperty(value = "预算金额", index = 7)
    private Double budget;
    /**
     * 收入金额
     */
    @ExcelProperty(value = "收入金额", index = 8)
    private Double incomeAmount;
    /**
     * 会费金额
     */
    @ExcelProperty(value = "会费金额", index = 9)
    private Double membershipFee;
    /**
     * 是否销账：0-未销账，1-已销账
     */
    @ExcelProperty(value = "是否销账：0-未销账，1-已销账", index = 10)
    private Integer writeOff;
    /**
     * 列支时间
     */
    @ExcelProperty(value = "列支时间", index = 11)
    private LocalDateTime expendTime;
    /**
     * 备注
     */
    @ExcelProperty(value = "备注", index = 12)
    private String remark;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间", index = 13)
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间", index = 14)
    private LocalDateTime updateTime;
}
