package com.boshz.udas.report.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.boshz.udas.report.entity.ReportColumn;
import com.boshz.udas.report.entity.ReportDef;
import com.boshz.udas.report.mapper.ReportColumnMapper;
import com.boshz.udas.report.mapper.ReportCommonMapper;
import com.boshz.udas.report.mapper.ReportDefMapper;
import com.boshz.udas.report.service.ReportCommonService;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReportCommonServiceImpl implements ReportCommonService {

    private final ReportDefMapper reportDefMapper;
    private final ReportColumnMapper reportColumnMapper;
    private final ReportCommonMapper reportCommonMapper;




    @Override
    public Map<String,Object> getConfig(String code){
        ReportDef def = reportDefMapper.selectByCode(code);
        List<ReportColumn> cols = reportColumnMapper.selectByReportCode(code);
        Map<String,Object> map = new HashMap<>();
        map.put("title", def.getReportName());
        map.put("columns", cols);
        return map;
    }

    @Override
    public PageVo<List<Map<String,Object>>> page(String code, QueryEntity q){
        ReportDef def = reportDefMapper.selectByCode(code);
        List<ReportColumn> cols = reportColumnMapper.selectByReportCode(code);

        List<String> fieldList = new ArrayList<>();
        fieldList.add(0,"id");
        for(ReportColumn c : cols){
            if(c.getIsList() == 1) fieldList.add(c.getColumnName());
        }
        String fields = String.join(",", fieldList);

        Map<String,Object> params = new HashMap<>();
        StringBuilder where = new StringBuilder("1=1");
        Map<String,Object> search = q.getSearchCondition();

        if(search != null){
            for(ReportColumn c : cols){
                if(c.getIsQuery() == 1 && search.containsKey(c.getColumnName())){
                    Object v = search.get(c.getColumnName());
                    if(v != null && !v.toString().isEmpty()){
                        where.append(" AND ").append(c.getColumnName()).append(" LIKE CONCAT('%',#{params.").append(c.getColumnName()).append("},'%')");
                        params.put(c.getColumnName(), v);
                    }
                }
            }
        }

        int current = q.getCurrent() == null ? 1 : q.getCurrent();
        int size = q.getPageSize();
        long offset = (long)(current-1)*size;

        List<Map<String,Object>> list = reportCommonMapper.selectPageList(
                def.getTableName(), fields, where.toString(), params, offset, size
        );
        long total = reportCommonMapper.selectCount(
                def.getTableName(), where.toString(), params
        );

        PageVo<List<Map<String,Object>>> vo = new PageVo<>();
        vo.setData(list);
        vo.setPage(current);
        vo.setSize(size);
        vo.setTotal((int)total);
        vo.setMessage("成功");
        return vo;
    }

    // ======================= 以下是我新增的通用CRUD（不影响原来代码） ==========================

    /**
     * 查询单条详情
     */
    @Override
    public Map<String, Object> get(String code, Long id) {
        ReportDef def = reportDefMapper.selectByCode(code);
        return reportCommonMapper.getById(def.getTableName(), id);
    }

    /**
     * 通用新增
     */
    @Override
    public int add(String code, Map<String, Object> data,String account) {
        ReportDef def = reportDefMapper.selectByCode(code);
        data.put("org_code","org_code");
        data.put("org_name","org_name");
        data.put("create_by", account);
        data.put("update_by", account);
//        data.put("create_time", new Date());
//        data.put("update_time", new Date());
        return reportCommonMapper.insert(def.getTableName(), data);
    }

    /**
     * 通用修改
     */
    @Override
    public int update(String code, Map<String, Object> data) {
        ReportDef def = reportDefMapper.selectByCode(code);
        return reportCommonMapper.update(def.getTableName(), data);
    }

    /**
     * 通用删除
     */
    @Override
    public int delete(String code, Long id) {
        ReportDef def = reportDefMapper.selectByCode(code);
        return reportCommonMapper.deleteById(def.getTableName(), id);
    }

    /**
     * 通用逻辑删除（自动获取表名）
     */
    public void logicDelete(String reportCode, Long dataId) {
        // 1. 根据 reportCode 获取表名
        ReportDef reportDef = reportDefMapper.selectByCode(reportCode);
        String tableName = reportDef.getTableName();

        // 2. 调用通用逻辑删除
        reportCommonMapper.logicDelete(tableName, dataId);
    }

    @Override
    public void createTable(String tableName, List<Map<String, Object>> columns) {
        // 1. 生成建表 SQL
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ").append(tableName).append(" (");
        sql.append("id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',");

        // 拼接字段
        for (Map<String, Object> col : columns) {
            String fieldName = col.get("fieldName").toString();
            String fieldType = col.get("fieldType").toString();
            String comment = col.get("comment").toString();

            String typeSql = "";
            switch (fieldType) {
                case "string": typeSql = "VARCHAR(255)"; break;
                case "number": typeSql = "BIGINT"; break;
                case "amount": typeSql = "DECIMAL(12,2)"; break;
                case "date": typeSql = "DATE"; break;
                case "datetime": typeSql = "DATETIME"; break;
                default: typeSql = "VARCHAR(255)";
            }

            sql.append(fieldName).append(" ").append(typeSql)
                    .append(" COMMENT '").append(comment).append("',");
        }

        sql.append("create_time DATETIME COMMENT '创建时间',");
        sql.append("update_time DATETIME COMMENT '更新时间'");
        sql.append(") ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='自动生成业务表'");

        // 2. 执行 SQL
        reportCommonMapper.createTable(sql.toString());
    }

    @Override
    public String createAndSaveTable(String reportName, List<ReportColumn> columnList) {

        // 1. 生成唯一表名（超级简单）
        long now = System.currentTimeMillis();
        String timeStr = String.valueOf(now);
        String bizCode = timeStr.substring(3); // 报表编码
        String realTableName = "tb_auto_" + bizCode;

        // 2. 建表SQL
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ").append(realTableName).append(" (");
        sql.append("id bigint primary key auto_increment comment '主键',");

        // 自动生成字段名：f_四位随机数_序号
        String autoFieldPrefix = "f_" + timeStr.substring(9);

        // 遍历字段
        for (int i = 0; i < columnList.size(); i++) {
            ReportColumn col = columnList.get(i);

            // ===================== 【核心】自动生成 columnName =====================
            String autoColumnName = autoFieldPrefix + "_" + String.format("%02d", i + 1);
            col.setColumnName(autoColumnName); // 后台自动赋值

            String fieldName = col.getColumnName();
            String type = col.getColumnType();
            String comment = col.getColumnLabel();

            String mysqlType;
            switch (type) {
                case "string": mysqlType = "varchar(255)"; break;
                case "number": mysqlType = "bigint"; break;
                case "amount": mysqlType = "decimal(12,2)"; break;
                case "date": mysqlType = "date"; break;
                case "datetime": mysqlType = "datetime"; break;
                default: mysqlType = "varchar(255)";
            }

            sql.append(fieldName).append(" ").append(mysqlType)
                    .append(" comment '").append(comment).append("',");
        }

        // 遍历字段 ... 【你的原有代码完全不动】

        // 👇 只改这里！给审计字段加 DEFAULT 默认值！
        sql.append("org_code varchar(64) NULL COMMENT '机构编码',");
        sql.append("org_name varchar(100) NULL COMMENT '机构名称',");
        sql.append("create_by varchar(64) NULL COMMENT '创建人',");
        sql.append("create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',");
        sql.append("update_by varchar(64) NULL COMMENT '更新人',");
        sql.append("update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',");
        sql.append("del_flag tinyint DEFAULT 0 COMMENT '删除标识'");

        sql.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='")
                .append(reportName).append("'");

        // 3. 执行建表
        reportCommonMapper.createTable(sql.toString());

        // 4. 保存配置
        ReportDef def = new ReportDef();
        def.setReportCode(bizCode);
        def.setReportName(reportName);
        def.setTableName(realTableName);
        reportDefMapper.insert(def);

        for (ReportColumn col : columnList) {
            col.setReportCode(bizCode);
            reportColumnMapper.insert(col);
        }
        return "/report/" + def.getReportCode();
    }

    // ==================== 1. 下载模板 ====================
    @Override
    public void downloadTemplate(String code, HttpServletResponse response) {
        try {
            List<List<String>> head = head(code);
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("导入模板.xlsx", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);

            // 写入空数据，只保留表头
            EasyExcel.write(response.getOutputStream())
                    .head(head)
                    .sheet("模板")
                    .doWrite(new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<List<String>> head(String code) {
        List<List<String>> head = new ArrayList<>();
        List<ReportColumn> columns = reportColumnMapper.selectByReportCode(code);
        for (ReportColumn c : columns) {
            List<String> headerRow = new ArrayList<>();
            headerRow.add(c.getColumnLabel());
            head.add(headerRow);
        }
        return head;
    }
    // ==================== 2. 导出数据 ====================
    @Override
    public void exportData(String code, QueryEntity q, HttpServletResponse response) {
        try {
            // 1. 获取报表配置
            ReportDef def = reportDefMapper.selectByCode(code);
            List<ReportColumn> columns = reportColumnMapper.selectByReportCode(code);

            // 2. 拼接查询字段
            StringBuilder columnStr = new StringBuilder("id");
            for (ReportColumn col : columns) {
                columnStr.append(",").append(col.getColumnName());
            }
            columnStr.append(",create_time,update_time");

            // 3. 拼接查询条件
            Map<String, Object> params = new HashMap<>();
            StringBuilder whereSql = new StringBuilder(" 1=1 ");

            Map<String,Object> search = q.getSearchCondition();

            if(search != null){
                for(ReportColumn c : columns){
                    if(c.getIsQuery() == 1 && search.containsKey(c.getColumnName())){
                        Object v = search.get(c.getColumnName());
                        if(v != null && !v.toString().isEmpty()){
                            whereSql.append(" AND ").append(c.getColumnName()).append(" LIKE CONCAT('%',#{params.").append(c.getColumnName()).append("},'%')");
                            params.put(c.getColumnName(), v);
                        }
                    }
                }
            }

            // 4. 查询数据
            List<Map<String, Object>> list = reportCommonMapper.selectPageList(
                    def.getTableName(),
                    columnStr.toString(),
                    whereSql.toString(),
                    params,
                    0L,
                    9999
            );

            List<List<String>> head = new ArrayList<>();
            List<String> fieldList = new ArrayList<>();

            List<String> headerRow1 = new ArrayList<>();
            headerRow1.add("ID");
            fieldList.add("id");
            head.add(headerRow1);
            for (ReportColumn c : columns) {
                List<String> headerRow = new ArrayList<>();
                headerRow.add(c.getColumnLabel());
                fieldList.add(c.getColumnName());
                head.add(headerRow);
            }

            List<String> headerRow2 = new ArrayList<>();
            headerRow2.add("创建时间");
            fieldList.add("create_time");
            head.add(headerRow2);

            List<String> headerRow3 = new ArrayList<>();
            headerRow3.add("更新时间");
            fieldList.add("update_time");
            head.add(headerRow3);

            // ===================== 【修复】数据行：Date 转字符串 =====================
            List<List<Object>> dataList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                List<Object> row = new ArrayList<>();
                for (String field : fieldList) {
                    Object val = map.get(field);

                    // ✅ 关键修复：Date 类型转字符串，解决 EasyExcel 不支持 Date
                    if (val instanceof Date) {
                        val = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(val);
                    }

                    row.add(val);
                }
                dataList.add(row);
            }

            // 5. 导出
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("数据导出.xlsx", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);

            EasyExcel.write(response.getOutputStream())
                    .head(head)
                    .sheet("数据")
                    .doWrite(dataList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==================== 3. 导入数据 ====================
    @Override
    public int importData(String code, MultipartFile file) throws Exception {
        // 1. 获取报表真实表名 + 字段配置
        ReportDef reportDef = reportDefMapper.selectByCode(code);
        List<ReportColumn> columns = reportColumnMapper.selectByReportCode(code);

        // 用于接收解析后的数据
        final int[] successCount = {0};

        // 2. 读取 Excel（监听解析）
        EasyExcel.read(file.getInputStream(), new AnalysisEventListener<Map<Integer, String>>() {

            // 读取每一行数据
            @Override
            public void invoke(Map<Integer, String> rowData, AnalysisContext context) {
                try {
                    // 跳过表头（第0行是表头，跳过）
                    if (context.readRowHolder().getRowIndex() == 0) {
                        return;
                    }

                    // 3. 封装动态字段 map
                    Map<String, Object> dataMap = new HashMap<>();
                    for (int i = 0; i < columns.size(); i++) {
                        ReportColumn column = columns.get(i);
                        String fieldName = column.getColumnName(); // f_xxxx_01
                        String value = rowData.get(i); // Excel 第 i 列的值
                        dataMap.put(fieldName, value);
                    }

                    // 4. 自动填充时间
                    dataMap.put("create_time", new Date());
                    dataMap.put("update_time", new Date());

                    // 5. 插入数据库
                    reportCommonMapper.insert(reportDef.getTableName(), dataMap);
                    successCount[0]++;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }

        }).sheet().doRead();

        return successCount[0];
    }


    @Override
    public String createTableByExcel(String reportName, MultipartFile file) throws IOException {
        // 1. 读取 Excel 所有行 → 修复：用 Map 接收，再转成 List
        List<List<String>> rows = new ArrayList<>();

        EasyExcel.read(file.getInputStream(), new AnalysisEventListener<Map<Integer, String>>() {
            @Override
            public void invoke(Map<Integer, String> rowMap, AnalysisContext context) {
                // 核心修复：把 Map 转成 List
                List<String> row = new ArrayList<>();
                for (int i = 0; i < rowMap.size(); i++) {
                    row.add(rowMap.getOrDefault(i, ""));
                }
                rows.add(row);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {}
        }).sheet().headRowNumber(0).doRead();

        // ====================== 下面代码完全不用改 ======================
        if (rows.size() < 4) {
            throw new RuntimeException("模板必须包含4行：字段名、类型、是否表格显示、是否可查询");
        }

        List<String> fieldNameRow = rows.get(0); // 第1行：字段名称
        List<String> typeRow = rows.get(1); // 第2行：类型
        List<String> isListRow = rows.get(2); // 第3行：是否列表
        List<String> isQueryRow = rows.get(3); // 第4行：是否查询

        List<ReportColumn> columnList = new ArrayList<>();
        int colCount = fieldNameRow.size();

        for (int i = 0; i < colCount; i++) {
            String label = getVal(fieldNameRow, i);
            String type = getVal(typeRow, i);
            String isListStr = getVal(isListRow, i);
            String isQueryStr = getVal(isQueryRow, i);

            ReportColumn col = new ReportColumn();
            col.setColumnName("f_" + (i + 1));
            col.setColumnLabel(label);
            col.setColumnType(type);
            col.setIsList("是".equalsIgnoreCase(isListStr) ? 1 : 0);
            col.setIsQuery("是".equalsIgnoreCase(isQueryStr) ? 1 : 0);

            columnList.add(col);
        }

        String path = createAndSaveTable(reportName, columnList);
        return path;
    }

    private String getVal(List<String> list,int idx){
        return idx<list.size()?(list.get(idx).trim()):"";
    }

    @Override
    public List<ReportColumn> parseExcelHead(MultipartFile file) throws IOException {

        List<List<String>> rows = new ArrayList<>();

        // 读取 Excel 第一行（表头）
        EasyExcel.read(file.getInputStream(), new AnalysisEventListener<Map<Integer, String>>() {
            @Override
            public void invoke(Map<Integer, String> rowMap, AnalysisContext context) {
                List<String> row = new ArrayList<>();
                for (int i = 0; i < rowMap.size(); i++) {
                    row.add(rowMap.getOrDefault(i, ""));
                }
                rows.add(row);
            }
            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {}
        }).sheet().headRowNumber(0).doRead();

        if (CollUtil.isEmpty(rows)) {
            return new ArrayList<>();
        }

        // 第一行是中文表头
        List<String> headList = rows.get(0);
        List<ReportColumn> columnList = new ArrayList<>();

        for (int i = 0; i < headList.size(); i++) {
            String label = headList.get(i) == null ? "" : headList.get(i).trim();
            if (StrUtil.isBlank(label)) continue;

            ReportColumn col = new ReportColumn();
            col.setColumnName("f_" + (i + 1));        // f_1,f_2...
            col.setColumnLabel(label);                 // 中文表头
            col.setColumnType("varchar(255)");         // 默认字符串
            col.setSort(i + 1);                        // 排序
            col.setIsList(1);                          // 列表显示
            col.setIsQuery(0);                         // 默认不查询
            col.setIsSortable(1);                      // 可排序
            col.setWidth(120);                         // 宽度
            col.setAlign("left");                      // 左对齐
            col.setIsImport(1);                        // 可导入
            col.setIsExport(1);                        // 可导出
            col.setIsRequired(0);                      // 不必填
            col.setIsDesensitize(0);                   // 不脱敏
            col.setDesensitizeType(null);
            col.setIsSelect(0);                        // 不是下拉
            col.setSelectOptions(null);
            col.setDelFlag(0);                         // 未删除

            columnList.add(col);
        }
        return columnList;
    }
}