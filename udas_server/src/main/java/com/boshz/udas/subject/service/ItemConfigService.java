package com.boshz.udas.subject.service;


import com.boshz.udas.subject.entity.ItemConfig;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;

import java.util.List;

public interface ItemConfigService {
    int add(ItemConfig entity);
    int update(ItemConfig entity);
    int deleteById(Long id);
    ItemConfig getById(Long id);
    //全量/模糊下拉
    List<ItemConfig> listAll(String keyword);
    //按科目查下属项目
    List<ItemConfig> listBySubjectCode(String subjectCode);
    PageVo<List<ItemConfig>> pageList(QueryEntity<ItemConfig> queryEntity);
}