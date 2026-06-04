package com.boshz.udas.subject.mapper;


import com.boshz.udas.subject.entity.ItemConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItemConfigMapper {
    int insert(ItemConfig entity);
    int update(ItemConfig entity);
    int deleteById(@Param("id")Long id);
    ItemConfig selectById(@Param("id")Long id);
    List<ItemConfig> selectAll(@Param("keyword")String keyword);
    List<ItemConfig> selectBySubjectCode(@Param("subjectCode")String subjectCode);
    List<ItemConfig> selectPage(@Param("query") ItemConfig query, @Param("offset") int offset, @Param("pageSize") int pageSize);
    Long selectCount(@Param("query") ItemConfig query);}