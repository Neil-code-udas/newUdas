package com.boshz.udas.mealTicket.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 餐券预约表(MealTicket)实体类
 *
 * @author makejava
 * @since 2026-07-07 17:31:38
 */
@Data
public class MealTicket implements Serializable {
    private static final long serialVersionUID = -48385238383693194L;
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 申请人姓名
     */
    private String applyName;
    /**
     * 申请人工号
     */
    private String applyAccount;
    /**
     * 申请部门名称
     */
    private String deptName;
    /**
     * 申请部门编码
     */
    private String deptCode;
    /**
     * 部门经办人员姓名
     */
    private String handlerName;
    /**
     * 部门经办人员工号
     */
    private String handlerCode;
    /**
     * 办公室审批人姓名
     */
    private String officeName;
    /**
     * 办公室审批人工号
     */
    private String officeCode;
    /**
     * 办公室经办人员姓名
     */
    private String officeHandlerName;
    /**
     * 办公室经办人员工号
     */
    private String officeHandlerCode;
    /**
     * 用餐日期
     */
    private LocalDate mealDate;
    /**
     * 用餐地点
     */
    private String mealLocation;
    /**
     * 用餐人数
     */
    private Integer attendNum;
    /**
     * 实习生姓名
     */
    private String internName;
    /**
     * 用餐事由
     */
    private String mealReason;
    /**
     * 附件文件ID，多个用英文逗号分隔
     */
    private String fileIds;
    /**
     * 流程类型
     */
    private String flowType;
    /**
     * 申请日期
     */
    private LocalDate applyDate;
    /**
     * 申请编号(唯一)
     */
    private String applyNo;
    /**
     * 当前审批节点
     */
    private String node;
    /**
     * 节点审批状态
     */
    private String nodeState;
    /**
     * 整体流程状态
     */
    private String flowStatus;
    /**
     * 单据状态
     */
    private String status;
    /**
     * 用餐开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;
    /**
     * 用餐结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

}
