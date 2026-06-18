package com.boshz.udas.club.mapper;

import com.boshz.udas.club.entity.ClubBillDetail;
import com.boshz.udas.club.vo.ClubSummaryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ClubBillDetailMapper {

    ClubBillDetail selectById(Long id);

    int deleteById(Long id);

    int insert(ClubBillDetail record);

    int update(ClubBillDetail record);

    List<ClubBillDetail> selectByCondition(@Param("param") ClubBillDetail param);

    /**
     * 分页查询
     */
    List<ClubBillDetail> queryAllByLimit(@Param("query") ClubBillDetail query,
                                         @Param("offset") Integer offset,
                                         @Param("pageSize") Integer pageSize);

    Long selectCount(@Param("query") ClubBillDetail query);

    /**
     * 批量新增
     */
    int insertBatch(@Param("entities") List<ClubBillDetail> entities);

    List<ClubSummaryVO> selectClubSummary();
}
