package com.boshz.udas.subject.mapper;


import com.boshz.udas.subject.dto.SubjectBudgetDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UnionBudgetMapper {
    List<SubjectBudgetDTO> selectSubjectBudgetList(@Param("yearList") List<Integer> yearList,
                                                   @Param("codeList") List<String> codeList,
                                                   @Param("subjectName") String subjectName);
}