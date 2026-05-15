package com.boshz.udas.report.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface ReportCommonMapper {

    // 分页查询
    List<Map<String, Object>> selectPageList(
            @Param("tableName") String tableName,
            @Param("columns") String columns,
            @Param("whereSql") String whereSql,
            @Param("params") Map<String, Object> params,
            @Param("offset") long offset,
            @Param("size") int size
    );

    // 统计总数
    long selectCount(
            @Param("tableName") String tableName,
            @Param("whereSql") String whereSql,
            @Param("params") Map<String, Object> params
    );

    // 根据ID查询单条
    Map<String, Object> getById(
            @Param("tableName") String tableName,
            @Param("id") Long id
    );

    // 通用新增
    int insert(
            @Param("tableName") String tableName,
            @Param("data") Map<String, Object> data
    );

    // 通用修改
    int update(
            @Param("tableName") String tableName,
            @Param("data") Map<String, Object> data
    );

    // 通用删除
    int deleteById(
            @Param("tableName") String tableName,
            @Param("id") Long id
    );

    // ===================== 自动建表（你最新功能） =====================
    void createTable(@Param("sql") String sql);
}