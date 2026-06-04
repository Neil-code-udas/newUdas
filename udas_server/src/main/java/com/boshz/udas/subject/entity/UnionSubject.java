package com.boshz.udas.subject.entity;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class UnionSubject {
    private Long id;
    private String code;
    private String name;
    private String parentCode;
    private Integer level;
    private Date createTime;
    private Date updateTime;
    //树形备用字段
    private List<UnionSubject> children;
}