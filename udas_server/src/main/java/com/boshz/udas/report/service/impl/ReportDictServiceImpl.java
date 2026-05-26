package com.boshz.udas.report.service.impl;

import com.boshz.udas.report.entity.ReportDict;
import com.boshz.udas.report.mapper.ReportDictMapper;
import com.boshz.udas.report.service.ReportDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ReportDictServiceImpl implements ReportDictService {

    @Resource
    private ReportDictMapper reportDictMapper;

    @Override
    public List<ReportDict> getDictList(String reportCode, String dictCode) {
        return reportDictMapper.listByDictCode(reportCode, dictCode);
    }

    @Override
    public void addDict(ReportDict dict) {
        reportDictMapper.insertDict(dict);
    }

    @Override
    public void deleteDict(String reportCode, String dictCode, String itemValue) {
        reportDictMapper.deleteDict(reportCode, dictCode, itemValue);
    }

    @Override
    public List<ReportDict> getAllDictByReport(String reportCode) {
        return reportDictMapper.listByReportCode(reportCode);
    }
}