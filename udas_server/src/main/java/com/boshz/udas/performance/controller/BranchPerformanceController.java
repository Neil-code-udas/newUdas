package com.boshz.udas.performance.controller;

import com.boshz.udas.performance.entity.BranchPerformance;
import com.boshz.udas.performance.query.BranchPerformanceQuery;
import com.boshz.udas.performance.service.BranchPerformanceService;
import com.boshz.udas.performance.vo.BranchPerformanceVO;
import com.boshz.udas.vo.QueryEntity;
import com.boshz.udas.vo.ResultVO;
import com.boshz.udas.vo.ResultVOUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 支行季度绩效表(BranchPerformance)表控制层
 *
 * @author makejava
 * @since 2026-06-18 16:26:58
 */
@RestController
@RequestMapping("/branchPerformance")
public class BranchPerformanceController {
    @Resource
    private BranchPerformanceService branchPerformanceService;

    /**
     * 分页查询：QueryEntity包装
     */
    @PostMapping("/getListByPage")
    public ResultVO getListByPage(@RequestBody QueryEntity<BranchPerformance> query) {
        return ResultVOUtil.success(branchPerformanceService.getListByPage(query));
    }

    /**
     * 根据主键查询单条
     */
    @GetMapping("/getById")
    public ResultVO getById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(branchPerformanceService.getById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public ResultVO add(@RequestBody BranchPerformance branchPerformance) {
        return ResultVOUtil.success(branchPerformanceService.insert(branchPerformance));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public ResultVO update(@RequestBody BranchPerformance branchPerformance) {
        return ResultVOUtil.success(branchPerformanceService.update(branchPerformance));
    }

    /**
     * 删除
     */
    @PostMapping("/deleteById")
    public ResultVO deleteById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(branchPerformanceService.deleteById(id));
    }

    /**
     * Excel导入
     */
    @PostMapping("/import")
    public ResultVO importExcel(@RequestParam("file") MultipartFile file) {
        Integer count = branchPerformanceService.importExcel(file);
        return ResultVOUtil.success(count);
    }

    /**
     * Excel导出（QueryEntity封装查询条件）
     */
    @PostMapping("/export")
    public void export(@RequestBody BranchPerformance query, HttpServletResponse response) throws IOException {
        branchPerformanceService.exportExcel(query, response);
    }

    /**
     * Excel上传导入接口
     * @param file 上传的Excel文件
     * @param period 统计周期，例：2026.3月-2026.5月
     */
    @PostMapping("/import1")
    public ResultVO importExcel(MultipartFile file, @RequestParam String period) throws IOException {
        branchPerformanceService.importExcel(file.getInputStream(), period);
        return ResultVOUtil.success("导入成功！统计周期：" + period);
    }

    /**
     * 单机构绩效查询接口
     * 必须传 period + branchCode，只返回该网点对应周期绩效
     * 无分页，最多返回一条记录
     */
    @PostMapping("/single")
    public ResultVO<BranchPerformanceVO> singleBranchQuery(@RequestBody BranchPerformance query) {
        return branchPerformanceService.getSingleBranchPerformance(query);
    }
}
