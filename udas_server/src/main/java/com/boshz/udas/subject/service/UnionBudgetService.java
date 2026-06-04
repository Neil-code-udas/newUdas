package com.boshz.udas.subject.service;

import com.boshz.udas.subject.dto.TableDataDTO;

import java.util.List;

public interface UnionBudgetService {

    /**
     * 获取预决算表格树形数据
     * @param yearList 年度集合
     * @param subjectCode 筛选科目编码
     * @param subjectName 科目名称模糊
     * @return 表格数据
     */
    TableDataDTO getTableData(List<Integer> yearList, String subjectCode, String subjectName);
}