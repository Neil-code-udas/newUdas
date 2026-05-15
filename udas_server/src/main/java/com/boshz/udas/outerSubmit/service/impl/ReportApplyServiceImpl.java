package com.boshz.udas.outerSubmit.service.impl;

import com.boshz.udas.report.entity.ReportApply;
import com.boshz.udas.report.mapper.ReportApplyMapper;
import com.boshz.udas.report.service.ReportApplyService;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportApplyServiceImpl implements ReportApplyService {

    private final ReportApplyMapper reportApplyMapper;

    @Override
    public void initTable() {
        reportApplyMapper.createTable();
    }

    @Override
    public PageVo<List<ReportApply>> page(QueryEntity<?> q) {
        int current = q.getCurrent() == null ? 1 : q.getCurrent();
        int size = q.getPageSize() == null ? 10 : q.getPageSize();
        long offset = (long) (current - 1) * size;

        Map<String, Object> searchCondition = q.getSearchCondition();
        List<ReportApply> list = reportApplyMapper.selectPage(offset, size, searchCondition);
        long total = reportApplyMapper.selectCount(searchCondition);

        PageVo<List<ReportApply>> pageVo = new PageVo<>();
        pageVo.setData(list);
        pageVo.setPage(current);
        pageVo.setSize(size);
        pageVo.setTotal((int) total);
        pageVo.setMessage("查询成功");
        return pageVo;
    }

    @Override
    public ReportApply getById(Long id) {
        return reportApplyMapper.selectById(id);
    }

    @Override
    public int add(ReportApply reportApply) {
        return reportApplyMapper.insert(reportApply);
    }

    @Override
    public int update(ReportApply reportApply) {
        return reportApplyMapper.update(reportApply);
    }

    @Override
    public int delete(Long id) {
        return reportApplyMapper.deleteById(id);
    }
}