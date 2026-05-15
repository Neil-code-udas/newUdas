package com.boshz.udas.report.service.impl;

import com.boshz.udas.report.entity.ReportDef;
import com.boshz.udas.report.mapper.ReportDefMapper;
import com.boshz.udas.report.service.ReportDefService;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportDefServiceImpl implements ReportDefService {

    private final ReportDefMapper reportDefMapper;

    @Override
    public PageVo getListByPage(QueryEntity<ReportDef> queryEntity, String account) {
        Integer current = queryEntity.getCurrent();
        Integer pageSize = queryEntity.getPageSize();
        ReportDef query = queryEntity.getQuery();

        String reportName = query == null ? null : query.getReportName();

        int offset = (current - 1) * pageSize;
        List<ReportDef> list = reportDefMapper.selectReportList(reportName, offset, pageSize);
        int total = reportDefMapper.selectReportCount(reportName);

        PageVo pageVo = new PageVo();
        pageVo.setData(list);
        pageVo.setPage(current);
        pageVo.setSize(pageSize);
        pageVo.setTotal(total);

        return pageVo;
    }
}