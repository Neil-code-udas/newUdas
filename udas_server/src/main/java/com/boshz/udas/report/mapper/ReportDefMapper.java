package com.boshz.udas.report.mapper;


import com.boshz.udas.report.entity.Menu;
import com.boshz.udas.report.entity.ReportDef;
import com.boshz.udas.report.query.MenuQuery;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReportDefMapper {
    List<ReportDef> selectAll();
    ReportDef selectByCode(String reportCode);

    int insert(ReportDef reportDef);

    int countByCodePrefix(@Param("prefix") String prefix);

    Menu findByPath(@Param("path") String path);

    PageVo getListByPage(QueryEntity<MenuQuery> queryEntity, String account);

    List<ReportDef> getList(@Param("query")MenuQuery query, @Param("begin")Integer begin, @Param("pageSize")Integer pageSize,@Param("account")String account);

    Integer getListTotal(@Param("query")MenuQuery query,@Param("account")String account);

    void updateStatus(@Param("id") Long id, @Param("status") Integer status);}
