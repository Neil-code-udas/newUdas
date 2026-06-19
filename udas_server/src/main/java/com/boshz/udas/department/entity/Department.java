package com.boshz.udas.department.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 机构部门表(Department)实体类
 *
 * @author makejava
 * @since 2026-06-18 20:37:24
 */
@Data
public class Department implements Serializable {
    private static final long serialVersionUID = 260365625401462304L;
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 机构编码
     */
    private String orgCode;
    /**
     * 机构名称
     */
    private String orgName;

}
