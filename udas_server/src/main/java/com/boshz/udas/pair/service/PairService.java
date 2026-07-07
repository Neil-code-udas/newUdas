package com.boshz.udas.pair.service;


import com.boshz.udas.pair.dto.PairCreateDTO;
import com.boshz.udas.pair.dto.PairQueryDTO;
import com.boshz.udas.pair.dto.StaffDTO;
import com.boshz.udas.pair.entity.TeacherStudentPair;
import com.boshz.udas.vo.ResultVO;

import java.util.List;

public interface PairService {
    List<StaffDTO> getTeacherList(String orgId);
    List<StaffDTO> getStudentList(String orgId);
    List<TeacherStudentPair> getPairList(PairQueryDTO query);
    ResultVO createPair(PairCreateDTO dto);
    ResultVO getPairDetail(String pairId);
}