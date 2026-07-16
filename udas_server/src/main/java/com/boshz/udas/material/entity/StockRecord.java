package com.boshz.udas.material.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 出入库台账流水(StockRecord)实体类
 *
 * @author makejava
 * @since 2026-07-07 15:27:46
 */
@Data
public class StockRecord implements Serializable {
    private static final long serialVersionUID = -94324328740048971L;

    private Long id;
    /**
     * 关联物资ID
     */
    private Long materialId;
    /**
     * IN入库 / OUT出库
     */
    private String changeType;
    /**
     * 变动数量
     */
    private BigDecimal changeNum;
    /**
     * 变动前库存
     */
    private BigDecimal beforeStock;
    /**
     * 变动后库存
     */
    private BigDecimal afterStock;
    /**
     * 出库领用人，入库为空
     */
    private String usePerson;
    /**
     * 备注说明
     */
    private String remark;
    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

}
