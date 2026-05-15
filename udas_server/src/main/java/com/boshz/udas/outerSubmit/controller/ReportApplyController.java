package com.boshz.udas.outerSubmit.controller;

import com.boshz.udas.report.entity.ReportApply;
import com.boshz.udas.report.service.ReportApplyService;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import com.boshz.udas.vo.ResultVO;
import com.boshz.udas.vo.ResultVOUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report/apply")
@RequiredArgsConstructor
public class ReportApplyController {

    private final ReportApplyService reportApplyService;

    // 初始化建表（仅首次调用）
    @GetMapping("/initTable")
    public ResultVO<Object> initTable() {
        reportApplyService.initTable();
        return ResultVOUtil.success("表创建成功");
    }

    // 分页查询
    @PostMapping("/page")
    public ResultVO<PageVo<List<ReportApply>>> page(@RequestBody QueryEntity<?> q) {
        return ResultVOUtil.success(reportApplyService.page(q));
    }

    // 单条查询
    @GetMapping("/get")
    public ResultVO<ReportApply> get(@RequestParam Long id) {
        return ResultVOUtil.success(reportApplyService.getById(id));
    }

    // 新增
    @PostMapping("/add")
    public ResultVO<Integer> add(@RequestBody ReportApply reportApply) {
        int count = reportApplyService.add(reportApply);
        return ResultVOUtil.success(count);
    }

    // 修改
    @PostMapping("/update")
    public ResultVO<Integer> update(@RequestBody ReportApply reportApply) {
        int count = reportApplyService.update(reportApply);
        return ResultVOUtil.success(count);
    }

    // 删除
    @GetMapping("/delete")
    public ResultVO<Integer> delete(@RequestParam Long id) {
        int count = reportApplyService.delete(id);
        return ResultVOUtil.success(count);
    }
}