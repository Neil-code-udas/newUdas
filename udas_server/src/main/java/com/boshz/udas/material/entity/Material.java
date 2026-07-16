package com.boshz.udas.material.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 办公用品基础档案(Material)实体类
 *
 * @author makejava
 * @since 2026-07-07 15:27:46
 */
@Data
public class Material implements Serializable {
    private static final long serialVersionUID = 394144564593293874L;
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 物资名称（唯一）
     */
    private String materialName;
    /**
     * 单位：支/盒/本
     */
    private String unit;
    /**
     * 采购单价
     */
    private BigDecimal price;
    /**
     * 当前实时库存
     */
    private BigDecimal stock;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}
