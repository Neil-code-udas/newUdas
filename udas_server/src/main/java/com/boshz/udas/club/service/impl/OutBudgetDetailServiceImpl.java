package com.boshz.udas.club.service.impl;


import com.boshz.udas.club.entity.BudgetTotal;
import com.boshz.udas.club.entity.OutBudgetDetail;
import com.boshz.udas.club.entity.Voucher;
import com.boshz.udas.club.mapper.BudgetTotalMapper;
import com.boshz.udas.club.mapper.OutBudgetDetailMapper;
import com.boshz.udas.club.service.OutBudgetDetailService;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class OutBudgetDetailServiceImpl implements OutBudgetDetailService {
    @Resource
    private OutBudgetDetailMapper mapper;
    @Resource
    private BudgetTotalMapper totalMapper;

    @Override
    public int add(OutBudgetDetail entity) {
        int i = mapper.insert(entity);
        refreshTotal(entity.getSubjectCode(),entity.getBudgetYear(),2);
        return i;
    }

    @Override
    public int update(OutBudgetDetail entity) {
        int i = mapper.update(entity);
        refreshTotal(entity.getSubjectCode(),entity.getBudgetYear(),2);
        return i;
    }

    @Override
    public int delete(Long id) {
        OutBudgetDetail detail = mapper.selectById(id);
        int i = mapper.deleteById(id);
        refreshTotal(detail.getSubjectCode(),detail.getBudgetYear(),2);
        return i;
    }

    @Override
    public OutBudgetDetail getById(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public PageVo<List<OutBudgetDetail>> pageList(QueryEntity<OutBudgetDetail> queryEntity) {
        OutBudgetDetail q = queryEntity.getQuery();
        Integer curr = queryEntity.getCurrent();
        Integer size = queryEntity.getPageSize();
        int off = (curr-1)*size;
        List<OutBudgetDetail> list = mapper.selectPage(q,off,size);
        Long cnt = mapper.selectCount(q);
        PageVo<List<OutBudgetDetail>> vo = new PageVo<>();
        vo.setData(list);
        vo.setPage(curr);
        vo.setSize(size);
        vo.setTotal(cnt.intValue());
        return vo;
    }

    @Override
    public void syncFromVoucher(Voucher voucher) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(voucher.getOccurTime());
        Integer year = cal.get(Calendar.YEAR);
        OutBudgetDetail d = new OutBudgetDetail();
        d.setItemId(voucher.getItemId());
        d.setSubjectCode(voucher.getSubjectCode());
        d.setBudgetYear(year);
        d.setBudgetAmount(BigDecimal.ZERO);
        d.setActualAmount(voucher.getPayAmount());
        d.setCreateBy(voucher.getCreateBy());
        d.setRemark("凭证自动生成："+voucher.getVoucherNo());
        mapper.insert(d);
        refreshTotal(voucher.getSubjectCode(),year,2);
    }

    @Override
    public void refreshTotal(String subjectCode, Integer year, Integer type) {
        totalMapper.deleteByCond(subjectCode,year,type);
        Map<String,BigDecimal> map = totalMapper.sumOut(subjectCode,year);
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