package com.boshz.udas.club.controller;

import com.boshz.udas.club.entity.Club;
import com.boshz.udas.club.service.ClubAccountService;
import com.boshz.udas.club.service.ClubService;
import com.boshz.udas.club.vo.ClubYearTotalVO;
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
 * 俱乐部收支台账(Club)表控制层
 *
 * @author makejava
 * @since 2026-06-18 10:05:40
 */
@RestController
@RequestMapping("/club")
public class ClubController {
    @Resource
    private ClubService clubService;

    /**
     * 分页查询：QueryEntity包装
     */
    @PostMapping("/getListByPage")
    public ResultVO getListByPage(@RequestBody QueryEntity<Club> query) {
        return ResultVOUtil.success(clubService.getListByPage(query));
    }

    /**
     * 根据主键查询单条
     */
    @GetMapping("/getById")
    public ResultVO getById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(clubService.getById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public ResultVO add(@RequestBody Club club) {
        return ResultVOUtil.success(clubService.insert(club));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public ResultVO update(@RequestBody Club club) {
        return ResultVOUtil.success(clubService.update(club));
    }

    /**
     * 删除
     */
    @PostMapping("/deleteById")
    public ResultVO deleteById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(clubService.deleteById(id));
    }

    /**
     * Excel导入
     */
    @PostMapping("/import")
    public ResultVO importExcel(@RequestParam("file") MultipartFile file) {
        Integer count = clubService.importExcel(file);
        return ResultVOUtil.success(count);
    }

    /**
     * Excel导出（QueryEntity封装查询条件）
     */
    @PostMapping("/export")
    public void export(@RequestBody Club query, HttpServletResponse response) throws IOException {
        clubService.exportExcel(query, response);
    }


    @Resource
    private ClubAccountService clubAccountService;

    /**
     * 根据年度查询各俱乐部完整台账（含会费、经费全部明细流水，场地费单独统计不计入总支出）
     * @param year 年度，例：2025、2026
     */
    @PostMapping("/queryYearDetail")
    public ResultVO<ClubYearTotalVO> queryYearClubDetail(@RequestParam String year) {
        ClubYearTotalVO data = clubAccountService.getYearClubAccountDetail(year);
        return ResultVOUtil.success(data);
    }
}
