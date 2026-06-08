package com.boshz.udas.club.service;


import com.boshz.udas.club.entity.BudgetTotal;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;

import java.util.List;

public interface BudgetTotalService {
    PageVo<List<BudgetTotal>> pageList(QueryEntity<BudgetTotal> query);
}