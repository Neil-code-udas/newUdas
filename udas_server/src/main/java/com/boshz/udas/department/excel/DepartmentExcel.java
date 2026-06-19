package com.boshz.udas.department.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 机构部门表 Excel导入导出实体
 *
 * @author makejava
 * @since 2026-06-18 20:37:24
 */
@Data
public class DepartmentExcel {
    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID", index = 0)
    private Long id;
    /**
     * 机构编码
     */
    @ExcelProperty(value = "机构编码", index = 1)
    private String orgCode;
    /**
     * 机构名称
     */
    @ExcelProperty(value = "机构名称", index = 2)
    private String orgName;
}
