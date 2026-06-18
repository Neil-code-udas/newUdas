package com.boshz.udas.student.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 学生信息表(Student)实体类
 *
 * @author makejava
 * @since 2026-06-12 15:12:47
 */
@Data
public class Student implements Serializable {
    private static final long serialVersionUID = 380108221046451735L;
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 学号（唯一）
     */
    private String studentNo;
    /**
     * 学生姓名
     */
    private String name;
    /**
     * 性别（1男 2女）
     */
    private String gender;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 班级名称
     */
    private String className;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 地址
     */
    private String address;
    /**
     * 创建人账号
     */
    private String createUser;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 更新人账号
     */
    private String updateUser;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}
