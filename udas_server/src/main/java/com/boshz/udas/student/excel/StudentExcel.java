package com.boshz.udas.student.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 学生信息表 Excel导入导出实体
 *
 * @author makejava
 * @since 2026-06-12 15:12:47
 */
@Data
public class StudentExcel {
    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID", index = 0)
    private Long id;
    /**
     * 学号（唯一）
     */
    @ExcelProperty(value = "学号（唯一）", index = 1)
    private String studentNo;
    /**
     * 学生姓名
     */
    @ExcelProperty(value = "学生姓名", index = 2)
    private String name;
    /**
     * 性别（1男 2女）
     */
    @ExcelProperty(value = "性别（1男 2女）", index = 3)
    private String gender;
    /**
     * 年龄
     */
    @ExcelProperty(value = "年龄", index = 4)
    private Integer age;
    /**
     * 班级名称
     */
    @ExcelProperty(value = "班级名称", index = 5)
    private String className;
    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号", index = 6)
    private String phone;
    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱", index = 7)
    private String email;
    /**
     * 地址
     */
    @ExcelProperty(value = "地址", index = 8)
    private String address;
    /**
     * 创建人账号
     */
    @ExcelProperty(value = "创建人账号", index = 9)
    private String createUser;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间", index = 10)
    private LocalDateTime createTime;
    /**
     * 更新人账号
     */
    @ExcelProperty(value = "更新人账号", index = 11)
    private String updateUser;
    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间", index = 12)
    private LocalDateTime updateTime;
}
