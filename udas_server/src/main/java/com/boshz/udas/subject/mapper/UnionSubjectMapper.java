package com.boshz.udas.subject.mapper;

import com.boshz.udas.subject.entity.UnionSubject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UnionSubjectMapper {
    //原有2个（预决算递归用）
    List<String> selectDirectChildCode(@Param("parentCode") String parentCode);
    List<String> selectCodeByCode(@Param("code") String code);
    //原有下拉模糊
    List<UnionSubject> selectSubjectLike(@Param("keyword") String keyword);

    //新增CRUD
    int insert(UnionSubject entity);
    int update(UnionSubject entity);
    int deleteById(@Param("id")Long id);
    UnionSubject selectById(@Param("id")Long id);
    //分页数据
    List<UnionSubject> selectPage(@Param("query") UnionSubject query,
                                  @Param("offset") int offset,
                                  @Param("pageSize") int pageSize);
    //总条数
    Long selectCount(@Param("query") UnionSubject query);}