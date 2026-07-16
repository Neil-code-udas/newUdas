package com.boshz.udas.material.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 出入库台账流水 Excel导入导出实体
 *
 * @author makejava
 * @since 2026-07-07 15:27:46
 */
@Data
public class StockRecordExcel {
    /**
     *
     */
    @ExcelProperty(value = "", index = 0)
    private Long id;
    /**
     * 关联物资ID
     */
    @ExcelProperty(value = "关联物资ID", index = 1)
    private Long materialId;
    /**
     * IN入库 / OUT出库
     */
    @ExcelProperty(value = "IN入库 / OUT出库", index = 2)
    private String changeType;
    /**
     * 变动数量
     */
    @ExcelProperty(value = "变动数量", index = 3)
    private BigDecimal changeNum;
    /**
     * 变动前库存
     */
    @ExcelProperty(value = "变动前库存", index = 4)
    private BigDecimal beforeStock;
    /**
     * 变动后库存
     */
    @ExcelProperty(value = "变动后库存", index = 5)
    private BigDecimal afterStock;
    /**
     * 出库领用人，入库为空
     */
    @ExcelProperty(value = "出库领用人，入库为空", index = 6)
    private String usePerson;
    /**
     * 备注说明
     */
    @ExcelProperty(value = "备注说明", index = 7)
    private String remark;
    /**
     * 操作时间
     */
    @ExcelProperty(value = "操作时间", index = 8)
    private LocalDateTime createTime;
}
