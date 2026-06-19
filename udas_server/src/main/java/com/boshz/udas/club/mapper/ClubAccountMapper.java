package com.boshz.udas.club.mapper;

import com.boshz.udas.club.vo.ClubItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface ClubAccountMapper {
    /**
     * 根据年度查询所有流水明细
     */
    List<Map<String, Object>> selectAllDetailByYear(@Param("year") String year);

    /**
     * 根据年度查询各俱乐部汇总统计（结余、各类小计、场地费单独统计）
     */
    List<ClubItemVO> selectClubSumByYear(@Param("year") String year);
}