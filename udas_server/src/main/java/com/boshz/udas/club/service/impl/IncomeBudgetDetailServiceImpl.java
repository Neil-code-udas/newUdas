package com.boshz.udas.club.service.impl;


import com.boshz.udas.club.entity.BudgetTotal;
import com.boshz.udas.club.entity.IncomeBudgetDetail;
import com.boshz.udas.club.mapper.BudgetTotalMapper;
import com.boshz.udas.club.mapper.IncomeBudgetDetailMapper;
import com.boshz.udas.club.service.IncomeBudgetDetailService;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class IncomeBudgetDetailServiceImpl implements IncomeBudgetDetailService {
    @Resource
    private IncomeBudgetDetailMapper mapper;
    @Resource
    private BudgetTotalMapper totalMapper;

    @Override
    public int add(IncomeBudgetDetail entity) {
        int i = mapper.insert(entity);
        refreshTotal(entity.getSubjectCode(),entity.getBudgetYear(),1);
        return i;
    }

    @Override
    public int update(IncomeBudgetDetail entity) {
        int i = mapper.update(entity);
        refreshTotal(entity.getSubjectCode(),entity.getBudgetYear(),1);
        return i;
    }

    @Override
    public int delete(Long id) {
        IncomeBudgetDetail detail = mapper.selectById(id);
        int i = mapper.deleteById(id);
        refreshTotal(detail.getSubjectCode(),detail.getBudgetYear(),1);
        return i;
    }

    @Override
    public IncomeBudgetDetail getById(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public PageVo<List<IncomeBudgetDetail>> pageList(QueryEntity<IncomeBudgetDetail> queryEntity) {
        IncomeBudgetDetail q = queryEntity.getQuery();
        Integer curr = queryEntity.getCurrent();
        Integer size = queryEntity.getPageSize();
        int off = (curr-1)*size;
        List<IncomeBudgetDetail> list = mapper.selectPage(q,off,size);
        Long cnt = mapper.selectCount(q);
        PageVo<List<IncomeBudgetDetail>> vo = new PageVo<>();
        vo.setData(list);
        vo.setPage(curr);
        vo.setSize(size);
        vo.setTotal(cnt.intValue());
        return vo;
    }

    @Override
    public void refreshTotal(String subjectCode, Integer year, Integer type) {
        totalMapper.deleteByCond(subjectCode,year,type);
        Map<String, BigDecimal> map = totalMapper.sumIncome(subjectCode,year);
        BigDecimal bud = map.get("totalBudget");
        BigDecimal act = map.get("totalActual");
        if(bud.compareTo(BigDecimal.ZERO)!=0 || act.compareTo(BigDecimal.ZERO)!=0){
            BudgetTotal t = new BudgetTotal();
            t.setSubjectCode(subjectCode);
            t.setBudgetYear(year);
            t.setInOutType(type);
            t.setBudgetAmount(bud);
            t.setActualAmount(act);
            totalMapper.insertTotal(t);
        }
    }
}