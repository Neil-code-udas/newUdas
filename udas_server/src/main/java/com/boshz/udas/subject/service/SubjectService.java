package com.boshz.udas.subject.service;

import com.boshz.udas.subject.entity.UnionSubject;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;

import java.util.List;

public interface SubjectService {
    List<UnionSubject> listLike(String keyword);

    //分页：入参通用QueryEntity，出参PageVo
    PageVo<List<UnionSubject>> pageList(QueryEntity<UnionSubject> queryEntity);

    int add(UnionSubject entity);
    int update(UnionSubject entity);
    int deleteById(Long id);
    UnionSubject getById(Long id);
}