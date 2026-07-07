package com.boshz.udas.pair.service;

import com.boshz.udas.pair.entity.TeacherStudentPair;
import com.boshz.udas.pair.excel.TeacherStudentPairExcel;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 师徒结对主表 业务接口
 */
public interface TeacherStudentPairService {

    boolean insert(TeacherStudentPair record);

    boolean deleteById(String id);

    boolean update(TeacherStudentPair record);

    TeacherStudentPair getById(String id);

    /**
     * 分页查询：QueryEntity封装条件+pageNum+pageSize
     */
    PageVo getListByPage(QueryEntity<TeacherStudentPair> queryEntity);

    List<TeacherStudentPair> getByCondition(TeacherStudentPair param);

    void exportExcel(TeacherStudentPair query, HttpServletResponse response) throws IOException;

    Integer importExcel(MultipartFile file);
}
