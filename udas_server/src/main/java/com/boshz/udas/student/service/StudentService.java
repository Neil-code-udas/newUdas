package com.boshz.udas.student.service;

import com.boshz.udas.student.entity.Student;
import com.boshz.udas.student.excel.StudentExcel;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 学生信息表 业务接口
 */
public interface StudentService {

    boolean insert(Student record);

    boolean deleteById(Long id);

    boolean update(Student record);

    Student getById(Long id);

    /**
     * 分页查询：QueryEntity封装条件+pageNum+pageSize
     */
    PageVo getListByPage(QueryEntity<Student> queryEntity);

    List<Student> getByCondition(Student param);

    void exportExcel(Student query, HttpServletResponse response) throws IOException;

    Integer importExcel(MultipartFile file);
}
