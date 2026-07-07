package com.boshz.udas.pair.controller;

import com.boshz.udas.pair.dto.TimeLineSaveDTO;
import com.boshz.udas.pair.entity.PairTimeLine;
import com.boshz.udas.pair.service.PairTimeLineService;
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
 * 师徒时间轴记录表(PairTimeLine)表控制层
 *
 * @author makejava
 * @since 2026-07-03 16:17:33
 */
@RestController
@RequestMapping("/api/pairTimeLine")
public class PairTimeLineController {
    @Resource
    private PairTimeLineService pairTimeLineService;

    /**
     * 分页查询：QueryEntity包装
     */
    @PostMapping("/getListByPage")
    public ResultVO getListByPage(@RequestBody QueryEntity<PairTimeLine> query) {
        return ResultVOUtil.success(pairTimeLineService.getListByPage(query));
    }

    /**
     * 根据主键查询单条
     */
    @GetMapping("/getById")
    public ResultVO getById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(pairTimeLineService.getById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public ResultVO add(@RequestBody PairTimeLine pairTimeLine) {
        return ResultVOUtil.success(pairTimeLineService.insert(pairTimeLine));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public ResultVO update(@RequestBody PairTimeLine pairTimeLine) {
        return ResultVOUtil.success(pairTimeLineService.update(pairTimeLine));
    }

    /**
     * 删除
     */
    @PostMapping("/deleteById")
    public ResultVO deleteById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(pairTimeLineService.deleteById(id));
    }

    /**
     * Excel导入
     */
    @PostMapping("/import")
    public ResultVO importExcel(@RequestParam("file") MultipartFile file) {
        Integer count = pairTimeLineService.importExcel(file);
        return ResultVOUtil.success(count);
    }

    /**
     * Excel导出（QueryEntity封装查询条件）
     */
    @PostMapping("/export")
    public void export(@RequestBody PairTimeLine query, HttpServletResponse response) throws IOException {
        pairTimeLineService.exportExcel(query, response);
    }


    /**
     * 新增时间轴记录：徒弟提交周报、师父审阅、管理员录入考核
     */
    @PostMapping("/save")
    public ResultVO saveTimeLine(@RequestBody TimeLineSaveDTO dto) {
        return pairTimeLineService.saveTimeLine(dto);
    }

    /**
     * 根据TP结对编号查询全部时间轴记录
     */
    @GetMapping("/list")
    public ResultVO getTimeLineList(@RequestParam String pairId) {
        return pairTimeLineService.getTimeLineList(pairId);
    }
}
