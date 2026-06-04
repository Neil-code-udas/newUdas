package com.boshz.udas.subject.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class SubjectBudgetDTO {
    // 1. 科目基础信息（从 tb_union_subject 映射）
    private String code;
    private String name;
    private String parentCode;
    private Integer level;

    // 2. 预算/实际数据（从 tb_union_budget 映射，你之前漏掉的！）
    private Integer year;
    private BigDecimal budget;
    private BigDecimal actual;

    // 3. 处理后的数据（给前端用的树形+动态年度）
    private Map<String, YearDataDTO> yearData = new HashMap<>();
    private List<SubjectBudgetDTO> children = new ArrayList<>();
}