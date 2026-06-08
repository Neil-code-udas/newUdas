package com.boshz.udas.club.mapper;


import com.boshz.udas.club.entity.BudgetTotal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface BudgetTotalMapper {
    List<BudgetTotal> selectPage(@Param("query") BudgetTotal query, @Param("offset")int offset, @Param("pageSize")int pageSize);
    Long selectCount(@Param("query") BudgetTotal query);
    int deleteByCond(@Param("code")String code,@Param("year")Integer year,@Param("type")Integer type);
    Map<String, BigDecimal> sumIncome(@Param("code")String code, @Param("year")Integer year);
    Map<String,BigDecimal> sumOut(@Param("code")String code,@Param("year")Integer year);
    int insertTotal(BudgetTotal total);
}