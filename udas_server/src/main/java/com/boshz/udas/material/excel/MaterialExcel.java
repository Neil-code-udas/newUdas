package com.boshz.udas.material.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 办公用品基础档案 Excel导入导出实体
 *
 * @author makejava
 * @since 2026-07-07 15:27:47
 */
@Data
public class MaterialExcel {
    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID", index = 0)
    private Long id;
    /**
     * 物资名称（唯一）
     */
    @ExcelProperty(value = "物资名称（唯一）", index = 1)
    private String materialName;
    /**
     * 单位：支/盒/本
     */
    @ExcelProperty(value = "单位：支/盒/本", index = 2)
    private String unit;
    /**
     * 采购单价
     */
    @ExcelProperty(value = "采购单价", index = 3)
    private BigDecimal price;
    /**
     * 当前实时库存
     */
    @ExcelProperty(value = "当前实时库存", index = 4)
    private BigDecimal stock;
    /**
     *
     */
    @ExcelProperty(value = "", index = 5)
    private LocalDateTime createTime;
    /**
     *
     */
    @ExcelProperty(value = "", index = 6)
    private LocalDateTime updateTime;
}
