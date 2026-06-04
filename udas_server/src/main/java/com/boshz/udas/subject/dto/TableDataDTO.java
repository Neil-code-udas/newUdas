package com.boshz.udas.subject.dto;

import lombok.Data;
import java.util.List;

/**
 * 表格整体返回：年度列表 + 树形行数据
 */
@Data
public class TableDataDTO {
    // 查询的年度集合
    private List<Integer> years;
    // 树形行数据
    private List<SubjectBudgetDTO> rows;
}