package com.boshz.udas.report.service;

import com.boshz.udas.report.entity.ReportDict;

import java.util.List;

public interface ReportDictService {
    List<ReportDict> getDictList(String reportCode, String dictCode);
    void addDict(ReportDict dict);
    void deleteDict(String reportCode, String dictCode, String itemValue);
    List<ReportDict> getAllDictByReport(String reportCode);
}