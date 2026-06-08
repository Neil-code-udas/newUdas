package com.boshz.udas.club.mapper;


import com.boshz.udas.club.entity.OutBudgetDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface OutBudgetDetailMapper {
    int insert(OutBudgetDetail entity);
    int update(OutBudgetDetail entity);
    int deleteById(@Param("id") Long id);
    OutBudgetDetail selectById(@Param("id") Long id);
    List<OutBudgetDetail> selectPage(@Param("query") OutBudgetDetail query, @Param("offset")int offset, @Param("pageSize")int pageSize);
    Long selectCount(@Param("query") OutBudgetDetail query);
}