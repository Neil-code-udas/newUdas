package com.boshz.udas.report.service.impl;

import com.boshz.udas.report.entity.ReportDef;
import com.boshz.udas.report.mapper.ReportColumnMapper;
import com.boshz.udas.report.mapper.ReportCommonMapper;
import com.boshz.udas.report.mapper.ReportDefMapper;
import com.boshz.udas.report.query.MenuQuery;
import com.boshz.udas.report.service.ReportDefService;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ReportDefServiceImpl implements ReportDefService {

    private final ReportDefMapper reportDefMapper;
    private final ReportColumnMapper reportColumnMapper;
    private final ReportCommonMapper reportCommonMapper;


    @Override
    public PageVo getListByPage(QueryEntity<MenuQuery> queryEntity, String account) {
        Integer current = queryEntity.getCurrent();
        Integer pageSize = queryEntity.getPageSize();
        if (current == null) {
            current = 1;
            pageSize = 10;
        }
        Integer begin = (current - 1) * pageSize;
        MenuQuery query = queryEntity.getQuery();
        if (query == null) {
            query = new MenuQuery();
        }
        log.info("筛选项信息：{} ", query);
        List<ReportDef> list = reportDefMapper.getList(query, begin, pageSize,account);
        Integer total = reportDefMapper.getListTotal(query,account);
        log.info("共查到 {} 条数据", total);
        PageVo<Object> pageVo = new PageVo<>();
        pageVo.setSize(pageSize);
        pageVo.setPage(current);
        pageVo.setData(list);
        pageVo.setTotal(total);
        return pageVo;
    }
}
