package com.boshz.udas.pair.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 师徒结对主表(TeacherStudentPair)实体类
 *
 * @author makejava
 * @since 2026-07-03 16:17:33
 */
@Data
public class TeacherStudentPair implements Serializable {
    private static final long serialVersionUID = -91551642207378691L;
    /**
     * 结对主键TP单据编号
     */
    private String id;
    /**
     * 师父员工工号
     */
    private String teacherAccount;
    /**
     * 师父姓名
     */
    private String teacherName;
    /**
     * 师父职等
     */
    private Integer teacherLevel;
    /**
     * 徒弟员工工号
     */
    private String studentAccount;
    /**
     * 徒弟姓名
     */
    private String studentName;
    /**
     * 徒弟职等
     */
    private Integer studentLevel;
    /**
     * 所属机构ID
     */
    private String orgId;
    /**
     * 机构名称
     */
    private String orgName;
    /**
     * 状态 TRAINING培养中 GRADUATED已出师
     */
    private String pairStatus;
    /**
     * 结对生效日期
     */
    private LocalDate startDate;
    /**
     * 12个月到期日
     */
    private LocalDate endDate;
    /**
     * 承诺书文件地址
     */
    private String signFileUrl;
    /**
     * 创建人工号
     */
    private String createAccount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    private String updateAccount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;


    private Integer remainDay;

}
