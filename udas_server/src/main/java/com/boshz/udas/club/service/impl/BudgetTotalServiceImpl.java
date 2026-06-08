package com.boshz.udas.club.service.impl;


import com.boshz.udas.club.entity.BudgetTotal;
import com.boshz.udas.club.mapper.BudgetTotalMapper;
import com.boshz.udas.club.service.BudgetTotalService;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BudgetTotalServiceImpl implements BudgetTotalService {
    @Resource
    private BudgetTotalMapper mapper;

    @Override
    public PageVo<List<BudgetTotal>> pageList(QueryEntity<BudgetTotal> queryEntity) {
        BudgetTotal q = queryEntity.getQuery();
        Integer curr = queryEntity.getCurrent();
        Integer size = queryEntity.getPageSize();
        int off = (curr-1)*size;
        List<BudgetTotal> list = mapper.selectPage(q,off,size);
        Long cnt = mapper.selectCount(q);
        PageVo<List<BudgetTotal>> vo = new PageVo<>();
        vo.setData(list);
        vo.setPage(curr);
        vo.setSize(size);
        vo.setTotal(cnt.intValue());
        return vo;
    }
}