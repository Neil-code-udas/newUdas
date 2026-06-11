package com.boshz.udas.secretPersonnel.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 涉密人员管理表 Excel导入导出实体
 *
 * @author makejava
 * @since 2026-06-08 17:37:51
 */
@Data
public class SecretPersonnelExcel {
    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID", index = 0)
    private Long id;
    /**
     * 单位编码
     */
    @ExcelProperty(value = "单位编码", index = 1)
    private String orgCode;
    /**
     * 单位名称
     */
    @ExcelProperty(value = "单位名称", index = 2)
    private String orgName;
    /**
     * 工号
     */
    @ExcelProperty(value = "工号", index = 3)
    private String account;
    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名", index = 4)
    private String name;
    /**
     * 岗位
     */
    @ExcelProperty(value = "岗位", index = 5)
    private String post;
    /**
     * 职务
     */
    @ExcelProperty(value = "职务", index = 6)
    private String duty;
    /**
     * 涉密岗位类别(字典值)
     */
    @ExcelProperty(value = "涉密岗位类别(字典值)", index = 7)
    private Integer secretPostType;
    /**
     * 涉密岗位程度：核心、重要、一般
     */
    @ExcelProperty(value = "涉密岗位程度：核心、重要、一般", index = 8)
    private String secretLevel;
    /**
     * 目前工作状态：在岗、离岗、离职
     */
    @ExcelProperty(value = "目前工作状态：在岗、离岗、离职", index = 9)
    private String workStatus;
    /**
     * 关联文件(多个逗号分隔)
     */
    @ExcelProperty(value = "关联文件(多个逗号分隔)", index = 10)
    private String relationFile;
    /**
     * 提交进度：已提交、未提交
     */
    @ExcelProperty(value = "提交进度：已提交、未提交", index = 11)
    private String submitProgress;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间", index = 12)
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间", index = 13)
    private LocalDateTime updateTime;
    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人", index = 14)
    private String createUser;
    /**
     * 更新人
     */
    @ExcelProperty(value = "更新人", index = 15)
    private String updateUser;
}
