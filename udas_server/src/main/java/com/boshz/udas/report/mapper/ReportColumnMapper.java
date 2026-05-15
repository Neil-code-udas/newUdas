package com.boshz.udas.report.mapper;


import com.boshz.udas.report.entity.ReportColumn;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportColumnMapper {
    List<ReportColumn> selectByReportCode(String reportCode);
    /**
     * 新增字段配置
     */
    int insert(ReportColumn reportColumn);
}