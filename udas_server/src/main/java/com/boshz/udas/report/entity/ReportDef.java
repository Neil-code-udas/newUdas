package com.boshz.udas.report.entity;

import lombok.Data;

@Data
public class ReportDef {
    private Long id;
    private String reportCode;
    private String reportName;
    private String tableName;
}