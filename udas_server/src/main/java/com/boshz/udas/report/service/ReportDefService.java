package com.boshz.udas.report.service;

import com.boshz.udas.report.entity.ReportDef;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;

public interface ReportDefService {

    PageVo getListByPage(QueryEntity<ReportDef> queryEntity, String account);
}