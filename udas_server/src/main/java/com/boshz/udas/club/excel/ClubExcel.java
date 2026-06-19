package com.boshz.udas.club.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 俱乐部收支台账 Excel导入导出实体
 *
 * @author makejava
 * @since 2026-06-18 10:05:40
 */
@Data
public class ClubExcel {
    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID", index = 0)
    private Long id;
    /**
     * 年度，字符串格式，如：2026
     */
    @ExcelProperty(value = "年度，字符串格式，如：2026", index = 1)
    private String year;
    /**
     * 俱乐部英文编码
     */
    @ExcelProperty(value = "俱乐部英文编码", index = 2)
    private String clubCode;
    /**
     * 收支大类：1-收入，2-支出
     */
    @ExcelProperty(value = "收支大类：1-收入，2-支出", index = 3)
    private Integer ioType;
    /**
     * 收支细分类型：会费、经费、奖励、历年结余
     */
    @ExcelProperty(value = "收支细分类型：会费、经费、奖励、历年结余", index = 4)
    private String tradeType;
    /**
     * 收支业务发生时间
     */
    @ExcelProperty(value = "收支业务发生时间", index = 5)
    private LocalDate tradeTime;
    /**
     * 会计科目：其他应付款、文体活动费、场地费
     */
    @ExcelProperty(value = "会计科目：其他应付款、文体活动费、场地费", index = 6)
    private String subject;
    /**
     * 收支金额，统一存正数，io_type区分收支
     */
    @ExcelProperty(value = "收支金额，统一存正数，io_type区分收支", index = 7)
    private Double amount;
    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人", index = 8)
    private String creator;
    /**
     * 备注说明
     */
    @ExcelProperty(value = "备注说明", index = 9)
    private String remark;
    /**
     * 记录创建时间
     */
    @ExcelProperty(value = "记录创建时间", index = 10)
    private LocalDateTime createTime;
    /**
     * 记录更新时间
     */
    @ExcelProperty(value = "记录更新时间", index = 11)
    private LocalDateTime updateTime;
}
