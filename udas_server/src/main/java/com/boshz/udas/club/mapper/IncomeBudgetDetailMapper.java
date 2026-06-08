package com.boshz.udas.club.mapper;


import com.boshz.udas.club.entity.IncomeBudgetDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface IncomeBudgetDetailMapper {
    int insert(IncomeBudgetDetail entity);
    int update(IncomeBudgetDetail entity);
    int deleteById(@Param("id") Long id);
    IncomeBudgetDetail selectById(@Param("id") Long id);
    List<IncomeBudgetDetail> selectPage(@Param("query") IncomeBudgetDetail query, @Param("offset")int offset, @Param("pageSize")int pageSize);
    Long selectCount(@Param("query") IncomeBudgetDetail query);
}