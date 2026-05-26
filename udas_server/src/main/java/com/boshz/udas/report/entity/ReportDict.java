package com.boshz.udas.report.entity;

import lombok.Data;
import java.util.Date;

@Data
public class ReportDict {
    private Long id;
    private String reportCode;    // 报表编码
    private String dictName;      // 下拉框名称
    private String dictCode;      // 下拉框编码
    private String itemValue;     // 下拉框对应值
    private String itemLabel;     // 对应名称
    private Integer sort;         // 排序
    private Date createTime;      // 创建时间
}