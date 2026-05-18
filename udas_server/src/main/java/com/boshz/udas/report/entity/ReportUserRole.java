package com.boshz.udas.report.entity;

import lombok.Data;
import java.util.Date;

@Data
public class ReportUserRole {
    private Long id;
    private String reportCode;
    private String userAccount;
    private String roleType;
    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;
}