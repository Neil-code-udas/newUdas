package com.boshz.udas.subject.service.impl;


import com.boshz.udas.subject.entity.UnionSubject;
import com.boshz.udas.subject.mapper.UnionSubjectMapper;
import com.boshz.udas.subject.service.SubjectService;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Resource
    private UnionSubjectMapper unionSubjectMapper;

    @Override
    public List<UnionSubject> listLike(String keyword) {
        return unionSubjectMapper.selectSubjectLike(keyword);
    }

    @Override
    public PageVo<List<UnionSubject>> pageList(QueryEntity<UnionSubject> queryEntity) {
        UnionSubject query = queryEntity.getQuery();
        Integer current = queryEntity.getCurrent();
        Integer pageSize = queryEntity.getPageSize();
        int offset = (current - 1) * pageSize;
        List<UnionSubject> list = unionSubjectMapper.selectPage(query, offset, pageSize);
        Long total = unionSubjectMapper.selectCount(query);
        PageVo<List<UnionSubject>> pageVo = new PageVo<>();
        pageVo.setData(list);
        pageVo.setPage(current);
        pageVo.setSize(pageSize);
        pageVo.setTotal(total.intValue());
        return pageVo;
    }

    @Override
    public int add(UnionSubject entity) {
        return unionSubjectMapper.insert(entity);
    }

    @Override
    public int update(UnionSubject entity) {
        return unionSubjectMapper.update(entity);
    }

    @Override
    public int deleteById(Long id) {
        return unionSubjectMapper.deleteById(id);
    }

    @Override
    public UnionSubject getById(Long id) {
        return unionSubjectMapper.selectById(id);
    }
}