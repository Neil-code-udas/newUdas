package com.boshz.udas.club.mapper;

import com.boshz.udas.club.entity.Club;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ClubMapper {

    Club selectById(Long id);

    int deleteById(Long id);

    int insert(Club record);

    int update(Club record);

    List<Club> selectByCondition(@Param("param") Club param);

    /**
     * 分页查询
     */
    List<Club> queryAllByLimit(@Param("query") Club query,
                               @Param("offset") Integer offset,
                               @Param("pageSize") Integer pageSize);

    Long selectCount(@Param("query") Club query);

    /**
     * 批量新增
     */
    int insertBatch(@Param("entities") List<Club> entities);
}
