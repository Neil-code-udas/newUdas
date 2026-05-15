package com.boshz.udas.report.entity;

import lombok.Data;

@Data
public class ReportColumn {
    private Long id;
    private String reportCode;
    private String columnName;
    private String columnLabel;
    private String columnType;
    private Integer isList;
    private Integer isQuery;
}