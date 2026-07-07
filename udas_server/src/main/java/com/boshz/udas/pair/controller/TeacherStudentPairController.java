package com.boshz.udas.pair.controller;

import com.boshz.udas.pair.entity.TeacherStudentPair;
import com.boshz.udas.pair.service.TeacherStudentPairService;
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
 * 师徒结对主表(TeacherStudentPair)表控制层
 *
 * @author makejava
 * @since 2026-07-03 16:17:33
 */
@RestController
@RequestMapping("/api/teacherStudentPair")
public class TeacherStudentPairController {
    @Resource
    private TeacherStudentPairService teacherStudentPairService;

    /**
     * 分页查询：QueryEntity包装
     */
    @PostMapping("/getListByPage")
    public ResultVO getListByPage(@RequestBody QueryEntity<TeacherStudentPair> query) {
        return ResultVOUtil.success(teacherStudentPairService.getListByPage(query));
    }

    /**
     * 根据主键查询单条
     */
    @GetMapping("/getById")
    public ResultVO getById(@RequestParam("id") String id) {
        return ResultVOUtil.success(teacherStudentPairService.getById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public ResultVO add(@RequestBody TeacherStudentPair teacherStudentPair) {
        return ResultVOUtil.success(teacherStudentPairService.insert(teacherStudentPair));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public ResultVO update(@RequestBody TeacherStudentPair teacherStudentPair) {
        return ResultVOUtil.success(teacherStudentPairService.update(teacherStudentPair));
    }

    /**
     * 删除
     */
    @PostMapping("/deleteById")
    public ResultVO deleteById(@RequestParam("id") String id) {
        return ResultVOUtil.success(teacherStudentPairService.deleteById(id));
    }

    /**
     * Excel导入
     */
    @PostMapping("/import")
    public ResultVO importExcel(@RequestParam("file") MultipartFile file) {
        Integer count = teacherStudentPairService.importExcel(file);
        return ResultVOUtil.success(count);
    }

    /**
     * Excel导出（QueryEntity封装查询条件）
     */
    @PostMapping("/export")
    public void export(@RequestBody TeacherStudentPair query, HttpServletResponse response) throws IOException {
        teacherStudentPairService.exportExcel(query, response);
    }
}
