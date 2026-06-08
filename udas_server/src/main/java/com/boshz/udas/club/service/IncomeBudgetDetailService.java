package com.boshz.udas.club.service;


import com.boshz.udas.club.entity.IncomeBudgetDetail;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;

import java.util.List;

public interface IncomeBudgetDetailService {
    int add(IncomeBudgetDetail entity);
    int update(IncomeBudgetDetail entity);
    int delete(Long id);
    IncomeBudgetDetail getById(Long id);
    PageVo<List<IncomeBudgetDetail>> pageList(QueryEntity<IncomeBudgetDetail> query);
    void refreshTotal(String subjectCode,Integer year,Integer type);
}