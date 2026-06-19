package com.boshz.udas.club.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 俱乐部收支台账(Club)实体类
 *
 * @author makejava
 * @since 2026-06-18 10:05:39
 */
@Data
public class Club implements Serializable {
    private static final long serialVersionUID = -87520760337421897L;
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 年度，字符串格式，如：2026
     */
    private String year;
    /**
     * 俱乐部英文编码
     */
    private String clubCode;
    /**
     * 收支大类：1-收入，2-支出
     */
    private Integer ioType;
    /**
     * 收支细分类型：会费、经费、奖励、历年结余
     */
    private String tradeType;
    /**
     * 收支业务发生时间
     */
    private LocalDate tradeTime;
    /**
     * 会计科目：其他应付款、文体活动费、场地费
     */
    private String subject;
    /**
     * 收支金额，统一存正数，io_type区分收支
     */
    private Double amount;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 备注说明
     */
    private String remark;
    /**
     * 记录创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 记录更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}
