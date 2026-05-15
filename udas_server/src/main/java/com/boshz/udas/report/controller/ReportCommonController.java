package com.boshz.udas.report.controller;

import com.boshz.udas.report.entity.ReportColumn;
import com.boshz.udas.report.service.ReportCommonService;
import com.boshz.udas.vo.ResultVO;
import com.boshz.udas.vo.ResultVOUtil;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report/common")
@RequiredArgsConstructor
public class ReportCommonController {

    private final ReportCommonService reportCommonService;

    // 获取配置
    @GetMapping("/config")
    public ResultVO<Map<String, Object>> config(@RequestParam String code) {
        return ResultVOUtil.success(reportCommonService.getConfig(code));
    }

    // 分页查询
    @PostMapping("/page")
    public ResultVO<PageVo<List<Map<String, Object>>>> page(
            @RequestParam String code,
            @RequestBody QueryEntity<?> q
    ) {
        return ResultVOUtil.success(reportCommonService.page(code, q));
    }

    // 单条详情
    @GetMapping("/get")
    public ResultVO<Map<String, Object>> get(
            @RequestParam String code,
            @RequestParam Long id
    ) {
        return ResultVOUtil.success(reportCommonService.get(code, id));
    }

    // 新增
    @PostMapping("/add")
    public ResultVO<Integer> add(
            @RequestParam String code,
            @RequestBody Map<String, Object> data
    ) {
        return ResultVOUtil.success(reportCommonService.add(code, data));
    }

    // 修改
    @PostMapping("/update")
    public ResultVO<Integer> update(
            @RequestParam String code,
            @RequestBody Map<String, Object> data
    ) {
        return ResultVOUtil.success(reportCommonService.update(code, data));
    }

    // 删除
    @GetMapping("/delete")
    public ResultVO<Integer> delete(
            @RequestParam String code,
            @RequestParam Long id
    ) {
        return ResultVOUtil.success(reportCommonService.delete(code, id));
    }


    // 一键自动建表（你最新功能）
    @PostMapping("/createTable")
    public ResultVO createTable(
            @RequestParam String reportName,
            @RequestBody List<ReportColumn> columns
    ) {
        reportCommonService.createAndSaveTable(reportName, columns);
        return ResultVOUtil.success();
    }

    // 1. 下载 Excel 模板
    @GetMapping("/downloadTemplate")
    public void downloadTemplate(@RequestParam String code, HttpServletResponse response) {
        reportCommonService.downloadTemplate(code, response);
    }

    // 导出数据（带查询条件）
    @PostMapping("/export")
    public void export(
            @RequestParam String code,
            @RequestBody QueryEntity<?> q,
            HttpServletResponse response
    ) {
        reportCommonService.exportData(code, q, response);
    }

    // 3. Excel 导入
    @PostMapping("/import")
    public ResultVO<Integer> importExcel(
            @RequestParam String code,
            @RequestParam("file") MultipartFile file
    ) throws Exception {
        return ResultVOUtil.success(reportCommonService.importData(code, file));
    }
    //========================================================================================
    @PostMapping("/createTableByExcel")
    public ResultVO createTableByExcel(
            @RequestParam String reportName,
            @RequestParam MultipartFile file) throws Exception {

        reportCommonService.createTableByExcel(reportName, file);
        return ResultVOUtil.success();
    }
}