package com.boshz.udas.report.mapper;


import com.boshz.udas.report.entity.ReportDef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportDefMapper {
    List<ReportDef> selectAll();
    ReportDef selectByCode(String reportCode);

    int insert(ReportDef reportDef);

    int countByCodePrefix(@Param("prefix") String prefix);

    List<ReportDef> selectReportList(String reportName, int offset, Integer pageSize);

    int selectReportCount(String reportName);
}