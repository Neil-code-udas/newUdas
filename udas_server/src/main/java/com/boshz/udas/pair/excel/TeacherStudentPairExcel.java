package com.boshz.udas.pair.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 师徒结对主表 Excel导入导出实体
 *
 * @author makejava
 * @since 2026-07-03 16:17:34
 */
@Data
public class TeacherStudentPairExcel {
    /**
     * 结对主键TP单据编号
     */
    @ExcelProperty(value = "结对主键TP单据编号", index = 0)
    private String id;
    /**
     * 师父员工工号
     */
    @ExcelProperty(value = "师父员工工号", index = 1)
    private String teacherAccount;
    /**
     * 师父姓名
     */
    @ExcelProperty(value = "师父姓名", index = 2)
    private String teacherName;
    /**
     * 师父职等
     */
    @ExcelProperty(value = "师父职等", index = 3)
    private Integer teacherLevel;
    /**
     * 徒弟员工工号
     */
    @ExcelProperty(value = "徒弟员工工号", index = 4)
    private String studentAccount;
    /**
     * 徒弟姓名
     */
    @ExcelProperty(value = "徒弟姓名", index = 5)
    private String studentName;
    /**
     * 徒弟职等
     */
    @ExcelProperty(value = "徒弟职等", index = 6)
    private Integer studentLevel;
    /**
     * 所属机构ID
     */
    @ExcelProperty(value = "所属机构ID", index = 7)
    private String orgId;
    /**
     * 机构名称
     */
    @ExcelProperty(value = "机构名称", index = 8)
    private String orgName;
    /**
     * 状态 TRAINING培养中 GRADUATED已出师
     */
    @ExcelProperty(value = "状态 TRAINING培养中 GRADUATED已出师", index = 9)
    private String pairStatus;
    /**
     * 结对生效日期
     */
    @ExcelProperty(value = "结对生效日期", index = 10)
    private LocalDate startDate;
    /**
     * 12个月到期日
     */
    @ExcelProperty(value = "12个月到期日", index = 11)
    private LocalDate endDate;
    /**
     * 承诺书文件地址
     */
    @ExcelProperty(value = "承诺书文件地址", index = 12)
    private String signFileUrl;
    /**
     * 创建人工号
     */
    @ExcelProperty(value = "创建人工号", index = 13)
    private String createAccount;
    /**
     *
     */
    @ExcelProperty(value = "", index = 14)
    private LocalDateTime createTime;
    /**
     *
     */
    @ExcelProperty(value = "", index = 15)
    private String updateAccount;
    /**
     *
     */
    @ExcelProperty(value = "", index = 16)
    private LocalDateTime updateTime;
}
