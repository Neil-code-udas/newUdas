package com.boshz.udas.pair.mapper;


import com.boshz.udas.pair.dto.PairQueryDTO;
import com.boshz.udas.pair.dto.StaffDTO;
import com.boshz.udas.pair.entity.TeacherStudentPair;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PairMapper {
    int insert(TeacherStudentPair pair);
    TeacherStudentPair selectById(@Param("pairId") String pairId);
    List<TeacherStudentPair> selectList(PairQueryDTO query);
    List<StaffDTO> selectTeacherList(@Param("orgId") String orgId);
    List<StaffDTO> selectStudentList(@Param("orgId") String orgId);

    @Select("SELECT COUNT(1) FROM tb_teacher_student_pair WHERE teacher_account = #{teacherAccount} AND pair_status = 'TRAINING'")
    int countTeacherTrain(@Param("teacherAccount") String teacherAccount);

    @Select("SELECT COUNT(1) FROM tb_teacher_student_pair WHERE student_account = #{studentAccount} AND pair_status = 'TRAINING'")
    int countStudentTrain(@Param("studentAccount") String studentAccount);
}