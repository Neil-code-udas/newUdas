package com.boshz.udas.outerSubmit.entity;

import lombok.Data;
import java.util.Date;

@Data
public class ReportApply {
    private Long id;
    private String applyDept;        // 申请部门
    private String applyDeptCode;    // 申请部门编码
    private String operator;         // 经办人员
    private String operatorCode;     // 经办人员编码
    private String contactPhone;     // 联系电话
    private String recipientInfo;    // 收件人信息
    private String emailSubject;     // 报送邮件主题
    private String timeRequirement;  // 报送时效要求
    private String materialName;     // 报送材料名称
    private String deptManager;      // 部门负责人
    private String deptManagerCode;  // 部门负责人编码
    private String vicePresident;    // 分管行长
    private String vicePresidentCode;// 分管行长编码
    private String relatedFileIds;   // 关联文件ids
    private Date createTime;         // 创建时间
    private Date updateTime;         // 更新时间
}