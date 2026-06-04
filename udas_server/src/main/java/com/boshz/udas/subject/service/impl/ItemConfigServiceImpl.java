package com.boshz.udas.subject.service.impl;


import com.boshz.udas.subject.entity.ItemConfig;
import com.boshz.udas.subject.mapper.ItemConfigMapper;
import com.boshz.udas.subject.service.ItemConfigService;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ItemConfigServiceImpl implements ItemConfigService {

    @Resource
    private ItemConfigMapper itemConfigMapper;

    @Override
    public int add(ItemConfig entity) {
        return itemConfigMapper.insert(entity);
    }

    @Override
    public int update(ItemConfig entity) {
        return itemConfigMapper.update(entity);
    }

    @Override
    public int deleteById(Long id) {
        return itemConfigMapper.deleteById(id);
    }

    @Override
    public ItemConfig getById(Long id) {
        return itemConfigMapper.selectById(id);
    }

    @Override
    public List<ItemConfig> listAll(String keyword) {
        return itemConfigMapper.selectAll(keyword);
    }

    @Override
    public List<ItemConfig> listBySubjectCode(String subjectCode) {
        return itemConfigMapper.selectBySubjectCode(subjectCode);
    }

    @Override
    public PageVo<List<ItemConfig>> pageList(QueryEntity<ItemConfig> queryEntity) {
        ItemConfig query = queryEntity.getQuery();
        Integer current = queryEntity.getCurrent();
        Integer pageSize = queryEntity.getPageSize();
        int offset = (current - 1) * pageSize;
        List<ItemConfig> dataList = itemConfigMapper.selectPage(query, offset, pageSize);
        Long total = itemConfigMapper.selectCount(query);
        PageVo<List<ItemConfig>> vo = new PageVo<>();
        vo.setData(dataList);
        vo.setPage(current);
        vo.setSize(pageSize);
        vo.setTotal(total.intValue());
        return vo;
    }
}