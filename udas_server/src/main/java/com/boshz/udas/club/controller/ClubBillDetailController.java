package com.boshz.udas.club.controller;

import com.boshz.udas.club.entity.ClubBillDetail;
import com.boshz.udas.club.service.ClubBillDetailService;
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
 * 俱乐部收支明细表(ClubBillDetail)表控制层
 *
 * @author makejava
 * @since 2026-06-11 15:33:44
 */
@RestController
@RequestMapping("/clubBillDetail")
public class ClubBillDetailController {
    @Resource
    private ClubBillDetailService clubBillDetailService;

    /**
     * 分页查询：QueryEntity包装
     */
    @PostMapping("/getListByPage")
    public ResultVO getListByPage(@RequestBody QueryEntity<ClubBillDetail> query) {
        return ResultVOUtil.success(clubBillDetailService.getListByPage(query));
    }

    /**
     * 根据主键查询单条
     */
    @GetMapping("/getById")
    public ResultVO getById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(clubBillDetailService.getById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public ResultVO add(@RequestBody ClubBillDetail clubBillDetail) {
        return ResultVOUtil.success(clubBillDetailService.insert(clubBillDetail));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public ResultVO update(@RequestBody ClubBillDetail clubBillDetail) {
        return ResultVOUtil.success(clubBillDetailService.update(clubBillDetail));
    }

    /**
     * 删除
     */
    @PostMapping("/deleteById")
    public ResultVO deleteById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(clubBillDetailService.deleteById(id));
    }

    /**
     * Excel导入
     */
    @PostMapping("/import")
    public ResultVO importExcel(@RequestParam("file") MultipartFile file) {
        Integer count = clubBillDetailService.importExcel(file);
        return ResultVOUtil.success(count);
    }

    /**
     * Excel导出（QueryEntity封装查询条件）
     */
    @PostMapping("/export")
    public void export(@RequestBody ClubBillDetail query, HttpServletResponse response) throws IOException {
        clubBillDetailService.exportExcel(query, response);
    }
}
