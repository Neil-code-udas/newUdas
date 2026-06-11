package com.boshz.udas.secretPersonnel.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 涉密人员文件关联表(SecretPersonFile)实体类
 *
 * @author makejava
 * @since 2026-06-09 11:35:55
 */
@Data
public class SecretPersonFile implements Serializable {
    private static final long serialVersionUID = 303554260384104450L;
    /**
     * 涉密人员ID
     */
    private Long secretId;
    /**
     * 文件ID
     */
    private Long fileId;
    /**
     * 文件类型:commit保密承诺书/handover离岗交接记录
     */
    private String type;

}
