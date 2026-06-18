package com.boshz.udas.student.mapper;

import com.boshz.udas.student.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface StudentMapper {

    Student selectById(Long id);

    int deleteById(Long id);

    int insert(Student record);

    int update(Student record);

    List<Student> selectByCondition(@Param("param") Student param);

    /**
     * 分页查询
     */
    List<Student> queryAllByLimit(@Param("query") Student query,
                                  @Param("offset") Integer offset,
                                  @Param("pageSize") Integer pageSize);

    Long selectCount(@Param("query") Student query);

    /**
     * 批量新增
     */
    int insertBatch(@Param("entities") List<Student> entities);
}
