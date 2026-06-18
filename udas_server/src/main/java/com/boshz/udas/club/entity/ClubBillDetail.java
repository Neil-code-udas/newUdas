package com.boshz.udas.club.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 俱乐部收支明细表(ClubBillDetail)实体类
 *
 * @author makejava
 * @since 2026-06-11 15:33:43
 */
@Data
public class ClubBillDetail implements Serializable {
    private static final long serialVersionUID = 422396099094821291L;
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 俱乐部类别
     */
    private String clubType;
    /**
     * 收支类型：1-收入，2-支出
     */
    private Integer incomeExpend;
    /**
     * 项目ID
     */
    private Long projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 科目编码
     */
    private String subjectCode;
    /**
     * 科目名称
     */
    private String subjectName;
    /**
     * 预算金额
     */
    private Double budget;
    /**
     * 收入金额
     */
    private Double incomeAmount;
    /**
     * 会费金额
     */
    private Double membershipFee;
    /**
     * 是否销账：0-未销账，1-已销账
     */
    private Integer writeOff;
    /**
     * 列支时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime expendTime;
    /**
     * 备注
     */
    private String remark;
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

}
