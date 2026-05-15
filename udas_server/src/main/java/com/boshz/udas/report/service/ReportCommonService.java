package com.boshz.udas.report.service;

import com.boshz.udas.report.entity.ReportColumn;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ReportCommonService {
    // 自动创建 MySQL 表
    void createTable(String tableName, List<Map<String, Object>> columns);

    // 原有
    Map<String, Object> getConfig(String code);

    PageVo<List<Map<String, Object>>> page(String code, QueryEntity q);

    // 新增CRUD
    Map<String, Object> get(String code, Long id);

    int add(String code, Map<String, Object> data,String account);

    int update(String code, Map<String, Object> data);

    int delete(String code, Long id);

    /**
     * 通用逻辑删除
     * @param tableName 表名
     * @param id 主键ID
     */
    void logicDelete(@Param("tableName") String tableName,
                     @Param("id") Long id);


    String createAndSaveTable(String reportName, List<ReportColumn> columns);

    void downloadTemplate(String code, HttpServletResponse response);
    // 导出（带条件）
    void exportData(String code, QueryEntity q, HttpServletResponse response);
    int importData(String code, MultipartFile file) throws Exception;

    String createTableByExcel(String reportName, MultipartFile file) throws IOException;
    List<ReportColumn> parseExcelHead(MultipartFile file) throws IOException;}