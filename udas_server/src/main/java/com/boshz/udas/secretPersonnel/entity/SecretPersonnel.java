package com.boshz.udas.secretPersonnel.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 涉密人员管理表(SecretPersonnel)实体类
 *
 * @author makejava
 * @since 2026-06-08 17:37:50
 */
@Data
public class SecretPersonnel implements Serializable {
    private static final long serialVersionUID = 525576956769525820L;
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 单位编码
     */
    private String orgCode;
    /**
     * 单位名称
     */
    private String orgName;
    /**
     * 工号
     */
    private String account;
    /**
     * 姓名
     */
    private String name;
    /**
     * 岗位
     */
    private String post;
    /**
     * 职务
     */
    private String duty;
    /**
     * 涉密岗位类别(字典值)
     */
    private Integer secretPostType;
    /**
     * 涉密岗位程度：核心、重要、一般
     */
    private String secretLevel;
    /**
     * 目前工作状态：在岗、离岗、离职
     */
    private String workStatus;
    /**
     * 关联文件(多个逗号分隔)
     */
    private String relationFile;
    /**
     * 提交进度：已提交、未提交
     */
    private String submitProgress;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 更新人
     */
    private String updateUser;

}
