package com.boshz.udas.department.mapper;

import com.boshz.udas.department.entity.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface DepartmentMapper {

    Department selectById(Long id);
    Department selectByOrgName(String orgName);

    int deleteById(Long id);

    int insert(Department record);

    int update(Department record);

    List<Department> selectByCondition(@Param("param") Department param);

    /**
     * 分页查询
     */
    List<Department> queryAllByLimit(@Param("query") Department query,
                                     @Param("offset") Integer offset,
                                     @Param("pageSize") Integer pageSize);

    Long selectCount(@Param("query") Department query);

    /**
     * 批量新增
     */
    int insertBatch(@Param("entities") List<Department> entities);
}
