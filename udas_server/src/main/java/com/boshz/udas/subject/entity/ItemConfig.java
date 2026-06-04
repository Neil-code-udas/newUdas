package com.boshz.udas.subject.entity;

import lombok.Data;
import java.util.Date;

@Data
public class ItemConfig {
    private Long id;
    private String itemName;
    private String subjectCode;
    private String remark;
    private String createBy;
    private Date createTime;
    private Date updateTime;
}