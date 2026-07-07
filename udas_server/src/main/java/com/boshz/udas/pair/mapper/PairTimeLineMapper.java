package com.boshz.udas.pair.mapper;

import com.boshz.udas.pair.entity.PairTimeLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface PairTimeLineMapper {

    PairTimeLine selectById(Long id);

    int deleteById(Long id);

    int insert(PairTimeLine record);

    int update(PairTimeLine record);

    List<PairTimeLine> selectByCondition(@Param("query") PairTimeLine param);

    /**
     * 分页查询
     */
    List<PairTimeLine> queryAllByLimit(@Param("query") PairTimeLine query,
                                       @Param("offset") Integer offset,
                                       @Param("pageSize") Integer pageSize);

    Long selectCount(@Param("query") PairTimeLine query);

    /**
     * 批量新增
     */
    int insertBatch(@Param("entities") List<PairTimeLine> entities);

    List<PairTimeLine> selectByPairId(@Param("pairId") String pairId);
}
