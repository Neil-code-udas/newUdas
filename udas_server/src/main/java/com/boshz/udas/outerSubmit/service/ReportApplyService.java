package com.boshz.udas.outerSubmit.service;

import com.boshz.udas.report.entity.ReportApply;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;

import java.util.List;
import java.util.Map;

public interface ReportApplyService {

    // 初始化建表
    void initTable();

    // 分页查询
    PageVo<List<ReportApply>> page(QueryEntity<?> q);

    // 单条查询
    ReportApply getById(Long id);

    // 新增
    int add(ReportApply reportApply);

    // 修改
    int update(ReportApply reportApply);

    // 删除
    int delete(Long id);
}