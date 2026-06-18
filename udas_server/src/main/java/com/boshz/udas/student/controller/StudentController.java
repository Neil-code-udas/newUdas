package com.boshz.udas.student.controller;

import com.boshz.udas.student.entity.Student;
import com.boshz.udas.student.service.StudentService;
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
 * 学生信息表(Student)表控制层
 *
 * @author makejava
 * @since 2026-06-12 15:12:47
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;

    /**
     * 分页查询：QueryEntity包装
     */
    @PostMapping("/getListByPage")
    public ResultVO getListByPage(@RequestBody QueryEntity<Student> query) {
        return ResultVOUtil.success(studentService.getListByPage(query));
    }

    /**
     * 根据主键查询单条
     */
    @GetMapping("/getById")
    public ResultVO getById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(studentService.getById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public ResultVO add(@RequestBody Student student) {
        return ResultVOUtil.success(studentService.insert(student));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public ResultVO update(@RequestBody Student student) {
        return ResultVOUtil.success(studentService.update(student));
    }

    /**
     * 删除
     */
    @PostMapping("/deleteById")
    public ResultVO deleteById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(studentService.deleteById(id));
    }

    /**
     * Excel导入
     */
    @PostMapping("/import")
    public ResultVO importExcel(@RequestParam("file") MultipartFile file) {
        Integer count = studentService.importExcel(file);
        return ResultVOUtil.success(count);
    }

    /**
     * Excel导出（QueryEntity封装查询条件）
     */
    @PostMapping("/export")
    public void export(@RequestBody Student query, HttpServletResponse response) throws IOException {
        studentService.exportExcel(query, response);
    }
}
