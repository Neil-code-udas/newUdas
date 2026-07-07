package com.boshz.udas.pair.mapper;

import com.boshz.udas.pair.entity.TeacherStudentPair;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface TeacherStudentPairMapper {

    TeacherStudentPair selectById(String id);

    int deleteById(String id);

    int insert(TeacherStudentPair record);

    int update(TeacherStudentPair record);

    List<TeacherStudentPair> selectByCondition(@Param("query") TeacherStudentPair param);

    /**
     * 分页查询
     */
    List<TeacherStudentPair> queryAllByLimit(@Param("query") TeacherStudentPair query,
                                             @Param("offset") Integer offset,
                                             @Param("pageSize") Integer pageSize);

    Long selectCount(@Param("query") TeacherStudentPair query);

    /**
     * 批量新增
     */
    int insertBatch(@Param("entities") List<TeacherStudentPair> entities);
}
