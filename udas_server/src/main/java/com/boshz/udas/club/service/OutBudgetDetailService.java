package com.boshz.udas.club.service;


import com.boshz.udas.club.entity.OutBudgetDetail;
import com.boshz.udas.club.entity.Voucher;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;

import java.util.List;

public interface OutBudgetDetailService {
    int add(OutBudgetDetail entity);
    int update(OutBudgetDetail entity);
    int delete(Long id);
    OutBudgetDetail getById(Long id);
    PageVo<List<OutBudgetDetail>> pageList(QueryEntity<OutBudgetDetail> query);
    void syncFromVoucher(Voucher voucher);
    void refreshTotal(String subjectCode,Integer year,Integer type);
}