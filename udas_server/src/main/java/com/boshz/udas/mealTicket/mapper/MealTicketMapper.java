package com.boshz.udas.mealTicket.mapper;

import com.boshz.udas.mealTicket.entity.MealTicket;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MealTicketMapper {

    MealTicket selectById(Long id);

    int deleteById(Long id);

    int insert(MealTicket record);

    int update(MealTicket record);

    List<MealTicket> selectByCondition(@Param("query") MealTicket param);

    /**
     * 分页查询
     */
    List<MealTicket> queryAllByLimit(@Param("query") MealTicket query,
                                     @Param("offset") Integer offset,
                                     @Param("pageSize") Integer pageSize);

    Long selectCount(@Param("query") MealTicket query);

    /**
     * 批量新增
     */
    int insertBatch(@Param("entities") List<MealTicket> entities);
}
