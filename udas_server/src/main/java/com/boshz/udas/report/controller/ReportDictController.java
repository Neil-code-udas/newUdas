package com.boshz.udas.report.controller;

import com.boshz.udas.report.entity.ReportDict;
import com.boshz.udas.report.service.ReportDictService;
import com.boshz.udas.vo.ResultVO;
import com.boshz.udas.vo.ResultVOUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/report/dict")
public class ReportDictController {

    @Resource
    private ReportDictService reportDictService;

    // 1. 新增下拉选项
    @PostMapping("/add")
    public ResultVO add(@RequestBody ReportDict dict) {
        reportDictService.addDict(dict);
        return ResultVOUtil.success();
    }

    // 2. 删除下拉选项
    @PostMapping("/delete")
    public ResultVO delete(
            @RequestParam String reportCode,
            @RequestParam String dictCode,
            @RequestParam String itemValue
    ) {
        reportDictService.deleteDict(reportCode, dictCode, itemValue);
        return ResultVOUtil.success();
    }

    // 3. 获取指定下拉框列表（前端渲染用）
    @GetMapping("/list")
    public ResultVO<List<ReportDict>> list(
            @RequestParam String reportCode,
            @RequestParam String dictCode
    ) {
        return ResultVOUtil.success(reportDictService.getDictList(reportCode, dictCode));
    }

    // 4. 获取当前报表所有字典（列表翻译用）
    @GetMapping("/all/{reportCode}")
    public ResultVO<List<ReportDict>> getAllDict(@PathVariable String reportCode) {
        return ResultVOUtil.success(reportDictService.getAllDictByReport(reportCode));
    }
}