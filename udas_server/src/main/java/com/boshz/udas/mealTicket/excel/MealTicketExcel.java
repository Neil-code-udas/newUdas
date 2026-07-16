package com.boshz.udas.mealTicket.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 餐券预约表 Excel导入导出实体
 *
 * @author makejava
 * @since 2026-07-07 17:31:39
 */
@Data
public class MealTicketExcel {
    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID", index = 0)
    private Long id;
    /**
     * 申请人姓名
     */
    @ExcelProperty(value = "申请人姓名", index = 1)
    private String applyName;
    /**
     * 申请人工号
     */
    @ExcelProperty(value = "申请人工号", index = 2)
    private String applyAccount;
    /**
     * 申请部门名称
     */
    @ExcelProperty(value = "申请部门名称", index = 3)
    private String deptName;
    /**
     * 申请部门编码
     */
    @ExcelProperty(value = "申请部门编码", index = 4)
    private String deptCode;
    /**
     * 部门经办人员姓名
     */
    @ExcelProperty(value = "部门经办人员姓名", index = 5)
    private String handlerName;
    /**
     * 部门经办人员工号
     */
    @ExcelProperty(value = "部门经办人员工号", index = 6)
    private String handlerCode;
    /**
     * 办公室审批人姓名
     */
    @ExcelProperty(value = "办公室审批人姓名", index = 7)
    private String officeName;
    /**
     * 办公室审批人工号
     */
    @ExcelProperty(value = "办公室审批人工号", index = 8)
    private String officeCode;
    /**
     * 办公室经办人员姓名
     */
    @ExcelProperty(value = "办公室经办人员姓名", index = 9)
    private String officeHandlerName;
    /**
     * 办公室经办人员工号
     */
    @ExcelProperty(value = "办公室经办人员工号", index = 10)
    private String officeHandlerCode;
    /**
     * 用餐日期
     */
    @ExcelProperty(value = "用餐日期", index = 11)
    private LocalDate mealDate;
    /**
     * 用餐地点
     */
    @ExcelProperty(value = "用餐地点", index = 12)
    private String mealLocation;
    /**
     * 用餐人数
     */
    @ExcelProperty(value = "用餐人数", index = 13)
    private Integer attendNum;
    /**
     * 实习生姓名
     */
    @ExcelProperty(value = "实习生姓名", index = 14)
    private String internName;
    /**
     * 用餐事由
     */
    @ExcelProperty(value = "用餐事由", index = 15)
    private String mealReason;
    /**
     * 附件文件ID，多个用英文逗号分隔
     */
    @ExcelProperty(value = "附件文件ID，多个用英文逗号分隔", index = 16)
    private String fileIds;
    /**
     * 流程类型
     */
    @ExcelProperty(value = "流程类型", index = 17)
    private String flowType;
    /**
     * 申请日期
     */
    @ExcelProperty(value = "申请日期", index = 18)
    private LocalDate applyDate;
    /**
     * 申请编号(唯一)
     */
    @ExcelProperty(value = "申请编号(唯一)", index = 19)
    private String applyNo;
    /**
     * 当前审批节点
     */
    @ExcelProperty(value = "当前审批节点", index = 20)
    private String node;
    /**
     * 节点审批状态
     */
    @ExcelProperty(value = "节点审批状态", index = 21)
    private String nodeState;
    /**
     * 整体流程状态
     */
    @ExcelProperty(value = "整体流程状态", index = 22)
    private String flowStatus;
    /**
     * 单据状态
     */
    @ExcelProperty(value = "单据状态", index = 23)
    private String status;
    /**
     * 用餐开始时间
     */
    @ExcelProperty(value = "用餐开始时间", index = 24)
    private LocalDateTime startTime;
    /**
     * 用餐结束时间
     */
    @ExcelProperty(value = "用餐结束时间", index = 25)
    private LocalDateTime endTime;
}
