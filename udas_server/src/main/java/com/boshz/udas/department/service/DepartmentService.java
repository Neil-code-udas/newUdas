package com.boshz.udas.department.service;

import com.boshz.udas.department.entity.Department;
import com.boshz.udas.department.excel.DepartmentExcel;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 机构部门表 业务接口
 */
public interface DepartmentService {

    boolean insert(Department record);

    boolean deleteById(Long id);

    boolean update(Department record);

    Department getById(Long id);

    /**
     * 分页查询：QueryEntity封装条件+pageNum+pageSize
     */
    PageVo getListByPage(QueryEntity<Department> queryEntity);

    List<Department> getByCondition(Department param);

    void exportExcel(Department query, HttpServletResponse response) throws IOException;

    Integer importExcel(MultipartFile file);
}
