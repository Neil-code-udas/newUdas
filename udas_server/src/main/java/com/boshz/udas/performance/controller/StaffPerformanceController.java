package com.boshz.udas.performance.controller;

import com.boshz.udas.performance.entity.StaffPerformance;
import com.boshz.udas.performance.query.StaffPerfBatchUpdateQuery;
import com.boshz.udas.performance.service.StaffPerformanceService;
import com.boshz.udas.vo.QueryEntity;
import com.boshz.udas.vo.ResultVO;
import com.boshz.udas.vo.ResultVOUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;

/**
 * 员工绩效汇总表(StaffPerformance)表控制层
 *
 * @author makejava
 * @since 2026-06-18 21:11:46
 */
@RestController
@RequestMapping("/staffPerformance")
public class StaffPerformanceController {
    @Resource
    private StaffPerformanceService staffPerformanceService;

    /**
     * 分页查询：QueryEntity包装
     */
    @PostMapping("/getListByPage")
    public ResultVO getListByPage(@RequestBody QueryEntity<StaffPerformance> query) {
        return ResultVOUtil.success(staffPerformanceService.getListByPage(query));
    }


    /**
     * 分页查询：QueryEntity包装
     */
    @PostMapping("/getList")
    public ResultVO getList(@RequestBody StaffPerformance query) {
        return ResultVOUtil.success(staffPerformanceService.getByCondition(query));
    }
    /**
     * 根据主键查询单条
     */
    @GetMapping("/getById")
    public ResultVO getById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(staffPerformanceService.getById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public ResultVO add(@RequestBody StaffPerformance staffPerformance) {
        return ResultVOUtil.success(staffPerformanceService.insert(staffPerformance));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public ResultVO update(@RequestBody StaffPerformance staffPerformance) {
        return ResultVOUtil.success(staffPerformanceService.update(staffPerformance));
    }

    /**
     * 删除
     */
    @PostMapping("/deleteById")
    public ResultVO deleteById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(staffPerformanceService.deleteById(id));
    }

    /**
     * Excel导入
     */
    @PostMapping("/import")
    public ResultVO importExcel(@RequestParam("file") MultipartFile file,@RequestParam("period") String period) {
        Integer count = staffPerformanceService.importExcel(file,period);
        return ResultVOUtil.success(count);
    }

    /**
     * Excel导出（QueryEntity封装查询条件）
     */
    @PostMapping("/export")
    public void export(@RequestBody StaffPerformance query, HttpServletResponse response) throws IOException {
        staffPerformanceService.exportExcel(query, response);
    }

    /**
     * 批量更新员工单项绩效指标
     * 入参：周期 + 列表（工号、指标字段、金额）
     */
    @PostMapping("/batchUpdateField")
    public ResultVO<Boolean> batchUpdate(@RequestBody StaffPerfBatchUpdateQuery query) {
        return staffPerformanceService.batchUpdatePerf(query);
    }
}
