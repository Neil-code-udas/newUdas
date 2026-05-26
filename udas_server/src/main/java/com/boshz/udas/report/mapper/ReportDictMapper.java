package com.boshz.udas.report.mapper;

import com.boshz.udas.report.entity.ReportDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportDictMapper {

    // 根据 报表编码 + 字典编码 获取下拉选项
    List<ReportDict> listByDictCode(
            @Param("reportCode") String reportCode,
            @Param("dictCode") String dictCode
    );

    // 查询当前报表所有字典（用于列表翻译）
    List<ReportDict> listByReportCode(@Param("reportCode") String reportCode);

    // 新增字典选项
    int insertDict(ReportDict dict);

    // 删除选项（物理删除）
    int deleteDict(
            @Param("reportCode") String reportCode,
            @Param("dictCode") String dictCode,
            @Param("itemValue") String itemValue
    );
}